package com.example.se_tasklistapp;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.time.*;

import java.util.Scanner;

public final class User extends Application {

    public User() {
        this.username="匿名用户";
        this.task_list=new ArrayList<Task>();
        this.temp_task_list=new ArrayList<Task>();
        this.close_task_list=new ArrayList<Task>();
        this.deleted_task_list=new ArrayList<Task>();
        this.categories=new ArrayList<Category>();
        this.reminders=new ArrayList<Notification>();
        this.labels=new ArrayList<Label>();
        this.categories.add(new Category("学习"));
        this.categories.add(new Category("工作"));
        this.categories.add(new Category("生活"));
    }
    
    private String username;
    private List<Task> task_list;
    private List<Task> temp_task_list;
    private List<Task> close_task_list;
    private List<Task> deleted_task_list;
    private List<Category> categories;
    private List<Label> labels;
    private List<Notification> reminders;
    private static boolean running = true;
    private int status=0;
    private Task temp=null;

    public String getUsername() {
        return this.username;
    }

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
        status=0;
    }

    public void viewFilteredTasks() {
        System.out.println(username+"你好,以下是筛选过的任务：");
        int no=1;
        for(Task task : this.temp_task_list) {
            System.out.print(no);
            System.out.print(".");
            task.showInfo();
            no++;
        }
        status=1;
    }

    private void viewExpiringTasks() {
        System.out.println(username+"你好,以下是即将到期的任务：");
        int no=1;
        for(Task task : this.close_task_list) {
            System.out.print(no);
            System.out.print(".");
            task.showInfo();
            no++;
        }
        status=2;
    }

    private void chooseTask(int index) {
        this.temp = task_list.get(index);
    }

    private void chooseFilteredTask(int index) {
        this.temp = temp_task_list.get(index);
    }

    private void chooseExpiringTask(int index) {
        this.temp = close_task_list.get(index);
    }

    public void createCategory(Category category) {
        this.categories.add(category);
    }

    public void createLabel(Label label) {
        this.labels.add(label);
    }

    public void createReminder(Notification notification) {
        this.reminders.add(notification);
    }

    public void checkNotifications() {
        for (Notification notification : this.reminders) {
            if (notification.isReminded())
                continue;
            Duration diff = Duration.between(LocalDateTime.now(), notification.getRemindTime());
            if (diff.compareTo(notification.getAdvance()) <= 0) {
                notification.send();
                close_task_list.add(notification.getTask());
            }
        }
    }

    public void filterByLabel(String label) {
        temp_task_list.clear();
        for (Task task : this.task_list) {
            for (Label label1 : task.getLabels()) {
                if (label1.getName().equals(label)) {
                    temp_task_list.add(task);
                    break;
                }
            }
        }

    }

    public void filterByCategory(Category category) {
        temp_task_list.clear();
        for (Task task : this.task_list) {
            if (task.getCategory().equals(category)) {
                temp_task_list.add(task);
            }
        }
    }

    public void filterByStatus(String status) {
        temp_task_list.clear();
        for (Task task : this.task_list) {
            if (task.getStatus().equals(status)) {
                temp_task_list.add(task);
            }
        }
    }

    public void sortByDueTime() {
        Collections.sort(task_list);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);

        UserController controller = fxmlLoader.getController();
        controller.setUser(this);

        stage.setTitle("DDL");
        stage.setScene(scene);
        stage.show();

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
                    switch (parts[0]) {
                        case "createTask" -> {
                            input = scanner.nextLine();
                            parts = input.split(" ");
                            Task t = new Task(parts[0], LocalDateTime.parse(parts[1]), Duration.parse(parts[2]));
                            createTask(t);
                            Category c = new Category(parts[3]);
                            boolean flag = true;
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
                            int index = 4;
                            while (index < parts.length) {
                                Label label = new Label(parts[index], Color.RED);
                                //createLabel(label);
                                t.setLabel(label);
                                index++;
                            }
                            createReminder(t.getNotification());
                        }
                        case "viewTasks" -> viewTasks();
                        case "viewFilteredTasks" -> viewFilteredTasks();
                        case "viewExpiringTasks"  -> viewExpiringTasks();
                        case "chooseTask" -> {
                            switch (status) {
                                case 0 -> {
                                    int index = scanner.nextInt() - 1;
                                    chooseTask(index);
                                }
                                case 1 -> {
                                    int index = scanner.nextInt() - 1;
                                    chooseFilteredTask(index);
                                }
                                case 2 -> {
                                    int index = scanner.nextInt() - 1;
                                    chooseExpiringTask(index);
                                }
                            }
                        }
                        case "setTitle" -> {
                            input = scanner.nextLine();
                            parts = input.split(" ");
                            temp.setTitle(parts[0]);
                        }
                        case "setDueTime" -> {
                            input = scanner.nextLine();
                            parts = input.split(" ");
                            temp.setDueTime(LocalDateTime.parse(parts[0]));
                            this.close_task_list.remove(temp);
                        }
                        case "setCategory" -> {
                            input = scanner.nextLine();
                            parts = input.split(" ");
                            Category c = new Category(parts[0]);
                            boolean flag = true;
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
                        case "setLabel" -> {
                            input = scanner.nextLine();
                            parts = input.split(" ");
                            int index = 0;
                            while (index < parts.length) {
                                Label label = new Label(parts[index], Color.BLUE);
                                //createLabel(label);
                                temp.setLabel(label);
                                index++;
                            }
                        }
                        case "complete" -> temp.complete();
                        case "deleteTask" -> {
                            deleted_task_list.add(temp);
                            task_list.remove(temp);
                            temp_task_list.remove(temp);
                            close_task_list.remove(temp);
                            temp=null;
                        }
                        case "setAdvance" -> {
                            input = scanner.nextLine();
                            parts = input.split(" ");
                            temp.setAdvance(Duration.parse(parts[0]));
                            this.close_task_list.remove(temp);
                        }
                        case "setUsername" -> {
                            input = scanner.nextLine();
                            parts = input.split(" ");
                            setUsername(parts[0]);
                        }
                        case "test" -> temp.showInfo();
                        case "filterTasks" -> {
                            input = scanner.nextLine();
                            parts = input.split(" ");
                            switch (parts[0]) {
                                case "Label" -> {
                                    filterByLabel(parts[1]);
                                    viewFilteredTasks();
                                }
                                case "Category" -> {
                                    filterByCategory(new Category(parts[1]));
                                    viewFilteredTasks();
                                }
                                case "Status" -> {
                                    filterByStatus(parts[1]);
                                    viewFilteredTasks();
                                }
                            }
                        }
                        case "sort" -> sortByDueTime();
                    }
                }
            }
            scanner.close();
        });
        inputThread.setDaemon(true);
        inputThread.start();

        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), event -> {
            if (running) {
                checkNotifications();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE); // 设置为无限循环
        timeline.play(); // 启动Timeline
    }


}
