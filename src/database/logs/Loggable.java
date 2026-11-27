package database.logs;

/**
 * Interface for logs, provides basic functionalities for logging
 */
public interface Loggable {
    String toCSVFormat();
    void voidLog();
    boolean isLogVoid();

    String logMessage();
}
