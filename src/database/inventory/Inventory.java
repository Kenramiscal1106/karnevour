package database.inventory;

import database.Database;
import database.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import database.logs.*;
import database.items.*;

import java.time.LocalDate;
import java.util.UUID;

public class Inventory {
    /*
    holds the inventory of the company
     */

    private final ObservableList<ItemHolder> itemHolderList = FXCollections.observableArrayList();

    private final static Inventory instance = new Inventory();
    private final static Logger logger = Logger.getInstance();

    public static Inventory getInstance() {
        return instance;
    }

    // getters
    public ObservableList<ItemHolder> getItemHolderList() {
        return this.itemHolderList;
    }

    public String getItemClass(String uid) {
        ItemHolder item = getItemHolderByUid(uid);

        return item == null ? null : item.getItem().getClass().getSimpleName();
    }

    // shortcuts

    public boolean doesItemExist(String uid){
        return this.itemHolderList.stream().filter(itemHolder -> itemHolder.getUid().equals(uid)).findFirst().orElse(null) != null;
    }

    public ItemHolder getItemHolderByUid(String uid) {
        ItemHolder holder = this.itemHolderList.stream().filter(itemHolder -> itemHolder.getUid().equals(uid)).findFirst().orElse(null);

        if (holder == null || holder.getItem() == null)
            throw new IllegalArgumentException("No such items exist.");

        return holder;
    }

    public Item getItemById(String uid) {
        ItemHolder holder = getItemHolderByUid(uid);

        return holder == null ? null : holder.getItem();
    }

    // ill add these for now
    public void addItem(String uid, Item item, String unit, double itemInStock, double lowStockLimit, User user) {
        if (user == null)
            throw new IllegalArgumentException("No user passed");

        if (this.doesItemExist(uid))
            throw new IllegalArgumentException("Item already exists");

        addToLogList(new InventoryLog(
                "Inventory edit: Add Item",
                LocalDate.now().toString(),
                user.getName() + " added item (" + item.getSpecificItemType() + ") to the inventory",
                user.getName(),
                "++" + UUID.randomUUID().toString()
        ));

        this.itemHolderList.add(new ItemHolder(uid, item, unit, itemInStock, lowStockLimit));
    }

    public void removeItemInInventory(String uid, User user) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        addToLogList(new InventoryLog(
                "Inventory edit: Remove Item",
                LocalDate.now().toString(),
                user.getName() + " removed item (" + itemHolder.getItem().getSpecificItemType() + ") to the inventory",
                user.getName(),
                "--" + UUID.randomUUID().toString()
        ));

