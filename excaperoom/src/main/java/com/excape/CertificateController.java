package com.excape;

import com.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.model.Certificate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        User testUser = new User("TestPlayer", "John", "Doe", "password123");

        // Simulate game actions
        testUser.useHint("hint1");
        testUser.useHint("hint2");
        testUser.useSkip();

        // Set the test user
        setUser(testUser);

        // Print to console for verification
        System.out.println("Username: " + testUser.getUsername());
        System.out.println("Score: " + testUser.getScore());
        System.out.println("Time: " + testUser.getCertificate().timeTakenMMSS());
        System.out.println("Hints: " + testUser.getCertificate().getHintsUsed());
        System.out.println("Skips: " + testUser.getCertificate().getSkipsUsed());
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
            Certificate cert = currentUser.getCertificate();

            usernameText.setText(currentUser.getUsername());

            scoreText.setText(String.valueOf(currentUser.getScore()));

            timeText.setText(cert.timeTakenMMSS());

            hintsText.setText(String.valueOf(cert.getHintsUsed()));

            skipsText.setText(String.valueOf(cert.getSkipsUsed()));

            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            dateText.setText(today.format(formatter));
        }
    }
}