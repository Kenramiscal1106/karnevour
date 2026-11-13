package database.logs;

public record DonationLog(String name, String date, String description, String author, String uid, double amount) implements Loggable, Transaction{
    @Override
    public String toCSVFormat() {
        return "";
    }
}
