package com.excape;

import com.model.EscapeRoom;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Multiple-choice puzzle controller.
 * Adds stylesheets at runtime to avoid FXML parsing differences across JavaFX versions.
 */
public class MultipleChoiceController {

    @FXML private Text questionDisplay;
    @FXML private Text selectionDisplay;
    @FXML private GridPane choicesGrid;
    @FXML private Button exitButton;
    @FXML private Button hintButton; // optional; may be null if not in FXML

    // Puzzle integration
    private final String PuzzleID = "mcPuzzle";
    private final EscapeRoom escapeRoom = new EscapeRoom();

    // Hard-coded question + choices
    private final String question = "How many USC Undergrad Students are there?";
    private final String[] choices = new String[] {
            "26,000",    // index 0 (top-left, A)
            "42,000",   // index 1 (top-right, B)
            "35,000 (correct)",// index 2 (bottom-left, C) <-- CORRECT
            "21,000"     // index 3 (bottom-right, D)
    };

    private int selectedIndex = -1;
    // private final int correctIndex = 2;

    public MultipleChoiceController() { }

    @FXML
    private void initialize() {
        if (questionDisplay != null) questionDisplay.setText(question);
        if (selectionDisplay != null) selectionDisplay.setText("");

        // Attach everything after scene is available to ensure stylesheet addition works.
        Platform.runLater(() -> {
            attachButtonHandlersIfMissing();
            populateChoiceTexts();
            attachStylesheetsSafely();
        });
    }

    /**
     * Adds the two CSS files to the Scene stylesheets. Waits for scene to be available if necessary.
     */
    private void attachStylesheetsSafely() {
        if (questionDisplay == null) return;

        Scene scene = questionDisplay.getScene();
        if (scene != null) {
            addCssIfFound(scene);
            return;
        }

        // If scene is not yet set, listen for it
        questionDisplay.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> obs, Scene oldScene, Scene newScene) {
                if (newScene != null) {
                    addCssIfFound(newScene);
                    // remove listener after first attach
                    questionDisplay.sceneProperty().removeListener(this);
                }
            }
        });
    }

    private void addCssIfFound(Scene scene) {
        try {
            URL def = getClass().getResource("multipleChoice-default.css");
            URL main = getClass().getResource("multipleChoice.css");
            if (def != null) scene.getStylesheets().add(def.toExternalForm());
            if (main != null) scene.getStylesheets().add(main.toExternalForm());
            System.out.println("MultipleChoiceController: added stylesheets -> " + def + ", " + main);
        } catch (Exception ex) {
            System.err.println("MultipleChoiceController: failed to add stylesheets: " + ex.getMessage());
        }
    }

    // ------------------- button wiring -------------------

    private void attachButtonHandlersIfMissing() {
        if (choicesGrid == null) {
            System.out.println("MultipleChoiceController: choicesGrid is null (no defensive attachment).");
            return;
        }

        for (Node node : choicesGrid.getChildren()) {
            if (node instanceof Button) {
                final Button b = (Button) node;

                if (b.getOnAction() == null) {
                    b.setOnAction(event -> handleChoiceClick(event));
                }

                b.setOnMouseClicked(evt -> {
                    Object ud = b.getUserData();
                    String label = (ud != null) ? ud.toString() : b.getText();
                    System.out.println("DEBUG: Choice button clicked -> userData=" + ud + " text=" + b.getText()
                            + " label=" + label);
                });
            }
        }
    }

    private void populateChoiceTexts() {
        if (choicesGrid == null) return;

        for (Node node : choicesGrid.getChildren()) {
            if (!(node instanceof Button)) continue;
            Button b = (Button) node;
            Object ud = b.getUserData();
            if (ud == null) continue;
            String udStr = ud.toString();
            try {
                int idx = Integer.parseInt(udStr);
                if (idx >= 0 && idx < choices.length) {
                    if (b.getGraphic() instanceof Parent) {
                        Parent root = (Parent) b.getGraphic();
                        Node found = root.lookup(".letters-text");
                        if (found instanceof Text) {
                            ((Text) found).setText(choices[idx]);
                        } else {
                            b.setText(choices[idx]);
                        }
                    } else {
                        b.setText(choices[idx]);
                    }
                }
            } catch (NumberFormatException ignored) {}
        }
    }

    // ------------------- handlers -------------------

    @FXML
    private void handleChoiceClick(ActionEvent event) {
        if (!(event.getSource() instanceof Button)) return;
        Button btn = (Button) event.getSource();

        Object ud = btn.getUserData();
        int idx = -1;
        if (ud != null) {
            try { idx = Integer.parseInt(ud.toString()); } catch (NumberFormatException ignored) {}
        }

        if (idx == -1) {
            String bt = btn.getText();
            if (bt != null) {
                for (int i = 0; i < choices.length; i++) {
                    if (choices[i].equalsIgnoreCase(bt.trim())) {
                        idx = i;
                        break;
                    }
                }
            }
        }

        if (idx < 0 || idx >= choices.length) {
            System.out.println("handleChoiceClick: could not determine choice index for button: " + btn.getText());
            return;
        }

        selectedIndex = idx;
        updateSelectionDisplay();
        highlightSelectedButton();
        System.out.println("handleChoiceClick: selected index = " + selectedIndex + " (" + choices[selectedIndex] + ")");
    }

    @FXML
    private void handleClear(ActionEvent event) {
        selectedIndex = -1;
        updateSelectionDisplay();
        clearChoiceHighlights();
        System.out.println("handleClear: selection cleared");
    }

    @FXML
