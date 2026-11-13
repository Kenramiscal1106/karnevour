package application.controllers.views;

import application.controllers.components.AdjustItemController;
import database.items.Product;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class RefundController {
    public ComboBox<Product> itemSelector;
    public Button windowCloseButton;
    public Button addInventoryButton;

    public AdjustItemController adjustItemController;
}
