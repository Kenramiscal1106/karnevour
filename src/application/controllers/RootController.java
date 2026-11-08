package application.controllers;

import application.services.NavigationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
        if (content.getChildren().isEmpty()) {
            content.getChildren().add(navService.getValue());
        } else {
            content.getChildren().set(0, navService.getValue());
        }
    }
}
