package application.controllers.views;

import application.controllers.components.DashboardStatCardController;
import database.logs.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class SalesControl {
    @FXML
    public ListView<Transaction> salesList;

    @FXML
    public DashboardStatCardController inventoryValueStatCardController;

    @FXML
    void initialize() {

//        TODO: bind salesList items to transactionlist, but filter only products
//        TODO: add listCell to tell listview how to display it
    }

    @FXML
    private void NewOrderClick(){}

    @FXML
    private void ItemRefundClick(){}
}
