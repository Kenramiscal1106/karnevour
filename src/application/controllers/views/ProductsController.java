package application.controllers.views;

import application.controllers.components.ProductEntryController;
import database.inventory.Inventory;
import database.inventory.ItemHolder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;

public class ProductsController {
    public ListView<ItemHolder> productsList;

    public void initialize(){
        productsList.setItems(Inventory.getInstance().getProductHoldersList());
        productsList.setCellFactory((_) -> new ListCell<>() {

            private final Node row;
            private final ProductEntryController controller;
            {
                try {
                    var loader = new FXMLLoader(getClass().getResource("/ui/components/ProductEntry.fxml"));
                    row = loader.load();
                    controller = loader.getController();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void updateItem(ItemHolder productHolder, boolean empty) {
                super.updateItem(productHolder, empty);
                if (empty || productHolder == null) {
                    setGraphic(null);
                    return;
                }
                controller.initData(productHolder);
                setGraphic(row);
            }
        });
    }
}
