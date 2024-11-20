package com.example.se_tasklistapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label anotherText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void viewTasks() {
        anotherText.setText("SEE");
    }
}