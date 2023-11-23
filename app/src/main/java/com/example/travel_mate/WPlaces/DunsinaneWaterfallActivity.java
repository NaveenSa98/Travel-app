package com.example.travel_mate.WPlaces;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.travel_mate.Adapters.ReviewAdapter;
import com.example.travel_mate.Adapters.WaterfallAdapter;
import com.example.travel_mate.Classes.Reviews;
import com.example.travel_mate.Classes.WaterFall;
import com.example.travel_mate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DunsinaneWaterfallActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private DatabaseReference placesRef;
    private TextView placeDescriptionTextView;
    private TextView journeyToPlaceTextView;
    private TextView reviewTitle;
    private ImageButton emergencyButton1,emergencyButton2;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private LocationManager locationManager;


    private RatingBar ratingBar;
    private EditText reviewInput,nameInput;
    private Button submitReviewButton;

    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_dunsinane_waterfall);

        // Initialize Firebase Database reference
        placesRef = FirebaseDatabase.getInstance().getReference("Reviews");


        // Initialize views
        ratingBar = findViewById(R.id.ratingBar_E);
        reviewInput = findViewById(R.id.reviewInput_E);
        nameInput = findViewById(R.id.nameInput_E);
        submitReviewButton = findViewById(R.id.submitReviewButton_E);
        reviewRecyclerView = findViewById(R.id.reviewRecyclerView_E);
        reviewTitle = findViewById(R.id.reviewTitle_E);

        reviewAdapter = new ReviewAdapter();
        reviewRecyclerView.setAdapter(reviewAdapter);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        // Set an onClickListener for the Submit button
        submitReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReview();
            }
        });

        loadReviews();



        ImageSlider imageSlider = findViewById(R.id.imageSlider_E);

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/Dunsinane_Falls.jpg/1200px-Dunsinane_Falls.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.thingstodosrilanka.com/wp-content/uploads/2020/05/dunsinane-falls.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://ceylonpages.lk/wp-content/uploads/listing-uploads/gallery/2020/06/90408400_509152293297635_3786555943581710221_n.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://duqjpivknq39s.cloudfront.net/2019/01/800x750-3.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageButton = findViewById(R.id.imageButtonX_E);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionDetails_E);
        journeyToPlaceTextView = findViewById(R.id.journeyToPlaceDetails_E);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places").child("WaterFalls");

        // Get the place's ID from your intent or any other method
        String placeId = "Dunsinane Waterfall";
        // Example description and journeyToPlace strings with paragraphs
        String description = "Dunsinane Falls, also known as Pundalu Oya Falls, is a hidden gem in the Pundalu Oya village of Nuwara Eliya district in Sri Lanka. With a height of 100 meters, it is the 15th highest waterfall in Sri Lanka and cascades in sequential steps to join the Pundalu Oya River, a tributary of Kotmale Oya. This writing aims to give readers an insight into this picturesque waterfall and provide guidance on how to reach it.\n\n" +
                "Dunsinane Falls is between two tea estates, Dunsinane Estate and Sheen Estate. As a result, it was previously known as Dunsinane-Sheen Falls or Dunsinanshin Falls. However, over time, it came to be known as Dunsinane Falls. The upper part of the fall is 30 meters and flows through a vast rocky plain. On the left side of the upper falls, you will find a small Hindu Kovil and a statue built in the middle of the rocky plain, adding to the waterfall's unique beauty. This part of the waterfall is viewable from the bridge. The lower part of the waterfall, which is 70 meters, cascades down the bridge, and there is no proper path to reach the base of the lower segment. However, if you walk the road a little further, you can photograph both parts in a beautiful frame.\n\n" +
                "Things to do\n" +
                "\n" +
                "Besides admiring the stunning waterfall, visitors can enjoy a picnic in scenic surroundings. Several spots near the waterfall allow visitors to set up a picnic and enjoy the breathtaking views.\n" +
                "\n" +
                "Hiking enthusiasts can take a trek to the top of the waterfall, which offers a panoramic view of the surrounding tea estates and hills. Visitors can also explore the nearby tea estates and learn about tea-making.\n" +
                "\n" +
                "For those seeking a more adventurous experience, there are opportunities for canyoning and rappelling down the waterfall. However, it is essential to do so with a licensed guide and ensure safety equipment is used";
        String journeyToPlace = "From Nuwara Eliya: If travelling from Nuwara Eliya, take A5 (PBC Highway) and reach Pundalu Oya junction. Then, turn left and travel along the road to reach your destination.\n" +
                "\n" +
                "From Colombo: If you are travelling from Colombo, take the Colombo-Kandy road, and travel up to Mawanella. Then, take B279 via Hemmathagama and Ambuluwawa to reach Gampola. From there, take A5 (PBC Highway), and at Thawalanthenna junction, take a right and travel along Tawalanthenna-Thalawakele road (B412). After crossing about 14.5 km, take a left to the street where Dunsinane Falls is situated. Travel another 2.7 km to reach the fall.\n" +
                "\n" +
                "Another option is to take the Avissawella-Hatton-Nuwara Eliya highway from Colombo and reach Ginigathhena. Turn left to Nawalapitiya-Ginigathhena road (B319), and from Nawalapitiya, take Nawalapitiya-Harangala road (B506). Join Harangala-Kalapitiya-Kumbaloluwa Road (B148), and from Kumbaloluwa, turn right to Tawalanthenna-Thalawakele Road (B412). From there, take a right to the road where Dunsinane Falls is situated.";

