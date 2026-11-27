package application.services;

import database.inventory.Inventory;
import database.inventory.ItemHolder;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class UpdatePriceService extends Service<Void> {
    private final Inventory inventory = Inventory.getInstance();
    private final DoubleProperty newPrice = new SimpleDoubleProperty();

    private final ObjectProperty<ItemHolder> itemHolderProperty = new SimpleObjectProperty<>();

    public DoubleProperty newPriceProperty() {
        return newPrice;
    }

    public ObjectProperty<ItemHolder> itemHolderObjectProperty() {
        return itemHolderProperty;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                inventory.setItemPrice(itemHolderProperty.get().getUid(), newPrice.get(), SessionHandler.getInstance().getCurrentUser());
                return null;
            }
        };
    }
}