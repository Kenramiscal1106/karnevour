package application.controllers.views;

import application.services.NavigationStore;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ClerkController {

    @FXML
    public BorderPane root;


    @FXML
    public void initialize() {
        var navigationStore = NavigationStore.getInstance();
        navigationStore.updateView("/ui/views/LoginView.fxml");
        navigationStore.getViewProperty().addListener((observable, oldValue, newValue) -> {
            var valueBin = newValue == null ? oldValue : newValue;
            if (valueBin == null) return;
            root.setCenter(newValue);
        });
    }
}
