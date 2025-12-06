package com.excape;

import java.io.IOException;

import com.model.EscapeRoom;
import com.model.GameList;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class RoomController {
    
    private EscapeRoom escapeRoom = new EscapeRoom();
    
    // --- Injected nodes (make sure these fx:id exist in your FXML) ---
    @FXML private AnchorPane root;            // root AnchorPane (fx:id="root")
    @FXML private ImageView mainImageView;    // background ImageView (fx:id="mainImageView")
    
    @FXML private ImageView phoneItem;
    @FXML private ImageView newspaperItem;
    @FXML private ImageView lockItem;
    @FXML private ImageView examItem;
    @FXML private ImageView menuButton;
    @FXML private ImageView arrowRight;
    @FXML private ImageView arrowLeft;
    
    @FXML
    private void initialize() {
        escapeRoom = new EscapeRoom();

        // Ensure we run after scene/layout pass so sizes & image are available
        Platform.runLater(() -> {
        if (mainImageView == null || root == null) return;

        // bind the ImageView requested size to the root size (so it scales)
        mainImageView.fitWidthProperty().bind(root.widthProperty());
        mainImageView.fitHeightProperty().bind(root.heightProperty());
        mainImageView.setPreserveRatio(true);

        // center the ImageView inside the root whenever measurements change
        Runnable centerImage = () -> {
            double viewW = mainImageView.getBoundsInLocal().getWidth();
            double viewH = mainImageView.getBoundsInLocal().getHeight();

            // If bounds not ready yet, skip
            if (viewW <= 0 || viewH <= 0) return;

            double x = (root.getWidth() - viewW) / 2.0;
            double y = (root.getHeight() - viewH) / 2.0;
            if (x < 0) x = 0;
            if (y < 0) y = 0;

            mainImageView.setLayoutX(x);
            mainImageView.setLayoutY(y);
        };

        // run centering after layout passes
        root.widthProperty().addListener((o, oldV, newV) -> Platform.runLater(centerImage));
        root.heightProperty().addListener((o, oldV, newV) -> Platform.runLater(centerImage));
        mainImageView.imageProperty().addListener((o, oldI, newI) -> Platform.runLater(centerImage));

        // initial attempt
        Platform.runLater(centerImage);
        });

    }
    
    @FXML
    private void handlePhoneClicked(MouseEvent event) {
        System.out.println("Phone clicked!");
        try {
            App.setRoot("phonePuzzle");
            System.out.println("setRoot call returned");
        } catch (IOException e) {
            System.err.println("Failed to switch to GameInventoryClues:");
            e.printStackTrace();
        }

    }
    
    @FXML
    private void handleNewspaperClicked(MouseEvent event) {
        System.out.println("Newspaper clicked!");
        try {
            App.setRoot("NewspaperPage1");
            System.out.println("setRoot call returned");
        } catch (IOException e) {
            System.err.println("Failed to switch to GameInventoryClues:");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleLockClicked(MouseEvent event) {
        System.out.println("Lock clicked!");
        try {
            App.setRoot("Lock");
            System.out.println("setRoot call returned");
        } catch (IOException e) {
            System.err.println("Failed to switch to GameInventoryClues:");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExamClicked(MouseEvent event) {
        System.out.println("Exam clicked!");
        try {
            App.setRoot("multipleChoice");   
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load Room1.fxml");
        }
    }
    
    @FXML
    private void handleMenuClick(MouseEvent event) {
        System.out.println("Menu clicked!");
        System.out.println("Menu clicked!");
        try {
            App.setRoot("GameInventoryClues"); // pass the FXML name without .fxml
            System.out.println("setRoot call returned");
        } catch (IOException e) {
            System.err.println("Failed to switch to GameInventoryClues:");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleArrowRightClicked(MouseEvent event) {
        escapeRoom.goNextRoom("Room2");
        try {
            App.setRoot(GameList.getInstance().getCurrentGame().getCurrentRoom().getName());   
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load Room2.fxml");
        }
    }

    @FXML
    private void handleArrowLeftClickedTwo(MouseEvent event) {
        escapeRoom.goNextRoom("Room1");
        try {
            App.setRoot(GameList.getInstance().getCurrentGame().getCurrentRoom().getName());   
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load Room1.fxml");
        }
    }

    @FXML
    private void handleArrowRightClickedTwo(MouseEvent event) {
        escapeRoom.goNextRoom("Room3");
        try {
            App.setRoot(GameList.getInstance().getCurrentGame().getCurrentRoom().getName());   
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load Room3.fxml");
        }
    }

    @FXML
    private void handleArrowLeftClickedThree(MouseEvent event) {
        escapeRoom.goNextRoom("Room2");
        try {
            App.setRoot(GameList.getInstance().getCurrentGame().getCurrentRoom().getName());   
        } catch (IOException e) {
            e.printStackTrace(); 
            System.out.println("Failed to load Room2.fxml");
        }
    }

    @FXML
    private void handleArrowRightClickedThree(MouseEvent event) {
        escapeRoom.goNextRoom("Room4");
        try {
            App.setRoot(GameList.getInstance().getCurrentGame().getCurrentRoom().getName());   
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load Room1.fxml");
        }
    }

    @FXML
    private void handleArrowLeftClickedFour(MouseEvent event) {
        escapeRoom.goNextRoom("Room3");
        try {
            App.setRoot(GameList.getInstance().getCurrentGame().getCurrentRoom().getName());   
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load Room1.fxml");
        }
    }
    
    private void loadScene(MouseEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}