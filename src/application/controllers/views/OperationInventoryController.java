package application.controllers.views;

import application.controllers.components.AdjustItemController;
import application.services.InventoryOperations;
import database.inventory.Inventory;
import database.inventory.ItemHolder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public abstract class OperationInventoryController
{
    @FXML
    public ComboBox<ItemHolder> itemSelector;

    @FXML
    public Button windowCloseButton;

    @FXML
    public Button addInventoryButton;

    @FXML
    public AdjustItemController adjustItemController;

    private final InventoryOperations operations = new InventoryOperations();

    protected abstract String getActionType();

    protected String validate(ItemHolder itemHolder, double amount){
        return null;
    }

    @FXML
    public void initialize(){

        if (getActionType().equals("sell")){
            itemSelector.setItems(Inventory.getInstance().getItemHolderList().filtered(holder -> holder.getItemInStock() > 0));
        }

        if (getActionType().equals("restock")){
            itemSelector.setItems(Inventory.getInstance().getItemHolderList());
        }

        itemSelector.setConverter(new StringConverter<ItemHolder>() {
            @Override
            public String toString(ItemHolder itemHolder) {
                return itemHolder == null ? "" : itemHolder.getItemAlternativeName().isEmpty() ? itemHolder.getItemDescription() : itemHolder.getItemAlternativeName();
            }
            @Override
            public ItemHolder fromString(String s) {
                return null;
            }
        });
        itemSelector.valueProperty().addListener((observableValue, oldValue, selected) -> {
            if (selected != null) adjustItemController.initData(selected);
        });
        addInventoryButton.disableProperty().bind(itemSelector.valueProperty().isNull());
        windowCloseButton.setOnAction(e -> close());
        addInventoryButton.setOnAction(e -> confirm());
    }

    private void confirm(){
        ItemHolder selected = itemSelector.getValue();
        double amount = adjustItemController.getQuantity();

        if (amount <= 0 ){
            showError("Enter a quantity greater than 0.");
            return;
        }

        String error = validate(selected, amount);
            if (error != null){
                showError("Error");
                return;
            }

        var service = operations.new updateStockService(Inventory.getInstance(), selected.getUid(), amount, getActionType());
        service.setOnSucceeded(e -> close());
        service.setOnFailed(e -> showError(service.getException().getMessage())); // placeholder lambda
        service.start();
    }

    private void close(){
        windowCloseButton.getScene().getWindow().hide();
    }
    private void showError(String error){
            System.out.println(error);
        }


}
