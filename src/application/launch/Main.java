package application.launch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        loadFonts();

        var sceneryLoader = new FXMLLoader(Main.class.getResource("/ui/views/ManagerView.fxml"));
        stage.setTitle("Karnevour - Farm Management System");
        stage.setScene(new Scene(sceneryLoader.load(), 500, 500));
        stage.show();
        // test commit
    }

    public void loadFonts() {
        Font.loadFont(getClass().getResourceAsStream("/fonts/InstrumentSans-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/fonts/InstrumentSans-Medium.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/fonts/InstrumentSans-SemiBold.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/fonts/InstrumentSans-Bold.ttf"), 16);
    }
}