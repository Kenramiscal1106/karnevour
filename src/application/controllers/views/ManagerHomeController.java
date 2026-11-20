package application.controllers.views;

import application.controllers.components.DashboardStatCardController;
import database.inventory.Inventory;
import javafx.fxml.FXML;

public class ManagerHomeController {
    @FXML
    private DashboardStatCardController revenueStatCardController;


    @FXML
    private DashboardStatCardController inventoryValueStatCardController;

    @FXML
    public void initialize() {
//       connect stats to service (via binding)
        var inventory = Inventory.getInstance();

        double value = inventory.getTotalInventoryValue();
        inventoryValueStatCardController.setData(
                String.format("₱%,.0f", value),
                "Total inventory value",
                "icons/database.png"
        );

    }
}
