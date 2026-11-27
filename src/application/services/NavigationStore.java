package application.services;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationStore {


    private static final NavigationStore instance = new NavigationStore();

    public static synchronized NavigationStore getInstance() {
        return instance;
    }

    private NavigationStore() {
    }

    private final SimpleObjectProperty<Node> viewProperty = new SimpleObjectProperty<>();

    public void updateView(String path) {
        var loader = new FXMLLoader(NavigationStore.class.getResource(path));
        try {
            viewProperty.setValue(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectProperty<Node> getViewProperty() {
        return viewProperty;
    }

    public Scene getScene() {
        return viewProperty.get().getScene();
    }
}
