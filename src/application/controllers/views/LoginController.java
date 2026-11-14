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
            return;
        }

        // search for username and password match in the db

            // if username not found, clear fields, show error

            // if username found but password incorrect, show error

        // check what type of user

        // load the appropriate user view

        // close login

    }

    private void showError(String error){

        if (error.equals("Empty fields")){
            usernameField.getStyleClass().add("error");
            passwordField.getStyleClass().add("error");
            System.out.println("Empty fields");
        }

        if (error.equals("Username")){
            usernameField.getStyleClass().add("error");

            usernameField.clear();
            passwordField.clear();

            // print statement placeholder
            System.out.println("User not found.");
        }

        if (error.equals("Password")){
            passwordField.getStyleClass().add("error");

            passwordField.clear();

            // print statement placeholder
            System.out.println("Incorrect password.");
        }
    }

    // add a function that listens to the fields when error was shown. updated when fields

    private void clearFields(){
        usernameField.clear();
        passwordField.clear();
    }
}

