package application.controllers.views;

import application.services.SessionHandler;
import database.inventory.Inventory;
import database.inventory.ItemHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RestockController {
    @FXML
    public TextField amount;
    @FXML
    public Label currentStock;
    @FXML
    public Label unit;
    @FXML
    public VBox root;

    private ItemHolder itemHolder;

    public void initData(ItemHolder itemHolder){
        this.itemHolder = itemHolder;
        unit.setText(itemHolder.getUnit());
    }

    public void handleRestock(ActionEvent actionEvent){
        amount.getStyleClass().remove("error");

        double value;

        try{
            value = Double.parseDouble(amount.getText().trim());

        } catch (NumberFormatException e){
            showError("Enter a number");
            return;
        }

        if (value <= 0){
            showError("Amount must be greater than zero");
            return;
        }

        try{
            Inventory.getInstance().restockItem(itemHolder.getUid(), value, SessionHandler.getInstance().getCurrentUser());
            closeDialog(null);
        } catch (IllegalArgumentException e){
            showError(e.getMessage());
        }

    }

    public void closeDialog(ActionEvent actionEvent){
        ((Stage) root.getScene().getWindow()).close();
    }

    public void showError(String error){
        if(!amount.getStyleClass().contains("error")){
            amount.getStyleClass().add("error");
        }
    }
}
