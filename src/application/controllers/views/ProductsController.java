package application.controllers.views;

import application.controllers.components.ProductEntryController;
import database.inventory.Inventory;
import database.inventory.ItemHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Function;

public class ProductsController {
    public ListView<ItemHolder> productsList;

    public void initialize() {
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

    public void openAddProductDialog(ActionEvent actionEvent) {
        try {
            var loader = new FXMLLoader(getClass().getResource("/ui/views/AddProductView.fxml"));
            var stage = new Stage();
            stage.setTitle("Add a product");
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(productsList.getScene().getWindow());
            stage.showAndWait();
            productsList.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
