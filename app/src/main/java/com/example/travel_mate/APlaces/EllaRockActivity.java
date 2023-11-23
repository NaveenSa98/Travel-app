package com.example.travel_mate.APlaces;

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
import com.example.travel_mate.Classes.Reviews;
import com.example.travel_mate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EllaRockActivity extends AppCompatActivity {

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
        setContentView(R.layout.a_ellarock);

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

        slideModels.add(new SlideModel("https://cdn.getyourguide.com/img/tour/61d0a114e3cfa.jpeg/146.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.srilankainstyle.com/storage/app/media/Experiences/Ella%20Rock%20Climb/Ella-Rock-Climb-slider-5.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.mustseespots.com/sri-lanka/img/ella-rock.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.visitella.com/img/places/ella-rock/966_1104/01.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageButton = findViewById(R.id.imageButtonX_E);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionDetails_E);
        journeyToPlaceTextView = findViewById(R.id.journeyToPlaceDetails_E);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places").child("Attractions");

        // Get the place's ID from your intent or any other method
        String placeId = "Ella Rock";

        String description = "  Nestled in the breathtaking landscapes of Sri Lanka, Ella Rock stands tall as a majestic hiking destination. Ella Rock has become a must-visit spot for nature enthusiasts and avid hikers, offering panoramic views, lush greenery, and a sense of adventure. In this writing, we will explore the marvels of Ella Rock, highlighting its rich history, the thrilling hike to the summit, and the mesmerizing sights that await explorers at the top.\n" +
                "  Ella Rock holds significant historical importance in Sri Lanka. It was once a strategic vantage point during the colonial era, providing a panoramic view of the surrounding valleys and tea plantations. The rock has witnessed the passage of time and the region's transformation.  Ella Rock was an observation point for British administrators and surveyors during the British colonial period. They utilized the elevated position of the rock to oversee the vast tea estates and monitor the movement of people and goods in the area. The rock's prominence made it an ideal spot for a comprehensive regional view.  The colonial influence in the area also led to construction of a railway line passing through Ella. This development opened up the region to more visitors and facilitated the transportation of goods, further contributing to the growth of the local economy.\n" +
                "  In addition to its colonial significance, Ella Rock has cultural importance within the local community. It is believed that the rock has ties to ancient legends and folklore. Stories of mythical beings and ancient battles add a touch of mystique to this natural wonder.  Today, Ella Rock continues to attract travellers and adventure enthusiasts from all over the world. The history embedded within its rugged terrain and captivating landscapes adds a layer of fascination to the hiking experience. Explorers can immerse themselves in the historical essence of the rock while relishing the breathtaking views that extend far and wide.";

        String journeyToPlace = "  Begin your hike from the iconic Ella train station, the starting point for your thrilling journey. As you set foot on the train tracks, anticipation and excitement fill the air. Take a moment to appreciate the natural beauty surrounding you before you walk towards Kithalella.\n" +
                "  Don't be alarmed by the passing trains as you walk on the train tracks. They move slowly, allowing you to hear them approaching from a distance. If a train comes while you're on the tracks, step into the grass on the sides and wait until it passes. Maintain a steady pace for approximately 20-30 minutes until you reach the Kithalella station.\n" +
                "  Continuing along the train tracks for another 10 minutes, keep your eyes peeled for a yellow sign on the left, indicating a speed limit of 15 km/h. This serves as your cue to get off the train tracks. Turn left at the yellow sign and make your way into the fields.\n" +
                "  As you leave the train tracks behind, you'll soon come across a small bridge and reach the first fork in the road. Ignore signs pointing to the right that say \"This way to Ella Rock\" or similar messages. Instead, opt for the uphill path on the left, which takes you through tea plantations.\n " +
                "  Transitioning from tea bushes to tall grass, continue following the main trail through the expansive fields. While smaller trails and forks may divert your attention, sticking to the broader, more beaten path is essential to ensure you're on the right track.\n" +
                "  After approximately 30 minutes in the tall grass, the terrain will change to rocky ground, accompanied by lush trees. Continue your ascent through the forest, pushing uphill until you reach a flat plateau. Take a well-deserved break at the intermediate viewpoint, offering a perfect spot to rest and enjoy the stunning surroundings. \n" +
                "  From the viewpoint, prepare for the final stretch—a steep hill adorned with numerous trees. There isn't a single defined trail, but numerous paths zig-zag their way up through the forest. Keep heading uphill, and you'll find yourself on the right path.\n" +
                "  Reaching the Peak: Ella Rock As you emerge from the forest and reach the next plateau, a sense of accomplishment washes over you. Look out for a little hut and a Sri Lankan flag, which proudly mark the peak of Ella Rock. Congratulations! You've made it to the top. Take a moment to revel in the panoramic views and let the beauty of the surroundings captivate your senses.\n" +
                "  The final stretch of the hike involves a steep climb, challenging your endurance and agility. However, the rewarding feeling of reaching the summit makes it all worthwhile. At the top, you'll be greeted by awe-inspiring views of the Ella Gap, Little Adam's Peak, and the surrounding mountain ranges";

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

        Button accommodationButton = findViewById(R.id.accommodationButton_E);

        accommodationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the URL for Booking.com
                String bookingUrl = "https://www.booking.com/searchresults.en-gb.html?ss=Ella+Rock&ssne=Ella+Rock&ssne_untouched=Ella+Rock&efdco=1&label=gog235jc-1DCAEoggI46AdIM1gDaIUBiAEBmAEJuAEXyAEM2AED6AEBiAIBqAIDuAKuwfmpBsACAdICJDYxMzkwOTRiLTlkZTEtNDY3NS1iY2NiLWNlOTIwYjVjYzBiNtgCBOACAQ&sid=edf2329e549776c19fde29e46829dcdf&aid=397594&lang=en-gb&sb=1&src_elem=sb&src=searchresults&dest_id=900059522&dest_type=landmark&checkin=2023-11-03&checkout=2023-11-04&group_adults=1&no_rooms=1&group_children=0";

                // Create an Intent to open a web browser with the Booking.com URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(bookingUrl));

                // Start the activity to open the web browser
                startActivity(intent);
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
        double latitude = 6.86;
        double longitude = 81.04;

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
        double latitude = 6.86;
        double longitude =  81.04;

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

            String placeId = "Ella Rock";

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
        String placeId = "Ella Rock";

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