package database.items;

/**
 * FarmItem holds the metadata of items that are not considered sellable.
 * A subtype of {@link Item}.
 */
public class FarmItem extends Item {

    /**
     * Calls the super method for instantiation
     *
     * @param itemName unique item name
     * @param description unique item description
     * @param altName optional alternative name for item
     */
    public FarmItem(String itemName, String description, String altName) {
        super(itemName, description, altName);
    }

    public FarmItem(String itemName, String description){
        super(itemName,description);
    }
}
