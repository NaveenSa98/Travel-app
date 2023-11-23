package com.example.travel_mate.Classes;

public class WaterFall {
    private String name;
    private double distance;

    private String imageUrl;


    public WaterFall(String name, double distance,String imageUrl ) {
        this.name = name;
        this.distance = distance;
        this.imageUrl = imageUrl;

    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }
    public String getImageUrl() {
        return imageUrl;
    }



}
