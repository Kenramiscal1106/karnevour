package database.inventory;

import database.Database;
import database.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import database.logs.*;
import database.items.*;
import javafx.collections.transformation.FilteredList;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * A singleton that holds the inventory, this class accepts {@link ItemHolder} Classes and stores the {@link ItemHolder}
 * in an {@link ObservableList<ItemHolder>}. This class holds the manipulation and methods to shape the inventory system,
 * and logic to perfom operations.
 *
 */
public class Inventory {
    /*
    holds the inventory of the company
     */

    private final ObservableList<ItemHolder> itemHolderList = FXCollections.observableArrayList();

    private final static Inventory instance = new Inventory();
    private final static Logger logger = Logger.getInstance();

    /**
     * Returns the instance of {@link Inventory}
     *
     * @return Inventory singleton
     */
    public static Inventory getInstance() {
        return instance;
    }

    private Inventory() {}

    // getters

    /**
     * Returns the whole {@link ItemHolder} list held by the {@link Inventory}
     *
     * @return {@link ObservableList<ItemHolder>} stored in the inventory
     */
    public ObservableList<ItemHolder> getItemHolderList() {
        return this.itemHolderList;
    }

    /**
     * A shortcut method that returns the item Class
     *
     * @param uid unique id held by the {@link ItemHolder}
     * @return String of the class simple name
     */
    public String getItemClass(String uid) {
        ItemHolder item = getItemHolderByUid(uid);

        return item == null ? null : item.getItem().getClass().getSimpleName();
    }

    /**
     * Verify's if an {@link Item} exist via searching for {@link ItemHolder}
     *
     * @param uid unique id of the {@link ItemHolder}
     * @return true if it exists, else false
     */
    public boolean doesItemExist(String uid){
        return this.itemHolderList.stream().filter(itemHolder -> itemHolder.getUid().equals(uid)).findFirst().orElse(null) != null;
    }

    /**
     * Returns the {@link ItemHolder} via {@param uid}
     *
     * @param uid unique id for the {@link ItemHolder}
     * @return {@link ItemHolder} if it exists
     *
     * @throws IllegalArgumentException if the {@param uid} and {@param ItemHolder} does not exist
     */
    public ItemHolder getItemHolderByUid(String uid) {
        ItemHolder holder = this.itemHolderList.stream().filter(itemHolder -> itemHolder.getUid().equals(uid)).findFirst().orElse(null);

        if (holder == null || holder.getItem() == null)
            throw new IllegalArgumentException("No such items exist.");

        return holder;
    }

    /**
     * A shortcut method that returns the {@link Item} via {@param uid}
     *
     * @param uid a unique id for the {@link ItemHolder}
     * @return {@link Item} if it exists
     *
     * @throws IllegalArgumentException if the {@param uid} and {@param ItemHolder} does not exist
     */
    public Item getItemById(String uid) {
        ItemHolder holder = getItemHolderByUid(uid);

        return holder == null ? null : holder.getItem();
    }

    /**
     * Adds an {@link Item} to the {@link Inventory}, adds a {@link InventoryLog} to the {@link Logger}
     *
     * @param uid a unique id for the {@link ItemHolder}
     * @param item a unique {@link Item}
     * @param unit a unique unit of measurement
     * @param itemInStock a non-negative value
     * @param lowStockLimit a non-negative value
     * @param user a non null {@link User}
     *
     * @throws IllegalArgumentException if {@link User} is null or {@link ItemHolder} already exists
     */
    public void addItem(String uid, Item item, String unit, double itemInStock, double lowStockLimit, User user) {
        if (user == null)
            throw new IllegalArgumentException("No user passed");

        if (this.doesItemExist(uid))
            throw new IllegalArgumentException("Item already exists");

        addToLogList(new InventoryLog(
                "Inventory edit: Add Item",
                LocalDate.now().toString(),
                user.getName() + " added item (" + item.getItemName() + ") to the inventory",
                user.getName(),
                "++" + UUID.randomUUID().toString(),
                uid
        ));

        this.itemHolderList.add(new ItemHolder(uid, item, unit, itemInStock, lowStockLimit));
    }

