module karnevour {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens icons to javafx.graphics;
    opens application.controllers.views to javafx.fxml;
    opens application.controllers.components to javafx.fxml;

    exports application.launch;
}