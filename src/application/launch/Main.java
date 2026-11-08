package application.launch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var sceneryLoader = new FXMLLoader(Main.class.getResource("/ui/views/RootView.fxml"));
        stage.setTitle("Hello World!");
        stage.setScene(new Scene(sceneryLoader.load(), 500, 500));
        stage.show();
        // test commit
    }
}