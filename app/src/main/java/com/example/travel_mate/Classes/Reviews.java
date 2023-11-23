package com.example.travel_mate.Classes;

public class Reviews {
    private float rating;
    private String text;

    private String username;

    private String userEmail;




    public Reviews() {
        // Default constructor required for DataSnapshot.getValue(Review.class)
    }

    public Reviews(float rating, String text,String username,String userEmail) {
        this.rating = rating;
        this.text = text;
        this.username = username;
        this.userEmail =userEmail;

    }

    public float getRating() {

        return rating;
    }

    public String getText() {
        return text;
    }
    public String getUsername() {

        return username;
    }
    public String getUserEmail() {

        return userEmail;
    }


}