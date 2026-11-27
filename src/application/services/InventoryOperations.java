package application.services;

import database.Database;
import database.inventory.Inventory;
//import database.inventory.ItemHolder;
import database.logs.Logger;
import database.items.Item;
//import javafx.collections.FXCollections;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class InventoryOperations {
    private Inventory inventory = Inventory.getInstance();
    private Logger logger = Logger.getInstance();

//
//    public Service<Void> addItemService = new Service<Void>() {
//        public final SimpleObjectProperty<Item> item = new SimpleObjectProperty<>();
//        public final SimpleDoubleProperty stock = new SimpleDoubleProperty();
//
//        public final SimpleStringProperty unit = new SimpleStringProperty();
//        public final SimpleDoubleProperty limit = new SimpleDoubleProperty();
//        public final SimpleStringProperty uid = new SimpleStringProperty();
//
//
//        @Override
//        protected Task<Void> createTask() {
//            return new Task<>() {
//                @Override
//                protected Void call() throws Exception {
//                    inventory.addItem(uid.get(), item.get(), unit.get(), stock.get(), limit.get(), SessionHandler.getInstance().getCurrentUser());
//                    return null;
//                }
//            };
//        }
//
//    };
//
//    public class replaceInventoryItemService extends Service<Void> {
//        private final Inventory inventory;
//        private final Item newItem;
//
//        private final String newUnit;
//        private final String oldUid;
//        private final String newUid;
//        private final double newItemInStock;
//        private final double newLowStockLimit;
//
//        public replaceInventoryItemService(Inventory inventory, String oldUid, String newUid, Item newItem, String newUnit, double newItemInStock, double newLowStockLimit) {
//            this.inventory = inventory;
//            this.oldUid = oldUid;
//            this.newItem = newItem;
//            this.newItemInStock = newItemInStock;
//            this.newUid = newUid;
//            this.newUnit = newUnit;
//            this.newLowStockLimit = newLowStockLimit;
//        }
//
//        @Override
//        protected Task<Void> createTask() {
//            return new Task<Void>() {
//                @Override
//                protected Void call() throws Exception {
//                    inventory.replaceItemInInventory(oldUid, newUid, newItem, newUnit, newItemInStock, newLowStockLimit, SessionHandler.getInstance().getCurrentUser());
//                    return null;
//                }
//            };
//        }
//    }

}
