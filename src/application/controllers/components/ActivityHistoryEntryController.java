package application.controllers.components;

import database.logs.Loggable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ActivityHistoryEntryController implements EntryControllerBase<Loggable> {
    public Text logText;
    public ImageView imageView;

    @Override
    public void initData(Loggable loggable) {
        logText.setText(loggable.logMessage());
    }
}
