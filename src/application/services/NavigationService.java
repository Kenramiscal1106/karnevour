package application.services;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class NavigationService {


    private static final NavigationService instance = new NavigationService();
    public static synchronized NavigationService getInstance() {
        return instance;
    }
    private final SimpleObjectProperty<Node> viewProperty = new SimpleObjectProperty<>();
    public void updateView(String path) {
        var loader = new FXMLLoader(NavigationService.class.getResource(path));
        try {
            viewProperty.setValue(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ObjectProperty<Node> getViewProperty() {
        return viewProperty;
    }
}
