package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.services.NavigationService;

public class NavigationRailController implements Initializable {

    @FXML
    public Button homeButton;
    public Button saleButton;
    public Button inventoryButton;
    public Button activityHistoryButton;

    private static final String ACTIVE = "navigation-item--active";
    private List<Button> buttons;

    private void setActive(Button active) {
    	buttons.forEach(b -> b.getStyleClass().remove(ACTIVE));
    	active.getStyleClass().add(ACTIVE);
    }

    private final NavigationService navigationService = NavigationService.getInstance();

    @FXML
    public void homeClick(ActionEvent mouseEvent) {
    	setActive(homeButton);
    }

    @FXML
    public void saleClick(ActionEvent mouseEvent) {
    	setActive(saleButton);
    }

    @FXML
    public void inventoryClick(ActionEvent mouseEvent) {
    	setActive(inventoryButton);
    	NavigationService.getInstance();
    	// (TO BE ADDED) navigate to inventory view navigateTo() method
    }

    @FXML
    public void activityHistoryClick(ActionEvent mouseEvent) {
    	setActive(activityHistoryButton);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	buttons = List.of(homeButton, saleButton, inventoryButton, activityHistoryButton);
    	setActive(homeButton);
    }


}
