package com.excape;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.model.GameList;
import com.model.Hint;
import com.model.User;
import com.model.UserList;

import javafx.application.Platform;
import javafx.collections.ObservableList;
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
public class InventoryHintsController implements Initializable {
    // FXML injection: scrollPane's content should be an AnchorPane in your FXML
    @FXML private ScrollPane scrollPane;

    private GameList gameList;
    private UserList userList;
    private User user;

    // keep track of rendered hints so we can compute layout and remove them later
    private final List<Hint> renderedHints = new ArrayList<>();

    // layout constants to match your FXML card sizes/positions
    private static final double CARD_WIDTH = 383.0;
    private static final double CARD_HEIGHT = 100.0;
    private static final double CARD_LEFT_X = 115.0;
    private static final double FIRST_CARD_TOP = 213.0;
    private static final double VERTICAL_SPACING = 20.0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gameList = GameList.getInstance();
        userList = UserList.getInstance();
        userList.loginUser("LRIVERS", "sAfEpAsSwOrD@!");
        user = userList.getCurrentUser();

        gameList.setCurrentGame("Escape from Swearingen");

        // show current user's hints
        displayUserItems();
    }

    /**
     * Iterate user's hint IDs and render cards for each hint.
     */
    private void displayUserItems() {
        if (user == null) return;

        ArrayList<String> hintIds = user.getInventory().getHintIDS();
        if (hintIds == null || hintIds.isEmpty()) return;

        Runnable uiTask = () -> {
            AnchorPane content = getContentAnchorPane();
            if (content == null) {
                System.err.println("InventoryHintsController: ScrollPane content is missing or not an AnchorPane.");
                return;
            }

            // clear any previous dynamic cards
            clearHints();

            int index = 0;
            for (String hintID : hintIds) {
                Hint hint = gameList.findHint(hintID);
                if (hint == null) {
                    System.err.println("Hint not found for id: " + hintID);
                    continue;
                }

                // gather fields (use the getters available in your model)
                String hintName = safe(hint.getHintID());        // you used getHintID() as name in your snippet
                String hintDescription = safe(hint.getDescription());
                String puzzle = safe(hint.getPuzzleID());

                // build card
                StackPane card = buildCard(hintName, hintDescription, puzzle);

                // set AnchorPane constraints (this is equivalent to AnchorPane.leftAnchor="115.0" in FXML)
                AnchorPane.setLeftAnchor(card, CARD_LEFT_X);
                AnchorPane.setTopAnchor(card, FIRST_CARD_TOP + index * (CARD_HEIGHT + VERTICAL_SPACING));

                // marker so clearHints can identify dynamically created cards
                card.setUserData("dynamic-hint-card");

                content.getChildren().add(card);
                renderedHints.add(hint);

                index++;
            }

            // ensure content prefHeight is large enough for scrolling to the last card
            double neededHeight = FIRST_CARD_TOP + renderedHints.size() * (CARD_HEIGHT + VERTICAL_SPACING) + 40;
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
    private StackPane buildCard(String title, String description, String puzzleName) {
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

        Label puzzleLabel = new Label(puzzleName);
        puzzleLabel.setWrapText(true);
        puzzleLabel.setTextFill(Color.web("#9d2235"));
        puzzleLabel.setFont(Font.font(11));

        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        descLabel.setPrefWidth(CARD_WIDTH - 20);
        descLabel.setMaxWidth(CARD_WIDTH - 20);
        descLabel.setTextFill(Color.WHITE);
        descLabel.setFont(Font.font(11));

        vbox.getChildren().addAll(titleLabel, puzzleLabel, descLabel);

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
        renderedHints.clear();

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
    private void onHintsClick() throws IOException {
        App.setRoot("GameInventoryClues"); 
    }
}



//                        <StackPane fx:id="cardStack"
//                                    AnchorPane.leftAnchor="115.0"
//                                    AnchorPane.topAnchor="453.0"
//                                    prefWidth="383.0" prefHeight="100.0">
//                             <children>
//                                 <Rectangle fx:id="cardRect"
//                                            arcWidth="20.0" arcHeight="20.0"
//                                            width="383.0" height="100.0"
//                                            fill="#27272a" stroke="BLACK" strokeType="INSIDE"/>

//                                 <VBox fx:id="cardVBox" spacing="4.0" alignment="TOP_LEFT"
//                                       prefWidth="363.0" maxWidth="363.0">
//                                     <padding>
//                                         <Insets top="8.0" right="10.0" bottom="8.0" left="10.0"/>
//                                     </padding>

//                                     <Label fx:id="hintTitle" text="Mock Hint Name" textFill="WHITE" wrapText="true"
//                                            style="-fx-font-weight: bold; -fx-font-size: 12px;" />

//                                     <Label fx:id="puzzleName" text="Mock Puzzle Name" textFill="#9d2235" wrapText="true"
//                                            style="-fx-font-size: 11px;" />

//                                     <Label fx:id="hintDescription"
//                                            text="Mock puzzle name description that is long and should wrap across multiple lines"
//                                            wrapText="true" prefWidth="363.0" maxWidth="363.0" textFill="WHITE"
//                                            style="-fx-font-size: 11px;" />
//                                 </VBox>
//                             </children>
//                         </StackPane>