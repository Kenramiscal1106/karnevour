package application.controllers.components;

import database.logs.Log;
import database.logs.Loggable;
import database.logs.Logger;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ActivityHistoryEntryController implements EntryControllerBase<Log> {
    public Text logText;
    public ImageView imageView;
    public HBox root;
    private Log log;
    private final Logger logger = Logger.getInstance();


    @Override
    public void initData(Log log) {
        logText.setText(log.logMessage());
        this.log = log;
    }

    public void voidLog(ActionEvent actionEvent) {
        log.voidLog();
        root.setDisable(true);
    }
}
