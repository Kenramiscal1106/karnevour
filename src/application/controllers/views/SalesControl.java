package application.controllers.views;

import application.controllers.components.DashboardStatCardController;
import application.controllers.components.SalesHistoryEntryController;
import database.inventory.Inventory;
import database.logs.Logger;
import database.logs.SaleLog;
import database.logs.Transaction;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SalesControl {
    @FXML
    public ListView<SaleLog> salesList;

    @FXML
    public DashboardStatCardController inventoryValueStatCardController;

    @FXML
    void initialize() {
        refreshTotalInventoryValue();
        var logger = Logger.getInstance();
        var sales = logger.getSalesLogs();
        salesList.setItems(sales);

        salesList.setCellFactory(listView -> new ListCell<>(){
            private final Node row;
            private final SalesHistoryEntryController controller;

            {
                try{
                    var loader = new FXMLLoader(getClass().getResource("/ui/components/SalesHistoryEntry.fxml"));
                    row = loader.load();
                    controller = loader.getController();
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void updateItem(SaleLog saleLog, boolean empty){
                super.updateItem(saleLog, empty);
                if (empty || saleLog == null){
                    setGraphic(null);
                } else {
                    controller.initData((SaleLog) saleLog);
                    setGraphic(row);
                }
            }
        });
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
                "/icons/value.png" // placeholder icon
        );
    }
}