        this.itemHolderList.remove(itemHolder);
    }

    public void restockItem(String uid, double amount, User user) {
        ItemHolder hold = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        if (amount <= 0)
            throw new IllegalArgumentException("Negative amount passed");

        addToLogList(new InventoryLog(
                "Inventory edit: Restock Item Amount",
                LocalDate.now().toString(),
                user.getName() + " restocked item (" + hold.getItem().getSpecificItemType() + ") to the inventory (from: " + hold.getItemInStock() + " to: " + (hold.getItemInStock() + amount) + ")",
                user.getName(),
                "+=" + UUID.randomUUID().toString()
        ));

        hold.updateItemInStock(amount, "add");
    }

    public void reduceItemAmount(String uid, double amount, User user) {
        ItemHolder hold = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        if (hold.getItemInStock() < amount)
            throw new IllegalArgumentException("Amount to be deducted is more than in stock");

        addToLogList(new InventoryLog(
                "Inventory edit: Reduce Item Amount",
                LocalDate.now().toString(),
                user.getName() + " reduced item (" + hold.getItem().getSpecificItemType() + ") to the inventory (from: " + hold.getItemInStock() + " to: " + (hold.getItemInStock() - amount) + ")",
                user.getName(),
                "-=" + UUID.randomUUID().toString()
        ));

        hold.updateItemInStock(amount, "subtract");
    }

    public void replaceItemInInventory(String oldUid, String newUid, Item newItem, String newUnit, double newItemInStock, double newLowStockLimit, User user) {
        ItemHolder itemHolder = this.getItemHolderByUid(oldUid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        if (newItem == null)
            throw new NullPointerException("No such item passed");

        int i = this.itemHolderList.indexOf(itemHolder);

        addToLogList(new InventoryLog(
                "Inventory edit: Replace Item",
                LocalDate.now().toString(),
                user.getName() + " replaced item " + itemHolder.getItem() + ", with item " + newItem.getSpecificItemType(),
                user.getName(),
                "+-" + UUID.randomUUID().toString()
        ));

        this.itemHolderList.set(i, new ItemHolder(newUid, newItem, newUnit, newItemInStock, newLowStockLimit));
    }

    public void setItemHolderInStock(String uid, double inStock, User user) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        if (inStock < 0)
            throw new IllegalArgumentException("Item cannot have negative stock");

        addToLogList(new InventoryLog(
                "Inventory edit: Set Item Stock",
                LocalDate.now().toString(),
                user.getName() + " set the item (" + itemHolder.getItem().getSpecificItemType() + ") stock from: " + itemHolder.getItemInStock() + ", to: " + inStock,
                user.getName(),
                "+-" + UUID.randomUUID().toString()
        ));

        itemHolder.updateItemInStock(inStock, "override");
    }

    public void setItemHolderLowInStock(String uid, double inStock, User user) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        if (inStock < 0)
            throw new IllegalArgumentException("Limit cannot be in negative");

        addToLogList(new InventoryLog(
                "Inventory edit: Set Item Stock",
                LocalDate.now().toString(),
                user.getName() + " set the low stock limit of item (" + itemHolder.getItem().getSpecificItemType() + ") from: " + itemHolder.getLowStockLimit() + ", to: " + inStock,
                user.getName(),
                "+-" + UUID.randomUUID().toString()
        ));

        itemHolder.setLowStockLimit(inStock);
    }

    public void addToLogList(Loggable log) {
        if (log == null) {
            throw new NullPointerException("Log does not exist");
        }
        logger.addLog(log);
    }

    public double getPrice(String uid) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);
        Item item = itemHolder.getItem();

        if (!(item instanceof Product)) {
            throw new IllegalArgumentException("Not a product item");
        }
        return ((Product) item).getPrice();
    }

    public void setItemPrice(String uid, double newPrice, User user) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        Item item = itemHolder.getItem();

        if (item instanceof FarmItem)
            throw new IllegalArgumentException("Farm items don't have prices");

        if (newPrice < 0)
            throw new IllegalArgumentException("Price cannot be less than zero.");

        addToLogList(new InventoryLog(
                "Inventory edit: Set Item Price",
                LocalDate.now().toString(),
                user.getName() + " set the item (" + itemHolder.getItem().getSpecificItemType() + ") price from: " + ((Product) item).getPrice() + ", to: " + newPrice,
                user.getName(),
                "I$" + UUID.randomUUID().toString()
        ));

        ((Product) item).setPrice(newPrice);
    }

    public void setItemDescription(String uid, String desc, User user) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        addToLogList(new InventoryLog(
                "Inventory edit: Set Item Description",
                LocalDate.now().toString(),
                user.getName() + " set the item (" + itemHolder.getItem().getSpecificItemType() + ") description from: " + itemHolder.getItemDescription() + ", to: " + desc,
                user.getName(),
                "ID" + UUID.randomUUID().toString()
        ));

        itemHolder.getItem().setDescription(desc);
    }

    public void setItemAlternativeName(String uid, String altName, User user) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        addToLogList(new InventoryLog(
                "Inventory edit: Set Item Description",
                LocalDate.now().toString(),
                user.getName() + " set the item (" + itemHolder.getItem().getSpecificItemType() + ") alternative name from: " + itemHolder.getLowStockLimit() + ", to: " + altName,
                user.getName(),
                "IA" + UUID.randomUUID().toString()
        ));

        itemHolder.getItem().setAlternativeName(altName);
    }

    public void setItem(String uid, Item item, String unit, double itemInStock, double lowStockLimit, Database db) {
        if (item == null)
            throw new NullPointerException("No such item");
        if (this.doesItemExist(uid))
            throw new IllegalArgumentException("Item already in inventory");
        if (db == null)
            throw new IllegalArgumentException("Database not found");

        addToLogList(new InventoryLog(
                "Inventory edit: Load Item",
                LocalDate.now().toString(),
                "the database loaded item (" + item.getSpecificItemType() + ") to the inventory",
                "database",
                "+?" + UUID.randomUUID().toString()
        ));

        this.itemHolderList.add(new ItemHolder(uid, item, unit, itemInStock, lowStockLimit));
    }
}
