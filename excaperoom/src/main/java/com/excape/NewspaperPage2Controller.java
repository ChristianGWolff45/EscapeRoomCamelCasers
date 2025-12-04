package com.excape;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NewspaperPage2Controller {
    @FXML private Button prevButton;
    @FXML private Button nextButton;
    @FXML private Button exitButton;


/* ---------- onAction handlers (must match onAction in FXML) ---------- */

    @FXML
    private void onNextPage(ActionEvent event) {
        try {
            App.setRoot("NewspaperPage3");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void onPrevPage(ActionEvent event) {
        try {
            App.setRoot("NewspaperPage1");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void onExit(ActionEvent event) {
        System.out.println("[UI] Exit requested");
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }


}
