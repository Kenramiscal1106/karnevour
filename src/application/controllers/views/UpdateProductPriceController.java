package application.controllers.views;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateProductPriceController {
    public TextField price;
    public VBox root;

    public void closeDialog(ActionEvent actionEvent) {
        var dialogStage = (Stage) root.getScene().getWindow();
        dialogStage.close();
    }

    public void handlePriceUpdate(ActionEvent actionEvent) {
//        TODO: add code for price update
    }
}
