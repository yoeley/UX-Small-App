package com.example.footapp;

public class TimeParser {

    public static String timeElementToString(int timeElement) {
        if (timeElement < 10) {
            return "0" + Integer.toString(timeElement);
        }
        else {
            return Integer.toString(timeElement);
        }
    }
}
