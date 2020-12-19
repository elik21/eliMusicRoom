package com.example.musicroom.Models;


import java.io.Serializable;

public class rehersalRoom implements Serializable {
 //   public boolean isChecked;
    private String roomName;
    private String country;
    private String imageUrl;
    private String openningHours;
    private String Site;
    private String FacebookPage;
    private String  services;
    private roomAdmin admin;
    private String startHour;
    private String endHour;
    private String city;
    private String  adress;
    private  String contact;
    private String teqnicalSpecification;
    private int imageIcon;
    private String email;
    private String price;

    private String price1;
    private String price2;
    private  String adminName;
    public String getPrice() {
        return price;
    }
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    static int pos=0;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String uid;

    public int getPos() {
        return pos;
    }



    public void setcPos(int pos) {
        this.pos = pos;
    }
    public static int incPos() {
        pos++;
        return pos++;
    }


    public rehersalRoom(String roomName, String city, int imageIcon) {
        this.roomName = roomName;
        this.city = city;
        this.imageIcon = imageIcon;
    }
    public rehersalRoom(String roomName, String city, String imageUrl, String uid,String email) {
        this.roomName = roomName;
        this.city = city;
        this.imageUrl= imageUrl;
        this.uid=uid;
        this.email=email;

    }
    public rehersalRoom(){

    }

  /*  protected rehersalRoom(Parcel in) {
        isChecked = in.readByte() != 0;
        roomName = in.readString();
        country = in.readString();
        imageUrl = in.readString();
        openningHours = in.readString();
        Site = in.readString();
        FacebookPage = in.readString();
        services = in.readString();
        startHour = in.readInt();
        endHour = in.readInt();
        imageIcon = in.readInt();
        city = in.readString();
        adress = in.readString();
        contact = in.readString();
        teqnicalSpecification = in.readString();

    }*/



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public void setAdminPhone(roomAdmin admin) {
        admin.getPhone();
    }


    public int getImageIcon() {
        return imageIcon;
    }
    public String getRoomName() {
        return roomName;
    }


    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOpenningHours() {
        return openningHours;
    }

    public void setOpenningHours(String openningHours) {
        this.openningHours = openningHours;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getFacebookPage() {
        return FacebookPage;
    }

    public void setFacebookPage(String facebookPage) {
        FacebookPage = facebookPage;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public roomAdmin getAdmin() {
        return admin;
    }

    public void setAdmin(roomAdmin admin) {
        this.admin = admin;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public void setImageIcon(int imageIcon) {
        this.imageIcon = imageIcon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTeqnicalSpecification() {
        return teqnicalSpecification;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {

        this.uid = uid;
    }

    public void setTeqnicalSpecification(String teqnicalSpecification) {
        this.teqnicalSpecification = teqnicalSpecification;
    }



}