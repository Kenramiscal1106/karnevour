package database.logs;

public record InventoryLog(String name, String date, String description, String author, String uid) implements Loggable {
    public String toCSVFormat() {
        return  name+","+
                date+","+
                "\""+description+"\""+","+
                author+","+
                uid;
    }
}
