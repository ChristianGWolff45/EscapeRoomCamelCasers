package com.excape;

import java.io.IOException;

import com.model.EscapeRoom;
import com.model.UserList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        escapeRoom.login(username, password);
        System.out.println(escapeRoom.getCurrentUser());
        System.out.println(UserList.getInstance().getCurrentUser());
        
        if (success) {
            // Sign up successful - navigate to next screen or show success message
            System.out.println("Sign up successful!");
            try {
                App.setRoot("GamePicker");
            } catch (IOException ex) {
            }
        } else {
            // Sign up failed - show error message
            System.out.println("Sign up failed. Username may already exist.");
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            // Load the Login FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent loginRoot = loader.load();
            
            // Get the current stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Set the new scene
            Scene loginScene = new Scene(loginRoot);
            stage.setScene(loginScene);
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Login.fxml");
        }
    }
}