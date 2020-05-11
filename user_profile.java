package com.ncg.testproject;

public class user_profile
{
    int login_chk;



    String name;
    String email;
    String start_date;
    String end_date;
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public int getLogin_chk() {
        return login_chk;
    }

    public void setLogin_chk(int login_chk) {
        this.login_chk = login_chk;
    }

    public user_profile(int login_chk, String name, String email, String password, String start_date, String end_date) {
        this.login_chk=login_chk;
        this.name = name;
        this.email = email;
        this.password=password;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public user_profile()
    {
    }

}

