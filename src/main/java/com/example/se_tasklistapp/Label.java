package com.example.se_tasklistapp;

import java.awt.Color;

public class Label {
    private String name;
    private Color color;

    public Label(String name,Color color) {
        this.name=name;
        this.color=color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
