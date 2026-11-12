package application.controllers.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.services.NavigationService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ManagerNavigationRailController implements Initializable {

    @FXML
    public Button homeButton;
    public Button saleButton;
    public Button inventoryButton;
    public Button activityHistoryButton;

    private static final String ACTIVE = "navigation-item--active";
    public ImageView homeIcon;
    public ImageView saleIcon;
    public ImageView inventoryIcon;
    public ImageView historyIcon;
    private List<Button> buttons;
    private List<ImageView> iconList;

    private void setActive(Button active, boolean filled) {

    	buttons.forEach(b -> {
            b.getStyleClass().remove(ACTIVE);
        });
        iconList.forEach(i -> {
            i.setImage(
                    new Image(i
                            .getImage()
                            .getUrl()
                            .replace("-filled", "")
                    )
            );
        });
    	active.getStyleClass().add(ACTIVE);
        active.getGraphic();
        if (!filled) return;

        var graphic = (VBox)active.getGraphic();

        if (graphic.getChildren().getFirst() instanceof ImageView imageView) {
            var image = imageView.getImage();
            imageView.setImage(new Image(image.getUrl().replace(".png", "") + "-filled.png"));
        }
    }

    private final NavigationService navigationService = NavigationService.getInstance();

    @FXML
    public void homeClick(ActionEvent mouseEvent) {
    	setActive(homeButton, true);
        navigationService.updateView("/ui/views/TestView.fxml");
    }

    @FXML
    public void saleClick(ActionEvent mouseEvent) {
    	setActive(saleButton, true);
    }

    @FXML
    public void inventoryClick(ActionEvent mouseEvent) {
    	setActive(inventoryButton, true);
        navigationService.updateView("/ui/views/InventoryView.fxml");
    	// (TO BE ADDED) navigate to inventory view navigateTo() method
    }

    @FXML
    public void activityHistoryClick(ActionEvent mouseEvent) {
    	setActive(activityHistoryButton, false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	buttons = List.of(homeButton, saleButton, inventoryButton, activityHistoryButton);
        iconList = List.of(homeIcon, saleIcon, inventoryIcon, historyIcon);
    	setActive(homeButton, true);
    }


}
