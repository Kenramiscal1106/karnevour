package database;

import database.logs.*;
import database.inventory.Inventory;

public class CashRegister {
    private double currentBalance;
    private int totalSales;
    private Inventory inventory;

    public CashRegister(double currentBalance, int totalSales, Inventory inventory) {
        this.currentBalance = currentBalance;
        this.totalSales = totalSales;
        this.inventory = inventory;
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
    public void sellItem(String uid, double unit, double payment) {

        if(payment < inventory.getPrice(uid)*unit)
            throw new IllegalArgumentException("The buyer does not have enough cash.");

        cashIn(payment);
        totalSales++;
    }

    public void refundItem(String uid, double unit) {
        cashOut(inventory.getPrice(uid)*unit);
    }

    public void cashIn(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than zero");
        this.currentBalance += amount;
    }

    public void cashOut(double amount) {
        if (amount > currentBalance)
            throw new IllegalArgumentException("Insufficient cash in drawer");
        this.currentBalance -= amount;
    }

    // help me here
    // TODO: add logs for this
    public void recieveDonateMoney(String name, double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Donation amount must be greater than zero.");

        cashIn(amount);
// maybe add donor name tas log din
    }

//    public void addToLogList(Logable log){
//        switch (log) {
//            case null -> throw new NullPointerException("Log does not exist");
//            case Activity activity -> this.activityList.add(activity);
//            case Transaction transaction -> this.transactionList.add(transaction);
//            default -> {
//            }
//    }
}
