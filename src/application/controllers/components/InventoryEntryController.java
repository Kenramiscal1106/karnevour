package application.controllers.components;

import database.inventory.ItemHolder;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    @FXML
    public String textContent;
    @FXML
    public Label stockBadge;

    public void initData(ItemHolder itemHolder) {
        var item =  itemHolder.getItem();
        imageView.setImage(new Image(item.getImagePath(), true));
        textContent = itemHolder.getItemAlternativeName().isEmpty() ? itemHolder.getItem().getItemName() : item.getAlternativeName();
        itemName.setText(textContent);
        itemQuantity.setText(itemHolder.getItemInStock() + " " + itemHolder.getUnit());
        StockBadge.apply(stockBadge, itemHolder);
    }


}
