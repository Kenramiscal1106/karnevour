package application.controllers.components;

import application.services.AuthOperations;
import application.services.NavigationStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.IOException;

import application.services.AuthOperations;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;

public class LogoutButtonController {
    public void logoutClick(ActionEvent actionEvent) throws IOException {
        var scene = NavigationStore.getInstance().getScene();
        DialogPane pane = new FXMLLoader(getClass().getResource("/ui/components/LogoutDialog.fxml")).load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Logout");
        dialog.setDialogPane(pane);
        dialog.initOwner(scene.getWindow());

        var result = dialog.showAndWait();
        if (result.isEmpty() || result.get().getButtonData() != ButtonBar.ButtonData.OK_DONE) return;

        new AuthOperations().logout(() -> {
            try {
                Parent login = new FXMLLoader(getClass().getResource("/ui/views/LoginView.fxml")).load();
                scene.setRoot(login);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        });
    }

}
