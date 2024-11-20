package com.example.se_tasklistapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.*;



public final class User extends Application {

    public User() {
        this.username="匿名用户";
        this.task_list=new ArrayList<Task>();
        this.categories=new ArrayList<Category>();
        this.categories.add(new Category("学习"));
        this.categories.add(new Category("工作"));
        this.categories.add(new Category("生活"));
    }
    
    private String username;
    private List<Task> task_list;
    private List<Category> categories;
    private List<Label> labels;

    public void createTask(Task t) {
        task_list.add(t);
    }

    /*public void setDueTime(LocalDateTime dateTime,Task t) {
        t.setDueTime(dateTime);
    }*/

    public void setUsername(String username) {
        this.username = username;
    }

    public void viewTasks() {
        
    }

    public void createCategory(Category category) {
        this.categories.add(category);
    }

    public void createLabel(Label label) {
        this.labels.add(label);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setTitle("Test!");
        stage.setScene(scene);
        stage.show();
    }

}
