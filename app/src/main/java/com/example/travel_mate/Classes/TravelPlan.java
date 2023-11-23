package com.example.travel_mate.Classes;

public class TravelPlan {
    private String userId;

    private String destination;
    private String details;
    private String travelDate;
    private String email;

    public TravelPlan() {
        // Default constructor required for Firebase
    }

    public TravelPlan(String userId, String destination, String details, String travelDate,String email) {
        this.userId = userId;
        this.destination = destination;
        this.details = details;
        this.travelDate =travelDate;
        this.email = email;
    }
    public String getUserId() {
        return userId;
    }


    public String getDestination() {
        return destination;
    }

    public String getDetails() {
        return details;
    }

    public String getTravelDate() {
        return travelDate;
    }
    public String getEmail(){
        return email;
    }


}
