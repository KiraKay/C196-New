package com.courseproject.model;

import android.database.Cursor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class AlertNotification {

    private int id;
    private String type;
    private String occurence;
    private LocalDate date;

    public AlertNotification(int id, String type, String occurence, LocalDate date) {

        this.id = id;
        this.type = type;
        this.occurence = occurence;
        this.date = date;

    }

    public AlertNotification(String type, String occurence, LocalDate date) {

        this.type = type;
        this.occurence = occurence;
        this.date = date;

    }

    public AlertNotification(Cursor cursor) {

        this(cursor.getInt(0), cursor.getString(1),
                cursor.getString(2),
                Instant.ofEpochMilli(cursor.getLong(3)).atZone(
                        ZoneId.systemDefault()).toLocalDate());

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOccurence() {
        return occurence;
    }

    public void setOccurence(String occurence) {
        this.occurence = occurence;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
