package application.controllers.views;

import database.inventory.ItemHolder;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Objects;


public class InventoryEntryController {

    private String path;
    @FXML
    public ImageView imageView;
    @FXML
    public Text itemName;
    @FXML
    public Text itemQuantity;


    public void initData(ItemHolder itemHolder) {
        var item =  itemHolder.getItem();
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(item.getImagePath()))));
        itemName.setText(itemHolder.getItem().getAlternativeName());
        itemQuantity.setText(itemHolder.getItemInStock() + " " + itemHolder.getUnit());
    }

}
