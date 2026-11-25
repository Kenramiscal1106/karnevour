package application.controllers.views;

import application.services.InventoryOperations;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddProductController {
    public TextField productName;
    public TextArea description;
    public TextField price;
    public TextField lowStockLimit;


    public void addProductButton(ActionEvent actionEvent) {
        InventoryOperations inventoryOperations = new InventoryOperations();
//        inventoryOperations.addItemService.;
        
    }
}
