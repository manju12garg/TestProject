package com.ncg.testproject;

public class data_input {

    String name,email,password,start_date,end_date;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {return password;}

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public data_input(String name, String email,String password, String start_date, String end_date) {
        this.name = name;
        this.email = email;
        this.password=password;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public data_input() {
    }
}
