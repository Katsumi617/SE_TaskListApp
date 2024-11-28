package com.example.se_tasklistapp;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private final String title;
    private LocalDateTime dueTime;
    private String status;
    private Category category;
    private List<Label> labels;
    private Notification notification;
    private Duration advance;

    public Task(String title,LocalDateTime dueTime,Duration advance) {
        this.title=title;
        this.dueTime=dueTime;
        this.status="unfinished";
        this.labels=new ArrayList<Label>();
        this.category=null;
        this.advance=advance;
        this.notification=new Notification(dueTime,this,advance);
    }

    public void complete() {
        this.status="finished";
    }

    public void setDueTime(LocalDateTime dateTime) {
        this.dueTime=dateTime;
        this.notification.setRemindTime(dateTime,advance);
    }

    public void setCategory(Category category) {
        this.category=category;
    }

    public void setLabel(Label label) {
        this.labels.add(label);
    }

    public String getTitle() {
        return title;
    }

    public void showInfo() {
        StringBuilder result= new StringBuilder("Name:" + this.title + " Category:" + this.category.getName() + " Label:");
        for(Label label:this.labels){
            result.append(ColorfulText.textWithColor(label.getColor(), label.getName()));
            result.append(" ");
        }
        result.append("Status:").append(this.status);
        System.out.println(result.toString());
        System.out.println("DueTime"+this.dueTime.toString());
    }

    public Notification getNotification() {
        return notification;
    }
}
