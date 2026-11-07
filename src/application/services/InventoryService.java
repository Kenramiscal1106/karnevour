package application.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import database.inventory.*;
import database.pictures.*;
import database.products.*;

public class InventoryService {
    //private Inventory inventory = new Inventory();

    // skeleton code for loading inventory items
    public class LoadService extends Service<Inventory> {
        @Override
        protected Task<Inventory> createTask() {
            return new Task<>() {
                protected Inventory call() throws Exception {

                    // load inventory program here

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


}
