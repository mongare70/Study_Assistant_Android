package com.example.studyassistant;

public class Module {
    private static int count = 0;
    private int id;
    private String name;
    private int rating;
    private String grade;
    private int schedule_id;

    public Module(){
        //you must have an empty constructor
    }

    public Module(String name, int rating, String grade, int schedule_id){
        setId(++count);
        this.name = name;
        this.rating = rating;
        this.grade = grade;
        this.schedule_id = schedule_id;
    }

    public Module(String name, int rating){
        setId(++count);
        this.name = name;
        this.rating = rating;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Module.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }
}
