package application.controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.controllers.components.InventoryEntryController;
import application.controllers.components.SalesHistoryEntryController;
import application.services.*;
import database.inventory.Inventory;
import database.inventory.ItemHolder;
import database.logs.Logger;
import database.logs.SaleLog;
import database.logs.Transaction;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InventoryController {

    @FXML
    public ListView<ItemHolder> inventoryList;

    @FXML
    void initialize() {
        inventoryList.setItems(Inventory.getInstance().getItemHolderList());
        inventoryList.setCellFactory(listView -> new ListCell<>(){
            private final Node row;
            private final InventoryEntryController controller;

            {
                try{
                    var loader = new FXMLLoader(getClass().getResource("/ui/components/InventoryEntry.fxml"));
                    controller = new InventoryEntryController();
                    loader.setController(controller);
                    row = loader.load();
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void updateItem(ItemHolder itemHolder, boolean empty){
                super.updateItem(itemHolder, empty);
                if (empty || itemHolder == null){
                    setGraphic(null);
                } else {
                    controller.initData(itemHolder);
                    setGraphic(row);
                }
            }
        });
    }

    @FXML
    private void addInventoryClick(){
        openPopup("/ui/views/AddInventoryView.fxml", "Add to inventory");
    }

    @FXML
    private void subtractInventoryClick(){
        openPopup("/ui/views/SubtractInventoryView.fxml", "Subtract from inventory");
    }

    private void openPopup(String fxmlPath, String title){
        try {
            var loader = new FXMLLoader(getClass().getResource(fxmlPath));
            var stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(inventoryList.getScene().getWindow());
            stage.showAndWait();
            inventoryList.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
