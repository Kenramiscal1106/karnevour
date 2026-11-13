package database.logs;

public class DonationLog extends Log{

    public final String name;
    public final String date;
    public final String description;
    public final String author;
    public final String uid;
    public final double amount;

    public DonationLog(String name, String date, String description, String author, String uid, double amount) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.author = author;
        this.uid = uid;
        this.amount = amount;
    }
    @Override
    public String toCSVFormat() {
        return "";
    }
}
