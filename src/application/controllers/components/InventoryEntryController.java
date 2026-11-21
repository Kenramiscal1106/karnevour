package application.controllers.components;

import database.inventory.ItemHolder;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.util.Objects;


public class InventoryEntryController implements EntryControllerBase<ItemHolder> {

    private String path;
    @FXML
    public ImageView imageView;
    @FXML
    public Text itemName;
    @FXML
    public Text itemQuantity;


    public void initData(ItemHolder itemHolder) {
        var item =  itemHolder.getItem();
        File file = new File(item.getImagePath());
        imageView.setImage(new Image(file.toURI().toString()));
        itemName.setText(itemHolder.getItem().getAlternativeName());
        itemQuantity.setText(itemHolder.getItemInStock() + " " + itemHolder.getUnit());
    }
}
