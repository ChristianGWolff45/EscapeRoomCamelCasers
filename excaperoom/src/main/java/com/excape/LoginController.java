package com.excape;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class LoginController {

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
        // Initialization logic here
    }

    @FXML
    private void handleBeginAdventure() {
        // Handle Begin Adventure button click
    }

    @FXML
    private void handleSignup() {
        // Handle Sign Up button click
    }

    @FXML
    private void handleForgotPassword() {
        // Handle Forgot Password text click (if you want it clickable)
    }
}