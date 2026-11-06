package database.inventory;

import database.logs.*;
import database.products.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    /*
    holds the inventory of the company
     */
    private Map<String, ItemHolder> itemHolderMap;
    private List<Activity> activityList;
    private List<Transaction> transactionList;

    public Inventory(){
        this.itemHolderMap = new HashMap<>();
        this.activityList = new ArrayList<>();
        this.transactionList = new ArrayList<>();
    }

    // getters
    public Map<String, ItemHolder> getItemHolderMap() {return this.itemHolderMap;}
    public List<Activity> getActivityList() {return this.activityList;}
    public List<Transaction> getTransactionList() {return this.transactionList;}
    public String getItemClass(String uid) {return this.itemHolderMap.get(uid).getClass().getSimpleName();}

    // shortcuts
    public ItemHolder getItemHolderByUid(String uid){
        return this.itemHolderMap.get(uid);
    }

    public Item getItemById(String uid){
        return this.itemHolderMap.get(uid).getItem();
    }

    // ill add these for now
    public void addItemToInventory(Item item, double itemInStock, double lowStockLimit){
        if (item==null)
            throw new NullPointerException("No such item");
        if (this.itemHolderMap.containsKey(item.getUid()))
            throw new IllegalArgumentException("Item already in inventory");

        this.itemHolderMap.put(item.getUid(),new ItemHolder(item, itemInStock, lowStockLimit));
    }

    public void removeItemInInventory(String uid){
        if (!this.itemHolderMap.containsKey(uid))
            throw new IllegalArgumentException("Item already not in inventory");

        this.itemHolderMap.remove(uid);
    }

    public void restockItem(String uid, double amount){
        ItemHolder hold = this.itemHolderMap.get(uid);
        if(hold==null)
            throw new NullPointerException("Item is not in inventory");
        hold.updateItemInStock(amount,"add");
    }

    public void sellItem(String uid, double amount){
        ItemHolder hold = this.itemHolderMap.get(uid);
        if(hold==null)
            throw new NullPointerException("Item is not in inventory");
        if(hold.getItemInStock()<amount)
            throw new IllegalArgumentException("Amount to be deducted is more than in stock");
        hold.updateItemInStock(amount,"subtract");
    }

    public void replaceItemInInventory(String oldUid, Item newItem, double newItemInStock, double newLowStockLimit){
        if (this.itemHolderMap.get(oldUid)==null)
            throw new NullPointerException("No such item exists");
        if (newItem==null)
            throw new NullPointerException("No such item passed");
        this.itemHolderMap.replace(newItem.getUid(), this.itemHolderMap.get(oldUid), new ItemHolder(newItem, newItemInStock, newLowStockLimit));
    }

    public void setItemHolderInStock(String uid, double inStock){
        if (this.itemHolderMap.get(uid) == null)
            throw new NullPointerException("No such item exists");
        if (inStock<0)
            throw new IllegalArgumentException("Item cannot have negative stock");
        this.itemHolderMap.get(uid).updateItemInStock(inStock, "override");
    }

    public void setItemHolderLowInStock(String uid, double inStock){
        if (this.itemHolderMap.get(uid)==null)
            throw new NullPointerException("No such item exists");
        if (inStock<0)
            throw new IllegalArgumentException("Limit cannot be in negative");

        this.itemHolderMap.get(uid).setLowStockLimit(inStock);
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
