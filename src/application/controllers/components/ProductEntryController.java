package application.controllers.components;

import database.inventory.ItemHolder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;

public class ProductEntryController implements EntryControllerBase<ItemHolder> {
    public ImageView imageView;
    public Text productDescription;
    public Text productName;

    public String textContent;

    public void initData(ItemHolder itemHolder) {
        var item = itemHolder.getItem();
        File file = new File(item.getImagePath());
        imageView.setImage(new Image(file.toURI().toString()));
        textContent = itemHolder.getItemAlternativeName().isEmpty() ? itemHolder.getItemDescription() : itemHolder.getItemAlternativeName();
        productName.setText(textContent);
        productDescription.setText(item.getDescription());
    }
}
