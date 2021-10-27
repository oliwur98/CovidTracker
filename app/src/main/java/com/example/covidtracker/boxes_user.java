package com.example.covidtracker;

import com.google.firebase.firestore.auth.User;

public class boxes_user {
    String email, booked_day_time, id, Vaccine, day_first_vaccination, month_first_vaccination, numeric_date, center, sick;


    public boxes_user(){}



    public boxes_user(String email, String booked_day_time, String id, String sick) {
        this.email = email;
        this.booked_day_time = booked_day_time;
        this.id = id;
        this.Vaccine = Vaccine;
        this.day_first_vaccination = day_first_vaccination;
        this.month_first_vaccination = month_first_vaccination;
        this.numeric_date = numeric_date;
        this.center = center;
        this.sick = sick;
    }

    public String getSick() {
        return sick;
    }

    public void setSick(String sick) {
        this.sick = sick;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBooked_day_time() {
        return booked_day_time;
    }

    public void setBooked_day_time(String booked_day_time) {
        this.booked_day_time = booked_day_time;
    }
    public String getVaccine() {
        return Vaccine;
    }

    public void setVaccine(String vaccine) {
        Vaccine = vaccine;
    }

    public String getDay_first_vaccination() {
        return day_first_vaccination;
    }

    public void setDay_first_vaccination(String day_first_vaccination) {
        this.day_first_vaccination = day_first_vaccination;
    }

    public String getMonth_first_vaccination() {
        return month_first_vaccination;
    }

    public void setMonth_first_vaccination(String month_first_vaccination) {
        this.month_first_vaccination = month_first_vaccination;
    }

    public String getNumeric_date() {
        return numeric_date;
    }

    public void setNumeric_date(String numeric_date) {
        this.numeric_date = numeric_date;
    }
}
