package application.controllers.views;

import application.services.UpdatePriceService;
import database.inventory.ItemHolder;
import database.items.Product;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class UpdateProductPriceController {
    @FXML
    public TextField price;

    @FXML
    public VBox root;

    UpdatePriceService service = new UpdatePriceService();

    public void initData(ItemHolder itemHolder){
        service.itemHolderObjectProperty().set(itemHolder);
        service.newPriceProperty().set(((Product)itemHolder.getItem()).getPrice());
        Bindings.bindBidirectional(price.textProperty(), service.newPriceProperty(), new NumberStringConverter());
    }

    public void closeDialog(ActionEvent actionEvent) {
        var dialogStage = (Stage) root.getScene().getWindow();
        dialogStage.close();
    }

    public void handlePriceUpdate(ActionEvent actionEvent) {
        double amount = Double.parseDouble(price.getText().trim());
        double value = Math.round(amount * 100.0) / 100.0;
        price.setText(Double.toString(value));

        if (value < 0){
            showError("Enter a positive integer.");
            return;
        }

        service.setOnSucceeded(e -> closeDialog(null));
        service.setOnFailed(e -> showError(service.getException().getMessage()));
        service.restart();
    }

    private void showError(String error){
        markError();
        System.out.println(error);
    }

    private void markError( ){
        if (!price.getStyleClass().contains("error")){
            price.getStyleClass().add("error");
        }
    }
}


