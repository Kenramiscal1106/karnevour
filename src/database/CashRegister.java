package database;

import database.logs.*;
import database.inventory.Inventory;
import database.items.Product;
import database.user.User;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDate;
import java.util.UUID;

/**
 * A Class to handle all transactions happening in the app.
 *
 * <p>
 *     A class that handles interactions between the {@link Inventory} and {@link User} to sell {@link Product}.
 *     A singleton class.
 * </p>
 */
public class CashRegister {
    private final static CashRegister instance = new CashRegister(0, 0);
    private final static Logger lg = Logger.getInstance();

    protected SimpleDoubleProperty currentBalance = new SimpleDoubleProperty();
    protected SimpleDoubleProperty totalSales = new SimpleDoubleProperty();
    protected SimpleIntegerProperty salesCount = new SimpleIntegerProperty();
    private final Inventory inventory = Inventory.getInstance();

    private CashRegister(double currentBalance, int totalSales) {
        this.currentBalance.set(currentBalance);
        this.totalSales.set(totalSales);
    }

    /**
     * Returns the {@link CashRegister} instance
     * @return {@link CashRegister} instance
     */
    public static CashRegister getInstance() {
        return instance;
    }

    /**
     * Returns the {@param totalSales}.
     * @return {@param totalSales}
     */
    public double getTotalSales() {
        return totalSales.get();
    }

    /**
     * Returns the {@param salesCount}.
     * @return {@param salesCount}
     */
    public int getSalesCount(){
        return salesCount.get();
    }

    /**
     * Returns the {@param currentBalance}.
     * @return {@param currentBalance}
     */
    public double getCurrentBalance() {
        return currentBalance.get();
    }

    /**
     * A method that sells the {@link Product} to the buyer via {@link User} discretion, adds a {@link SaleLog} via {@link Logger}
     *
     * @param uid unique id of {@link Product}
     * @param unit a non-negative value
     * @param payment a non-negative value
     * @param user a non-null {@link User}
     *
     * @throws NullPointerException if {@link database.inventory.ItemHolder} does not exist
     * @throws IllegalArgumentException if {@param unit} is a negative or zero value
     * @throws IllegalArgumentException if {@param payment} is less than the price of {@link Product}*{@param unit}
     */
    public void sellItem(String uid, double unit, double payment, User user, String buyerName) {
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

        lg.addLog(new SaleLog(
                "Transaction Processed: Item Sold",
                LocalDate.now().toString(),
                "Item ("+inventory.getItemById(uid).getItemName()+") is sold with "+unit+" sold, by employee "+user.getName()+" to "+buyerName+", for "+(inventory.getPrice(uid)*unit)+"[Payment: "+payment+", Change: "+change+"]",
                user.getName(),
                "+S"+ UUID.randomUUID(),
                uid,
                inventory.getPrice(uid)*unit,
                unit,
                payment,
                change,
                user.getClass().getSimpleName()
        ));


        salesCount.add(1);
        totalSales.add(unit*(inventory.getPrice(uid)));
    }
    /**
     * A method that refunds the {@link Product} to the {@link Inventory} via {@link User} discretion, adds a {@link RefundLog} via {@link Logger}
     *
     * @param uid unique id of {@link Product}
     * @param unit a non-negative value
     * @param payment a non-negative value
     * @param user a non-null {@link User}
     *
     * @throws NullPointerException if {@link database.inventory.ItemHolder} does not exist
     * @throws IllegalArgumentException if {@param unit} is a negative or zero value
     * @throws IllegalArgumentException if {@param payment} is less than the price of {@link Product}*{@param unit}
     */
    public void refundItem(String uid, double unit, User user) {
        var itemHolder = inventory.getItemHolderByUid(uid);
        if (itemHolder==null)
            throw new NullPointerException("Item does not exist");
        if (unit<=0)
            throw new IllegalArgumentException("Refund quantity must be greater than 0");
        if (itemHolder.getItem() instanceof Product product) {
            lg.addLog(new RefundLog(
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

        lg.addLog(new DonationLog(
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