// Store the data in Firebase
        placesRef.child(placeId).child("Description").setValue(description);
        placesRef.child(placeId).child("journeyToPlace").setValue(journeyToPlace);


        // Retrieve place data from Firebase
        placesRef.child(placeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get location data
                    String destinationlatitude = dataSnapshot.child("latitude").getValue(String.class);
                    String destinationlongitude = dataSnapshot.child("longitude").getValue(String.class);

                    // Get place details
                    String description = dataSnapshot.child("Description").getValue(String.class);
                    String journeyToPlace = dataSnapshot.child("journeyToPlace").getValue(String.class);

                    String[] descriptionParagraphs = description.split("\n");
                    String[] journeyParagraphs = journeyToPlace.split("\n");

                    StringBuilder descriptionText = new StringBuilder();
                    for (String paragraph : descriptionParagraphs) {
                        descriptionText.append(paragraph).append("\n"); // Add a newline after each paragraph
                    }

                    StringBuilder journeyText = new StringBuilder();
                    for (String paragraph : journeyParagraphs) {
                        journeyText.append(paragraph).append("\n"); // Add a newline after each paragraph
                    }

                    placeDescriptionTextView.setText(descriptionText.toString());
                    journeyToPlaceTextView.setText(journeyText.toString());

                    // Set an OnClickListener for the "Open in Google Maps" button
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Open Google Maps with the specified location
                            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destinationlatitude + "," + destinationlongitude);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database errors here
            }
        });


        emergencyButton1 = findViewById(R.id.hospitalButton_E);
        emergencyButton2 = findViewById(R.id.policeStationButton_E);

        emergencyButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLocationPermission()) {
                    // Get the user's current location and open Google Maps with nearby emergency places
                    openHospitalInGoogleMaps ();
                }
            }
        });
        emergencyButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLocationPermission()) {
                    // Get the user's current location and open Google Maps with nearby emergency places
                    openPoliceStationInGoogleMaps();
                }
            }
        });
        ImageButton backButton = findViewById(R.id.backButton_E);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back button click here
                // You can use finish() to close the current activity
                finish();
            }
        });

        // Initialize your RecyclerView
        RecyclerView nearestLocationsRecyclerView = findViewById(R.id.nearestLocationsRecyclerView_E);
        nearestLocationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Create a list of Waterfall objects with data
        List<WaterFall> waterfalls = new ArrayList<>();
        waterfalls.add(new WaterFall("Devon Falls", 26,"https://www.travellankaconnection.com/images/destinations/gallery_Devon-Water-Falls.jpg"));
        waterfalls.add(new WaterFall("Gartmore Falls", 68,"https://www.attractionsinsrilanka.com/wp-content/uploads/2022/02/Upper-Gartmore-Falls.jpg"));
        waterfalls.add(new WaterFall("Bomburu Ella", 56,"https://amazinglanka.com/wp/wp-content/uploads/2014/03/bomburu2.jpg"));
        waterfalls.add(new WaterFall("Ramboda Falls", 26,"https://amazinglanka.com/wp/wp-content/uploads/2014/06/lowerramboda-01-1024x576.jpg"));
        // Add more waterfall data as needed

        // Create the adapter and set it to the RecyclerView
        WaterfallAdapter adapter = new WaterfallAdapter(this, waterfalls);

        nearestLocationsRecyclerView.setAdapter(adapter);




    }
    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }
    //    private void openEmergencyPlacesInGoogleMaps() {
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Location lastKnownLocation = null;
//
//        if (locationManager != null) {
//            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        }
//
//        if (lastKnownLocation != null) {
//            double latitude = lastKnownLocation.getLatitude();
//            double longitude = lastKnownLocation.getLongitude();
    private void openHospitalInGoogleMaps() {
        // Replace the coordinates below with the user's current location
        double latitude = 7.01;
        double longitude =  80.67;

        // Create a URI for searching nearby hospitals
        Uri hospitalUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=hospitals");

        // Create an intent to open Google Maps for hospitals
        Intent hospitalIntent = new Intent(Intent.ACTION_VIEW, hospitalUri);
        hospitalIntent.setPackage("com.google.android.apps.maps");

        // Start the intent to show nearby hospitals
        startActivity(hospitalIntent);
    }
    private void openPoliceStationInGoogleMaps() {
        // Replace the coordinates below with the user's current location
        double latitude = 7.01;
        double longitude =  80.67;

        // Create a URI for searching nearby police stations
        Uri policeStationUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=police stations");

        // Create an intent to open Google Maps for police stations
        Intent policeStationIntent = new Intent(Intent.ACTION_VIEW, policeStationUri);
        policeStationIntent.setPackage("com.google.android.apps.maps");

        // Start the intent to show nearby police stations
        startActivity(policeStationIntent);
    }

    //}
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openHospitalInGoogleMaps();
            } if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openPoliceStationInGoogleMaps();
            }else {
                // Handle permission denial
            }
        }
    }
    // Method to submit a review
    private void submitReview() {
        // Get the user's rating and review text
        float rating = ratingBar.getRating();
        String reviewText = reviewInput.getText().toString();
        String reviewUser = nameInput.getText().toString();


        // Check if the review text is not empty
        if (TextUtils.isEmpty(reviewText)) {
            Toast.makeText(this, "Please enter a review", Toast.LENGTH_SHORT).show();
            return;
        }

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // Get the user's email address
            String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            String placeId = "Dunsinane Waterfall";

            placesRef = FirebaseDatabase.getInstance().getReference("Reviews");


            // Create a new review object
            Reviews review = new Reviews(rating, reviewText, reviewUser, userEmail);

            // Generate a unique key for the review
            String reviewId = placesRef.child(placeId).push().getKey();

            // Save the review in Firebase with the generated key
            placesRef.child(placeId).child(reviewId).setValue(review);
            // Clear the review input field
            reviewInput.setText("");
            nameInput.setText("");
            ratingBar.setRating(0);

            ratingBar.setVisibility(View.GONE);
            reviewInput.setVisibility(View.GONE);
            nameInput.setVisibility(View.GONE);
            submitReviewButton.setVisibility(View.GONE);
            reviewTitle.setVisibility(View.GONE);

            Toast.makeText(this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadReviews() {
        // Get the place ID for which you want to load reviews (replace with the actual place ID)
        String placeId = "Dunsinane Waterfall";

        // Get a reference to the reviews for the specific place
        DatabaseReference placeReviewsRef = placesRef.child(placeId);

        placeReviewsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Reviews> reviews = new ArrayList<>();
                for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                    Reviews review = reviewSnapshot.getValue(Reviews.class);
                    if (review != null) {
                        reviews.add(review);
                    }
                }

                // Update the RecyclerView with the reviews
                reviewAdapter.setReviews(reviews);

                // Show the RecyclerView if there are reviews to display
                reviewRecyclerView.setVisibility(reviews.isEmpty() ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database errors here
            }
        });
    }
}