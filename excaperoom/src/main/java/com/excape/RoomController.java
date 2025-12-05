package com.excape;

import java.io.IOException;

import com.model.EscapeRoom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RoomController {
    
    private EscapeRoom escapeRoom;
    
    @FXML
    private ImageView phoneItem;
    
    @FXML
    private ImageView newspaperItem;
    
    @FXML
    private ImageView lockItem;
    
    @FXML
    private ImageView examItem;
    @FXML
    private ImageView menuButton;

    @FXML
    private ImageView arrowRight;

    @FXML
    private ImageView arrowLeft;
    
    @FXML
    private void initialize() {
        escapeRoom = new EscapeRoom();
    }
    
    @FXML
    private void handlePhoneClicked(MouseEvent event) {
        System.out.println("Phone clicked!");

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
    }

    @FXML
    private void handleExamClicked(MouseEvent event) {
        System.out.println("Exam clicked!");
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
    private void handleArrowLeftClicked(MouseEvent event) {
        System.out.println("Left Arrow clicked!");
    }

    @FXML
    private void handleArrowRightClicked(MouseEvent event) {
        System.out.println("Right Arrow clicked!");
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