package database.logs;

import database.inventory.Inventory;

/**
 * This class holds the basic metadata and information of refunds from {@link database.CashRegister}
 *
 * <p>
 *     Holds basic formatting and information about a refund from {@link database.CashRegister}.
 *     A subclass of {@link Log}.
 * </p>
 */
public class RefundLog extends Log implements Transaction {
    public final String name;
    public final String date;
    public final String description;
    public final String author;
    public final String uid;
    public final String productUid;
    public final double value;
    public final double qty;

    /**
     * Instantiate a {@link RefundLog} via given parameters
     *
     * @param name a non-empty String {@link RefundLog} name
     * @param date {@link java.time.LocalDate}
     * @param description a non-empty String
     * @param author a non-empty string of {@link database.user.User}
     * @param uid {@link java.util.UUID} generated uid
     * @param productUid {@link java.util.UUID} of {@link database.inventory.ItemHolder}
     * @param value a non-negative value
     * @param qty a non-negative value
     */
    public RefundLog(String name, String date, String description, String author, String uid,
                     String productUid, double value, double qty) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.author = author;
        this.uid = uid;
        this.productUid = productUid;
        this.value = value;
        this.qty = qty;

    }

    /**
     * An overridden method from {@link Log} Class, automatically formats the {@link Log} to CSV
     *
     * @return a CSV String
     */
    @Override
    public String toCSVFormat() {
        return this.getClass().getSimpleName()+","+
                "\""+name+"\"" + "," +
                date +"," +
                "\""+description+"\"" + "," +
                "\""+author+"\"" + "," +
                uid + "," +
                productUid + ","+
                value + "," +
                qty;
    }

    /**
     * An overridden method from {@link Log} Class, returns a message for UI
     *
     * @return a formatted String message
     */
    @Override
    public String logMessage() {
        return author + " processed a refunded " + Inventory.getInstance().getItemById(productUid).getItemName() + " worth " + value + " pesos" ;
    }

}
