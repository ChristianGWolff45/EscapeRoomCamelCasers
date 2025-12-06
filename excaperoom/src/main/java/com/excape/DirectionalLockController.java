package com.excape;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.EscapeRoom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * Controller for the directional lock screen.
 * Matches fx:id and onAction handlers used in Lock.fxml.
 */
public class DirectionalLockController implements Initializable{

    @FXML private Button upButton;
    @FXML private Button downButton;
    @FXML private Button leftButton;
    @FXML private Button rightButton;
    @FXML private Button enterButton;
    @FXML private Button exitButton;
    @FXML private Button hintButton;
    @FXML private Text sequenceText;
    


    // internal representation of the entered sequence (using arrow chars)
    private final StringBuilder entered = new StringBuilder();

    // maximum number of arrow entries shown/allowed
    private final int MAX_LENGTH = 12;

    private final String PuzzleID = "directionalPuzzle";

    EscapeRoom escapeRoom = new EscapeRoom();

    public DirectionalLockController() {
        // required public no-arg constructor
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ensure display starts empty
        sequenceText.setText("");
    }

    // Arrow button handlers - append symbol and update displayed text
    @FXML
    private void pressedUp() {
        appendSymbol("↑");
    }

    @FXML
    private void pressedDown() {
        appendSymbol("↓");
    }

    @FXML
    private void pressedLeft() {
        appendSymbol("←");
    }

    @FXML
    private void pressedRight() {
        appendSymbol("→");
    }

    // Enter: validate sequence against secret
    @FXML
    private void pressedEnter() {
        String current = entered.toString();

        // Show raw submission in console for debugging
        System.out.println("DirectionalLock: raw entered = [" + current + "]");

        // Print codepoints so we can spot different arrow chars
        StringBuilder cps = new StringBuilder();
        current.codePoints().forEach(cp -> cps.append(String.format("U+%04X ", cp)));
        System.out.println("DirectionalLock: codepoints = " + cps.toString());

        // Normalize (NFC) and trim whitespace / control chars just in case
        String normalized = java.text.Normalizer.normalize(current, java.text.Normalizer.Form.NFC).trim();
        System.out.println("DirectionalLock: normalized = [" + normalized + "]");

        // Create ASCII mapping: → = R, ← = L, ↑ = U, ↓ = D
        StringBuilder ascii = new StringBuilder();
        normalized.codePoints().forEach(cp -> {
            switch (cp) {
                case 0x2192: // →
                case 0x27A1: // ➡ (just in case)
                    ascii.append('R'); break;
                case 0x2190: // ←
                case 0x2B05: // ⬅
                    ascii.append('L'); break;
                case 0x2191: // ↑
                case 0x2B06: // ⬆
                    ascii.append('U'); break;
                case 0x2193: // ↓
                case 0x2B07: // ⬇
                    ascii.append('D'); break;
                default:
                    // preserve other chars (or ignore)
                    ascii.append((char) cp);
            }
        });

        String asciiStr = ascii.toString();
        System.out.println("DirectionalLock: ascii mapping = [" + asciiStr + "]");

        // Show a small debug popup so you can copy the submission if needed
        

        // Try solving using the raw normalized arrow sequence first
        escapeRoom.solvePuzzle(PuzzleID, normalized);
        if (escapeRoom.puzzleUnlocked(PuzzleID)) {
            onUnlock();
            return;
        }

        // If that failed, try solving using the ASCII letter mapping
        escapeRoom.solvePuzzle(PuzzleID, asciiStr);
        if (escapeRoom.puzzleUnlocked(PuzzleID)) {
            onUnlock();
            return;
        }

        // If both failed, show incorrect and clear
        showAlert(AlertType.ERROR, "Incorrect", "That sequence is incorrect.");
        clearSequence();
    }

    /** helper to run when puzzle is unlocked */
    private void onUnlock() {
        showAlert(AlertType.INFORMATION, "Unlocked!", "The lock clicks open — correct sequence!");
        setButtonsDisabled(true);
        try {
            App.setRoot("Certificate");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load Certificate.fxml");
        }
    }


    // Exit: closes the window (bound to Exit button). Accepts ActionEvent so we can close the stage.
    @FXML
    private void pressedExit(ActionEvent event) {
        // Close the window that contains the Exit button
        try {
            App.setRoot("Room4");   
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load Room1.fxml");
        }
    }

    // Hint: show a small hint dialog
    @FXML
    private void pressedHint() {
        escapeRoom.hearHint("directionalPuzzle_Hint1");
        escapeRoom.useHint("directionalPuzzle_Hint1");
        showAlert(AlertType.INFORMATION, "Hint", escapeRoom.useHint("directionalPuzzle_Hint1"));
    }

    // Helper: append a symbol, enforce max length, update UI
    private void appendSymbol(String symbol) {
        // if already disabled, ignore
        if (enterButton.isDisable() && upButton.isDisable()) return;

        // Enforce max number of symbols
        if (entered.length() >= MAX_LENGTH * 1 /* each symbol is one char here */) {
            // simple behavior: rotate-out oldest symbol to make room
            // remove first symbol (assumes symbols are single chars)
            entered.deleteCharAt(0);
        }

        entered.append(symbol);
        sequenceText.setText(entered.toString());
    }

    // Optionally clear sequence (call this if you want a clear button or on failure)
    private void clearSequence() {
        entered.setLength(0);
        sequenceText.setText("");
    }

    // Convenience: show alerts
    private void showAlert(AlertType type, String title, String message) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }

    // Enable/disable input buttons (used after success)
    private void setButtonsDisabled(boolean disabled) {
        upButton.setDisable(disabled);
        downButton.setDisable(disabled);
        leftButton.setDisable(disabled);
        rightButton.setDisable(disabled);
        enterButton.setDisable(disabled);
        hintButton.setDisable(disabled);
    }
}
