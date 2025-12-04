package com.excape;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class SignUpController {

    @FXML
    private Pane Login_Pane;

     @FXML
    private TextField SignUp_FirstName_TextField;

     @FXML
    private TextField SignUp_LastName_TextField;

    @FXML
    private TextField SignUp_Username_TextField;

    @FXML
    private TextField SignUp_Password_TextField;

    @FXML
    private Text Login_Password_Text;

    @FXML
    private Text Login_Username_Text;

    @FXML
    private Button SignUp_Begin_Button;

    @FXML
    private ImageView Login_Key_ImageView;

    @FXML
    private Button SignUp_Login_Button;

    @FXML
    private void initialize() {
        // Initialization logic here
    }

    @FXML
    private void handleBeginAdventure() {
        // Handle Begin Adventure button click
    }

    @FXML
    private void handleLogin() {
        // Handle Log In button click
    }
}