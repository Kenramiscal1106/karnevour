package application.controllers.views;

import application.controllers.components.DashboardStatCardController;
import application.models.Dashboard;
import database.inventory.Inventory;
import database.logs.Logger;
import database.logs.SaleLog;
import javafx.fxml.FXML;

public class ManagerHomeController {
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


    }
}
