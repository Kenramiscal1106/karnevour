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



    public SaleLog(String name, String date, String description, String author, String uid,
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

    @Override
    public String logMessage() {
        return "";
    }
}
