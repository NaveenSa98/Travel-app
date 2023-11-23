package com.example.travel_mate.Classes;

public class PlaceA {
    private String name;
    private String district;
    private String imageURL;

    private String weatherDescription;
    private String temperature;






    public PlaceA() {
        // Default constructor required for Firebase
    }

    public PlaceA(String name, String district, String imageURL) {
        this.name = name;
        this.district = district;
        this.imageURL = imageURL;

    }

    public String getName() {

        return name;
    }

    public String getdistrict() {

        return district;
    }

    public String getImageURL() {

        return imageURL;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }


}
