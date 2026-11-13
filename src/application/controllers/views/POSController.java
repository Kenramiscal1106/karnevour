package application.controllers.views;

import database.items.Product;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class POSController {
    @FXML
    public ListView<Product> choicesList;
    @FXML
    public ListView<Product> selectedList;
    public Text totalText;
}
