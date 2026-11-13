package database.logs;

public class SaleLog extends Log implements Transaction{
    public final String name;
    public final String date;
    public final String description;
    public final String author;
    public final String uid;
    public final double value;

    public SaleLog(String name, String date, String description, String author, String uid,
                     double value) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.author = author;
        this.uid = uid;
        this.value = value;

    }
    @Override
    public String toCSVFormat() {
        return this.getClass().getSimpleName()+","+
                "\""+name+"\"" + "," +
                date +"," +
                "\""+description+"\"" + "," +
                "\""+author+"\"" + "," +
                uid + "," +
                value;
    }
}
