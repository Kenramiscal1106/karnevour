module karnevour {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    opens application.controllers to javafx.fxml;

    exports application.launch;
}