package com.excape;

import com.model.EscapeRoom;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Phone puzzle controller — multi-tap keypad that decodes digits to letters and submits a guess.
 * Integrates with EscapeRoom to mark puzzle as solved in the same way as your other puzzles.
 */
public class PhoneController {

    @FXML private Text numberDisplay;        // fx:id in FXML for showing raw digits / final word
    @FXML private Button exitButton;         // fx:id in FXML (optional)
    @FXML private GridPane keypadGrid;       // fx:id in FXML (optional, defensive attachments)

    // Optional: if you have a hint button, add an @FXML field for it so we can disable it on success.
    @FXML private Button hintButton;         // optional; if not present in FXML, leave it null

    // typed raw digits buffer (keeps repeats for multi-tap)
    private final StringBuilder typed = new StringBuilder();

    // mapping digit -> letters for multi-tap decoding
    private final Map<Character, String> digitToLetters = new HashMap<>();

    // Puzzle integration
    private final String PuzzleID = "phonePuzzle";
    private final EscapeRoom escapeRoom = new EscapeRoom();

    public PhoneController() {
        digitToLetters.put('0', " ");
        digitToLetters.put('1', "");
        digitToLetters.put('2', "ABC");
        digitToLetters.put('3', "DEF");
        digitToLetters.put('4', "GHI");
        digitToLetters.put('5', "JKL");
        digitToLetters.put('6', "MNO");
        digitToLetters.put('7', "PQRS");
        digitToLetters.put('8', "TUV");
        digitToLetters.put('9', "WXYZ");
    }

