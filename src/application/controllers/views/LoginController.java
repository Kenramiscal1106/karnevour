package application.controllers.views;

import application.services.SessionHandler;
import database.Database;
import database.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginController {

    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public VBox errorBox;

    @FXML
    public Label errorLabel;

    @FXML
    public void initialize(){
        usernameField.textProperty().addListener((obs, oldText, textInput) -> {
           if (textInput != null) {
               usernameField.getStyleClass().remove("error");

               hideError();
           }
        });

        passwordField.textProperty().addListener((obs, oldText, textInput) -> {
            if (textInput != null) {
                passwordField.getStyleClass().remove("error");

                hideError();
            }
        });
    }


    @FXML
    private void loginClick(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()){
            showError("Empty fields");
            return;
        }

        // search for username and password match in the db
        User user = Database.getInstance().loadUserFromDatabase(username, password);
        if (user == null){
            showError("Username");
            return;
        }

        // check what type of user
        SessionHandler.getOrCreateInstance(user);
        // load the appropriate user view

        // close login

    }

    private void hideError(){
        errorBox.setVisible(false);
        errorBox.setManaged(false);
    }


    private void showError(String error){
        if (error.equals("Empty fields")) {

            if (passwordField.getText().isEmpty() && usernameField.getText().isEmpty()){
                markError(usernameField);
                markError(passwordField);
                errorLabel.setText("Empty fields");
                return;
            }

            if (usernameField.getText().isEmpty()){
                markError(usernameField);
                errorLabel.setText("Empty username");
            }

            else if (passwordField.getText().isEmpty()){
                markError(passwordField);
                errorLabel.setText("Empty password");
            }
        }


        if (error.equals("Username")) {
            usernameField.clear();
            passwordField.clear();

            markError(usernameField);
            markError(passwordField);
            errorLabel.setText("User not found");
        }

        if (error.equals("Password")) {
            passwordField.clear();


            markError(passwordField);
            errorLabel.setText("Incorrect password");


        }

        errorBox.setVisible(true);
        errorBox.setManaged(true);

    }

    private void markError(TextField field){
        if (!field.getStyleClass().contains("error")){
            field.getStyleClass().add("error");
        }
    }

    private void clearFields(){
        usernameField.clear();
        passwordField.clear();
    }
}

