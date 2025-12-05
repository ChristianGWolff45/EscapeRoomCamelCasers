package com.excape;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.model.Clue;
import com.model.GameList;
import com.model.User;
import com.model.UserList;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * Controller for the Inventory Hints view.
 */
public class InventoryCluesController implements Initializable {
    // FXML injection: scrollPane's content should be an AnchorPane in your FXML
    @FXML private ScrollPane scrollPane;

    private User user;

    // keep track of rendered hints so we can compute layout and remove them later
    private final List<Clue> renderedClues = new ArrayList<>();

    // layout constants to match your FXML card sizes/positions
    private static final double CARD_WIDTH = 383.0;
    private static final double CARD_HEIGHT = 100.0;
    private static final double CARD_LEFT_X = 115.0;
    private static final double FIRST_CARD_TOP = 213.0;
    private static final double VERTICAL_SPACING = 20.0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = UserList.getInstance().getCurrentUser();
        // show current user's hints
        displayUserItems();
    }

    /**
     * Iterate user's hint IDs and render cards for each hint.
     */
    private void displayUserItems() {
        if (user == null) return;

        ArrayList<String> clueIDs = user.getInventory().getClueIDs();
        if (clueIDs == null || clueIDs.isEmpty()) return;

        Runnable uiTask = () -> {
            AnchorPane content = getContentAnchorPane();
            if (content == null) {
                System.err.println("InventoryHintsController: ScrollPane content is missing or not an AnchorPane.");
                return;
            }

            // clear any previous dynamic cards
            clearHints();

            int index = 0;
            for (String clueID : clueIDs) {
                Clue clue = GameList.getInstance().findClue(clueID);
                if (clue == null) {
                    System.err.println("Hint not found for id: " + clueID);
                    continue;
                }

                // gather fields (use the getters available in your model)
                String clueName = safe(clue.getClueID());        // you used getHintID() as name in your snippet
                String cluesDescription = safe(clue.getDescription());

                // build card
                StackPane card = buildCard(clueName, cluesDescription);

                // set AnchorPane constraints (this is equivalent to AnchorPane.leftAnchor="115.0" in FXML)
                AnchorPane.setLeftAnchor(card, CARD_LEFT_X);
                AnchorPane.setTopAnchor(card, FIRST_CARD_TOP + index * (CARD_HEIGHT + VERTICAL_SPACING));

                // marker so clearHints can identify dynamically created cards
                card.setUserData("dynamic-clue-card");

                content.getChildren().add(card);
                renderedClues.add(clue);

                index++;
            }

            // ensure content prefHeight is large enough for scrolling to the last card
            double neededHeight = FIRST_CARD_TOP + renderedClues.size() * (CARD_HEIGHT + VERTICAL_SPACING) + 40;
            if (content.getPrefHeight() < neededHeight) {
                content.setPrefHeight(neededHeight);
            }
        };

        if (Platform.isFxApplicationThread()) uiTask.run();
        else Platform.runLater(uiTask);
    }

    /**
     * Build a single card StackPane containing rectangle + VBox with labels.
     */
    private StackPane buildCard(String title, String description) {
        StackPane stack = new StackPane();
        stack.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        stack.setMaxSize(CARD_WIDTH, CARD_HEIGHT);
        stack.setMinSize(CARD_WIDTH, CARD_HEIGHT);

        // background rectangle (matches your FXML styling)
        Rectangle rect = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        rect.setArcWidth(20.0);
        rect.setArcHeight(20.0);
        rect.setFill(Color.web("#27272a"));
        rect.setStroke(Color.BLACK);

        // VBox with text
        VBox vbox = new VBox(4.0);
        vbox.setPadding(new Insets(8, 10, 8, 10));
        vbox.setPrefWidth(CARD_WIDTH - 20);
        vbox.setMaxWidth(CARD_WIDTH - 20);

        Label titleLabel = new Label(title);
        titleLabel.setWrapText(true);
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font(12));
        titleLabel.setStyle("-fx-font-weight: bold;");


        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        descLabel.setPrefWidth(CARD_WIDTH - 20);
        descLabel.setMaxWidth(CARD_WIDTH - 20);
        descLabel.setTextFill(Color.WHITE);
        descLabel.setFont(Font.font(11));

        vbox.getChildren().addAll(titleLabel, descLabel);

        stack.getChildren().addAll(rect, vbox);

        // optional click handler
        stack.setOnMouseClicked(evt -> {
            System.out.println("Clicked hint: " + title);
            // TODO: show more details or open a detail pane
        });

        return stack;
    }

    /**
     * Remove all dynamically-created hint cards.
     */
    private void clearHints() {
        AnchorPane content = getContentAnchorPane();
        if (content == null) return;

        ObservableList<javafx.scene.Node> children = content.getChildren();
        children.removeIf(node -> "dynamic-hint-card".equals(node.getUserData()));
        renderedClues.clear();

        // shrink prefHeight so layout stays tidy (optional)
        content.setPrefHeight(0);
    }

    /**
     * Helper to get the AnchorPane used as ScrollPane content.
     */
    private AnchorPane getContentAnchorPane() {
        if (scrollPane == null) return null;
        javafx.scene.Node c = scrollPane.getContent();
        if (c instanceof AnchorPane) {
            return (AnchorPane) c;
        }
        return null;
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }

    @FXML
    private Button hintsButton;

    @FXML
    private void onHintsClick() {
        try {
            App.setRoot("GameInventoryHints"); 
            System.out.println("setRoot call returned");
        } catch (Exception e) {
            System.err.println("Failed to load GameInventoryHints.fxml:");
            e.printStackTrace();
        }
        
    }
    @FXML
    private void onExit(ActionEvent event) {
        try {
            App.setRoot(GameList.getInstance().getCurrentGame().getCurrentRoom().getName());   
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load Room1.fxml");
        }
    }
}



