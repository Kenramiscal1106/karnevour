package application.controllers.components;

import database.inventory.ItemHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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

    public void initData(ItemHolder itemHolder) {
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
        openPopup("/ui/views/UpdateProductPriceView.fxml", "Update Product Price");
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
