package application.controllers.views;

import application.services.NavigationStore;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class ManagerController {

        @FXML
        public BorderPane root;


        @FXML
        public void initialize() {
            var navService = NavigationStore.getInstance();
            navService.updateView("/ui/views/LoginView.fxml");
            navService.getViewProperty().addListener((observable, oldValue, newValue) -> {
                var valueBin = newValue == null ? oldValue : newValue;
                if (valueBin == null) return;
                root.setCenter(newValue);
            });
        }
}
