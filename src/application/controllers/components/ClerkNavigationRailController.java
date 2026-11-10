package application.controllers.components;

import application.services.NavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClerkNavigationRailController implements Initializable {

    @FXML
    public Button homeButton;
    public Button saleButton;
    public Button inventoryButton;

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
        navigationService.updateView("/ui/views/TestView.fxml");
    }

    @FXML
    public void saleClick(ActionEvent mouseEvent) {
    	setActive(saleButton);
    }

    @FXML
    public void inventoryClick(ActionEvent mouseEvent) {
    	setActive(inventoryButton);
        navigationService.updateView("/ui/views/InventoryView.fxml");
    	// (TO BE ADDED) navigate to inventory view navigateTo() method
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	buttons = List.of(homeButton, saleButton, inventoryButton);
    	setActive(homeButton);
    }


}
