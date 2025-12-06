package com.excape;

import java.net.URL;
import java.util.ResourceBundle;

import com.model.Certificate;
import com.model.User;
import com.model.UserList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CertificateController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate certificate UI after FXML loads
        populateCertificate();
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
        if (currentUser == null) {
            currentUser = UserList.getInstance().getCurrentUser();
        }

        if (currentUser != null) {
            Certificate cert = currentUser.getCertificate();

            usernameText.setText(currentUser.getUsername());
            scoreText.setText(String.valueOf(currentUser.getScore()));
            timeText.setText("5 minutes");
            hintsText.setText(String.valueOf(cert.getHintsUsed()));
            skipsText.setText(String.valueOf(cert.getSkipsUsed()));
        }
    }
}
