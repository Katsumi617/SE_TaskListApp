package com.example.se_tasklistapp;

import java.time.LocalDateTime;

public class Notification {
    private LocalDateTime remindTime;
    private Task t;
    
    public Notification(LocalDateTime dateTime,Task task) {
        this.remindTime=dateTime;
        this.t=task;
    }

    public void send() {
        
    }
}
