package application.controllers.views;

import application.controllers.components.ActivityHistoryEntryController;
import application.controllers.components.SalesHistoryEntryController;
import application.services.SessionHandler;
import database.logs.*;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Comparator;

public class ActivityHistoryController {

    @FXML
    public ListView<Log> activityHistoryList;


    private final SessionHandler sessionHandler = SessionHandler.getInstance();

    @FXML
    public void initialize() {
        var logger = Logger.getInstance();


        var logs = logger.getLogs().filtered(log -> {
            if (sessionHandler.isAdmin()) return !(log.isLogVoid());
            if (log instanceof SaleLog saleLog) return saleLog.author.equals(sessionHandler.getCurrentUser().getName());
            if (log instanceof RefundLog refundLog)
                return refundLog.author.equals(sessionHandler.getCurrentUser().getName());
            return false;
        });


        if (!logs.isEmpty()) {
            activityHistoryList.setItems(new SortedList<>(logs, Comparator.comparingInt(logs::indexOf).reversed()));
            activityHistoryList.setCellFactory(listView -> new ListCell<Log>() {
                private final Node row;
                private final ActivityHistoryEntryController controller;

                {
                    try {
                        var loader = new FXMLLoader(getClass().getResource("/ui/components/ActivityHistoryEntry.fxml"));
                        row = loader.load();
                        controller = loader.getController();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void updateItem(Log logEntry, boolean empty) {
                    super.updateItem(logEntry, empty);
                    if (empty || logEntry == null) {
                        setGraphic(null);
                    } else {
                        controller.initData(logEntry);
                        setGraphic(row);
                    }
                }
            });
        }
    }
}