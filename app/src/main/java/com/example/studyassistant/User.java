package com.example.studyassistant;

public class User {
    private String name;
    private String email;
    private String institution;
    private String sex;

    public User(){
        //You must have an empty constructor
    }

    public User(String name, String email, String institution, String sex){
        this.name = name;
        this.email = email;
        this.institution = institution;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", institution='" + institution + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
