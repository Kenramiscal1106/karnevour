package application.services;

import application.models.CartLine;
import database.inventory.Inventory;
import database.CashRegister;
import database.logs.Logger;
import database.logs.SaleLog;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

// TODO: loadCashRegister();

public class SalesOperations {
    protected static CashRegister cashRegister = CashRegister.getInstance();
    private Inventory inventory = Inventory.getInstance();
    public final SimpleStringProperty refundUID = new SimpleStringProperty();
    public final SimpleDoubleProperty refundAmount = new SimpleDoubleProperty();
    public final SimpleDoubleProperty refundSoldQty = new SimpleDoubleProperty();
    public final SimpleObjectProperty<SaleLog> refundSale = new SimpleObjectProperty<>();


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
        @Override
        protected Task<Void> createTask(){
            return new Task<Void>(){
               @Override
                protected Void call() throws Exception{
                   String uid = refundUID.get();
                   double amount = refundAmount.get();

                   if (amount <= 0 || amount > refundSoldQty.get())
                       throw new IllegalArgumentException("Refund quantity must be more than 0 and at most " + refundSoldQty.get() + ".");

                   SaleLog sale = refundSale.get();
                   var user = SessionHandler.getInstance().getCurrentUser();
                   var refund = new FutureTask<Void>(() -> {
                       cashRegister.refundItem(uid, amount, user);
                       inventory.restockItem(uid, amount, user);
                       Logger.getInstance().removeLog(sale);
                       return null;
                   });
                   Platform.runLater(refund);

                   try{
                       refund.get();
                   } catch (ExecutionException e){
                       throw (Exception) e.getCause();
                   }
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

    public class checkoutService extends Service<Void>{
        private final List<CartLine> lines;

        public checkoutService(List<CartLine> lines){
            this.lines = List.copyOf(lines);
        }

        @Override
        protected Task<Void> createTask(){
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    var user = SessionHandler.getInstance().getCurrentUser();
                    var sell = new FutureTask<Void>(() -> {
                        for (CartLine line: lines){
                            cashRegister.sellItem(line.getItemHolder().getUid(), line.getQty(), line.getTotal(), user);
                        }
                        return null;
                    });
                    Platform.runLater(sell);

                    try{
                        sell.get();
                    } catch (ExecutionException e){
                        throw (Exception) e.getCause();
                    }
                    return null;
                }
            };
        }
    }
}
