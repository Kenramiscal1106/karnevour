package application;
import database.inventory.Inventory;

public class CashRegister {
    private double currentBalance; // puwede pa tong iexpand into bills para mas maangas
    private int totalSales;
    private Inventory inventory;

    public CashRegister(double currentBalance, int totalSales, Inventory inventory) {
        this.currentBalance = currentBalance;
        this.totalSales = totalSales;
    }

    //getters
    public int getTotalSales() {
        return totalSales;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    // methods
    public void sellItem() {

    }

    public void refundItem() {

    }

    protected double computeBalance() {
        double computedAmount = 0;
        return computedAmount;
    }
}
