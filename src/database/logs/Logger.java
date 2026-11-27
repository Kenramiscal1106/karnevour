package database.logs;

import application.services.SessionHandler;
import database.CashRegister;
import database.inventory.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.function.Predicate;

/**
 * A class that specifically holds the {@link Log} link via {@link ObservableList<Log>}.
 *
 * <p>
 *     A singleton Class.
 *     A class that holds all {@link Log} from the {@link database.Database} and from other classes that sends {@link Log}.
 * </p>
 */
public class Logger{
    private final ObservableList<Log> logs =  FXCollections.observableArrayList();
    private final Inventory inventory = Inventory.getInstance();
    private final CashRegister cashRegister = CashRegister.getInstance();

    private final static Logger instance = new Logger();

    /**
     * Returns the singleton instance of {@link Logger}
     *
     * @return {@link Logger} singleton
     */
    public static Logger getInstance() {
        return instance;
    }

    /**
     * Returns all {@link Log} from {@param logs}
     *
     * @return {@param logs} that contains {@link Log}
     */
    public ObservableList<Log> getLogs() {
        return logs;
    }

    /**
     * Returns the filtered {@link Log} list
     *
     * @param filter a non-empty String
     * @return {@link FilteredList} of the parameter {@param filter}
     */
    private FilteredList<?> getFilteredLogs(Predicate<? super Log> filter) {
        return new FilteredList<>(logs, filter);
    }

    /**
     * Adds {@link Log} to the list
     *
     * @param log a non-null {@link Log}
     */
    public void addLog(Log log) {
        logs.add(log);
    }

    /**
     * Removes a {@link Log} from the list
     *
     * @param log a non-null {@link Log}
     */
    public void removeLog(Log log) {
        logs.remove(log);
    }

    /**
     * Returns {@link Transaction} subclass {@link Log}
     *
     * @return {@link FilteredList} of {@link Log} with {@link Transaction}
     */
    public FilteredList<Log> getTransactionLogs() {
        return (FilteredList<Log>) getFilteredLogs(log -> log instanceof Transaction);
    }

    /**
     * Returns {@link SaleLog} subclass {@link Log}
     *
     * @return {@link FilteredList} of {@link Log} with {@link SaleLog}
     */
    public FilteredList<SaleLog> getSalesLogs() {
        var filteredList = getFilteredLogs(log -> log instanceof SaleLog);
        return (FilteredList<SaleLog>) filteredList;
    }

    /**
     * Returns {@link InventoryLog} subclass {@link Log}
     *
     * @return {@link FilteredList} of {@link Log} with {@link InventoryLog}
     */
    public FilteredList<InventoryLog> getInventoryLogs() {
        var filteredList = getFilteredLogs(log -> log instanceof InventoryLog);
        return (FilteredList<InventoryLog>) filteredList;
    }

    public void voidLog(int index) {
        var log = logs.get(index);

        if (log instanceof RefundLog refundLog) {
            var itemHolder = inventory.getItemHolderByUid(refundLog.itemHolderUid);
            cashRegister.cancelRefund(refundLog.value);
            inventory.restockItem(itemHolder.getUid(), refundLog.qty, SessionHandler.getInstance().getCurrentUser());
        } else if (log instanceof SaleLog saleLog) {
            var itemHolder = inventory.getItemHolderByUid(saleLog.itemHolderUid);
            cashRegister.cancelSell(saleLog.value);
            inventory.reduceItemAmount(itemHolder.getUid(), saleLog.qty, SessionHandler.getInstance().getCurrentUser());
        } else if (log instanceof InventoryLog inventoryLog) {
            switch(inventoryLog.name) {
                case "Inventory edit: Restock Item Amount":
                    break;

                case "Inventory edit: Reduce Item Amount":
                    break;
            }
        }
        log.voidLog();
//        logs.get(index).voidLog();
    }
}
