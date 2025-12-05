package com.excape;

import java.io.IOException;

import com.model.EscapeRoom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {

    private EscapeRoom escapeRoom;

    @FXML
    private Pane Login_Pane;

    @FXML
    private TextField Login_Username_TextField;

    @FXML
    private TextField Login_Password_TextField;

    @FXML
    private Text Login_ForgotPassword_Text;

    @FXML
    private Text Login_Password_Text;

    @FXML
    private Text Login_Username_Text;

    @FXML
    private Button Login_Begin_Button;

    @FXML
    private ImageView Login_Key_ImageView;

    @FXML
    private Button Login_Signup_Button;

    @FXML
    private void initialize() {
        escapeRoom = new EscapeRoom();
    }

    @FXML
    private void handleBeginAdventure() throws IOException {
        String username = Login_Username_TextField.getText();
        String password = Login_Password_TextField.getText();

        boolean success = escapeRoom.login(username, password);
        
        if (success) {
            // Login successful - navigate to main game screen or show success message
            System.out.println("Login successful!");
            // TODO: Navigate to game screen
            try {
                App.setRoot("GamePicker");
            } catch (IOException ex) {
            }
        } else {
            // Login failed - show error message
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        try {
            // Load the SignUp FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent signupRoot = loader.load();
            
            // Get the current stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Set the new scene
            try {
                App.setRoot("SignUp");
            } catch (IOException ex) {
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading SignUp.fxml");
        }
    }
}