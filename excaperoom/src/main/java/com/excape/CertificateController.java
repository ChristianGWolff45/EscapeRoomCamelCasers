package com.excape;

import com.model.User;
import com.model.Certificate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private Button leaderboardButton;

    private User currentUser;

    @FXML
    public void initialize() {
        // TODO: Remove this test data when connecting to real game
        User testUser = new User("SWiley", "Scott", "Wiley", "password122");

        // Simulate game actions
        testUser.useHint("hint1");
        testUser.useHint("hint2");
        testUser.useHint("hint3");
        testUser.useHint("hint4");

        // Set the test user
        setUser(testUser);

        // Print to console for verification
        System.out.println("Username: " + testUser.getUsername());
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

            usernameText.setText(currentUser.getUsername());;

            hintsText.setText(String.valueOf(cert.getHintsUsed()));

            skipsText.setText(String.valueOf(cert.getSkipsUsed()));
        }
    }
}