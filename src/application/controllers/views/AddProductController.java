package application.controllers.views;

import application.services.InventoryOperations;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddProductController {
    public TextField productName;
    public TextArea description;
    public TextField price;
    public TextField lowStockLimit;
    public VBox root;


    public void addProductButton(ActionEvent actionEvent) {
        InventoryOperations inventoryOperations = new InventoryOperations();
//        inventoryOperations.addItemService.;
        
    }

    public void closeDialog(ActionEvent actionEvent) {
        ((Stage)root.getScene().getWindow()).close();
    }
}
