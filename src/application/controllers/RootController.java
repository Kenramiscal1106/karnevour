package application.controllers;

import application.services.NavigationService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    public HBox content;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var navService = NavigationService.getInstance();
        navService.updateView("/ui/views/LoginView.fxml");
        navService.getViewProperty().addListener((observable, oldValue, newValue) -> {
            var valueBin = newValue == null ? oldValue : newValue;
            if (valueBin == null) return;
            if (content.getChildren().isEmpty()) {
                content.getChildren().add(newValue);
            } else {
                content.getChildren().set(0, newValue);
            }
        });
    }
}
