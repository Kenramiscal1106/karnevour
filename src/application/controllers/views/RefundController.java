package application.controllers.views;

import application.controllers.components.RefundEntryController;
import application.services.SalesOperations;
import database.inventory.Inventory;
import database.logs.Logger;
import database.logs.SaleLog;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.util.Comparator;

public class RefundController {
    @FXML
    public ComboBox<SaleLog> itemSelector;
    @FXML
    public Button windowCloseButton;
    @FXML
    public Button addInventoryButton;
    @FXML
    public RefundEntryController refundEntryController;


    private final SalesOperations operations = new SalesOperations();

    @FXML
    public void initialize(){
        var sales = Logger.getInstance().getSalesLogs();
        itemSelector.setItems(new SortedList<>(sales, Comparator.comparingInt(sales::indexOf).reversed()));
        itemSelector.setConverter(new StringConverter<SaleLog>() {
            @Override
            public String toString(SaleLog sale) {
                return sale == null ? "" : String.format("%s  ·  %s  ·  ₱%,.2f  ·  %s", getItemName(sale), sale.qty, sale.value, sale.date);
            }

            @Override
            public SaleLog fromString(String s) {
                return null;
            }
        });
        itemSelector.valueProperty().addListener((observableValue, oldValue, selected) -> {
                if (selected != null && Inventory.getInstance().doesItemExist(selected.productUid)){
                    var holder = Inventory.getInstance().getItemHolderByUid(selected.productUid);
                    refundEntryController.initData(holder);
                    refundEntryController.itemQuantityText.setText("Sold: " + selected.qty + " " + holder.getUnit());
                }
        });
        addInventoryButton.disableProperty().bind(itemSelector.valueProperty().isNull());
        windowCloseButton.setOnAction(e -> close());
        addInventoryButton.setOnAction(e -> confirm());
    }

    private void confirm(){
        SaleLog selected = itemSelector.getValue();

        operations.refundUID.set(selected.productUid);
        operations.refundAmount.set(selected.qty);
        operations.refundSoldQty.set(selected.qty);
        operations.refundSale.set(selected);

        var service = operations.refundProductService;
        service.setOnSucceeded(e -> close());
        service.setOnFailed(e -> showError(service.getException().getMessage()));
        service.restart();
    }

    private String getItemName(SaleLog sale){
        if (!Inventory.getInstance().doesItemExist(sale.productUid)) return "deleted item!";
        var holder = Inventory.getInstance().getItemHolderByUid(sale.productUid);
        return holder.getItemAlternativeName().isEmpty() ? holder.getItemDescription() : holder.getItemAlternativeName();
    }

    public void close(){
        windowCloseButton.getScene().getWindow().hide();
    }

    public void showError(String error){
        new Alert (Alert.AlertType.WARNING, error).show();
    }
}
