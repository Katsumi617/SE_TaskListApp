package com.example.se_tasklistapp;

import java.awt.Color;

public class ColorfulText {
    // 将 Color 对象转换为 ANSI 转义码
    public static String getAnsiColor(Color color) {
        // 提取 RGB 值
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        // 构造 ANSI 转义码
        return String.format("\u001B[38;2;%d;%d;%dm", red, green, blue);
    }

    public static String textWithColor(Color color, String text) {
        // 获取对应的 ANSI 转义码
        String ansiColor = getAnsiColor(color);
        return ansiColor + text + "\u001B[0m";
    }
}

