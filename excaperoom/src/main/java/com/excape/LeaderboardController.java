package com.excape;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import com.model.User;
import com.model.UserList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LeaderboardController implements Initializable {

    @FXML
    private VBox leaderboardVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadLeaderboard();
    }

     @FXML
    private void handleContinue() {
        System.out.println("Exiting application...");
        Platform.exit(); // Gracefully closes JavaFX application
        System.exit(0);  // Ensures complete shutdown
    }

    private void loadLeaderboard() {
        ArrayList<User> allUsers = UserList.getInstance().getAllUsers();

        Collections.sort(allUsers, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return Integer.compare(u2.getScore(), u1.getScore());
            }
        });

        leaderboardVBox.getChildren().clear();

        int rank = 1;
        for (User user : allUsers) {
            HBox row = createPlayerRow(rank, user);

            leaderboardVBox.getChildren().add(row);

            rank++;
        }
    }

    private HBox createPlayerRow(int rank, User user) {
        HBox row = new HBox();
        row.getStyleClass().add("leaderboard-row");

        Label rankLabel = new Label("#" + rank);
        rankLabel.getStyleClass().add("rank-label");

        Label nameLabel = new Label(user.getUsername());
        nameLabel.getStyleClass().add("username-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); 

        Label scoreLabel = new Label(user.getScore() + " pts");
        scoreLabel.getStyleClass().add("score-label");

        scoreLabel.setMinWidth(80);

        scoreLabel.setAlignment(Pos.CENTER_RIGHT);

        row.getChildren().addAll(rankLabel, nameLabel, spacer, scoreLabel);

        return row;
    }
}
