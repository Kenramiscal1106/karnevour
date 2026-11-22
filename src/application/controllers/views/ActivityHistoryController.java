package application.controllers.views;

import application.controllers.components.ActivityHistoryEntryController;
import application.controllers.components.SalesHistoryEntryController;
import database.logs.Log;
import database.logs.Loggable;
import database.logs.Logger;
import database.logs.SaleLog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;

public class ActivityHistoryController {

    @FXML
    public ListView<Log> activityHistoryList;

    @FXML
    public void initialize() {
        var logger = Logger.getInstance();
        var logs = logger.getLogs();
        activityHistoryList.setItems(logs);
        activityHistoryList.setCellFactory(listView -> new ListCell<Log>(){
            private final Node row;
            private final ActivityHistoryEntryController controller;

            {
                try{
                    var loader = new FXMLLoader(getClass().getResource("/ui/components/ActivityHistoryEntry.fxml"));
                    row = loader.load();
                    controller = loader.getController();
                } catch (IOException e){
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
