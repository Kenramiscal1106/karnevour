package database.logs;

public class SaleLog extends Log implements Transaction{
    public final String name;
    public final String date;
    public final String description;
    public final String author;
    public final String uid;
    public final String productUid;
    public final double value;
    public final double qty;
    public final double payment;
    public final double change;

    public SaleLog(String name, String date, String description, String author, String uid,
                   String productUid, double value, double qty, double payment, double change) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.author = author;
        this.uid = uid;
        this.productUid = productUid;
        this.value = value;
        this.qty = qty;
        this.payment = payment;
        this.change = change;

    }
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
                qty +","+
                payment + "," +
                change;
    }

    @Override
    public String logMessage() {
        return "";
    }
}
