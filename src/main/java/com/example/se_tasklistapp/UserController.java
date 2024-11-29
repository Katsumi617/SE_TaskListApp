package com.example.se_tasklistapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserController {

    private User user;
    @FXML
    private Label welcomeText;
    @FXML
    private Label anotherText;
    @FXML
    private Label sortText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome,"+user.getUsername());
    }

    @FXML
    protected void viewTasks() {
        anotherText.setText("查看任务");
        user.viewTasks();
    }

    @FXML
    protected void sort() {
        sortText.setText("按时间排序");
        user.sortByDueTime();
        user.viewTasks();
    }

    public void setUser(User user) {
        this.user=user;
    }
}