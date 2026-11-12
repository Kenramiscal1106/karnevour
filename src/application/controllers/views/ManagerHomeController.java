package application.controllers.views;

import application.controllers.components.DashboardStatCardController;
import database.inventory.Inventory;
import javafx.fxml.FXML;

public class ManagerHomeController {
    @FXML
    private DashboardStatCardController revenueStatCard;


    @FXML
    private DashboardStatCardController inventoryValueStatCard;

    @FXML
    public void initialize() {
//       connect stats to service (via binding)
        var inventory = Inventory.getInstance();
//        inventoryValueStatCard.setData("Total Inventory Value", inventory.get);
    }
}
