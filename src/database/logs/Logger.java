package database.logs;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.function.Predicate;

public class Logger{
    private final ObservableList<Log> logs =  FXCollections.observableArrayList();

    private final static Logger instance = new Logger();

    public static Logger getInstance() {
        return instance;
    }

    public ObservableList<Log> getLogs() {
        return logs;
    }

    private FilteredList<?> getFilteredLogs(Predicate<? super Log> filter) {
        return new FilteredList<>(logs, filter);
    }

    public void addLog(Log log) {
        logs.add(log);
    }

    public void removeLog(Log log) {
        logs.remove(log);
    }

    public FilteredList<Log> getTransactionLogs() {
        return (FilteredList<Log>) getFilteredLogs(log -> log instanceof Transaction);
    }

    public FilteredList<SaleLog> getSalesLogs() {
        var filteredList = getFilteredLogs(log -> log instanceof SaleLog);
        return (FilteredList<SaleLog>) filteredList;
    }

    public FilteredList<InventoryLog> getInventoryLogs() {
        var filteredList = getFilteredLogs(log -> log instanceof InventoryLog);
        return (FilteredList<InventoryLog>) filteredList;
    }
}
