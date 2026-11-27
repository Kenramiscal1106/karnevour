package database.items;

import database.Database;
import database.ImageCache;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Item class holds the metadata of any item in the inventory, utilizing the abstract keyword for inheritance
 * in case of any new item type. The base inventory item containing the most basic item data.
 *
 * <p>
 *     Subclasses of this class are expected to pass concrete data needed for the construction of this class.
 *     Classes {@link FarmItem} and {@link Product} and others added later on are expected to extend this
 *     for general item functionalities.
 * </p>
 **/
public abstract class Item {

    private static final File PICTURE_CACHE_FILE = new File("src/database/data/picturecache");
    protected final Database db = Database.getInstance();

    protected String itemName;
    protected String description;
    protected String alternativeName;

    /**
     * Constructs a new {@code Item} instance after passing required arguments
     *
     * @param itemName  unique non-blank item name
     * @param description unique description per item
     * @param alternativeName optional alternative name for item
     */
    Item(String itemName, String description, String alternativeName) {
        this.itemName = itemName;
        this.description = description;
        this.alternativeName = alternativeName.trim();
    }

    Item(String itemName, String description) {
        this.itemName = itemName;
        this.description = description;
        this.alternativeName = "";
    }

    // getters

    /**
     * Returns the passed argument item name
     *
     * @return name of the item
     */
    public String getItemName() {return this.itemName;}

    /**
     * Returns the passed description of the item
     *
     * @return item description
     */
    public String getDescription() {return this.description;}

    /**
     * Returns the alternative name of the item if there's any, else returns ""
     *
     * @return alternative item name
     */
    public String getAlternativeName() { return this.alternativeName; }

    /**
     * Returns the image path of the item
     *
     * @return URL path if not cached, else URI
     */
    public String getImagePath() {
        return db.loadImageURLPath(itemName);
    }

    /**
     * Sets the item description to the passed String
     *
     * @param desc unique item description
     */
    public void setDescription(String desc) {this.description=desc;}

    /**
     * Sets the item alternative name to the passed String
     *
     * @param altName unique item alternative name
     */
    public void setAlternativeName(String altName) {this.alternativeName=altName;}
}
