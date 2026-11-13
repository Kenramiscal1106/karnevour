package database.logs;

public record RefundLog(String name, String date, String description, String author, String uid,
                        double value) implements Loggable, Transaction {
    @Override
    public String toCSVFormat() {
        return name + "," + description + "," + author + "," + uid + "," + value;
    }
}
