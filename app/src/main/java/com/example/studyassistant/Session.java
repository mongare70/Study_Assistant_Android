package com.example.studyassistant;

public class Session {
    private static int count = 0;
    private int id;
    private String module;
    private String date;
    private String status;
    private int completed_time;

    public Session(){
        //You must have an empty constructor
    }

    public Session(String module, String date){
        setId(++count);
        this.module = module;
        this.date = date;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Session.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
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

    public int getCompleted_time() {
        return completed_time;
    }

    public void setCompleted_time(int completed_time) {
        this.completed_time = completed_time;
    }
}
