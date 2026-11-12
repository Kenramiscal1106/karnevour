package application.services;

import database.Database;
import database.inventory.Inventory;
import database.products.Item;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class InventoryOperations {
    protected static Inventory inventory = Inventory.getInstance(); 

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
        private final SimpleStringProperty uid = new SimpleStringProperty();
        private final SimpleObjectProperty<Item> item = new SimpleObjectProperty<>();
        private final SimpleStringProperty unit = new SimpleStringProperty();
        private final SimpleDoubleProperty itemInStock = new SimpleDoubleProperty();
        private final SimpleDoubleProperty lowStockLimit = new SimpleDoubleProperty();

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    inventory.addItem(uid.get(), item.get(), unit.get(), itemInStock.get(), lowStockLimit.get());
                    return null;
                }
            };
        }
    };

    public Service<Void> removeItemService = new Service<Void>() {
        private final SimpleStringProperty uid = new SimpleStringProperty();

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    inventory.removeItemInInventory(String.valueOf(uid));
                    return null;
                }
            };
        }
    };

    // general update stock service
    public Service<Void> updateStockService = new Service<Void>() {
        private final SimpleStringProperty uid = new SimpleStringProperty();
        private final SimpleDoubleProperty amount = new SimpleDoubleProperty();
        private final SimpleStringProperty actionType = new SimpleStringProperty();

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    if (actionType.get().equalsIgnoreCase("restock")) {
                        inventory.restockItem(uid.get(), amount.get());
                        return null;
                    }
                    if (actionType.get().equalsIgnoreCase("sell")) {
                        inventory.sellItem(uid.get(), amount.get());
                        return null;
                    }
                    if (actionType.get().equalsIgnoreCase("override")) {
                        inventory.setItemHolderInStock(uid.get(), amount.get());
                        return null;
                    }
                    throw new IllegalArgumentException("No stock type");
                }
            };
        }
    };

    public Service<Void> setLowStockService = new Service<Void>() {
        private final SimpleStringProperty uid = new SimpleStringProperty();
        private final SimpleDoubleProperty inStock = new SimpleDoubleProperty();

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    inventory.setItemHolderLowInStock(uid.get(), inStock.get());
                    return null;
                }
            };
        }
    };

    public Service<Void> setInStockService = new Service<Void>() {
        private final SimpleStringProperty uid = new SimpleStringProperty();
        private final SimpleDoubleProperty inStock = new SimpleDoubleProperty();

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    inventory.setItemHolderInStock(uid.get(), inStock.get());
                    return null;
                }
            };
        }
    };

    public Service<Void> replaceInventoryItemService = new Service<Void>() {
        private final SimpleObjectProperty<Item> newItem = new SimpleObjectProperty<>();

        private final SimpleStringProperty newUnit = new SimpleStringProperty();
        private final SimpleStringProperty oldUid = new SimpleStringProperty();
        private final SimpleStringProperty newUid = new SimpleStringProperty();
        private final SimpleDoubleProperty newItemInStock = new SimpleDoubleProperty();
        private final SimpleDoubleProperty newLowStockLimit = new SimpleDoubleProperty();

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    inventory.replaceItemInInventory(oldUid.get(), newUid.get(), newItem.get(), newUnit.get(), newItemInStock.get(), newLowStockLimit.get());
                    return null;
                }
            };
        }
    };
}
