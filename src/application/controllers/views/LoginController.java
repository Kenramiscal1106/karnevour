package application.controllers.views;

import application.services.AuthOperations;
import application.services.DataLoader;
import application.services.SessionHandler;
import database.Database;
import database.user.Admin;
import database.user.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

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

        new AuthOperations().login(username, password,
                user -> {
                    DataLoader.loadOnce();
                    openView(user instanceof Admin ? "/ui/views/ManagerView.fxml" : "/ui/views/ClerkView.fxml");
                },
                message -> showError("Incorrect"));
    }

    private void openView(String fxmlPath){
        try{
            Parent root = new FXMLLoader(getClass().getResource(fxmlPath)).load();
            usernameField.getScene().setRoot(root);
        } catch (IOException e){
            throw new RuntimeException(e);
        }

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
                clearFields();
                markError(usernameField);
                errorLabel.setText("Empty username");
            }

            else if (passwordField.getText().isEmpty()){
                clearFields();
                markError(passwordField);
                errorLabel.setText("Empty password");
            }
        }

        if (error.equals("Incorrect")) {
            clearFields();
            markError(usernameField);
            markError(passwordField);
            errorLabel.setText("Incorrect username or password");

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

