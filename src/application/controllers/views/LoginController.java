package application.controllers.views;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {


    public TextField usernameField;
    public PasswordField passwordField;

    @FXML
    private void loginClick(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()){
            showError("Empty fields");
        }

        // search for username and password match in the db

            // if username not found, clear fields, show error

            // if username found but password incorrect, show error

        // check what type of user

        // load the appropriate user view

        // close login

    }

    private void showError(String error){
        System.out.println(error);
    }

    private void clearFields(){
        usernameField.clear();
        passwordField.clear();
    }
}

