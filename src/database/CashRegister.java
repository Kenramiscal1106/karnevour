package database;

import database.logs.*;
import database.inventory.Inventory;
import database.user.User;

import java.time.LocalDate;
import java.util.UUID;

public class CashRegister {
    private static CashRegister instance;

    private double currentBalance;
    private int totalSales;
    private Inventory inventory;

    public CashRegister(double currentBalance, int totalSales, Inventory inventory) {
        this.currentBalance = currentBalance;
        this.totalSales = totalSales;
        this.inventory = inventory;
    }

    public static CashRegister getInstance() {
        return instance;
    }


    // getters
    public int getTotalSales() {
        return totalSales;
    }
    public double getCurrentBalance() {
        return currentBalance;
    }
    public Inventory getInventory() {
        return inventory;
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

        inventory.addToLogList(new Transaction(
                "Transaction Processed: Item Sold",
                LocalDate.now().toString(),
                "Item ("+inventory.getItemById(uid).getSpecificItemType()+") is sold with "+unit+" sold, by employee "+user.getName()+", for "+(inventory.getPrice(uid)*unit),
                user.getName(),
                "+S"+UUID.randomUUID().toString()
        ));

        inventory.reduceItemAmount(uid,unit,user);
        cashIn(payment);
        totalSales++;
    }

    // TODO: again, unit is string
    // CHANGES: added exception handling, also inventory editing, unless sa services sya lalagay
    public void refundItem(String uid, double unit, User user) {
        if (inventory.getItemHolderByUid(uid)==null)
            throw new NullPointerException("Item does not exist");
        if (unit<=0)
            throw new IllegalArgumentException("Refund quantity must be greater than 0");

        inventory.addToLogList(new Transaction(
                "Transaction Processed: Item Refund",
                LocalDate.now().toString(),
                "Item ("+inventory.getItemById(uid).getSpecificItemType()+") is refunded with employee "+user.getName()+"\'s discretion",
                user.getName(),
                "+R"+UUID.randomUUID().toString()
        ));

        cashOut(inventory.getPrice(uid)*unit);
    }

    private void cashIn(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than zero");
        this.currentBalance += amount;
    }

    private void cashOut(double amount) {
        if (amount > currentBalance)
            throw new IllegalArgumentException("Insufficient cash in drawer");
        this.currentBalance -= amount;
    }

    // help me here
    // TODO: add logs for this
    public void recieveDonateMoney(String name, double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Donation amount must be greater than zero.");

        inventory.addToLogList(new Transaction(
                "Transaction Processed: Donation",
                LocalDate.now().toString(),
                name + " donated "+amount,
                name,
                "+D"+UUID.randomUUID().toString()
        ));

        cashIn(amount);
// maybe add donor name tas log din
    }

    void setCurrentBalance(double currentBalance) {currentBalance=currentBalance;}
    void setTotalSales(double totalSales) {totalSales=totalSales;}

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
