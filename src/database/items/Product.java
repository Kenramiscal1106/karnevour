package database.items;

/**
 * Product holds the metadata of items that are considered sellable.
 * A subtype of {@link Item} and holds additional parameter {@param price}.
 */
public class Product extends Item {
    protected double price;

    /**
     * Calls the super method for instantiation, price is held by this Class
     *
     * @param itemName unique item name
     * @param description unique item description
     * @param price unique item price
     * @param alternativeName optional alternative name for item
     *
     * @throws IllegalArgumentException if {@code price} is set to 0 or a negative value
     */
    public Product(String itemName, String description, double price, String alternativeName) {
        super(itemName, description, alternativeName);
        setPrice(price);
    }

    public Product(String itemName, String description, double price) {
        super(itemName, description);
        setPrice(price);
    }

    // getters

    /**
     * Returns the set price of the product
     *
     * @return Product price
     */
    public double getPrice() {
        return this.price;
    }

    // setters
    /*
    make sure user instance of admin
     */

    /**
     * Sets the price of the item
     *
     * @param price unique non-zero, and non-negative price
     *
     * @throws IllegalArgumentException if zero or a negative value is passed
     */
    public void setPrice(double price) {
        if (price <= 0)
            throw new IllegalArgumentException("Negative number or zero passed as price");
        this.price = price;
    }


}
