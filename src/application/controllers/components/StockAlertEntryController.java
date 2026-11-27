package application.controllers.components;

import application.controllers.views.*;
import database.inventory.ItemHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StockAlertEntryController implements EntryControllerBase<ItemHolder> {
    public ImageView itemImage;
    public Label itemName;
    public Label itemRemaining;
    public Label stockBadge;

    private ItemHolder itemHolder;
    private ManagerHomeController home;

    @Override
    public void initData(ItemHolder itemHolder) {
        this.itemHolder = itemHolder;
        var item =  itemHolder.getItem();
        itemName.setText(itemHolder.getItem().getItemName());
        itemRemaining.setText(itemHolder.getItemInStock() + " " + itemHolder.getUnit() + " remaining" );
        itemImage.setImage(new Image(item.getImagePath(), true));
        StockBadge.apply(stockBadge, itemHolder);
    }

    public void openRestockItemDialog(ActionEvent actionEvent) {
        try{
            var loader = new FXMLLoader(getClass().getResource("/ui/views/RestockProductView.fxml"));
            Parent root = loader.load();
            RestockProductController popup = loader.getController();
            popup.initData(itemHolder);

            var stage = new Stage();
            stage.setTitle("Restock " + itemHolder.getItem().getItemName());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(itemImage.getScene().getWindow());
            stage.showAndWait();
            home.refreshAfterRestock();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setHome(ManagerHomeController home){
        this.home = home;
    }

}
