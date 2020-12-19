package com.example.musicroom.Models;

import java.text.ParseException;
import java.util.Calendar;

public class clientObj {
    private  String permission;
    private String name;
    private String Email;

    public String getImageSorce() {
        return ImageSorce;
    }

    private String ImageSorce;

    private int password;


    private  int age;
    private String id;



    private int day;
    private int month;
    private int year;
    public clientObj(String name, String email, String id, String permission) {
        this.name = name;
        this.Email = email;
        this.id = id;
        this.permission=permission;
    }
    public int getPassword() { return password; }
    public void setPassword(int password) { this.password = password; }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day= day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public  void setAge(int year,int month,int day) throws ParseException {
        this.age =Integer.parseInt(countAge(year,month,day));
    }
    public int getAge() throws ParseException {
        return age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String countAge (int year,int month, int day)throws ParseException
    {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month-1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (dob.get(Calendar.MONTH) > today.get(Calendar.MONTH) ||
                (dob.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                        dob.get(Calendar.DATE) > today.get(Calendar.DATE))){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}
