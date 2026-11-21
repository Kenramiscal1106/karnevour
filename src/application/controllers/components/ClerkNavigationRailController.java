package application.controllers.components;

import application.services.NavigationStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.List;

public class ClerkNavigationRailController extends NavigationRailControllerBase {

    @FXML
    public Button saleButton;
    @FXML
    public ImageView saleIcon;
    @FXML
    public void saleClick(ActionEvent mouseEvent) {
    	setActive(saleButton, true);
        navigationStore.updateView("/ui/views/SalesView.fxml");
    }

    @FXML
    public void initialize() {
    	buttons = List.of(saleButton);
        iconList = List.of(saleIcon);
    	setActive(saleButton, true);
    }


}
