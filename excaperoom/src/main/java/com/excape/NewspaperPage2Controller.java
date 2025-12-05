package com.excape;

import java.io.IOException;

import com.model.EscapeRoom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NewspaperPage2Controller {
    @FXML private Button prevButton;
    @FXML private Button nextButton;
    @FXML private Button exitButton;


/* ---------- onAction handlers (must match onAction in FXML) ---------- */

    @FXML
    private void onNextPage(ActionEvent event) {
        EscapeRoom escapeRoom = new EscapeRoom();
        escapeRoom.pickUpClue("directionalPuzzle_Clue");
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
        try {
            App.setRoot("Room1");   
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load Room1.fxml");
        }
    }


}
