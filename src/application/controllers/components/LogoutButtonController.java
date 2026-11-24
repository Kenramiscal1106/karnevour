package application.controllers.components;

import application.services.AuthOperations;
import application.services.NavigationStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class LogoutButtonController {
    public void logoutClick(ActionEvent actionEvent) {
        new AuthOperations().logout(() -> {
            var scene = NavigationStore.getInstance().getScene();
            try {
                var loader = new FXMLLoader(getClass().getResource("/ui/views/LoginView.fxml"));
                scene.setRoot(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
