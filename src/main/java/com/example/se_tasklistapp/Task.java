package com.example.se_tasklistapp;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private String title;
    private LocalDateTime dueTime;
    private String status;
    private Category category;
    private List<Label> labels;
    private Notification notification;

    public Task(String title,LocalDateTime dueTime) {
        this.title=title;
        this.dueTime=dueTime;
        this.status="unfinished";
        this.labels=new ArrayList<Label>();
    }

    public void complete() {
        this.status="finished";
    }

    public void setDueTime(LocalDateTime dateTime) {
        this.dueTime=dateTime;
        this.notification=new Notification(dateTime,this);
    }

    public void setCategory(Category category) {
        this.category=category;
    }

    public void setLabel(Label label) {
        this.labels.add(label);
    }
}
