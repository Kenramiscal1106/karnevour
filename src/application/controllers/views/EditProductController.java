package application.controllers.views;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditProductController {
    public TextField lowStockLimit;
    public TextField productName;
    public TextArea description;
    public TextField price;
    public VBox root;

    public void closeDialog(ActionEvent actionEvent) {
        ((Stage)root.getScene().getWindow()).close();
    }

    public void updateProductButton(ActionEvent actionEvent) {
//        TODO: add functionality to Update product
    }
}
