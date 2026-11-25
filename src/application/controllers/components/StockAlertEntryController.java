package application.controllers.components;

import database.inventory.ItemHolder;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;

public class StockAlertEntryController implements EntryControllerBase<ItemHolder> {
    public ImageView itemImage;
    public Label itemName;
    public Label itemRemaining;

    @Override
    public void initData(ItemHolder itemHolder) {
        itemName.setText(itemHolder.getItem().getAlternativeName());
        itemRemaining.setText(itemHolder.getItemInStock() + " " + itemHolder.getUnit() + "remaining" );
        File imageFile = new File(itemHolder.getItem().getImagePath());
        itemImage.setImage(new Image(imageFile.toURI().toString()));
    }
}
