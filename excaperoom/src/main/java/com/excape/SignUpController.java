package com.excape;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import com.model.EscapeRoom;

public class SignUpController {

    private EscapeRoom escapeRoom;

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
        escapeRoom = new EscapeRoom();
    }

    @FXML
    private void handleBeginAdventure() {
        String firstName = SignUp_FirstName_TextField.getText();
        String lastName = SignUp_LastName_TextField.getText();
        String username = SignUp_Username_TextField.getText();
        String password = SignUp_Password_TextField.getText();

        boolean success = escapeRoom.signUp(username, firstName, lastName, password);
        
        if (success) {
            System.out.println("Sign up successful!");
        } else {
            System.out.println("Sign up failed. Username may already exist.");
        }
    }

    @FXML
    private void handleLogin() {
        // Handle Log In button click - navigate to login screen
    }
}