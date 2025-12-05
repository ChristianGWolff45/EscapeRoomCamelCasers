package com.excape;

import java.io.IOException;

import com.model.EscapeRoom;
import com.model.GameList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GamePickerController {

    @FXML
    private Button playGameButton;

    @FXML
    private void initialize() {
        // Add any initialization logic here if needed
    }

    @FXML
    private void EscapeSwearingen() {
        EscapeRoom escapeRoom = new EscapeRoom();
        escapeRoom.pickGame("Escape from Swearingen");
        System.out.println(GameList.getInstance().getCurrentGame());
        try {
            App.setRoot("Room1");   
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load Room1.fxml");
        }
    }
}
