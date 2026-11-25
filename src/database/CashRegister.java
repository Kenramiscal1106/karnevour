package database;

import database.logs.*;
import database.inventory.Inventory;
import database.items.Product;
import database.user.User;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDate;
import java.util.UUID;

public class CashRegister {
    private final static CashRegister instance = new CashRegister(0, 0);

    protected SimpleDoubleProperty currentBalance = new SimpleDoubleProperty();
    protected SimpleIntegerProperty totalSales = new SimpleIntegerProperty();
    private final Inventory inventory = Inventory.getInstance();

    public CashRegister(double currentBalance, int totalSales) {
        this.currentBalance.set(currentBalance);
        this.totalSales.set(totalSales);
    }

    public static CashRegister getInstance() {
        return instance;
    }

    // getters
    public int getTotalSales() {
        return totalSales.get();
    }
    public double getCurrentBalance() {
        return currentBalance.get();
    }

    // methods
    // TODO: make it readable, unit is in string
    // CHANGES: added exception handling, also inventory editing, unless sa services sya lalagay
    public void sellItem(String uid, double unit, double payment, User user) {
        if(inventory.getItemHolderByUid(uid)==null)
            throw new NullPointerException("Item does not exist");
        if(unit<=0)
            throw new IllegalArgumentException("Item quantity must be greater than 0");
        if(payment < inventory.getPrice(uid)*unit)
            throw new IllegalArgumentException("The buyer does not have enough cash.");

        double change = payment-(inventory.getPrice(uid)*unit);
        inventory.reduceItemAmount(uid,unit,user);
        cashIn(payment);

        if (change!=0)
            cashOut(change);

        inventory.addToLogList(new SaleLog(
                "Transaction Processed: Item Sold",
                LocalDate.now().toString(),
                "Item ("+inventory.getItemById(uid).getItemName()+") is sold with "+unit+" sold, by employee "+user.getName()+", for "+(inventory.getPrice(uid)*unit)+"[Payment: "+payment+", Change: "+change+"]",
                user.getName(),
                "+S"+ UUID.randomUUID(),
                uid,
                inventory.getPrice(uid)*unit,
                unit,
                payment,
                change
        ));


        totalSales.add(1);
    }

    // TODO: again, unit is string
    // CHANGES: added exception handling, also inventory editing, unless sa services sya lalagay
    public void refundItem(String uid, double unit, User user) {
        var itemHolder = inventory.getItemHolderByUid(uid);
        if (itemHolder==null)
            throw new NullPointerException("Item does not exist");
        if (unit<=0)
            throw new IllegalArgumentException("Refund quantity must be greater than 0");
        if (itemHolder.getItem() instanceof Product product) {
            inventory.addToLogList(new RefundLog(
                    "Transaction Processed: Item Refund",
                    LocalDate.now().toString(),
                    "Item ("+itemHolder.getItem().getItemName()+") is refunded with employee "+user.getName()+"'s discretion",
                    user.getName(),
                    "+R"+ UUID.randomUUID(),
                    itemHolder.getUid(),
                    product.getPrice()*unit,
                    unit
            ));
            cashOut(product.getPrice()*unit);
        }
    }

    private void cashIn(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than zero");
        this.currentBalance.set(this.currentBalance.get() + amount);
    }

    private void cashOut(double amount) {
        if (amount > currentBalance.get())
            throw new IllegalArgumentException("Insufficient cash in drawer");
        this.currentBalance.set(this.currentBalance.get() - amount);
    }

    // help me here
    // TODO: add logs for this
    public void recieveDonateMoney(String name, double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Donation amount must be greater than zero.");

        inventory.addToLogList(new DonationLog(
                "Transaction Processed: Donation",
                LocalDate.now().toString(),
                name + " donated "+amount,
                name,
                "+D"+ UUID.randomUUID(),
                amount
        ));

        cashIn(amount);
// maybe add donor name tas log din
    }

    void setCurrentBalance(double currentBalance) {this.currentBalance.set(currentBalance);}
    void setTotalSales(int totalSales) {this.totalSales.set(totalSales);}

//    wtf is this
//    public void addToLogList(Logable log){
//        switch (log) {
//            case null -> throw new NullPointerException("Log does not exist");
//            case Activity activity -> this.activityList.add(activity);
//            case Transaction transaction -> this.transactionList.add(transaction);
//            default -> {
//            }
//    }
}
