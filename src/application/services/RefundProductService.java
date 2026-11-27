package application.services;

import database.CashRegister;
import database.inventory.Inventory;
import database.logs.Logger;
import database.logs.SaleLog;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RefundProductService extends Service<Void> {

    private final ObjectProperty<SaleLog> saleLogProperty = new SimpleObjectProperty<>();

    private final CashRegister cashRegister = CashRegister.getInstance();
    private final Inventory inventory = Inventory.getInstance();

    public ObjectProperty<SaleLog> saleLogProperty() {return saleLogProperty;}
    @Override
    protected Task<Void> createTask(){
        return new Task<>(){
            @Override
            protected Void call() throws Exception{
                String uid = saleLogProperty.get().productUid;
                double amount = saleLogProperty.get().qty;

                if (amount <= 0)
                    throw new IllegalArgumentException("Refund quantity must be more than 0.");

                SaleLog sale = saleLogProperty.get();
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
