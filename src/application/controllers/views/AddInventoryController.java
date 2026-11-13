package application.controllers.views;

import application.controllers.components.AdjustItemController;
import database.products.Item;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class AddInventoryController {
    public ComboBox<Item> itemSelector;
    public Button windowCloseButton;
    public Button addInventoryButton;

    public AdjustItemController adjustItemController;
}
