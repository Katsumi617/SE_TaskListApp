package com.example.se_tasklistapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.*;

import java.util.Scanner;

public final class User extends Application {

    public User() {
        this.username="匿名用户";
        this.task_list=new ArrayList<Task>();
        this.categories=new ArrayList<Category>();
        this.reminders=new ArrayList<Notification>();
        //this.labels=new ArrayList<Label>();
        this.categories.add(new Category("学习"));
        this.categories.add(new Category("工作"));
        this.categories.add(new Category("生活"));
    }
    
    private String username;
    private List<Task> task_list;
    private List<Category> categories;
    //private List<Label> labels;
    private List<Notification> reminders;
    private static boolean running = true;
    private Task temp=null;

    public void createTask(Task t) {
        task_list.add(t);
    }

    public void setDueTime(LocalDateTime dateTime,Task t) {
        t.setDueTime(dateTime);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void viewTasks() {
        System.out.println(username+"你好,以下是你的任务：");
        int no=1;
        for(Task task : this.task_list) {
            System.out.print(no);
            System.out.print(".");
            task.showInfo();
            no++;
        }
    }

    private void chooseTask(int index) {
        this.temp = task_list.get(index);
    }

    public void createCategory(Category category) {
        this.categories.add(category);
    }

    /*public void createLabel(Label label) {
        this.labels.add(label);
    }*/

    public void createReminder(Notification notification) {
        this.reminders.add(notification);
    }

    public void checkNotifications() {
        for (Notification notification : this.reminders) {
            if (notification.isReminded())
                break;
            Duration diff = Duration.between(LocalDateTime.now(), notification.getRemindTime());
            if (diff.compareTo(notification.getAdvance()) <= 0) {
                notification.send();
            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setTitle("Test!");
        stage.setScene(scene);
        stage.show();*/

        Thread inputThread = new Thread(() -> {
            System.out.println("您好，"+username);
            Scanner scanner = new Scanner(System.in);
            while (running) {
                String input = scanner.nextLine();
                if ("exit".equalsIgnoreCase(input.trim())) {
                    running = false;
                    System.out.println("检测到退出命令，程序即将终止...");
                } else {
                    String[] parts = input.split(" ");
                    if (parts[0].equals("createTask")) {
                        input = scanner.nextLine();
                        parts = input.split(" ");
                        Task t=new Task(parts[0],LocalDateTime.parse(parts[1]),Duration.parse(parts[2]));
                        createTask(t);
                        Category c=new Category(parts[3]);
                        boolean flag=true;
                        for (Category c1 : this.categories) {
                            if (c1.getName().equals(parts[3])) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            createCategory(c);
                        }
                        t.setCategory(c);
                        int index=4;
                        while (index < parts.length) {
                            Label label = new Label(parts[index], Color.RED);
                            //createLabel(label);
                            t.setLabel(label);
                            index++;
                        }
                        createReminder(t.getNotification());
                    }
                    else if (parts[0].equals("viewTasks")) {
                        viewTasks();
                    }
                    else if (parts[0].equals("chooseTask")) {
                        int index=scanner.nextInt()-1;
                        chooseTask(index);
                    }
                    if (parts[0].equals("setDueTime")) {
                        input = scanner.nextLine();
                        parts = input.split(" ");
                        temp.setDueTime(LocalDateTime.parse(parts[0]));
                    }
                    else if (parts[0].equals("setCategory")) {
                        input = scanner.nextLine();
                        parts = input.split(" ");
                        Category c=new Category(parts[0]);
                        boolean flag=true;
                        for (Category c1 : this.categories) {
                            if (c1.getName().equals(parts[0])) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            createCategory(c);
                        }
                        temp.setCategory(c);
                    }
                    else if (parts[0].equals("setLabel")) {
                        input = scanner.nextLine();
                        parts = input.split(" ");
                        int index=0;
                        while (index < parts.length) {
                            Label label = new Label(parts[index], Color.BLUE);
                            //createLabel(label);
                            temp.setLabel(label);
                            index++;
                        }
                    }
                    else if (parts[0].equals("complete")) {
                        temp.complete();
                    }
                    else if (parts[0].equals("setUsername")) {
                        input = scanner.nextLine();
                        parts = input.split(" ");
                        setUsername(parts[0]);
                    }
                    else if (parts[0].equals("test")){
                        temp.showInfo();
                    }
                }
            }
            scanner.close();
        });

        inputThread.start();

        while (running) {
            try {
                // 暂停1秒钟
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("线程被中断: " + e.getMessage());
            }
            checkNotifications();
        }

        System.out.println("程序结束！");
    }

}
