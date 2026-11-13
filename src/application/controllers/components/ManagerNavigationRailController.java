package application.controllers.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;

import javafx.scene.image.ImageView;

public class ManagerNavigationRailController extends NavigationRailControllerBase {

    @FXML
    public Button homeButton;
    public Button saleButton;
    public Button inventoryButton;
    public Button activityHistoryButton;

    public ImageView homeIcon;
    public ImageView saleIcon;
    public ImageView inventoryIcon;
    public ImageView historyIcon;
    @FXML
    public void homeClick(ActionEvent mouseEvent) {
    	setActive(homeButton, true);
        navigationStore.updateView("/ui/views/TestView.fxml");
    }

    @FXML
    public void saleClick(ActionEvent mouseEvent) {
    	setActive(saleButton, true);
        navigationStore.updateView("/ui/views/SalesView.fxml");
    }

    @FXML
    public void inventoryClick(ActionEvent mouseEvent) {
    	setActive(inventoryButton, true);
        navigationStore.updateView("/ui/views/InventoryView.fxml");
    	// (TO BE ADDED) navigate to inventory view navigateTo() method
    }

    @FXML
    public void activityHistoryClick(ActionEvent mouseEvent) {
    	setActive(activityHistoryButton, false);
        navigationStore.updateView("/ui/views/ActivityHistoryView.fxml");
    }

    @FXML
    public void initialize() {
    	buttons = List.of(homeButton, saleButton, inventoryButton, activityHistoryButton);
        iconList = List.of(homeIcon, saleIcon, inventoryIcon, historyIcon);
    	setActive(homeButton, true);
    }


}
