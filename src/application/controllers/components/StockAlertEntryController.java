package application.controllers.components;

import database.inventory.ItemHolder;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class StockAlertEntryController implements EntryControllerBase<ItemHolder> {
    public ImageView itemImage;
    public Label itemName;
    public Label itemRemaining;

    @Override
    public void initData(ItemHolder itemHolder) {
        var item =  itemHolder.getItem();
        itemName.setText(itemHolder.getItem().getItemName());
        itemRemaining.setText(itemHolder.getItemInStock() + " " + itemHolder.getUnit() + "remaining" );
        itemImage.setImage(new Image(item.getImagePath(), true));
    }

    public void openRestockItemDialog(ActionEvent actionEvent) {

    }
}
