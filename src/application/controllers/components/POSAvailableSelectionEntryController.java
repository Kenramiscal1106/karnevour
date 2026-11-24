package application.controllers.components;

import database.inventory.ItemHolder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.util.function.Consumer;


public class POSAvailableSelectionEntryController implements EntryControllerBase<ItemHolder>{
    public ImageView availableImage;
    public Text productName;
    public Text productRemaining;
    @FXML
    public Button addButton;

    private ItemHolder itemHolder;
    private Consumer<ItemHolder> onAdd;

    public void initData(ItemHolder itemHolder){
        this.itemHolder = itemHolder;
        File file = new File(itemHolder.getItem().getImagePath());
        availableImage.setImage(new Image(file.toURI().toString()));
        String alt = itemHolder.getItemAlternativeName();
        productName.setText(alt.isEmpty() ? itemHolder.getItemDescription() : alt);
        productRemaining.setText(itemHolder.getItemInStock() + " " + itemHolder.getUnit() + " remaining");
        addButton.setDisable(itemHolder.getItemInStock() <= 0);
    }

    public void setOnAdd(Consumer<ItemHolder> onAdd){
        this.onAdd = onAdd;
    }

    @FXML
    private void addClick(){
        onAdd.accept(itemHolder);
    }
}
