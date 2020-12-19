package com.example.musicroom.Models;

import java.util.Comparator;

public class sortedList implements Comparator<rehersalRoom> {
    @Override
    public int compare(rehersalRoom i1, rehersalRoom i2) {

        int x= i1.getRoomName().compareTo(i2.getRoomName());
        if(x!=0){
            return x;
        }
        Integer x1=Integer.valueOf(i1.getPrice());
        Integer x2=Integer.valueOf(i2.getPrice());
        return (x1).compareTo(x2);
    }



}