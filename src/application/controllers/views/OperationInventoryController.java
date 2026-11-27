package application.controllers.views;

import application.controllers.components.AdjustItemController;
import application.services.InventoryOperations;
import application.services.UpdateStockService;
import database.inventory.Inventory;
import database.inventory.ItemHolder;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;

public abstract class OperationInventoryController {
    @FXML
    public ComboBox<ItemHolder> itemSelector;
    @FXML
    public Button windowCloseButton;
    @FXML
    public Button confirmActionButton;
    @FXML
    public AdjustItemController adjustItemController;
    private final UpdateStockService updateStockService = new UpdateStockService(getActionType());
    protected abstract String getActionType();

    protected String validate(ItemHolder itemHolder, double amount) {
        return null;
    }

    @FXML
    public void initialize() {
        updateStockService.actionTypeProperty().set(getActionType());
        updateStockService.itemHolderObjectProperty().bindBidirectional(itemSelector.valueProperty());
        Bindings.bindBidirectional(adjustItemController.quantityField.textProperty(), updateStockService.amountProperty(),  new NumberStringConverter());
        if (getActionType().equals("subtract")) {
            itemSelector.setItems(Inventory.getInstance().getItemHolderList().filtered(holder -> holder.getItemInStock() > 0));
        }

        if (getActionType().equals("restock")) {
            itemSelector.setItems(Inventory.getInstance().getItemHolderList());
        }

        itemSelector.setConverter(new StringConverter<ItemHolder>() {
            @Override
            public String toString(ItemHolder itemHolder) {
                return itemHolder.getItemAlternativeName().isEmpty() ? itemHolder.getItem().getItemName() : itemHolder.getItemAlternativeName();
            }

            @Override
            public ItemHolder fromString(String s) {
                return null;
            }
        });
        itemSelector.valueProperty().addListener((observableValue, oldValue, selected) -> {
            if (selected != null) adjustItemController.initData(selected);
        });

        windowCloseButton.setOnAction((actionEvent -> close()));
        confirmActionButton.setOnAction((actionEvent) -> confirm());
        confirmActionButton.disableProperty().bind(itemSelector.valueProperty().isNull());
    }

    public void confirm() {

        System.out.println("Works here");
        ItemHolder selected = itemSelector.getValue();
        double amount = adjustItemController.getQuantity();

        if (amount <= 0) {
            showError("Enter a quantity greater than 0.");
            return;
        }
        System.out.println("Works here");

        String error = validate(selected, amount);
        if (error != null) {
            showError("Error");
            return;
        }
        System.out.println("Works here");

        updateStockService.setOnSucceeded(e -> close());
        updateStockService.setOnFailed(e -> showError(updateStockService.getException().getMessage())); // placeholder lambda
        updateStockService.start();
    }

    public void close() {
        ((Stage) windowCloseButton.getScene().getWindow()).close();
    }

    private void showError(String error) {
        markError();
        System.out.println(error);
    }

    private void markError() {
        if (!adjustItemController.quantityField.getStyleClass().contains("error")) {
            adjustItemController.quantityField.getStyleClass().add("error");
        }
    }

}
