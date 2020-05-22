package com.example.studyassistant;

public class Schedule {
    private static int count = 0;
    private int id;
    private String user_id;
    private String start;
    private String end;
    private int weekday_hours;
    private int weekend_hours;

    public Schedule(){
        //you must have an empty constructor
    }
    public Schedule(String user_id, String start, String end, int weekday_hours, int weekend_hours){
        setId(++count);
        this.user_id = user_id;
        this.start = start;
        this.end = end;
        this.weekday_hours = weekday_hours;
        this.weekend_hours = weekend_hours;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Schedule.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getWeekday_hours() {
        return weekday_hours;
    }

    public void setWeekday_hours(int weekday_hours) {
        this.weekday_hours = weekday_hours;
    }

    public int getWeekend_hours() {
        return weekend_hours;
    }

    public void setWeekend_hours(int weekend_hours) {
        this.weekend_hours = weekend_hours;
    }
}
