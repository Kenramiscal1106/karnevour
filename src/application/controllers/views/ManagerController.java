package application.controllers.views;

import application.services.NavigationService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerController {

        @FXML
        public ScrollPane content;


        @FXML
        public void initialize() {
            var navService = NavigationService.getInstance();
            navService.updateView("/ui/views/LoginView.fxml");
            navService.getViewProperty().addListener((observable, oldValue, newValue) -> {
                var valueBin = newValue == null ? oldValue : newValue;
                if (valueBin == null) return;
                content.setContent(newValue);
            });
        }
}
