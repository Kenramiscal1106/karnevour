package application.controllers.views;

import application.controllers.components.DashboardStatCardController;
import application.controllers.components.StockAlertEntryController;
import application.models.Dashboard;
import database.inventory.Inventory;
import database.inventory.ItemHolder;
import database.logs.Logger;
import database.logs.SaleLog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;

public class ManagerHomeController {
    public ListView<ItemHolder> stockAlertList;
    @FXML
    private DashboardStatCardController revenueStatCardController;

    @FXML
    private DashboardStatCardController inventoryValueStatCardController;

    private double revenue;

    @FXML
    public void initialize() {
//       connect stats to service (via binding)
        var inventory = Inventory.getInstance();
        for (SaleLog sale: Logger.getInstance().getSalesLogs()){
            revenue += sale.value;
        }

        double totalInventoryValue = inventory.getTotalInventoryValue();
        inventoryValueStatCardController.setData(
                String.format("₱%,.0f", totalInventoryValue),
                "Total Inventory Value",
                "/icons/value.png" // placeholder icon
        );

        revenueStatCardController.setData(
                String.format("₱%,.0f", revenue),
                "Revenue",
                "/icons/value.png" // placeholder icon
        );

        stockAlertList.setItems(inventory.filterList(ItemHolder::isLowInStock));

        stockAlertList.setCellFactory(_ -> new ListCell<>() {
            final Node row;
            final StockAlertEntryController controller;
            {
                try {
                    var loader = new FXMLLoader(getClass().getResource("/ui/components/StockAlertEntry.fxml"));
                    row = loader.load();
                    controller = loader.getController();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void updateItem(ItemHolder itemHolder, boolean empty) {
                super.updateItem(itemHolder, empty);
                if (empty || itemHolder == null) {
                    setGraphic(null);
                } else {
                    controller.initData(itemHolder);
                    setGraphic(row);
                }
            }
        });
    }
}
