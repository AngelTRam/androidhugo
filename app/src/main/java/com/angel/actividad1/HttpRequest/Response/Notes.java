package com.angel.actividad1.HttpRequest.Response;

public class Notes {
    int id;
    String title;
    String message;
    String date;

    public Notes(int id, String title, String message, String date) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