    /**
     * Removes an item in the {@link Inventory}, adds a {@link InventoryLog} to the {@link Logger}
     *
     * @param uid a unique id for the {@link ItemHolder}
     * @param user a non-null {@link User}
     *
     * @throws IllegalArgumentException if {@link User} or {@link ItemHolder} does not exist
     */
    public void removeItemInInventory(String uid, User user) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if (!doesItemExist(uid))
            throw new IllegalArgumentException("No such item exists");

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        addToLogList(new InventoryLog(
                "Inventory edit: Remove Item",
                LocalDate.now().toString(),
                user.getName() + " removed item (" + itemHolder.getItem().getItemName() + ") to the inventory",
                user.getName(),
                "--" + UUID.randomUUID().toString(),
                uid
        ));

        this.itemHolderList.remove(itemHolder);
    }

    /**
     * Restocks an item by using a method in {@link ItemHolder}, adds a {@link InventoryLog} to the {@link Logger}
     *
     * @param uid a unique id for the {@link ItemHolder}
     * @param amount a non-zero, non-negative value
     * @param user a non-null {@link User}
     *
     * @throws IllegalArgumentException if no {@link User} is null or {@param amount} is a negative or zero value
     */
    public void restockItem(String uid, double amount, User user) {
        ItemHolder hold = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        if (amount <= 0)
            throw new IllegalArgumentException("Negative amount passed");

        addToLogList(new InventoryLog(
                "Inventory edit: Restock Item Amount",
                LocalDate.now().toString(),
                user.getName() + " restocked item (" + hold.getItem().getItemName() + ") to the inventory (from: " + hold.getItemInStock() + " to: " + (hold.getItemInStock() + amount) + ")",
                user.getName(),
                "+=" + UUID.randomUUID().toString(),
                uid
        ));

        hold.updateItemInStock(amount, "add");
    }

    /**
     * Reduces an item amount by using a method in {@link ItemHolder}, adds a {@link Log} to the {@link Logger}
     *
     * @param uid a unique id for the {@link ItemHolder}
     * @param amount a non-zero, non-negative value
     * @param user a non-null {@link User}
     *
     * @throws IllegalArgumentException if no {@link User} is null or {@param amount} is a negative or zero value
     * or {@param itemInStock} is lower than {@param amount}
     */
    public void reduceItemAmount(String uid, double amount, User user) {
        ItemHolder hold = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        if (amount<=0)
            throw new IllegalArgumentException("Amount passed is zero or negative");

        if (hold.getItemInStock() < amount)
            throw new IllegalArgumentException("Amount to be deducted is more than in stock");

        addToLogList(new InventoryLog(
                "Inventory edit: Reduce Item Amount",
                LocalDate.now().toString(),
                user.getName() + " reduced item (" + hold.getItem().getItemName() + ") to the inventory (from: " + hold.getItemInStock() + " to: " + (hold.getItemInStock() - amount) + ")",
                user.getName(),
                "-=" + UUID.randomUUID().toString(),
                uid
        ));

        hold.updateItemInStock(amount, "subtract");
    }

    /**
     * Replaces an existing {@link Item} in {@link Inventory} via {@link ItemHolder}, adds a {@link Log} to the {@link Logger}
     *
     * @param oldUid the uid of the {@link ItemHolder} being replaced
     * @param newUid the uid of the {@link ItemHolder} replacement
     * @param newItem new non-null {@link Item}
     * @param newUnit new unique unit of measurement
     * @param newItemInStock new non-negative value
     * @param newLowStockLimit new non-negative value
     * @param user a non-null {@link User}
     *
     * @throws IllegalArgumentException if no {@link User} is passed
     * @throws NullPointerException if {@link Item} is null
     */
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
                user.getName() + " replaced item " + itemHolder.getItem() + ", with item " + newItem.getItemName(),
                user.getName(),
                "+-" + UUID.randomUUID().toString(),
                newUid
        ));

        this.itemHolderList.set(i, new ItemHolder(newUid, newItem, newUnit, newItemInStock, newLowStockLimit));
    }

    /**
     * Sets stock parameter in {@param itemHolder}, adds a {@link InventoryLog} to the {@link Logger}
     *
     * @param uid unique id of the {@link ItemHolder}
     * @param inStock a non-negative value
     * @param user a non-null {@link User}
     *
     * @throws IllegalArgumentException if {@link User} is null or {@param inStock} is a negative value
     */
    public void setItemHolderInStock(String uid, double inStock, User user) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        if (inStock < 0)
            throw new IllegalArgumentException("Item cannot have negative stock");

        addToLogList(new InventoryLog(
                "Inventory edit: Set Item Stock",
                LocalDate.now().toString(),
                user.getName() + " set the item (" + itemHolder.getItem().getItemName() + ") stock from: " + itemHolder.getItemInStock() + ", to: " + inStock,
                user.getName(),
                "+~" + UUID.randomUUID().toString(),
                uid
        ));

        itemHolder.updateItemInStock(inStock, "override");
    }

    /**
     * Sets {@link ItemHolder} low in stock parameter, adds a {@link InventoryLog} to the {@link Logger}
     *
     * @param uid unique id of the {@link ItemHolder}
     * @param inStock a non-negative value
     * @param user a non-null {@link User}
     *
     * @throws IllegalArgumentException if {@link User} is null or {@param inStock} is a negative value
     */
    public void setItemHolderLowInStock(String uid, double inStock, User user) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        if (inStock < 0)
            throw new IllegalArgumentException("Limit cannot be in negative");

        addToLogList(new InventoryLog(
                "Inventory edit: Set Item Stock",
                LocalDate.now().toString(),
                user.getName() + " set the low stock limit of item (" + itemHolder.getItem().getItemName() + ") from: " + itemHolder.getLowStockLimit() + ", to: " + inStock,
                user.getName(),
                "+#" + UUID.randomUUID().toString(),
                uid
        ));

        itemHolder.setLowStockLimit(inStock);
    }

    /**
     * Adds {@link Log} to the log list in {@link Loggable}
     *
     * @param log a non-null {@link Log}
     *
     * @throws NullPointerException if {@param log} is null
     */
    private void addToLogList(Log log) {
        if (log == null) {
            throw new NullPointerException("Log does not exist");
        }
        logger.addLog(log);
    }

    /**
     * A shortcut method that returns the {@link Product} price, adds a {@link InventoryLog} to the {@link Logger}
     *
     * @param uid unique id of the {@link ItemHolder}
     * @return double {@param itemPrice}
     *
     * @throws IllegalArgumentException if {@link Item} is not an instance of {@link Product}
     */
    public double getPrice(String uid) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);
        Item item = itemHolder.getItem();

        if (!(item instanceof Product)) {
            throw new IllegalArgumentException("Not a product item");
        }
        return ((Product) item).getPrice();
    }

    /**
     * Returns the current total {@link Inventory} liquid value, adds a {@link InventoryLog} to the {@link Logger}
     *
     * @return double {@param total}
     */
    public double getTotalInventoryValue(){
        double total = 0;
        for (ItemHolder itemHolder: itemHolderList){
            if(itemHolder.getItem() instanceof Product product){
                total += product.getPrice() * itemHolder.getItemInStock();
            }
        }
        return total;
    }

    /**
     * A shortcut method that sets the price of the {@link Item}, adds a {@link InventoryLog} to the {@link Logger}
     *
     * @param uid unique id of the {@link ItemHolder}
     * @param newPrice a non-negative value
     * @param user a non-null {@link User}
     *
     * @throws IllegalArgumentException if {@link User} is null
     * @throws IllegalArgumentException if {@link Item} is an instance of {@link FarmItem}
     * @throws IllegalArgumentException if {@param newPrice} is a negative value
     */
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
                user.getName() + " set the item (" + itemHolder.getItem().getItemName() + ") price from: " + ((Product) item).getPrice() + ", to: " + newPrice,
                user.getName(),
                "I$" + UUID.randomUUID().toString(),
                uid
        ));

        ((Product) item).setPrice(newPrice);
    }

    /**
     * A shortcut method that sets the {@link Item} description parameter, adds a {@link InventoryLog} to the {@link Logger}
     *
     * @param uid a unique id of {@link ItemHolder}
     * @param desc a non-empty String
     * @param user a non-null {@link User}
     *
     * @throws IllegalArgumentException if {@link User} is null
     */
    public void setItemDescription(String uid, String desc, User user) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        addToLogList(new InventoryLog(
                "Inventory edit: Set Item Description",
                LocalDate.now().toString(),
                user.getName() + " set the item (" + itemHolder.getItem().getItemName() + ") description from: " + itemHolder.getItemDescription() + ", to: " + desc,
                user.getName(),
                "ID" + UUID.randomUUID().toString(),
                uid
        ));

        itemHolder.getItem().setDescription(desc);
    }

    /**
     * A shortcut method that sets the {@link Item} alternative name parameter, adds a {@link InventoryLog} to the {@link Logger}
     *
     * @param uid unique id of the {@link ItemHolder}
     * @param altName non-empty String
     * @param user a non-null {@link User}
     *
     * @throws IllegalArgumentException if {@link User} is null
     */
    public void setItemAlternativeName(String uid, String altName, User user) {
        ItemHolder itemHolder = this.getItemHolderByUid(uid);

        if (user == null)
            throw new IllegalArgumentException("No user passed");

        addToLogList(new InventoryLog(
                "Inventory edit: Set Item Description",
                LocalDate.now().toString(),
                user.getName() + " set the item (" + itemHolder.getItem().getItemName() + ") alternative name from: " + itemHolder.getLowStockLimit() + ", to: " + altName,
                user.getName(),
                "IA" + UUID.randomUUID(),
                uid
        ));

        itemHolder.getItem().setAlternativeName(altName);
    }

    /**
     * Sets an item in the {@link Inventory}, a method for the database
     *
     * @param uid unique id of the {@link ItemHolder}
     * @param item a non-null {@link Item}
     * @param unit a non-empty String
     * @param itemInStock a non-negative value
     * @param lowStockLimit a non-negative value
     * @param db {@link Database} instance
     *
     * @throws NullPointerException if {@link Item} is null
     */
    public void setItem(String uid, Item item, String unit, double itemInStock, double lowStockLimit, Database db) {
        if (item == null)
            throw new NullPointerException("No such item");
        if (this.doesItemExist(uid))
            throw new IllegalArgumentException("Item already in inventory");
        if (db == null)
            throw new IllegalArgumentException("Database not found");

        this.itemHolderList.add(new ItemHolder(uid, item, unit, itemInStock, lowStockLimit));
    }

    /**
     * Replaces {@link Item} and {@link ItemHolder} parameters, adds a {@link InventoryLog} to the {@link Logger}
     *
     * @param uid unique id of the {@link ItemHolder}
     * @param newUnit a non-empty String
     * @param newItemInStock a non-negative value
     * @param newLowStockInLimit a non-negative value
     * @param user a non-null {@link User}
     *
     * @throws IllegalArgumentException if {@link ItemHolder} does not exist
     */
    public void replaceItemDetails(String uid, String newUnit, double newItemInStock, double newLowStockInLimit, User user){
        if (!doesItemExist(uid))
            throw new IllegalArgumentException("Item does not exist");

        ItemHolder holder = getItemHolderByUid(uid);

        addToLogList(new InventoryLog(
                "Inventory edit: Replace Item Edits",
                LocalDate.now().toString(),
                user.getName()+" replaced item (" + holder.getItem().getItemName()+") details.",
                user.getName(),
                "//" + UUID.randomUUID(),
                uid
        ));

        holder.setUnit(newUnit);
        holder.updateItemInStock(newItemInStock,"override");
        holder.setLowStockLimit(newLowStockInLimit);
    }

    /**
     * Returns the product holder list
     *
     * @return a non-empty {@link ItemHolder} of {@link Product} list
     */
    public ObservableList<ItemHolder> getProductHoldersList() {
        return itemHolderList.filtered(itemHolder ->  itemHolder.getItem() instanceof Product);
    }

    /**
     * Returns a {@link FilteredList} of the given parameter
     *
     * @param predicate a non-empty {@param predicate}
     * @return a {@link FilteredList} of the given {@param predicate}
     */
    public FilteredList<ItemHolder> filterList(Predicate<? super ItemHolder> predicate) {
        return new FilteredList<>(itemHolderList, predicate);
    }
}
