package com.excape;

import com.model.Wordle;
import com.model.Clue;
import com.model.EscapeRoom;
import com.model.GameList;
import com.model.Hint;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * NewspaperPage1Controller integrated with com.model.Wordle.
 * Uses Wordle.guessAnswer(...) to compute colors.
 */
public class NewspaperPage1Controller implements Initializable {

    @FXML private GridPane boardGrid;
    @FXML private Text statusText;

    private static final int COLS = 5;
    private static final int ROWS = 6;
    private final LabelTile[][] tiles = new LabelTile[ROWS][COLS];

    private int curRow = 0;
    private int curCol = 0;
    private final StringBuilder currentGuess = new StringBuilder();


    private String puzzleID = "wordlePuzzle";
    private EscapeRoom escapeRoom = new EscapeRoom();

    // single handler instance so add/remove use same object
    private final EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent ev) {
            coreOnKeyPressed(ev);
        }
    };

    private boolean sceneHandlerRegistered = false;
    private boolean nodeHandlerRegistered = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        escapeRoom.signUp("Test", "PuzzleID", "PuzzleID", "Password");
        escapeRoom.login("Test", "Password");
        escapeRoom.pickGame("Escape from Swearingen");


        buildBoard();
        if (statusText != null) statusText.setText("Type letters on your keyboard; Enter to submit; Backspace to delete.");

        // Node-level registration on boardGrid
        if (boardGrid != null) {
            try {
                boardGrid.setFocusTraversable(true);
                boardGrid.addEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
                nodeHandlerRegistered = true;
                System.out.println("[Controller] Node-level key handler registered on boardGrid.");
            } catch (Exception e) {
                System.out.println("[Controller] Node-level registration failed: " + e);
            }
        } else {
            System.out.println("[Controller] WARNING: boardGrid is null in initialize()");
        }

        // Scene-level registration when scene becomes available (idempotent)
        if (boardGrid != null) {
            boardGrid.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    System.out.println("[Controller] sceneProperty changed - newScene available.");
                    registerSceneHandlerOnce(newScene);
                } else {
                    System.out.println("[Controller] sceneProperty changed - scene removed.");
                    sceneHandlerRegistered = false;
                }
            });
        }

        // If scene already present, try register and request focus
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (boardGrid != null && boardGrid.getScene() != null) {
                    registerSceneHandlerOnce(boardGrid.getScene());
                    System.out.println("[Controller] Scene already present - attempted registration.");
                } else {
                    System.out.println("[Controller] Scene not present yet; will request focus later.");
                }
                attemptRequestFocusRepeatedly();
            }
        });
    }

    private void registerSceneHandlerOnce(javafx.scene.Scene scene) {
        if (scene == null) {
            System.out.println("[Controller] registerSceneHandlerOnce: scene is null");
            return;
        }

        try { scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyHandler); } catch (Exception ignored) {}

        if (!sceneHandlerRegistered) {
            scene.addEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
            sceneHandlerRegistered = true;
            System.out.println("[Controller] Scene-level key handler registered.");
        } else {
            System.out.println("[Controller] Scene-level handler already registered; skipping.");
        }
    }

    private void attemptRequestFocusRepeatedly() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (boardGrid != null) {
                    boardGrid.requestFocus();
                    System.out.println("[Controller] Requested focus on boardGrid.");
                }
                if (boardGrid != null && boardGrid.getScene() != null && boardGrid.getScene().getRoot() != null) {
                    boardGrid.getScene().getRoot().requestFocus();
                    System.out.println("[Controller] Requested focus on scene root.");
                }
            }
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() { try { Thread.sleep(50); } catch (InterruptedException ignored) {} if (boardGrid != null) boardGrid.requestFocus(); }
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() { try { Thread.sleep(100); } catch (InterruptedException ignored) {} if (boardGrid != null) boardGrid.requestFocus(); }
        });
    }

    // Centralized key handler logic
    private void coreOnKeyPressed(KeyEvent ev) {
        if (ev.getTarget() instanceof javafx.scene.control.Button) {
            System.out.println("[Controller] Ignored key event from Button target.");
            return;
        }

        KeyCode code = ev.getCode();
        System.out.println("[Controller] Key pressed: code=" + code + " text='" + ev.getText() + "'");

        if (code.isLetterKey()) {
            String txt = ev.getText();
            if (txt != null && txt.length() > 0) {
                char ch = Character.toUpperCase(txt.charAt(0));
                handleLetter(ch);
            }
            ev.consume();
            return;
        }

        if (code == KeyCode.ENTER) {
            onEnter();
            ev.consume();
            return;
        }

        if (code == KeyCode.BACK_SPACE || code == KeyCode.DELETE) {
            onBackspace();
            ev.consume();
            return;
        }
    }

    // Build a 6x5 board programmatically
    private void buildBoard() {
        if (boardGrid == null) return;
        boardGrid.getChildren().clear();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                LabelTile t = new LabelTile();
                tiles[r][c] = t;
                boardGrid.add(t.container, c, r);
            }
        }
        System.out.println("[Controller] Board built with " + ROWS + "x" + COLS + " tiles.");
    }

    private void handleLetter(char ch) {
        if (curRow >= ROWS) return;
        if (curCol >= COLS) return;
        currentGuess.append(ch);
        tiles[curRow][curCol].setLetter(String.valueOf(ch));
        curCol++;
    }

    private void onBackspace() {
        if (curRow >= ROWS) return;
        if (curCol <= 0) return;
        curCol--;
        int len = currentGuess.length();
        if (len > 0) currentGuess.deleteCharAt(len - 1);
        tiles[curRow][curCol].clear();
    }

    private void onEnter() {
        if (curRow >= ROWS) return;
        if (curCol != COLS) {
            if (statusText != null) statusText.setText("Not enough letters");
            return;
        }

        String guess = currentGuess.toString().toUpperCase(Locale.ROOT);
        applyModelResult(escapeRoom.wordleResponse(puzzleID, guess));
        if (guess.length() != COLS) return;

        escapeRoom.solvePuzzle(puzzleID, guess);
        if (escapeRoom.puzzleUnlocked(puzzleID)) {
            if (statusText != null) statusText.setText("Puzzle Completed");
            curRow = ROWS; // lock further input
            return;
        }

        // move to next row
        curRow++;
        curCol = 0;
        currentGuess.setLength(0);

        if (curRow >= ROWS) {
            if (statusText != null) statusText.setText("Game over. Word was " + "LOGIC");
            showAlert("Game Over", "The correct word was: " + "LOGIC");
        } else {
            if (statusText != null) statusText.setText("Try again");
        }
    }

    // Map model result to tile states and apply
    private void applyModelResult(String[][] result) {
        if (result == null) return;
        int n = Math.min(result.length, COLS);
        for (int i = 0; i < n; i++) {
            String color = result[i][1];
            if ("green".equalsIgnoreCase(color)) {
                tiles[curRow][i].setState(TileState.CORRECT);
            } else if ("yellow".equalsIgnoreCase(color)) {
                tiles[curRow][i].setState(TileState.PRESENT);
            } else {
                tiles[curRow][i].setState(TileState.ABSENT);
            }
        }
    }

    // Fallback evaluation (controller-only) in case model is absent or throws
   
    private void showAlert(String title, String message) {
        Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }

    private enum TileState { CORRECT, PRESENT, ABSENT, EMPTY }

    private static class LabelTile {
        final StackPane container = new StackPane();
        final Text label = new Text();

        LabelTile() {
            Rectangle bg = new Rectangle(58, 58);
            bg.getStyleClass().add("tile-bg");
            label.setFont(Font.font("Georgia", 28));
            label.getStyleClass().add("tile-text");
            container.getChildren().addAll(bg, label);
            container.setAlignment(Pos.CENTER);
        }

        void setLetter(String s) { label.setText(s); }
        void clear() { label.setText(""); setStyleClass(""); }

        void setState(TileState state) {
            switch (state) {
                case CORRECT: setStyleClass("tile-correct"); break;
                case PRESENT: setStyleClass("tile-present"); break;
                case ABSENT:  setStyleClass("tile-absent");  break;
                default: setStyleClass(""); break;
            }
        }

        private void setStyleClass(String cls) {
            Rectangle bg = (Rectangle) container.getChildren().get(0);
            bg.getStyleClass().removeAll("tile-correct","tile-present","tile-absent");
            if (cls != null && !cls.isEmpty()) bg.getStyleClass().add(cls);
        }
    }


    /* ---------- UI fields (must match fx:id in FXML) ---------- */
    @FXML private Button prevButton;
    @FXML private Button nextButton;
    @FXML private Button exitButton;
    @FXML private Button hintButton;

