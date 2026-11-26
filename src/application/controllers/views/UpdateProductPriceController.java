package application.controllers.views;

import application.services.InventoryOperations;
import database.inventory.Inventory;
import database.inventory.ItemHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateProductPriceController {
    @FXML
    public TextField price;

    @FXML
    public VBox root;

    private ItemHolder itemHolder;

    InventoryOperations operations = new InventoryOperations();

    public void initData(ItemHolder itemHolder){
        this.itemHolder = itemHolder;
        price.setText(Double.toString(Inventory.getInstance().getPrice(itemHolder.getUid())));
    }

    public void handleCancel(ActionEvent actionEvent) {
        var dialogStage = (Stage) root.getScene().getWindow();
        dialogStage.close();
    }

    public void handlePriceUpdate(ActionEvent actionEvent) {
        double amount = Double.parseDouble(price.getText().trim());
        double value = Math.round(amount * 100.0) / 100.0;
        price.setText(Double.toString(value));

        if (value < 0){
            showError("Enter a positive integer.");
            return;
        }

        var service = operations.new updatePriceService(Inventory.getInstance(), itemHolder.getUid(), value);
        service.setOnSucceeded(e -> handleCancel(null));
        service.setOnFailed(e -> showError(service.getException().getMessage()));
        service.start();
    }

    private void showError(String error){
        markError();
        System.out.println(error);
    }

    private void markError( ){
        if (!price.getStyleClass().contains("error")){
            price.getStyleClass().add("error");
        }
    }
}


