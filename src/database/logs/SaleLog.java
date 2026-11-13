package database.logs;

public record SaleLog(String name, String date, String description, String author, String uid, double value) implements Loggable, Transaction{
    public String toCSVFormat() {
        return  name+","+
                date+","+
                "\""+description+"\""+","+
                author+","+
                uid;
    }
}
