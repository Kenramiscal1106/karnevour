package application.controllers.views;

import application.services.SessionHandler;
import database.inventory.Inventory;
import database.inventory.ItemHolder;
import database.items.Product;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class EditProductController {
    public TextField lowStockLimit;
    public TextField productName;
    public TextArea description;
    public TextField price;
    public ComboBox<String> unit;
    public VBox root;

    private ItemHolder itemHolder;

    public void closeDialog(ActionEvent actionEvent) {
        ((Stage)root.getScene().getWindow()).close();
    }


    public void initData(ItemHolder itemHolder) {
        this.itemHolder = itemHolder;
        var item = itemHolder.getItem();
        String alt = item.getAlternativeName();
        productName.setText(alt.isEmpty() ? item.getItemName() : alt);
        description.setText(item.getDescription());
        price.setText(Double.toString(((Product) item).getPrice()));
        unit.setValue(itemHolder.getUnit());
        lowStockLimit.setText(Double.toString(itemHolder.getLowStockLimit()));
    }

    public void updateProductButton(ActionEvent actionEvent) {
        clearErrors();

        String typedName = productName.getText().trim();
        // typing the real product name (or nothing) means "no display name"
        String newName = typedName.isEmpty() || typedName.equals(itemHolder.getItem().getItemName()) ? "" : typedName;
        String newDesc = description.getText().trim();

        String newUnit = unit.getEditor().getText().trim();
        if (newUnit.isEmpty()) {
            showError(unit, "Unit is required.");
            return;
        }

        double newPrice;
        try {
            newPrice = Double.parseDouble(price.getText().trim());
        } catch (NumberFormatException e) {
            showError(price, "Price must be a number");
            return;
        }
        if (newPrice <= 0) {
            showError(price, "Price must be greater than ze ro");
            return;
        }

        double newLimit;
        try {
            newLimit = lowStockLimit.getText().isBlank() ? 0 : Double.parseDouble(lowStockLimit.getText().trim());
        } catch (NumberFormatException e) {
            showError(lowStockLimit, "Stock limit must be a number");
            return;
        }
        if (newLimit < 0) {
            showError(lowStockLimit, "Stock limit can't be negative");
            return;
        }

        var inventory = Inventory.getInstance();
        var user = SessionHandler.getInstance().getCurrentUser();
        var item = itemHolder.getItem();
        String uid = itemHolder.getUid();

        try {
            if (!newName.equals(item.getAlternativeName())) {
                inventory.setItemAlternativeName(uid, newName, user);
            }
            if (!newDesc.equals(item.getDescription())) {
                inventory.setItemDescription(uid, newDesc, user);
            }
            if (newPrice != ((Product) item).getPrice()) {
                inventory.setItemPrice(uid, newPrice, user);
            }
            if (!newUnit.equals(itemHolder.getUnit())) {
                inventory.replaceItemDetails(uid, newUnit, itemHolder.getItemInStock(), newLimit, user);
            } else if (newLimit != itemHolder.getLowStockLimit()) {
                inventory.setItemHolderLowInStock(uid, newLimit, user);
            }
            closeDialog(null);
        } catch (IllegalArgumentException e) {
            showError(productName, e.getMessage());
        }
    }

    private void clearErrors() {
        for (Control field : List.of(productName, unit, price, lowStockLimit)) {
            field.getStyleClass().remove("error");
        }
    }
    public void showError(Control field, String message){
        if(!field.getStyleClass().contains("error")){
            field.getStyleClass().add("error");
        }
        System.out.println(message);
    }
}
