package database.inventory;

import database.items.Item;

/**
 * ItemHolder holds the {@link Item} along with other parameter, this is the main
 * Class to manipulate item logic in the {@link Inventory} class.
 *
 * <p>
 *     The ItemHolder accepts a non-empty, non-null {@link Item} and holds additional information
 *     for the {@link Inventory} class to manipulate and function.
 * </p>
 *
 */
public class ItemHolder {
    private final Item item;
    private double itemInStock;
    private double lowStockLimit;
    protected String unit;
    protected String uid;

    /**
     * Creates the ItemHolder Class for a specific {@link Item}.
     *
     * @param uid unique id for the item
     * @param item unique item that holds the item metadata
     * @param unit unique item unit of measurement
     * @param itemInStock a non-negative, non-zero value item in stock
     * @param lowStockLimit a non-negative, non-zero value low stock limit
     *
     * @throws IllegalArgumentException if {@param itemInStock} or {@param lowStockLimit} are passed with zero or negative values
     */
    public ItemHolder(String uid, Item item, String unit, double itemInStock, double lowStockLimit){
        this.uid = uid;
        this.item = item;
        this.unit = unit;
        this.updateItemInStock(itemInStock, "override");
        this.setLowStockLimit(lowStockLimit);
    }

    // getters

    /**
     * Returns the item being held
     *
     * @return unique item held
     */
    public Item getItem() {return this.item;}

    /**
     * Returns the item stock
     *
     * @return item in stock
     */
    public double getItemInStock() {return this.itemInStock;}

    /**
     * Returns the low stock limit for the item
     *
     * @return item low stock limit
     */
    public double getLowStockLimit() {return this.lowStockLimit;}

    /**
     * Returns the assigned item UID
     *
     * @return item uid
     */
    public String getUid() {return this.uid;}

    // shortcuts

    /**
     * A shortcut method to get item description
     *
     * @return item description
     */
    public String getItemDescription() {return this.getItem().getDescription();}

    /**
     * A shortcut method to get item alternative name
     *
     * @return item's alternative name
     */
    public String getItemAlternativeName() { return this.getItem().getAlternativeName(); }

    /**
     * Returns item unit
     *
     * @return item's unit
     */
    public String getUnit() {return this.unit;}

    // setters

    /**
     * Replaces the item unit to unit passed
     *
     * @param unit unique unit of measurement
     */
    public void setUnit(String unit) {this.unit=unit;}

    /**
     * A method that updates item in stock depending on the passed operation
     * Operations include: "add", "subtract", "override"
     *
     * @param stock unique non-zero, non-negative stock
     * @param operation set operation pre-determined by the method
     *
     * @throws IllegalArgumentException if stock is passed as zero or a negative value
     */
    void updateItemInStock(double stock, String operation){
        if (stock<0)
            throw new IllegalArgumentException("Negative stock passed");

        switch (operation.toLowerCase()){
            case "add":
                this.itemInStock+=stock;
                break;
            case "subtract":
                this.itemInStock = stock>this.itemInStock ? 0:itemInStock-stock;
                break;
            case "override":
                this.itemInStock = stock;
                break;
        }
    }

    /**
     * Sets the low stock limit of an {@link ItemHolder} to passed value
     *
     * @param stockLimit a unique non-negative number
     *
     * @throws IllegalArgumentException if stockLimit is a negative value
     */
    public void setLowStockLimit(double stockLimit){
        if (stockLimit<0)
            throw new IllegalArgumentException("Limit cannot be in negative");
        this.lowStockLimit = stockLimit;
    }

    /**
     * Returns a true if item in stock is less than or equal to low stock limit
     *
     * @return true if itemInStock<=lowStockLimit, else false
     */
    public boolean isLowInStock(){
        return this.getItemInStock() <= this.getLowStockLimit();
    }
    public boolean isOutOfStock() {return this.getItemInStock() <= 0; }
}
