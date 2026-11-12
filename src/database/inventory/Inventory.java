package database.inventory;

import database.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import database.logs.*;
import database.products.*;

import java.time.LocalDate;
import java.util.UUID;

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
    public void addItem(String uid, Item item, String unit, double itemInStock, double lowStockLimit, User user){
        if (item==null)
            throw new NullPointerException("No such item");
        if (this.getItemHolderByUid(uid).getItem()!=null)
            throw new IllegalArgumentException("Item already in inventory");

        addToLogList(new Activity(
                "Inventory edit: Add Item",
                LocalDate.now().toString(),
                user.getName()+" added item ("+item.getSpecificItemType()+") to the inventory",
                user.getName(),
                "++"+UUID.randomUUID().toString()
        ));

        this.itemHolderList.add(new ItemHolder(uid, item, unit, itemInStock, lowStockLimit));
    }

    public void removeItemInInventory(String uid, User user) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);
        if (itemHolder == null)
            throw new NullPointerException("Item already not in inventory");

        addToLogList(new Activity(
                "Inventory edit: Remove Item",
                LocalDate.now().toString(),
                user.getName() + " removed item (" + itemHolder.getItem().getSpecificItemType() + ") to the inventory",
                user.getName(),
                "--" + UUID.randomUUID().toString()
        ));
    }

    public void removeItemInInventory(String uid){
        ItemHolder itemHolder = itemEnsureExists(uid);
        this.itemHolderList.remove(itemHolder);
    }

    public void restockItem(String uid, double amount, User user) {
        ItemHolder hold = this.getItemHolderByUid(uid);
        if (hold == null)
            throw new NullPointerException("Item is not in inventory");

        addToLogList(new Activity(
                "Inventory edit: Restock Item Amount",
                LocalDate.now().toString(),
                user.getName() + " restocked item (" + hold.getItem().getSpecificItemType() + ") to the inventory (from: " + hold.getItemInStock() + " to: " + (hold.getItemInStock() + amount) + ")",
                user.getName(),
                "+=" + UUID.randomUUID().toString()
        ));
    }

    public void restockItem(String uid, double amount){
        ItemHolder hold = itemEnsureExists(uid);
        hold.updateItemInStock(amount,"add");
    }

    public void reduceItemAmount(String uid, double amount, User user){
        ItemHolder hold = this.getItemHolderByUid(uid);
        if(hold==null)
            throw new NullPointerException("Item is not in inventory");
        if(hold.getItemInStock()<amount)
            throw new IllegalArgumentException("Amount to be deducted is more than in stock");

        addToLogList(new Activity(
                "Inventory edit: Reduce Item Amount",
                LocalDate.now().toString(),
                user.getName()+" restocked item ("+hold.getItem().getSpecificItemType()+") to the inventory (from: "+hold.getItemInStock()+" to: "+(hold.getItemInStock()-amount)+")",
                user.getName(),
                "-="+UUID.randomUUID().toString()
        ));

        hold.updateItemInStock(amount,"subtract");
    }

    public void replaceItemInInventory(String oldUid, String newUid, Item newItem, String newUnit, double newItemInStock, double newLowStockLimit, User user){
        ItemHolder itemHolder = itemEnsureExists(oldUid);

        if (newItem==null)
            throw new NullPointerException("No such item passed");

        int i = this.itemHolderList.indexOf(itemHolder);

        addToLogList(new Activity(
                "Inventory edit: Replace Item",
                LocalDate.now().toString(),
                user.getName()+" replaced item "+itemHolder.getItem()+", with item "+ newItem.getSpecificItemType(),
                user.getName(),
                "+-"+UUID.randomUUID().toString()
        ));

        this.itemHolderList.set(i, new ItemHolder(newUid, newItem, newUnit, newItemInStock, newLowStockLimit));
    }

    public void setItemHolderInStock(String uid, double inStock, User user){
        ItemHolder itemHolder = itemEnsureExists(uid);

        if (inStock<0)
            throw new IllegalArgumentException("Item cannot have negative stock");

        addToLogList(new Activity(
                "Inventory edit: Set Item Stock",
                LocalDate.now().toString(),
                user.getName()+" set the item ("+ itemHolder.getItem().getSpecificItemType()+") stock from: "+itemHolder.getItemInStock()+", to: "+inStock,
                user.getName(),
                "+-"+UUID.randomUUID().toString()
        ));

        itemHolder.updateItemInStock(inStock, "override");
    }

    public void setItemHolderLowInStock(String uid, double inStock, User user){
        ItemHolder itemHolder = itemEnsureExists(uid);

        if (inStock<0)
            throw new IllegalArgumentException("Limit cannot be in negative");

        addToLogList(new Activity(
                "Inventory edit: Set Item Stock",
                LocalDate.now().toString(),
                user.getName()+" set the low stock limit of item ("+ itemHolder.getItem().getSpecificItemType()+") from: "+itemHolder.getLowStockLimit()+", to: "+inStock,
                user.getName(),
                "+-"+UUID.randomUUID().toString()
        ));

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
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if(itemHolder == null || itemHolder.getItem() == null)
            throw new IllegalArgumentException("No such items exist.");

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