private void handleCall(ActionEvent event) {
    if (selectedIndex < 0 || selectedIndex >= choices.length) {
        showAlert(AlertType.WARNING, "No selection", "Please select an option before submitting.");
        return;
    }

    String letter = indexToLetter(selectedIndex);  // A, B, C, D

    if (selectionDisplay != null) {
        selectionDisplay.setText("Selected: " + letter);
        selectionDisplay.getStyleClass().remove("number-correct");
        selectionDisplay.getStyleClass().remove("number-wrong");
    }

    boolean correct = isCorrectGuess(letter);

    if (selectionDisplay != null) {
        selectionDisplay.getStyleClass().add(correct ? "number-correct" : "number-wrong");
    }

    if (correct) {
        showAlert(AlertType.INFORMATION, "Unlocked!", "Correct choice — puzzle solved!");
        setButtonsDisabled(true);
    } else {
        showAlert(AlertType.ERROR, "Incorrect", "That choice did not unlock the puzzle.");
    }
}


    /** Converts choice index (0–3) to letter A–D */
    private String indexToLetter(int index) {
        return String.valueOf((char) ('A' + index));
    }


    private boolean isCorrectGuess(String letter) {
        if (letter == null) return false;

        // Correct answer is "C"
        boolean correct = letter.equalsIgnoreCase("C");

        // Inform backend
        escapeRoom.solvePuzzle(PuzzleID, letter);

        // Allow backend to override if needed
        return correct || escapeRoom.puzzleUnlocked(PuzzleID);
    }

    // ------------------- UI helpers -------------------

    private void updateSelectionDisplay() {
        if (selectionDisplay == null) return;
        if (selectedIndex >= 0 && selectedIndex < choices.length) {
            selectionDisplay.setText("Selected: " + choices[selectedIndex]);
        } else {
            selectionDisplay.setText("");
        }
    }

    private void highlightSelectedButton() {
        if (choicesGrid == null) return;

        for (Node node : choicesGrid.getChildren()) {
            if (!(node instanceof Button)) continue;
            Button b = (Button) node;
            Object ud = b.getUserData();
            int idx = -1;
            if (ud != null) {
                try { idx = Integer.parseInt(ud.toString()); } catch (NumberFormatException ignored) {}
            }
            if (idx == selectedIndex) {
                if (!b.getStyleClass().contains("choice-selected")) {
                    b.getStyleClass().add("choice-selected");
                }
            } else {
                b.getStyleClass().remove("choice-selected");
            }
        }
    }

    private void clearChoiceHighlights() {
        if (choicesGrid == null) return;
        for (Node node : choicesGrid.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).getStyleClass().remove("choice-selected");
            }
        }
    }

    private void setButtonsDisabled(boolean disabled) {
        if (choicesGrid != null) {
            for (Node node : choicesGrid.getChildren()) {
                if (node instanceof Button) {
                    ((Button) node).setDisable(disabled);
                }
            }
        }

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

    private void showAlert(AlertType type, String title, String message) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
}