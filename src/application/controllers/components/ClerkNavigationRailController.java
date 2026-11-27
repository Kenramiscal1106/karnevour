package application.controllers.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.List;

public class ClerkNavigationRailController extends NavigationRailControllerBase {

    @FXML
    public Button saleButton;
    @FXML
    public Button inventoryButton;
    @FXML
    public ImageView saleIcon;
    @FXML
    public ImageView inventoryIcon;

    @FXML
    public void saleClick(ActionEvent mouseEvent) {
    	setActive(saleButton, true);
        navigationStore.updateView("/ui/views/SalesView.fxml");
    }

    @FXML
    public void inventoryClick(ActionEvent mouseEvent) {
        setActive(inventoryButton, true);
        navigationStore.updateView("/ui/views/InventoryView.fxml");
    }

    @FXML
    public void initialize() {
    	buttons = List.of(saleButton, inventoryButton);
        iconList = List.of(saleIcon, inventoryIcon);
    	setActive(saleButton, true);
    }


}
