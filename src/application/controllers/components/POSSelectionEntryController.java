package application.controllers.components;

import application.models.CartLine;
import database.inventory.ItemHolder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.util.function.Consumer;

public class POSSelectionEntryController implements EntryControllerBase<CartLine>{
    @FXML
    public TextField quantityField;
    @FXML
    public ImageView checkoutImage;
    @FXML
    public Text productName;
    @FXML
    public Text productPrice;
    @FXML
    public Text unitText;
    @FXML
    public Button plusButton;
    @FXML
    public Button minusButton;
    @FXML
    public Button removeButton;

    private CartLine line;
    private Consumer<CartLine> onRemove;
    private Runnable onChange;

    @Override
    public void initData(CartLine line){
        this.line = line;
        var itemHolder = line.getItemHolder();
        String alt = itemHolder.getItemAlternativeName();
        String unit = itemHolder.getUnit();

        checkoutImage.setImage(new Image(itemHolder.getItem().getImagePath(), true));
        productName.setText(alt.isEmpty() ? itemHolder.getItemDescription() : alt);
        productPrice.setText(String.format("₱%,.0f", line.getPrice()));
        unitText.setText(unit);
        setQuantity(line.getQty());
    }

    @FXML
    public void initialize(){
        quantityField.textProperty().addListener((obs, oldText, newText) -> {
            if (line == null){
                return;
            }

            double q = getQuantity();
            boolean valid = q >= 0 && q <= line.getItemHolder().getItemInStock();

            if (valid){
                line.setQty(q);
            }

            onChange.run();
            // TO DO: add error popup?
        });
    }

    @FXML
    private void increment(){
        changeBy(1);
    }

    @FXML
    private void decrement(){
        changeBy(-1);
    }

    private void changeBy(double amount){
        double stock = line.getItemHolder().getItemInStock();
        double q = (Math.max(0, Math.min(stock, line.getQty() + amount)));

        if (q == 0){
            removeClick();
            return;
        }
        setQuantity(q);
    }

    private void setQuantity(double q){
        q = Math.round(q * 100)/100.0;
        quantityField.setText(q == Math.floor(q) ? String.valueOf((int) q) : String.valueOf(q));
    }

    public double getQuantity(){
        try{
            return Double.parseDouble(quantityField.getText().trim());
        } catch (NumberFormatException e){
            return -1;
        }
    }

    public void setOnRemove(Consumer<CartLine> onRemove){
        this.onRemove = onRemove;
    }

    public void setOnChange(Runnable onChange){
        this.onChange = onChange;
    }

    @FXML
    private void removeClick() { onRemove.accept(line); }


}
