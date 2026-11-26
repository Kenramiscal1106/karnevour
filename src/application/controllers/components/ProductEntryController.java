package application.controllers.components;

import application.controllers.views.UpdateProductPriceController;
import database.inventory.ItemHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ProductEntryController implements EntryControllerBase<ItemHolder> {
    public ImageView imageView;
    public Text productDescription;
    public Text productName;
    public String textContent;

    private ItemHolder itemHolder;

    public void initData(ItemHolder itemHolder) {
        this.itemHolder = itemHolder;
        var item = itemHolder.getItem();
        imageView.setImage(new Image(item.getImagePath(), true));
        textContent = itemHolder.getItemAlternativeName().isEmpty() ? itemHolder.getItemDescription() : itemHolder.getItemAlternativeName();
        productName.setText(textContent);
        productDescription.setText(item.getDescription());
    }

    public void handleEditDialog(ActionEvent actionEvent) {
        openPopup("/ui/views/EditProductView.fxml", "Edit Product Details");
    }

    public void handleUpdatePrice(ActionEvent actionEvent) {
        try{
            var loader = new FXMLLoader(getClass().getResource("/ui/views/UpdateProductPriceView.fxml"));
            Parent root = loader.load();
            UpdateProductPriceController popup = loader.getController();
            popup.initData(itemHolder);

            var stage = new Stage();
            stage.setTitle("Update Product Price");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(imageView.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openPopup(String path, String windowTitle) {
        try {
            var loader = new FXMLLoader(getClass().getResource(path));
            var stage = new Stage();
            stage.setTitle(windowTitle);
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(imageView.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
