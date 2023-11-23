package com.example.travel_mate.NpPlaces;

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

public class KumanaParkActivity extends AppCompatActivity {

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
        setContentView(R.layout.np_kumana_park);

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

        slideModels.add(new SlideModel("https://arugambay.org/wp-content/uploads/2018/08/DSC7223.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://premiumtravelog.com/wp-content/uploads/2019/06/Kumana-13-1024x682.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.freetour.com/images/tours/7037/full-day-kumana-national-park-safari-02.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.kumananationalpark.com/images/bird.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageButton = findViewById(R.id.imageButtonX_E);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionDetails_E);
        journeyToPlaceTextView = findViewById(R.id.journeyToPlaceDetails_E);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places").child("NationalParks");

        // Get the place's ID from your intent or any other method
        String placeId = "Kumana National Park";
        // Example description and journeyToPlace strings with paragraphs
        String description = "In Sri Lanka’s southeast, a wildlife paradise is Kumana National Park. The park is noted for its birds, especially during the migration season, and its enormous number of elephants, leopards, and other species. The Kumana National Park and Safari should be your must-see locations if you plan a vacation to Sri Lanka. In this post, we will thoroughly examine the park, Safari and provide all the information you require to make travel arrangements.\n\n" +
                "History of Kumana National Park\n\n" +
                "Since the region was initially declared a wildlife refuge in 1938, Kumana National Park has a long history. The park, which at the time had an area of around 6,117 hectares, was created to preserve the local wildlife, especially the sizable flocks of waterbirds bred in the area’s lagoons and marshes.\n" +
                "\n" +
                "The Department of Wildlife Conservation controlled the park, mainly for bird hunting. Yet because the park was poorly administered and there was a lot of poaching and illegal hunting, the number of birds in the area decreased.\n" +
                "\n" +
                "The Mahaweli Development Authority took over the park’s administration in 1970, intending to use the park’s resources for irrigation and agriculture. Consequently, the park’s soil was cleared for farming, and its water sources were changed for irrigation.\n" +
                "\n" +
                "Yet in 1984, the Sri Lankan government designated Kumana National Park as a protected area after realising the value of safeguarding the nation’s biodiversity. The park today spans over 35,664 hectares and was formally established as a national park in 2006.\n" +
                "\n" +
                "The park’s administration significantly improved once it was again declared a protected area, and illicit activities like poaching and hunting were reduced. In addition, due to its diversified bird population, BirdLife International designated the park as an Important Bird Area.\n" +
                "\n" +
                "Nowadays, Kumana National Park attracts many tourists, especially birdwatchers who come in droves during the migratory season. Elephants, leopards, and sloth bears are just a few of the park’s native animals that draw tourists worldwide.\n\n The fauna of Kumana National Park is recognised for its variety, and its distinctive habitats, such as lagoons, mangroves, and woods, sustain a wide range of animal species. We’ll look at the wildlife of Kumana National Park, including its recognisable elephants, elusive leopards, and numerous bird species.\n" +
                "\n" +
                "Elephants: There are said to be between 200 and 300 elephants living in Kumana National Park. These magnificent creatures may frequently be spotted grazing in the meadows or sipping water from one of the park’s many watering holes.\n" +
                "\n" +
                "Leopards: Kumana National Park is one of the greatest sites in Sri Lanka to view leopards, despite being notoriously hard to find. With any luck, tourists can spot one of these large cats in the park’s lush woodlands, providing the perfect cover for them.\n" +
                "\n" +
                "Sloth bears: The Kumana National Park is home to sloth bears native to Sri Lanka. These unusual bears are distinguished by their large snouts and shaggy fur.\n" +
                "\n" +
                "Other raptors: including eagles, hawks, and kites, are also found in Kumana National Park, in addition to waterbirds. It’s common to observe these raptors swooping overhead in quest of their next meal.\n" +
                "\n" +
                "Many additional bird species, including owls, woodpeckers, and parakeets, may be found at Kumana National Park, in addition to waterbirds and raptors. In addition, crocodiles, turtles, and several snake species are among the reptile species that may be found at Kumana National Park.";
        String journeyToPlace = "The Kumana National Park is adjacent to Ampara in southeast Sri Lanka. Depending on where you want to start and how you get there, there are a few different methods to get to Kumana National Park.\n" +
                "\n" +
                "By vehicle/taxi: To get to Kumana National Park, take a car or cab from Colombo or any other significant Sri Lankan city. From Colombo, the trip takes about 7-8 hours, while the exact time will depend on the amount of traffic and the state of the roads. To get to Kumana National Park, follow the Colombo-Batticaloa route and head towards Ampara.\n" +
                "\n" +
                "By bus: From Colombo or other significant cities, you may take a bus to Ampara, where you can take a cab or tuk-tuk to Kumana National Park. The bus trip is less expensive but may take longer than a private vehicle or taxi.\n" +
                "\n" +
                "Via train: Kumana National Park cannot be reached by train in a straight route. You may take a train from Colombo or other significant cities to Ampara and then a cab or tuk-tuk to the national park.";


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
                String bookingUrl = "https://www.booking.com/searchresults.en-gb.html?ss=Kumana+National+Park%2C+Panama%2C+Ampara+District%2C+Sri+Lanka&ssne=Minneriya+National+Park&ssne_untouched=Minneriya+National+Park&label=gog235jc-1DCAEoggI46AdIM1gDaIUBiAEBmAEJuAEXyAEM2AED6AEBiAIBqAIDuAKCwaeqBsACAdICJDVhZWYzZTRjLTQyY2QtNGNlNS1hOTk5LWU2Nzg3ODE2OTZjMNgCBOACAQ&sid=edf2329e549776c19fde29e46829dcdf&aid=397594&lang=en-gb&sb=1&src_elem=sb&src=searchresults&dest_id=900126203&dest_type=landmark&ac_position=0&ac_click_type=b&ac_langcode=en&ac_suggestion_list_length=3&search_selected=true&search_pageview_id=d6ea5e33eb6e02e8&ac_meta=GhBkNmVhNWUzM2ViNmUwMmU4IAAoATICZW46E2t1bW5hIE5hdGlvbmFsIFBhcmtAAUoUa3VtYW5hIG5hdGlvbmFsIHBhcmtQIg%3D%3D&checkin=2023-12-02&checkout=2023-12-03&group_adults=1&no_rooms=1&group_children=0";                // Create an Intent to open a web browser with the Booking.com URL
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
        double latitude = 6.57;
        double longitude =  81.66;

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
        double latitude = 6.57;
        double longitude =  81.66;

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

            String placeId = "Kumana National Park";

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
        String placeId = "Kumana National Park";

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