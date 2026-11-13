package application.controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.controllers.components.InventoryEntryController;
import application.services.*;
import database.inventory.Inventory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InventoryController implements Initializable {
    @FXML
    private VBox container;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refresh();
    }

    @FXML
    private void addInventoryClick(){
        openPopup("/ui/views/AddInventoryView.fxml", "Add to inventory");
    }

    @FXML
    private void subtractInventoryClick(){
        openPopup("/ui/views/SubtractInventoryView.fxml", "Subtract from inventory");
    }

    private void refresh(){
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

    private void openPopup(String fxmlPath, String title){
        try {
            var loader = new FXMLLoader(getClass().getResource(fxmlPath));
            var stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(container.getScene().getWindow());
            stage.showAndWait();
            refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
