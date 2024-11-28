package com.example.se_tasklistapp;

import java.time.*;

public class Notification {
    private LocalDateTime remindTime;
    private Task t;
    private Duration advance;
    private boolean reminded;
    
    public Notification(LocalDateTime dateTime,Task task,Duration advance) {
        this.remindTime=dateTime;
        this.t=task;
        this.advance=advance;
        this.reminded=false;
    }

    public void send() {
        System.out.println(this.t.getTitle()+" is due in "+advance.getSeconds()+ " seconds");
        this.reminded=true;
    }

    public void setRemindTime(LocalDateTime remindTime,Duration advance) {
        this.remindTime = remindTime;
        this.advance=advance;
    }

    public LocalDateTime getRemindTime() {
        return remindTime;
    }

    public Duration getAdvance() {
        return advance;
    }

    public boolean isReminded() {
        return reminded;
    }
}
