package application.services;

import database.inventory.Inventory;
import database.inventory.ItemHolder;
import database.items.Item;
import javafx.beans.property.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class UpdateStockService  extends Service<Void> {
    private final Inventory inventory = Inventory.getInstance();
    private final StringProperty actionType = new SimpleStringProperty();
    private final ObjectProperty<ItemHolder> itemHolder = new SimpleObjectProperty<>();
    private final DoubleProperty amount = new SimpleDoubleProperty();

    public ObjectProperty<ItemHolder> itemHolderObjectProperty() {
        return itemHolder;
    }

    public StringProperty actionTypeProperty() {
        return actionType;
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public UpdateStockService(String actionType) {
        this.actionType.set(actionType);
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                if (actionType.get().equalsIgnoreCase("restock")) {
                    inventory.restockItem(itemHolder.get().getUid(), amount.get(), SessionHandler.getInstance().getCurrentUser());
                    return null;
                }
                if (actionType.get().equalsIgnoreCase("sell")) {
                    inventory.reduceItemAmount(itemHolder.get().getUid(), amount.get(), SessionHandler.getInstance().getCurrentUser());
                    return null;
                }
                if (actionType.get().equalsIgnoreCase("override")) {
                    inventory.setItemHolderInStock(itemHolder.get().getUid(), amount.get(), SessionHandler.getInstance().getCurrentUser());
                    return null;
                }
                throw new IllegalArgumentException("No stock type");
//                    return null;
            }
        };
    }
}
//
//public class setLowStockService extends Service<Void> {
//    private final Inventory inventory;
//    private final String uid;
//    private final double inStock;
//
//    public setLowStockService(Inventory inventory, String uid, double inStock) {
//        this.inventory = inventory;
//        this.uid = uid;
//        this.inStock = inStock;
//    }
//
//    @Override
//    protected Task<Void> createTask() {
//        return new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                inventory.setItemHolderLowInStock(uid, inStock, SessionHandler.getInstance().getCurrentUser());
//                return null;
//            }
//        };
//    }
//}
//
//public class setInStockService extends Service<Void> {
//    private final Inventory inventory;
//    private final String uid;
//    private final double inStock;
//
//    public setInStockService(Inventory inventory, String uid, double inStock) {
//        this.inventory = inventory;
//        this.uid = uid;
//        this.inStock = inStock;
//    }
//
//    @Override
//    protected Task<Void> createTask() {
//        return new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                inventory.setItemHolderInStock(uid, inStock, SessionHandler.getInstance().getCurrentUser());
//                return null;
//            }
//        };
//    }
//}
//
//public class replaceInventoryItemService extends Service<Void> {
//    private final Inventory inventory;
//    private final Item newItem;
//
//    private final String newUnit;
//    private final String oldUid;
//    private final String newUid;
//    private final double newItemInStock;
//    private final double newLowStockLimit;
//
//    public replaceInventoryItemService(Inventory inventory, String oldUid, String newUid, Item newItem, String newUnit, double newItemInStock, double newLowStockLimit) {
//        this.inventory = inventory;
//        this.oldUid = oldUid;
//        this.newItem = newItem;
//        this.newItemInStock = newItemInStock;
//        this.newUid = newUid;
//        this.newUnit = newUnit;
//        this.newLowStockLimit = newLowStockLimit;
//    }
//
//    @Override
//    protected Task<Void> createTask() {
//        return new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                inventory.replaceItemInInventory(oldUid, newUid, newItem, newUnit, newItemInStock, newLowStockLimit, SessionHandler.getInstance().getCurrentUser());
//                return null;
//            }
//        };
//    }
//}
//
//}
