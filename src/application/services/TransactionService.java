package application.services;

import database.Database;
import database.inventory.Inventory;
import database.products.Item;
import database.CashRegister;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class TransactionService extends InventoryOperations {
    protected static CashRegister cashRegister = CashRegister.getInstance();

    public Service<Void> sellProductService = new Service<> () {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    return null;
                }
            };
        }
    };

    public Service<Void> refundProductService = new Service<>() {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    return null;
                }
            };
        }
    };

    public Service<Void> changeProductPriceService = new Service<Void>() {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    return null;
                }
            };
        }
    };

    public Service<Void> collectDonatedMoneyService = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    return null;
                }
            };
        }
    };
}
