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
    public void sellItem(String uid, double amount, double totalPrice) {
    }
    public void refundItem(String uid, double amount, double refundAmount) {
    }

    public void cashIn(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than zero");
        this.currentBalance += amount;
    }

    public void cashOut(double amount, String reason) {
        if (amount > currentBalance)
            throw new IllegalArgumentException("Insufficient cash in drawer");
        this.currentBalance -= amount;
    }

    protected double computeBalance() {
        double computedAmount = 0;
        return computedAmount;
    }

    public void donateMoney(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Donation amount must be greater than zero.");
        }

        this.currentBalance += amount;
        String description = "Donation received";

        // maybe add donor name tas log din
    }

    public void addToLogList(Logable log){
//        switch (log) {
//            case null -> throw new NullPointerException("Log does not exist");
//            case Activity activity -> this.activityList.add(activity);
//            case Transaction transaction -> this.transactionList.add(transaction);
//            default -> {
//            }
    }
}