    @FXML
    private void initialize() {
        // ensure display starts empty and base display class is present
        if (numberDisplay != null) {
            numberDisplay.setText("");
            // ensure default display class is present so CSS default color applies
            if (!numberDisplay.getStyleClass().contains("display-text")) {
                numberDisplay.getStyleClass().add("display-text");
            }
        }

        // Attach defensive handlers once the scene graph is realized
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                attachButtonHandlersIfMissing();
            }
        });
    }

    private void attachButtonHandlersIfMissing() {
        if (keypadGrid == null) {
            System.out.println("PhoneController: keypadGrid is null (no defensive attachment).");
            return;
        }

        for (Node node : keypadGrid.getChildren()) {
            if (node instanceof Button) {
                final Button b = (Button) node;

                // If no onAction was wired in FXML, attach our handler
                if (b.getOnAction() == null) {
                    b.setOnAction(event -> handleNumberClick(event));
                }

                // Add a mouse-click debug print for visibility (optional)
                b.setOnMouseClicked(evt -> {
                    Object ud = b.getUserData();
                    String label = (ud != null) ? ud.toString() : b.getText();
                    System.out.println("DEBUG: Button mouseClicked -> userData=" + ud + " text=" + b.getText()
                            + " label=" + label);
                });
            }
        }

        System.out.println("PhoneController: attached handlers to keypad buttons (defensive).");
    }

    // ------------------- button handlers -------------------

    @FXML
    private void handleNumberClick(ActionEvent event) {
        if (!(event.getSource() instanceof Button)) return;
        Button btn = (Button) event.getSource();

        String digitStr = getDigitFromButton(btn);
        if (digitStr == null || digitStr.isEmpty()) return;

        char d = digitStr.charAt(0);
        if (!Character.isDigit(d)) return;

        typed.append(d);

        // Show raw digits while typing and clear any previous result styling
        if (numberDisplay != null) {
            numberDisplay.setText(typed.toString());
            numberDisplay.getStyleClass().remove("number-correct");
            numberDisplay.getStyleClass().remove("number-wrong");
        }

        System.out.println("handleNumberClick: appended digit " + d + " (raw buffer: " + typed + ")");
    }

    @FXML
    private void handleClear(ActionEvent event) {
        typed.setLength(0);
        if (numberDisplay != null) numberDisplay.setText("");
        System.out.println("handleClear: cleared buffer");
    }

    @FXML
    private void handleBackspace(ActionEvent event) {
        if (typed.length() > 0) {
            typed.deleteCharAt(typed.length() - 1);
            if (numberDisplay != null) numberDisplay.setText(typed.toString());
            System.out.println("handleBackspace: buffer now " + typed);
        }
    }

    @FXML
    private void handleCall(ActionEvent event) {
        String raw = typed.toString();
        if (raw.isEmpty()) {
            System.out.println("handleCall: nothing to submit");
            return;
        }

        String decoded = decodeMultiTap(raw);

        if (numberDisplay != null) {
            // show decoded word
            numberDisplay.setText(decoded);

            // reset classes
            numberDisplay.getStyleClass().remove("number-correct");
            numberDisplay.getStyleClass().remove("number-wrong");
        }

        // Use centralized helper to evaluate guess (it will call solvePuzzle & check unlocked)
        boolean correct = isCorrectGuess(decoded);

        if (numberDisplay != null) {
            if (correct) numberDisplay.getStyleClass().add("number-correct");
            else numberDisplay.getStyleClass().add("number-wrong");

            System.out.println("handleCall: classes now = " + numberDisplay.getStyleClass());
        }

        if (correct) {
            showAlert(AlertType.INFORMATION, "Unlocked!", "The puzzle is solved — well done!");
            setButtonsDisabled(true);
        } else {
            showAlert(AlertType.ERROR, "Incorrect", "That guess is incorrect.");
        }

        // clear buffer for next attempt
        typed.setLength(0);
    }

    /**
    * Minimal helper that asks the EscapeRoom backend whether the given decoded word
    * solves the puzzle. It will call solvePuzzle(...) to let the backend evaluate it,
    * then return the unlocked state.
    */
    private boolean isCorrectGuess(String decodedWord) {
        if (decodedWord == null) return false;
        // normalize (adjust depending on backend expectations)
        String guess = decodedWord.trim();

        // let backend evaluate the guess (some backends expect specific casing)
        escapeRoom.solvePuzzle(PuzzleID, guess);

        // return whether the puzzle is now unlocked
        return escapeRoom.puzzleUnlocked(PuzzleID);
    }


    // ------------------- multi-tap decoder -------------------

    private String decodeMultiTap(String digits) {
        if (digits == null || digits.isEmpty()) return "";

        StringBuilder out = new StringBuilder();
        int i = 0, n = digits.length();
        while (i < n) {
            char cur = digits.charAt(i);
            int j = i + 1;
            while (j < n && digits.charAt(j) == cur) j++;
            int count = j - i;

            String letters = digitToLetters.getOrDefault(cur, "");
            if (letters != null && !letters.isEmpty()) {
                int idx = (count - 1) % letters.length();
                out.append(letters.charAt(idx));
            }
            i = j;
        }

        return out.toString().toLowerCase();
    }

    // ------------------- digit extraction -------------------

    private String getDigitFromButton(Button btn) {
        Object ud = btn.getUserData();
        if (ud != null && !ud.toString().isEmpty()) {
            return ud.toString();
        }

        if (btn.getGraphic() instanceof Parent) {
            Parent root = (Parent) btn.getGraphic();

            try {
                Node found = root.lookup(".digit-text");
                if (found instanceof Text) {
                    String t = ((Text) found).getText();
                    if (t != null && !t.isEmpty()) return t.substring(0, 1);
                }
            } catch (Exception ignored) {}

            String foundText = findTextInNode(root);
            if (foundText != null) return foundText;
        }

        String bt = btn.getText();
        if (bt != null && !bt.isEmpty()) return bt.substring(0, 1);

        return null;
    }

    private String findTextInNode(Node node) {
        if (node instanceof Text) {
            String t = ((Text) node).getText();
            if (t != null && !t.isEmpty()) return t.substring(0, 1);
            return null;
        }
        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                String res = findTextInNode(child);
                if (res != null) return res;
            }
        }
        return null;
    }

    // ------------------- helpers: alerts & disabling -------------------

    private void showAlert(AlertType type, String title, String message) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }

    private void setButtonsDisabled(boolean disabled) {
        // disable all buttons in keypadGrid
        if (keypadGrid != null) {
            for (Node node : keypadGrid.getChildren()) {
                if (node instanceof Button) {
                    ((Button) node).setDisable(disabled);
                }
            }
        }

        // disable exit/hint if present
        if (exitButton != null) exitButton.setDisable(disabled);
        if (hintButton != null) hintButton.setDisable(disabled);
    }

    // ------------------- exit / navigator -------------------

    public interface Navigator { void goBack(); }
    private Navigator navigator;
    public void setNavigator(Navigator nav) { this.navigator = nav; }

    @FXML
    private void handleExit(ActionEvent event) {
        if (navigator != null) {
            navigator.goBack();
            return;
        }

        Object src = event.getSource();
        if (src instanceof Node) {
            Node n = (Node) src;
            Stage stage = (Stage) n.getScene().getWindow();
            if (stage != null) stage.close();
        }
    }
}
