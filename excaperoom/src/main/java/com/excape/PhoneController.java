package com.excape;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class PhoneController {

    @FXML private Text numberDisplay;        // fx:id in FXML for showing raw digits / final word
    @FXML private Button exitButton;         // fx:id in FXML (optional)
    @FXML private GridPane keypadGrid;       // fx:id in FXML (optional, defensive attachments)

    // typed raw digits buffer (keeps repeats for multi-tap)
    private final StringBuilder typed = new StringBuilder();

    // mapping digit -> letters for multi-tap decoding
    private final Map<Character, String> digitToLetters = new HashMap<>();

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
        // ensure display starts empty
        if (numberDisplay != null) numberDisplay.setText("");

        // Attach defensive handlers once the scene graph is realized
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                attachButtonHandlersIfMissing();
            }
        });
    }

    /**
     * Attach handlers to any Button children of the keypadGrid that are missing onAction.
     * Also adds a mouseClicked debug print so we can see clicks in the console.
     */
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

                // Add a mouse-click debug print for visibility
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

        // Show raw digits while typing
        if (numberDisplay != null) {
            numberDisplay.setText(typed.toString());
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
        if (numberDisplay != null) numberDisplay.setText(decoded);

        System.out.println("handleCall: submitted raw=" + raw + " decoded=" + decoded);

        // clear after submit
        typed.setLength(0);
    }

    // ------------------- multi-tap decoder -------------------

    /**
     * Group consecutive identical digits and pick the corresponding letter.
     * e.g. "36664" -> groups: "3","666","4" -> D O G -> "dog"
     */
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
            // else ignore digits with no letter mapping

            i = j;
        }

        return out.toString().toLowerCase();
    }

    // ------------------- digit extraction -------------------

    /**
     * Try to extract a single-digit string from the given Button.
     * Order:
     *  1) userData
     *  2) graphic Text with styleClass "digit-text" (via lookup)
     *  3) first Text found recursively inside graphic
     *  4) button.getText()
     */
    private String getDigitFromButton(Button btn) {
        // 1) userData
        Object ud = btn.getUserData();
        if (ud != null && !ud.toString().isEmpty()) {
            return ud.toString();
        }

        // 2) graphic lookup
        if (btn.getGraphic() instanceof Parent) {
            Parent root = (Parent) btn.getGraphic();

            try {
                Node found = root.lookup(".digit-text");
                if (found instanceof Text) {
                    String t = ((Text) found).getText();
                    if (t != null && !t.isEmpty()) return t.substring(0, 1);
                }
            } catch (Exception ignored) {}

            // 3) recursive search
            String foundText = findTextInNode(root);
            if (foundText != null) return foundText;
        }

        // 4) button text
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

    // ------------------- exit / navigator -------------------

    public interface Navigator {
        void goBack();
    }
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
