package application.controllers.views;

import application.controllers.components.DashboardStatCardController;
import database.inventory.Inventory;
import database.logs.Logger;
import database.logs.SaleLog;
import database.logs.Transaction;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SalesControl {
    @FXML
    public ListView<Transaction> salesList;

    @FXML
    public DashboardStatCardController inventoryValueStatCardController;

    @FXML
    void initialize() {
        refreshTotalInventoryValue();
        var sales = new FilteredList<>(Logger.getInstance().getTransactionLogs(), t -> t instanceof SaleLog);
        salesList.setItems(sales);
//        TODO: add listCell to tell listview how to display it
    }

    @FXML
    private void NewOrderClick(){
        openPopup("/ui/views/POSView.fxml","New Order");
    }

    @FXML
    private void ItemRefundClick(){
        openPopup("/ui/views/RefundView.fxml","Item Refund");
    }

    private void openPopup(String fxmlPath, String title){
        try {
            var loader = new FXMLLoader(getClass().getResource(fxmlPath));
            var stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(salesList.getScene().getWindow());
            stage.showAndWait();
            refreshTotalInventoryValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshTotalInventoryValue(){
        double totalInventoryValue = Inventory.getInstance().getTotalInventoryValue();
        inventoryValueStatCardController.setData(
                String.format("₱%,.0f", totalInventoryValue),
                "Total Inventory Value",
                "/icons/warehouse.png" // placeholder icon
        );
    }
}
