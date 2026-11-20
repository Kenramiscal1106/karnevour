package application.controllers.components;

import database.inventory.Inventory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class DashboardStatCardController {
    public Text primaryText;
    public Text secondaryText;
    public ImageView imageContainer;

    public void setData(String primaryTextContent, String secondaryTextContent, String imagePath) {
        primaryText.setText(primaryTextContent);
        secondaryText.setText(secondaryTextContent);
        imageContainer.setImage(new Image(imagePath));
    }
}
