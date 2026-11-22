package application.controllers.views;

import application.controllers.components.POSAvailableSelectionEntry;
import application.controllers.components.SalesHistoryEntryController;
import database.inventory.Inventory;
import database.inventory.ItemHolder;
import database.items.Product;
import database.logs.Logger;
import database.logs.SaleLog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;

public class POSController {
    @FXML
    public ListView<ItemHolder> choicesList;
    @FXML
    public ListView<Product> selectedList;
    public Text totalText;

    @FXML
    public void initialize(){
        choicesList.setItems(Inventory.getInstance().getItemHolderList().filtered(holder -> holder.getItem() instanceof Product));


        choicesList.setCellFactory(listView -> new ListCell<>(){
            private final Node row;
            private final POSAvailableSelectionEntry controller;

            {
                try{
                    var loader = new FXMLLoader(getClass().getResource("/ui/components/POSAvailableSelectionEntry.fxml"));
                    row = loader.load();
                    controller = loader.getController();
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
}
