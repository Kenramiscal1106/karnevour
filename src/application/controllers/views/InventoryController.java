package application.controllers.views;

import java.net.URL;
import java.util.ResourceBundle;

import application.controllers.components.InventoryEntryController;
import application.services.*;
import database.inventory.Inventory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.*;

public class InventoryController implements Initializable {
    private final InventoryOperations service = new InventoryOperations();

    @FXML
    private VBox container;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        container.getChildren().clear();
        var inventory = Inventory.getInstance();
        inventory.getItemHolderList().forEach(itemHolder -> {
            try {
                var loader = new FXMLLoader(getClass().getResource("/ui/components/InventoryEntry.fxml"));
                InventoryEntryController controller = new InventoryEntryController();
                loader.setController(controller);
                Node inventoryEntry = loader.load();
                controller.initData(itemHolder);
                container.getChildren().add(inventoryEntry);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
