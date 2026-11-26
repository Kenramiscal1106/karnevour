package application.controllers.views;

import application.services.InventoryOperations;
import application.services.SessionHandler;
import database.Database;
import database.inventory.Inventory;
import database.items.Product;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.UUID;

public class AddProductController {
    public TextField imageUrl;
    public TextField productName;
    public TextArea description;
    public ComboBox<String> unit;
    public TextField price;
    public TextField lowStockLimit;
    public VBox root;

    public void addProductButton(ActionEvent actionEvent) {

        String name = productName.getText().trim();
        String desc = description.getText().trim();
        String unitText = unit.getEditor().getText().trim();


        if (name.isEmpty()){
            showError(productName, "Product name is required");
            return;
        }

        if (unitText.isEmpty()){
            showError(productName, "Unit is required");
            return;
        }

        double priceValue;

        try{
            priceValue = Double.parseDouble(price.getText().trim());
        } catch (NumberFormatException e) {
            showError(price,"Price must be a number");
            return;
        }

        if (priceValue <= 0){
            showError(price, "Price must be greater than zero");
        }

        double limitNumber;

        try{
            limitNumber = lowStockLimit.getText().isBlank() ? 0 : Double.parseDouble(lowStockLimit.getText().trim());
        } catch (NumberFormatException e) {
            showError(lowStockLimit,"Stock limit must be a number");
            return;
        }

        if (limitNumber < 0){
            showError(lowStockLimit, "Stock cant be negative");
        }

        var inventory = Inventory.getInstance();
        boolean nameTaken = inventory.getItemHolderList().stream().anyMatch(holder -> holder.getItem().getItemName().equalsIgnoreCase(name));

        if(nameTaken){
            showError(productName, "A product with that name already exists");
            return;
        }

        try{
            Product product = new Product(name, desc, priceValue);
            inventory.addItem(UUID.randomUUID().toString(), product, unitText, 0, limitNumber, SessionHandler.getInstance().getCurrentUser());

            if(!imageUrl.getText().isBlank()){
                Database.getInstance().savePictureURL(name, imageUrl.getText());
            }
            closeDialog(null);
        } catch (IllegalArgumentException e){
            showError(productName, e.getMessage());
        }

    }

    public void closeDialog(ActionEvent actionEvent) {
        ((Stage)root.getScene().getWindow()).close();
    }

    public void showError(Control field, String message){
        if(!field.getStyleClass().contains("error")){
            field.getStyleClass().add("error");
        }
        System.out.println(message);
    }

    public void clearErrors(){
        for (Control field : List.of(productName, unit, price, lowStockLimit)){
            field.getStyleClass().remove("error");
        }
    }
}
