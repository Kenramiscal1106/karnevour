package database.logs;

/**
 * This class holds the basic metadata and information of {@link database.inventory.Inventory} edits
 *
 * <p>
 *     Holds basic formatting and information about the {@link database.inventory.Inventory} edit.
 *     A subclass of {@link Log}.
 * </p>
 */
public class InventoryLog extends Log {

    public final String name;
    public final String date;
    public final String description;
    public final String author;
    public final String uid;
    public final String itemUid;

    /**
     * Instantiate a {@link InventoryLog} via given parameters
     *
     * @param name a non-empty String of {@link Log} name
     * @param date {@link java.time.LocalDate}
     * @param description a non-empty String
     * @param author a non-empty string of {@link database.user.User}
     * @param uid {@link java.util.UUID} generated uid
     * @param itemUid uid of {@link database.inventory.ItemHolder}
     */
    public InventoryLog(String name, String date, String description, String author, String uid, String itemUid) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.author = author;
        this.uid = uid;
        this.itemUid = itemUid;
    }

    /**
     * An overridden method from {@link Log} Class, automatically formats the {@link Log} to CSV
     *
     * @return a CSV String
     */
    @Override
    public String toCSVFormat() {
        return  this.getClass().getSimpleName()+","+
                "\""+name+"\"" + "," +
                date +"," +
                "\""+description+"\"" + "," +
                "\""+author+"\"" + "," +
                uid + "," +
                itemUid;
    }

    /**
     * An overridden method from {@link Log} Class, returns a message for UI
     *
     * @return a formatted String message
     */
    @Override
    public String logMessage() {
        return description;
    }
}
