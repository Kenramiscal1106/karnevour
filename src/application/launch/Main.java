package application.launch;

import application.services.DataLoader;
import database.CashRegister;
import database.Database;
import database.ImageCache;
import database.inventory.Inventory;
import database.logs.Logger;
import database.user.Admin;
import database.user.Clerk;
import javafx.application.Application;
import javafx.application.Platform;
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
        Database.getInstance().saveUserToDatabase(new Admin("admin", 1, "1234"));
        Database.getInstance().saveUserToDatabase(new Clerk("clerk", 2, "1"));
        loadFonts();
        var sceneryLoader = new FXMLLoader(Main.class.getResource("/ui/views/LoginView.fxml"));
        stage.setTitle("Karnevour - Farm Management System");
        stage.setScene(new Scene(sceneryLoader.load(), 500, 500));
        stage.show();
        stage.setOnCloseRequest(_ -> {
            System.out.println("saving");
            if (DataLoader.isLoaded()){
                var db = Database.getInstance();
                db.saveInventoryToDatabase(Inventory.getInstance().getItemHolderList());

                var logs = Logger.getInstance().getLogs();
                if (!logs.isEmpty()){
                    db.saveLogsToDatabase(FXCollections.observableArrayList(logs));
                }
                db.saveCashRegisterCredentials(CashRegister.getInstance());
            }
            System.out.println("saved");
            ImageCache.getInstance().clearImageCache();
            Platform.exit();
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