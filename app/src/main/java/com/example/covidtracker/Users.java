package com.example.covidtracker;

public class Users {

    String firstName, lastName;
    String booked_day_time;
    String county;

    public Users(){}


    public String getFirstName() {
        return firstName;
    }

    public Users(String firstName, String lastName, String booked_day_time, String county) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.booked_day_time = booked_day_time;
        this.county = county;
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

    public String getBooked_day_time() {
        return booked_day_time;
    }

    public void setBooked_day_time(String booked_day_time) {
        this.booked_day_time = booked_day_time;
    }

    public void setCounty(String county) {this.county = county;}

    public String getCounty(){return county;}
}
