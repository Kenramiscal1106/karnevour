package database.inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import database.logs.*;
import database.products.*;

public class Inventory {
    /*
    holds the inventory of the company
     */
    private ObservableList<ItemHolder> itemHolderList;
    private ObservableList<Activity> activityList;
    private ObservableList<Transaction> transactionList;

    private final static Inventory instance =  new Inventory();

    public static Inventory getInstance() {
        return instance;
    }

    public Inventory(){
        this.itemHolderList = FXCollections.observableArrayList();
        this.activityList = FXCollections.observableArrayList();
        this.transactionList = FXCollections.observableArrayList();
    }

    // getters
    public ObservableList<ItemHolder> getItemHolderList() {return this.itemHolderList;}
    public ObservableList<Activity> getActivityList() {return this.activityList;}
    public ObservableList<Transaction> getTransactionList() {return this.transactionList;}
    public String getItemClass(String uid) {
        ItemHolder item = getItemHolderByUid(uid);

        return item==null ? null : item.getItem().getClass().getSimpleName();
    }

    // shortcuts

    private ItemHolder itemEnsureExists(String uid) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if(itemHolder == null || itemHolder.getItem() == null)
            throw new IllegalArgumentException("No such items exist.");

        return itemHolder;
    }

    public ItemHolder getItemHolderByUid(String uid){
        return this.itemHolderList.stream().filter(itemHolder -> itemHolder.getUid().equals(uid)).findFirst().orElse(null);
    }

    public Item getItemById(String uid){
        ItemHolder holder = getItemHolderByUid(uid);
        return holder==null ? null : holder.getItem();
    }

    // ill add these for now
    public void addItem(String uid, Item item, String unit, double itemInStock, double lowStockLimit){
        if (item==null)
            throw new NullPointerException("No such item");
        if (this.getItemHolderByUid(uid).getItem()!=null)
            throw new IllegalArgumentException("Item already in inventory");

        this.itemHolderList.add(new ItemHolder(uid, item, unit, itemInStock, lowStockLimit));
    }

    public void removeItemInInventory(String uid){
        ItemHolder itemHolder = itemEnsureExists(uid);
        this.itemHolderList.remove(itemHolder);
    }

    public void restockItem(String uid, double amount){
        ItemHolder hold = itemEnsureExists(uid);
        hold.updateItemInStock(amount,"add");
    }

    public void sellItem(String uid, double amount){
        ItemHolder hold = itemEnsureExists(uid);
        if(hold.getItemInStock()<amount)
            throw new IllegalArgumentException("Amount to be deducted is more than in stock");
        hold.updateItemInStock(amount,"subtract");
    }

    public void replaceItemInInventory(String oldUid, String newUid, Item newItem, String newUnit, double newItemInStock, double newLowStockLimit){
        ItemHolder itemHolder = itemEnsureExists(oldUid);
        if (newItem==null)
            throw new NullPointerException("No such item passed");
        int i = this.itemHolderList.indexOf(itemHolder);
        this.itemHolderList.set(i, new ItemHolder(newUid, newItem, newUnit, newItemInStock, newLowStockLimit));
    }

    public void setItemHolderInStock(String uid, double inStock){
        ItemHolder itemHolder = itemEnsureExists(uid);
        if (inStock<0)
            throw new IllegalArgumentException("Item cannot have negative stock");
        itemHolder.updateItemInStock(inStock, "override");
    }

    public void setItemHolderLowInStock(String uid, double inStock){
        ItemHolder itemHolder = itemEnsureExists(uid);
        if (inStock<0)
            throw new IllegalArgumentException("Limit cannot be in negative");

        itemHolder.setLowStockLimit(inStock);
    }

    public void addToLogList(Logable log){
        switch (log) {
            case null -> throw new NullPointerException("Log does not exist");
            case Activity activity -> this.activityList.add(activity);
            case Transaction transaction -> this.transactionList.add(transaction);
            default -> {
            }
        }

    }

    public double getPrice(String uid) {
        ItemHolder itemHolder = itemEnsureExists(uid);

        Item item = itemHolder.getItem();

        if(!(item instanceof Product)) {
            throw new IllegalArgumentException("Not a product item");
        }
        return ((Product) item).getPrice();
    }

    public void setPrice(String uid, double newPrice) {
        if (newPrice < 0)
            throw new IllegalArgumentException("Price cannot be less than zero.");

        ItemHolder itemHolder = itemEnsureExists(uid);
        Item item = itemHolder.getItem();

        if(item instanceof Product) {
            ((Product) item).setPrice(newPrice);
        }   else {
            throw new IllegalArgumentException("Not a product item");
        }
    }
}
