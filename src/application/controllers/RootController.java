package application.controllers;

import application.services.NavigationService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

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
