package application.services;

import database.Database;
import database.inventory.Inventory;
//import database.inventory.ItemHolder;
import database.inventory.ItemHolder;
import database.products.Item;
//import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class InventoryService {
    //private Inventory inventory = new Inventory();

    // load only on startup
    public class LoadService extends Service<Inventory> {
        private Database database = new Database();

//        public LoadService(Inventory inventory) {
//            this.inventory = inventory;
//        }

        @Override
        protected Task<Inventory> createTask() {
            return new Task<>() {
                protected Inventory call() throws Exception {
                   Inventory inventory = new Inventory();
                   database.loadFromDatabase(inventory);
                   return inventory;
                }
            };
        }
    }

    public class getItemHolderService extends Service<ItemHolder> {
        private Inventory inventory;
        private String uid;

        public getItemHolderService(Inventory inventory, String uid) {
            this.inventory = inventory;
            this.uid = uid;
        }

        @Override
        protected Task<ItemHolder> createTask() {
            return new Task<ItemHolder>() {
                @Override
                protected ItemHolder call() throws Exception {
                    return inventory.getItemHolderByUid(uid);
                }
            };
        }
    }


    public class addItemService extends Service<Void> {
        private Inventory inventory;
        private Item item;
        private double stock;
        private double limit;

        public addItemService(Inventory inventory, Item item, double stock, double limit) {
            this.inventory = inventory;
            this.item = item;
            this.stock = stock;
            this.limit = limit;
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception{
//                    inventory.addItem(item, stock, limit);
                    return null;
                }
            };
        }
    }

    public class removeItemService extends Service<Void> {
        private Inventory inventory;
        private String uid;

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
        private Inventory inventory;
        private String uid;
        private double amount;
        private String actionType;

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
                    if(actionType.toLowerCase().equals("restock")) {
                        inventory.restockItem(uid, amount);
                        return null;
                    }
                    if(actionType.toLowerCase().equals("sell")) {
                        inventory.sellItem(uid, amount);
                        return null;
                    }
                    if(actionType.toLowerCase().equals("override")) {
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
        private Inventory inventory;
        private String uid;
        private double inStock;

        public setLowStockService(Inventory inventory, String uid, double inStock){
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
        private Inventory inventory;
        private String uid;
        private double inStock;

        public setInStockService(Inventory inventory, String uid, double inStock){
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
        private Inventory inventory;
        private Item newItem;

        private String newUnit;
        private String oldUid;
        private String newUid;
        private double newItemInStock;
        private double newLowStockLimit;

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
