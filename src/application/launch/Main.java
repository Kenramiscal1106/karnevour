package application.launch;

import application.services.DataLoader;
import database.CashRegister;
import database.Database;
import database.inventory.Inventory;
import database.logs.Loggable;
import database.logs.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        loadFonts();

        var sceneryLoader = new FXMLLoader(Main.class.getResource("/ui/views/LoginView.fxml"));
        stage.setTitle("Karnevour - Farm Management System");
        stage.setScene(new Scene(sceneryLoader.load(), 500, 500));
        stage.show();
        stage.setOnCloseRequest(_ -> {
            if (DataLoader.isLoaded()){
                var db = Database.getInstance();
                db.saveInventoryToDatabase(Inventory.getInstance().getItemHolderList());

                var logs = Logger.getInstance().getLogs();
                if (!logs.isEmpty()){
                    db.saveLogsToDatabase(FXCollections.<Loggable>observableArrayList(logs));
                }
                db.saveCashRegisterCredentials(CashRegister.getInstance());
            }
//            Database.getInstance().saveLogsToDatabase(Logs);
        });
        // test commit
    }

    public void loadFonts() {
        Font.loadFont(getClass().getResourceAsStream("/fonts/InstrumentSans-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/fonts/InstrumentSans-Medium.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/fonts/InstrumentSans-SemiBold.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/fonts/InstrumentSans-Bold.ttf"), 16);
    }
}