package application.controllers.components;

import database.inventory.ItemHolder;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;


public class InventoryEntryController implements EntryControllerBase<ItemHolder> {

    private String path;
    @FXML
    public ImageView imageView;
    @FXML
    public Text itemName;
    @FXML
    public Text itemQuantity;

    public String textContent;


    public void initData(ItemHolder itemHolder) {
        var item =  itemHolder.getItem();
        imageView.setImage(new Image(item.getImagePath(), true));
        textContent = itemHolder == null ? "" : itemHolder.getItemAlternativeName().isEmpty() ? itemHolder.getItemDescription() : itemHolder.getItemAlternativeName();
        itemName.setText(textContent);
        itemQuantity.setText(itemHolder.getItemInStock() + " " + itemHolder.getUnit());
    }
}
