package application.controllers.views;

import application.controllers.components.RefundEntryController;
import application.services.RefundProductService;
import application.services.SessionHandler;
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

    private final RefundProductService refundProductService = new RefundProductService();

    private final SessionHandler sessionHandler = SessionHandler.getInstance();

    @FXML
    public void initialize(){
        var sales = Logger.getInstance().getSalesLogs().filtered(log -> {
            if (!sessionHandler.isAdmin()) return log.author.equals(sessionHandler.getCurrentUser().getName());
            return true;
        });
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
        refundProductService.saleLogProperty().bind(itemSelector.valueProperty());
        itemSelector.valueProperty().addListener((observableValue, oldValue, selected) -> {
                if (selected != null && Inventory.getInstance().doesItemExist(selected.itemHolderUid)){
                    var holder = Inventory.getInstance().getItemHolderByUid(selected.itemHolderUid);
                    refundEntryController.initData(holder);
                    refundEntryController.itemQuantityText.setText("Sold: " + selected.qty + " " + holder.getUnit());
                    refundEntryController.itemPriceText.setText(String.format("₱%,.2f", selected.value));
                }
        });
        addInventoryButton.disableProperty().bind(itemSelector.valueProperty().isNull());
        windowCloseButton.setOnAction(e -> close());
        addInventoryButton.setOnAction(e -> confirm());
    }

    private void confirm(){
        refundProductService.setOnSucceeded(e -> close());
        refundProductService.setOnFailed(e -> showError(refundProductService.getException().getMessage()));
        refundProductService.restart();
    }

    private String getItemName(SaleLog sale){
        if (!Inventory.getInstance().doesItemExist(sale.itemHolderUid)) return "deleted item!";
        var holder = Inventory.getInstance().getItemHolderByUid(sale.itemHolderUid);
        return holder.getItemAlternativeName().isEmpty() ? holder.getItemDescription() : holder.getItemAlternativeName();
    }

    public void close(){
        windowCloseButton.getScene().getWindow().hide();
    }

    public void showError(String error){
        new Alert (Alert.AlertType.WARNING, error).show();
    }
}
