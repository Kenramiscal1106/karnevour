package application.controllers.components;

import database.logs.Loggable;
import javafx.scene.text.Text;

public class ActivityHistoryEntryController implements EntryControllerBase<Loggable> {
    public Text logText;

    @Override
    public void initData(Loggable loggable) {
        logText.setText(loggable.logMessage());
    }
}
