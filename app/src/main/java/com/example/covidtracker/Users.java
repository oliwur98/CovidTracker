package com.example.covidtracker;

public class Users {

    String firstName, lastName;
    long doses;

    public Users(){}


    public String getFirstName() {
        return firstName;
    }

    public Users(String firstName, String lastName, long doses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.doses = doses;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getDoses() {
        return doses;
    }

    public void setDoses(long doses) {
        this.doses = doses;
    }
}
