package database.logs;

public class InventoryLog extends Log {

    public final String name;
    public final String date;
    public final String description;
    public final String author;
    public final String uid;
    public InventoryLog(String name, String date, String description, String author, String uid) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.author = author;
        this.uid = uid;
    }
    public String toCSVFormat() {
        return  name+","+
                date+","+
                "\""+description+"\""+","+
                author+","+
                uid;
    }
}
