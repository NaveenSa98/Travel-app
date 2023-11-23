package com.example.travel_mate.Classes;


public class SearchPlace {
    private String name;
    private String district;
    private String category;

    // Empty constructor for Firebase
    public SearchPlace() {}

    public SearchPlace(String name, String district,String category) {
        this.name = name;
        this.district = district;
        this.category = category;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getDistrict() {
        return district;
    }

    public String getCategory() {
        return category;
    }
}