/* ---------- onAction handlers (must match onAction in FXML) ---------- */

    @FXML
    private void onNextPage(ActionEvent event) {
        System.out.println("[UI] Next page requested");
        // enable Prev once we move forward
        if (prevButton != null) {
            prevButton.setDisable(false);
            prevButton.setOpacity(1.0);
        }

    // Example navigation (commented) — replace with your real FXML filename and logic:
    // try {
    //     Parent nextRoot = FXMLLoader.load(getClass().getResource("NewspaperPage2.fxml"));
    //     Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
    //     st.getScene().setRoot(nextRoot);
    // } catch (Exception e) {
    //     e.printStackTrace();
    // }
    }

    @FXML
    private void onPrevPage(ActionEvent event) {
        System.out.println("[UI] Previous page requested");
        // Implement your backward navigation here. If this is the first page, keep disabled:
        // Example to disable again after going back:
        // prevButton.setDisable(true);
        // prevButton.setOpacity(0.45);
    }

    @FXML
    private void onExit(ActionEvent event) {
        System.out.println("[UI] Exit requested");
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onHint(ActionEvent event) {
        System.out.println("[UI] Hint requested");
        Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle("Hint");
        a.setHeaderText("Small hint");
        a.setContentText("Look for mechanical parts in the room. (Example hint text)");
        a.showAndWait();
    }
}
