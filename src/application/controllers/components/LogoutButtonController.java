package application.controllers.components;

import application.services.AuthOperations;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;

public class LogoutButtonController {
    @FXML
    public Button logoutButton;

    @FXML
    public void logoutButtonClick() throws IOException{
        var scene = logoutButton.getScene();

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
