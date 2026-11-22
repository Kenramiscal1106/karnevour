package application.controllers.components;

import database.inventory.ItemHolder;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;

public class POSAvailableSelectionEntry implements EntryControllerBase<ItemHolder>{
    public ImageView availableImage;
    public Text productName;
    public Text productRemaining;

    public void initData(ItemHolder itemHolder){
        File file = new File(itemHolder.getItem().getImagePath());
        availableImage.setImage(new Image(file.toURI().toString()));
        String alt = itemHolder.getItemAlternativeName();
        productName.setText(alt.isEmpty() ? itemHolder.getItemDescription() : alt);
        productRemaining.setText(itemHolder.getItemInStock() + " " + itemHolder.getUnit() + " remaining");
    }
}
