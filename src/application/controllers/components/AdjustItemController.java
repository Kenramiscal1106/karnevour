package application.controllers.components;

import database.inventory.ItemHolder;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.util.Objects;

public class AdjustItemController implements EntryControllerBase<ItemHolder> {

    @FXML
    public TextField quantityField;
    @FXML
    public Text unitField;
    @FXML
    public Text itemQuantityText;
    @FXML
    public ImageView itemImage;
    @FXML
    public Text itemNameText;

     public String textContent;

    @Override
    public void initData(ItemHolder itemHolder) {
        var item =  itemHolder.getItem();
        itemImage.setImage(new Image(item.getImagePath(), true));
        quantityField.setText("0");
        unitField.setText(itemHolder.getUnit());
        itemQuantityText.setText(itemHolder.getItemInStock() + " " + itemHolder.getUnit());
        textContent = itemHolder == null ? "" : itemHolder.getItemAlternativeName().isEmpty() ? itemHolder.getItemDescription() : itemHolder.getItemAlternativeName();
        itemNameText.setText(textContent);
    }
    @FXML
    private void increment(){
        setQuantity(Math.max(0, getQuantity() + 1));
    }

    @FXML
    private void decrement(){
        setQuantity(Math.max(0, getQuantity() - 1));
    }

    private void setQuantity(double q){
        quantityField.setText(String.valueOf(q));
    }

    public double getQuantity(){
        try{
            return Double.parseDouble(quantityField.getText().trim());
        } catch (NumberFormatException e){
            return -1;
        }
    }
}
