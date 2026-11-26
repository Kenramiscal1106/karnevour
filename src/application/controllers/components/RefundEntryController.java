package application.controllers.components;

import database.inventory.ItemHolder;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.File;
import java.util.Objects;

public class RefundEntryController implements EntryControllerBase<ItemHolder> {
    @FXML
    public Text itemQuantityText;
    @FXML
    public ImageView itemImage;
    @FXML
    public Text itemNameText;
    @FXML
    public Text itemPriceText;

    public String textContent;

    @Override
    public void initData(ItemHolder itemHolder) {
        var item =  itemHolder.getItem();
        itemImage.setImage(new Image(item.getImagePath(), true));
        itemQuantityText.setText(itemHolder.getItemInStock() + " " + itemHolder.getUnit());
        textContent = itemHolder == null ? "" : itemHolder.getItemAlternativeName().isEmpty() ? itemHolder.getItemDescription() : itemHolder.getItemAlternativeName();
        itemNameText.setText(textContent);
    }
}
