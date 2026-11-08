package application.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import database.inventory.*;
//import database.pictures.*;
import database.products.*;

// TODO: make LoadService()

public class InventoryService {
    //private Inventory inventory = new Inventory();

    // skeleton code for loading inventory items
    public class LoadService extends Service<Inventory> {
        @Override
        protected Task<Inventory> createTask() {
            return new Task<>() {
                protected Inventory call() throws Exception {



                    return null;
                }
            };
        }
    }

    public class addItemService extends Service<Void> {
        private Inventory inventory;
        private Item item;
        private double stock;
        private double limit;

        public addItemService(Inventory inventory, Item item, double stock, double limt) {
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
                    inventory.addItemToInventory(item, stock, limit);
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

        public class setLowStockService extends Service<Void> {
            private Inventory inventory;
            private String uid;
            private double inStock;

            public void SetLowStockService(Inventory inventory, String uid, double inStock){
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

            public void SetInStockService(Inventory inventory, String uid, double inStock){
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
            private Item item;
            private String oldUid;
            private double newItemInStock;
            private double newLowStockLimit;

            public replaceInventoryItemService(Inventory inventory, String oldUid, Item item, double newItemInStock, double newLowStockLimit) {
                this.inventory = inventory;
                this.oldUid = oldUid;
                this.item = item;
                this.newItemInStock = newItemInStock;
                this.newLowStockLimit = newLowStockLimit;
            }

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        inventory.replaceItemInInventory(oldUid, item, newItemInStock, newLowStockLimit);
                        return null;
                    }
                };
            }
        }


    }
}
