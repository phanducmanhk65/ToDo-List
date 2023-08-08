package com.example.todolist;

public class Task {
    private String name;
    private String date;
    private String status;

    public Task(String name, String date, String status) {
        this.name = name;
        this.date = date;
        this.status = status;
    }
    public Task() {
        // Empty constructor required by Firebase Database for deserialization.
        // You can leave it empty or initialize default values if needed.
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
