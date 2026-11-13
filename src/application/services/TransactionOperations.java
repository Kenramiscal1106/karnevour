package application.services;

import database.inventory.Inventory;
import database.CashRegister;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

// TODO: loadCashRegister();

public class TransactionOperations extends InventoryOperations {
    protected static CashRegister cashRegister = CashRegister.getInstance();
    private Inventory inventory = Inventory.getInstance();

    public Service<Void> loadCashRegister = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    return null; //load from savefile
                }
            };
        }
    };

    public Service<Void> sellProductService = new Service<> () {
        private final SimpleStringProperty uid = new SimpleStringProperty();
        private final SimpleDoubleProperty amount = new SimpleDoubleProperty();
        private final SimpleDoubleProperty payment = new SimpleDoubleProperty();

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    cashRegister.sellItem(uid.get(), amount.get(), payment.get(), SessionHandler.getInstance().getCurrentUser());
                    inventory.reduceItemAmount(uid.get(), amount.get(), SessionHandler.getInstance().getCurrentUser());
                    return null;
                }
            };
        }
    };

    public Service<Void> refundProductService = new Service<>() {
        private final SimpleStringProperty uid = new SimpleStringProperty();
        private final SimpleDoubleProperty amount = new SimpleDoubleProperty();

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    cashRegister.refundItem(uid.get(), amount.get(), SessionHandler.getInstance().getCurrentUser());
                    inventory.restockItem(uid.get(), amount.get(), SessionHandler.getInstance().getCurrentUser());
                    return null;
                }
            };
        }
    };

    public Service<Void> changeProductPriceService = new Service<Void>() {
        private final SimpleStringProperty uid = new SimpleStringProperty();
        private final SimpleDoubleProperty amount = new SimpleDoubleProperty();

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    inventory.setItemPrice(uid.get(), amount.get(), SessionHandler.getInstance().getCurrentUser());
                    return null;
                }
            };
        }
    };

    public Service<Void> collectDonatedMoneyService = new Service<Void>() {
        private final SimpleStringProperty name = new SimpleStringProperty();
        private final SimpleDoubleProperty amount = new SimpleDoubleProperty();

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    cashRegister.recieveDonateMoney(name.get(), amount.get());
                    return null;
                }
            };
        }
    };
}
