package database.logs;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.function.Predicate;

public class Logger{
    private final ObservableList<Loggable> logs =  FXCollections.observableArrayList();

    private final static Logger instance = new Logger();

    public static Logger getInstance() {
        return instance;
    }

    public ObservableList<Loggable> getLogs() {
        return logs;
    }

    private FilteredList<?> getFilteredLogs(Predicate<? super Loggable> filter) {
        return new FilteredList<>(logs, filter);
    }

    public void addLog(Loggable log) {
        logs.add(log);
    }

    public void removeLog(Loggable log) {
        logs.remove(log);
    }

    public FilteredList<Transaction> getTransactionLogs() {
        var filteredList = getFilteredLogs(log -> log instanceof Transaction);
        return (FilteredList<Transaction>) filteredList;
    }

    public FilteredList<InventoryLog> getInventoryLogs() {
        var filteredList = getFilteredLogs(log -> log instanceof Transaction);
        return (FilteredList<InventoryLog>) filteredList;
    }
}
