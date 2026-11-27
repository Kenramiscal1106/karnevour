package database.logs;

/**
 * This class holds the metadata and format for donations in the app.
 *
 * <p>
 *     Holds basic formatting and information about the given Donation.
 *     A subclass of {@link Log}
 * </p>
 */
public class DonationLog extends Log{

    public final String name;
    public final String date;
    public final String description;
    public final String author;
    public final String uid;
    public final double amount;

    /**
     * Instantiate a {@link DonationLog} via given parameters
     *
     * @param name a non-empty String {@link Log} name
     * @param date {@link java.time.LocalDate}
     * @param description a non-empty String
     * @param author a non-empty string of {@link database.user.User}
     * @param uid {@link java.util.UUID} generated uid
     * @param amount a non-negative value
     */
    public DonationLog(String name, String date, String description, String author, String uid, double amount) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.author = author;
        this.uid = uid;
        this.amount = amount;
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
                amount;
    }

    /**
     * An overridden method from {@link Log} Class, returns a message for UI
     *
     * @return a formatted String message
     */
    @Override
    public String logMessage() {
        return name + " processed a " + amount + "-peso donation" ;
    }
}
