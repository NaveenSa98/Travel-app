package com.example.travel_mate.Classes;

public class NearbyListing {
    private String name;
    private double distance;

    private String imageUrl;
    private String plusCode;

    public NearbyListing(String name, double distance,String imageUrl, String plusCode) {
        this.name = name;
        this.distance = distance;
        this.imageUrl = imageUrl;
        this.plusCode = plusCode;
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
    public String getPlusCode() {
        return plusCode;
    }
}
