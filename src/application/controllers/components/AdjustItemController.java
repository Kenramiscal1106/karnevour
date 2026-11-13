package application.controllers.components;

import database.products.Item;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class AdjustItemController implements EntryControllerBase<Item> {

    @FXML
    public TextField quantityField;
    @FXML
    public Text unitField;
    @FXML
    public Text itemQuantityText;
    @FXML
    public ImageView itemImage;
    @FXML
    public Text itemNameText;

    @Override
    public void initData(Item item) {

    }
}
