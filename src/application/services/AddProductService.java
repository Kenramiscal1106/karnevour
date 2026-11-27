package application.services;

import database.Database;
import database.inventory.Inventory;
import database.items.Product;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.util.UUID;

public class AddProductService extends Service<Void> {

    private final Inventory inventory = Inventory.getInstance();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final DoubleProperty priceValue = new SimpleDoubleProperty();
    private final StringProperty unitText = new SimpleStringProperty();
    private final DoubleProperty lowStockLimit = new SimpleDoubleProperty();
    private final StringProperty imageUrl = new SimpleStringProperty();

    public StringProperty nameProperty() {return name;}
    public StringProperty descriptionProperty() {return description;}
    public DoubleProperty priceValueProperty() {return priceValue;}
    public StringProperty unitTextProperty() {return unitText;}
    public DoubleProperty lowStockLimitProperty() {return lowStockLimit;}
    public StringProperty imageUrlProperty() {return imageUrl;}

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                Product product = new Product(name.get(), description.get(), priceValue.get());
                inventory.addItem(UUID.randomUUID().toString(), product, unitText.get(), 0, lowStockLimit.get(), SessionHandler.getInstance().getCurrentUser());

                if (!imageUrl.get().isBlank()) {
                    Database.getInstance().savePictureURL(name.get(), imageUrl.get());
                };

                return null;
            }

        };
    }
}
