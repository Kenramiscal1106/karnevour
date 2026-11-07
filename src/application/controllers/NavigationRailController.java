package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class NavigationRailController implements Initializable {

    @FXML
    public Button homeButton;

    @FXML
    public void homeClick(ActionEvent mouseEvent) {
        homeButton.getStyleClass().add("navigation-item--active");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
