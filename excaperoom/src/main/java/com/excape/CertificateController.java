package com.excape;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.model.Certificate;
import com.model.EscapeRoom;
import com.model.User;
import com.model.UserList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CertificateController {

    @FXML
    private Text scoreText; 

    @FXML
    private Text usernameText;

    @FXML
    private Text timeText;

    @FXML
    private Text hintsText;

    @FXML
    private Text skipsText;
    
    @FXML
    private Text dateText;

    @FXML
    private Button leaderboardButton;

    private User currentUser;

    @FXML
    public void initialize() {
        // Get the current user from UserList
        User user = UserList.getInstance().getCurrentUser();
        if (user != null) {
            setUser(user);
        } else {
            System.out.println("No current user found!");
        }
    }

    @FXML
    private void handleContinueToLeaderboard() {
        System.out.println("Button clicked!");

        try {
            App.setRoot("Leaderboard");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading leaderboard: " + e.getMessage());
        }
    }

    public void setUser(User user) {
        this.currentUser = user;
        populateCertificate();
    }

    private void populateCertificate() {
        if (currentUser != null) {
            EscapeRoom escapeRoom = new EscapeRoom();
            Certificate cert = currentUser.getCertificate();

            usernameText.setText(currentUser.getUsername());

            scoreText.setText(String.valueOf(currentUser.getScore()));

            timeText.setText("05:13");

            hintsText.setText(String.valueOf(cert.getHintsUsed()));

            skipsText.setText(String.valueOf(cert.getSkipsUsed()));
            
            // Set the current date
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            dateText.setText(today.format(formatter));
        }
    }
}