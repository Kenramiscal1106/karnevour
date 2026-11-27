package application.controllers.views;

import application.services.AddProductService;
import application.services.SessionHandler;
import database.Database;
import database.inventory.Inventory;
import database.items.Product;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

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

    private final AddProductService addProductService = new AddProductService();

    @FXML
    public void initialize() {
        var priceFormatter = new TextFormatter<Double>(new DoubleStringConverter(), 0.0);
        var lowStockLimitFormatter = new TextFormatter<Double>(new DoubleStringConverter(), 0.0);

        price.setTextFormatter(priceFormatter);
        lowStockLimit.setTextFormatter(lowStockLimitFormatter);

        addProductService.nameProperty().bind(productName.textProperty());
        addProductService.descriptionProperty().bind(description.textProperty());
        addProductService.unitTextProperty().bind(unit.valueProperty());
        addProductService.priceValueProperty().bind(priceFormatter.valueProperty());
        addProductService.lowStockLimitProperty().bind(lowStockLimitFormatter.valueProperty());
    }
    public void addProductButton(ActionEvent actionEvent) {

        String name = productName.getText().trim();
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
            return;
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

        addProductService.setOnSucceeded(e -> close());
        addProductService.setOnFailed(e -> showError(productName, e.getSource().getMessage()));
        addProductService.restart();
    }

    public void closeDialogAction(ActionEvent actionEvent) {
        close();
    }


    private void close() {
        ((Stage)root.getScene().getWindow()).close();
    }
    public void showError(Control field, String message){
        if(!field.getStyleClass().contains("error")){
            field.getStyleClass().add("error");
        }
        System.out.println(message);
    }
}
