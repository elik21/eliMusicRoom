package com.example.musicroom.Models;

import java.text.ParseException;
import java.util.Calendar;

public class roomAdmin  {

      private String name ;
   private String id;
    private int year;
    private String status;
    private String imageUrl;
    private int month;
    private int day;
    private String experience;
    private int age;
    private String permission;
    public String email;
    private String facebook;
    private String acountType;
    public String phone;

    public roomAdmin(String name, String Email, String phone,String id,String facebook,String accountType,String permission) {
        this.name = name;
        this.email=Email;
        this.phone = phone;
        this.status=status;
        this.id=id;
        this.facebook=facebook;
        this.acountType=accountType;
        this.permission=permission;
    }


    public roomAdmin(String name, String id, String imageUrl) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
    }
    public roomAdmin(){

    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }



    public void setStatus(String status) {
        this.status = status;
    }

    public int getYear() {
          return year; }

      public void setYear(int year) {
          this.year = year; }

      public int getMonth() {
          return month; }

      public void setMonth(int month) {
          this.month = month; }

      public int getDay() { return day; }

      public void setDay(int day) { this.day = day; }
      public String getName() {
        return name;
    }

      public void setName(String name) {
        this.name = name;
    }

      public String getId() {
        return id;
    }

      public void setId(String id) {
        this.id = id;
    }

      public String getExperience() {
        return experience;
    }

      public void setExperience(String experience) {
        this.experience = experience;
    }


      public  void setAge() throws ParseException {
          this.age = Integer.parseInt(countAge(year, month, day));
      }
      public int getAge() throws ParseException {
          return age; }
      public String getEmail() {
        return email;
      }

     /* public void setEmail(String email) {
        this.email = email;
      }*/

      public String getFacebook() {
        return facebook;
      }

      public void setFacebook(String facebook) {
        this.facebook = facebook;
      }
      public String getPhone() {
        return phone;
      }

      public void setPhone(String phone) {
        this.phone = phone;
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
          //int ageInt1 = age + 1;
          Integer ageInt = new Integer(age);
          String ageS = ageInt.toString();

          return ageS;
      }

    public String getAcountType() {
        return acountType;
    }

    public void setAcountType(String acountType) {
        this.acountType = acountType;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
