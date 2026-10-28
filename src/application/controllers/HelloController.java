package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class HelloController {

    @FXML
    private Label helloText;


    @FXML
    public void buttonClick() {
        helloText.setText("Hello JavaFX!");
    }
}
