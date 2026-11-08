package database.inventory;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import database.logs.*;
import database.products.*;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    /*
    holds the inventory of the company
     */
    private ObservableList<ItemHolder> itemHolderList;
    private ObservableList<Activity> activityList;
    private ObservableList<Transaction> transactionList;

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
    public ItemHolder getItemHolderByUid(String uid){
        return this.itemHolderList.stream().filter(itemHolder -> itemHolder.getItem().getUid().equals(uid)).findFirst().orElse(null);
    }

    public Item getItemById(String uid){
        ItemHolder holder = getItemHolderByUid(uid);

        return holder==null ? null : holder.getItem();
    }

    // ill add these for now
    public void addItemToInventory(Item item, double itemInStock, double lowStockLimit){
        if (item==null)
            throw new NullPointerException("No such item");
        if (this.getItemById(item.getUid())!=null)
            throw new IllegalArgumentException("Item already in inventory");

        this.itemHolderList.add(new ItemHolder(item, itemInStock, lowStockLimit));
    }

    public void removeItemInInventory(String uid){
        ItemHolder itemHolder = this.getItemHolderByUid(uid);
        if (itemHolder==null)
            throw new NullPointerException("Item already not in inventory");

        this.itemHolderList.remove(itemHolder);
    }

    public void restockItem(String uid, double amount){
        ItemHolder hold = this.getItemHolderByUid(uid);
        if(hold==null)
            throw new NullPointerException("Item is not in inventory");
        hold.updateItemInStock(amount,"add");
    }

    public void sellItem(String uid, double amount){
        ItemHolder hold = this.getItemHolderByUid(uid);
        if(hold==null)
            throw new NullPointerException("Item is not in inventory");
        if(hold.getItemInStock()<amount)
            throw new IllegalArgumentException("Amount to be deducted is more than in stock");
        hold.updateItemInStock(amount,"subtract");
    }

    public void replaceItemInInventory(String oldUid, Item newItem, double newItemInStock, double newLowStockLimit){
        ItemHolder itemHolder = this.getItemHolderByUid(oldUid);

        if (itemHolder==null)
            throw new NullPointerException("No such item exists");
        if (newItem==null)
            throw new NullPointerException("No such item passed");
        int i = this.itemHolderList.indexOf(itemHolder);
        this.itemHolderList.set(i, new ItemHolder(newItem, newItemInStock, newLowStockLimit));
    }

    public void setItemHolderInStock(String uid, double inStock){
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if (itemHolder == null)
            throw new NullPointerException("No such item exists");
        if (inStock<0)
            throw new IllegalArgumentException("Item cannot have negative stock");
        itemHolder.updateItemInStock(inStock, "override");
    }

    public void setItemHolderLowInStock(String uid, double inStock){
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if (itemHolder==null)
            throw new NullPointerException("No such item exists");
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
}
