package application.services;

import database.Database;
import database.inventory.Inventory;
//import database.inventory.ItemHolder;
import database.inventory.ItemHolder;
import database.products.Item;
//import javafx.collections.FXCollections;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class InventoryService {
    //private Inventory inventory = new Inventory();

    private Inventory inventory = Inventory.getInstance();
    // load only on startup
    public Service<Void> loadService = new Service<>() {
        private final Database database = new Database();

        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                protected Void call() throws Exception {
                    database.loadFromDatabase(inventory);
                    return null;
                }
            };
        }
    };


    public Service<Void> addItemService = new Service<Void>() {
        private final SimpleObjectProperty<Item> item = new SimpleObjectProperty<>();
        private final SimpleDoubleProperty stock = new SimpleDoubleProperty();

        private final SimpleStringProperty unit = new SimpleStringProperty();
        private final SimpleDoubleProperty limit = new SimpleDoubleProperty();
        private final SimpleStringProperty uid = new SimpleStringProperty();


        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    inventory.addItem(uid.get(), item.get(), unit.get(), stock.get(), limit.get());
                    return null;
                }
            };
        }

    };

    public class removeItemService extends Service<Void> {
        private final Inventory inventory;
        private final String uid;

        public removeItemService(Inventory inventory, String uid) {
            this.inventory = inventory;
            this.uid = uid;
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    inventory.removeItemInInventory(uid);
                    return null;
                }
            };
        }
    }

    // general update stock service
    public class updateStockService extends Service<Void> {
        private final Inventory inventory;
        private final String uid;
        private final double amount;
        private final String actionType;

        public updateStockService(Inventory inventory, String uid, double amount, String actionType) {
            this.inventory = inventory;
            this.uid = uid;
            this.amount = amount;
            this.actionType = actionType;
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    if (actionType.equalsIgnoreCase("restock")) {
                        inventory.restockItem(uid, amount);
                        return null;
                    }
                    if (actionType.equalsIgnoreCase("sell")) {
                        inventory.sellItem(uid, amount);
                        return null;
                    }
                    if (actionType.equalsIgnoreCase("override")) {
                        inventory.setItemHolderInStock(uid, amount);
                        return null;
                    }
                    throw new IllegalArgumentException("No stock type");
//                    return null;
                }
            };
        }
    }

    public class setLowStockService extends Service<Void> {
        private final Inventory inventory;
        private final String uid;
        private final double inStock;

        public setLowStockService(Inventory inventory, String uid, double inStock) {
            this.inventory = inventory;
            this.uid = uid;
            this.inStock = inStock;
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    inventory.setItemHolderLowInStock(uid, inStock);
                    return null;
                }
            };
        }
    }

    public class setInStockService extends Service<Void> {
        private final Inventory inventory;
        private final String uid;
        private final double inStock;

        public setInStockService(Inventory inventory, String uid, double inStock) {
            this.inventory = inventory;
            this.uid = uid;
            this.inStock = inStock;
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    inventory.setItemHolderInStock(uid, inStock);
                    return null;
                }
            };
        }
    }

    public class replaceInventoryItemService extends Service<Void> {
        private final Inventory inventory;
        private final Item newItem;

        private final String newUnit;
        private final String oldUid;
        private final String newUid;
        private final double newItemInStock;
        private final double newLowStockLimit;

        public replaceInventoryItemService(Inventory inventory, String oldUid, String newUid, Item newItem, String newUnit, double newItemInStock, double newLowStockLimit) {
            this.inventory = inventory;
            this.oldUid = oldUid;
            this.newItem = newItem;
            this.newItemInStock = newItemInStock;
            this.newUid = newUid;
            this.newUnit = newUnit;
            this.newLowStockLimit = newLowStockLimit;
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    inventory.replaceItemInInventory(oldUid, newUid, newItem, newUnit, newItemInStock, newLowStockLimit);
                    return null;
                }
            };
        }
    }

}
