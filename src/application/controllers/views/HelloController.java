package application.controllers.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {

    @FXML
    private Label helloText;


    @FXML
    public void buttonClick() {
        helloText.setText("Hello JavaFX!");
    }
}
