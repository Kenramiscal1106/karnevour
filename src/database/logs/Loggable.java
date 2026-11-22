package database.logs;

public interface Loggable {
    String toCSVFormat();
    void voidLog();
    boolean isLogVoid();

    String logMessage();
}
