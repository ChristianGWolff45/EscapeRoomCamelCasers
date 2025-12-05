package com.excape;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.model.EscapeRoom;
import java.io.IOException;

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
        loadScene(event, "GameInventoryClues.fxml");
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