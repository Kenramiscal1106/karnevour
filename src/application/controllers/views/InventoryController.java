package application.controllers.views;

import java.net.URL;
import java.util.ResourceBundle;

import application.services.*;
import database.inventory.Inventory;
import database.inventory.ItemHolder;
import database.products.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class InventoryController implements Initializable {
    private final InventoryService service = new InventoryService();

    @FXML
    private VBox container;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        container.getChildren().clear();
        var inventory = Inventory.getInstance();
//        ObservableList<ItemHolder> itemHolders = FXCollections.observableArrayList();
//        itemHolders.addAll(new ItemHolder("1312312", new Product(
//                        SpecificProduct.BEEF_BRISKET,
//                        "Lorem ipsum dolor",
//                        23,
//                        "Beef Brikset"
//                ), "KG", 12, 5),
//                new ItemHolder("1312312", new Product(
//                        SpecificProduct.CHICKEN_BREAST,
//                        "Lorem ipsum dolor",
//                        23,
//                        "Chicken Breast"
//                ), "KG", 12, 5),
//                new ItemHolder("1312312", new Product(
//                        SpecificProduct.CHICKEN_BREAST,
//                        "Lorem ipsum dolor",
//                        23,
//                        "Chicken Breast"
//                ), "KG", 12, 5),
//                new ItemHolder("1312312", new Product(
//                        SpecificProduct.CHICKEN_BREAST,
//                        "Lorem ipsum dolor",
//                        23,
//                        "Chicken Breast"
//                ), "KG", 12, 5),
//                new ItemHolder("1312312", new Product(
//                        SpecificProduct.CHICKEN_BREAST,
//                        "Lorem ipsum dolor",
//                        23,
//                        "Chicken Breast"
//                ), "KG", 12, 5),
//                new ItemHolder("1312312", new Product(
//                        SpecificProduct.CHICKEN_BREAST,
//                        "Lorem ipsum dolor",
//                        23,
//                        "Chicken Breast"
//                ), "KG", 12, 5),
//                new ItemHolder("1312312", new Product(
//                        SpecificProduct.CHICKEN_BREAST,
//                        "Lorem ipsum dolor",
//                        23,
//                        "Chicken Breast"
//                ), "KG", 12, 5),
//                new ItemHolder("1312312", new Product(
//                        SpecificProduct.CHICKEN_BREAST,
//                        "Lorem ipsum dolor",
//                        23,
//                        "Chicken Breast"
//                ), "KG", 12, 5));
//        inventory.addItem("2312312",
//                new Product(
//                        SpecificProduct.BEEF_BRISKET,
//                        "Lorem ipsum dolor",
//                        23,
//                        "Beef Brikset"
//                ), "KG", 12, 5);
        inventory.getItemHolderList().forEach(itemHolder -> {
            try {
                var loader = new FXMLLoader(getClass().getResource("/ui/components/InventoryEntry.fxml"));
                InventoryEntryController controller = new InventoryEntryController();
                loader.setController(controller);
                Node inventoryEntry = loader.load();
                controller.initData(itemHolder);
                container.getChildren().add(inventoryEntry);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

//	private void refresh() {
//		grid.getChildren().clear();
//
//		// load the inventory
//
//	}

    // for adding items
    private HBox makeCard(ItemHolder itemHolder) {
        var item = itemHolder.getItem();
        Label name = new Label(item.getSpecificItemType().toString());
        Label quantity = new Label(itemHolder.getUnit());

        // add other elements

        HBox card = new HBox();
        return card;

    }

    // fetching image for the item
    private Image loadImage(Item item) {
        String path = item.getImagePath();
        var url = getClass().getResource(path);

        if (url == null) {
            url = getClass().getResource("database/pictures/default.png");
        }
        return new Image(url.toExternalForm());
    }


    // (TO BE DONE): addItem, removeItem, restockItem
}
