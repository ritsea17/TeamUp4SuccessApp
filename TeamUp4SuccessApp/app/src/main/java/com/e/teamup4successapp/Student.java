package com.e.teamup4successapp;

public class Student {

    private String userID;
    private String Abteilung;
private String email;
    private String Person;

    public Student(String userID, String abteilung,String email, String person) {
        this.userID = userID;
        Abteilung = abteilung;

        Person = person;
    }

    public String getUserID() {
        return userID;
    }

    public String getAbteilung() {
        return Abteilung;
    }

    public String getEmail(){
        return email;
    }


    public String getPerson() {
        return Person;
    }
}
