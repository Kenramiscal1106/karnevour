package application.controllers.components;

import database.inventory.Inventory;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class DashboardStatCardController {
    @FXML
    public Text primaryText;

    @FXML
    public Text secondaryText;

    @FXML
    public ImageView imageContainer;

    @FXML
    public void setData(String primaryTextContent, String secondaryTextContent, String imagePath) {
        primaryText.setText(primaryTextContent);
        secondaryText.setText(secondaryTextContent);
        imageContainer.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
    }
}