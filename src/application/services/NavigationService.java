package application.services;

import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class NavigationService extends Service<Node> {

    private static final NavigationService instance = new NavigationService();
    public static synchronized NavigationService getInstance() {
        return instance;
    }
    private final SimpleObjectProperty<Node> property = new SimpleObjectProperty<>();
    public void updateNodeView(Node node) {
        property.setValue(node);
        this.restart();
    }
    @Override
    protected Task<Node> createTask() {
        return new Task<>() {
            @Override
            protected Node call() throws Exception {
                return property.get();
            }
        };
    }
}
