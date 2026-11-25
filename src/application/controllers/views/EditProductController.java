package application.controllers.views;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EditProductController {
    public TextField lowStockLimit;
    public TextField productName;
    public TextArea description;
    public TextField price;
    public VBox root;

    public void handleCancel(ActionEvent actionEvent) {
        root.getScene().getWindow().getOnCloseRequest();
    }

    public void updateProductButton(ActionEvent actionEvent) {
//        TODO: add functionality to Update product
    }
}
