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

public class BomburuWaterfallActivity extends AppCompatActivity {

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
        setContentView(R.layout.w_bomburu_waterfall);

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

        slideModels.add(new SlideModel("https://www.discover.lk/assets/Uploads/Bomburu-falls__Resampled.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://upload.wikimedia.org/wikipedia/commons/d/df/Bomburu_ella_falls.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media-cdn.tripadvisor.com/media/photo-s/1b/48/42/c7/bomburu-ella-waterfall.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://campinglanka.com/storage/files/lk/794/thumb-320x240-71497555a3447e08ed5a84a74a8db635.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageButton = findViewById(R.id.imageButtonX_E);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionDetails_E);
        journeyToPlaceTextView = findViewById(R.id.journeyToPlaceDetails_E);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places").child("WaterFalls");

        // Get the place's ID from your intent or any other method
        String placeId = "Bomburu Waterfall";
        // Example description and journeyToPlace strings with paragraphs
        String description = "If you are looking for a stunning natural wonder to explore, the Bomburu Ella Waterfalls in Sri Lanka should be on your list. This waterfall is in the central highlands of Sri Lanka. It is known for its unique beauty and features that make it stand out. This article will take a closer look at the Bomburu Ella Waterfalls and everything you need to know about this natural attraction\n\n" +
                "The Bomburu Ella Waterfalls is a beautiful natural wonder that draws people from all over the world. The waterfalls in the Border Central Province of Sri Lanka, full of green hills. Its stunning cascades of water plunging from over 800 feet, surrounded by a lush forest teeming with wildlife, is truly a sight to behold.\n" +
                "\n" +
                "The falls are located in the village of Uva-Paranagama, about 15 km from Welimada. The area is known for its relaxed, misty climate and rolling hills covered in tea plantations, making it a popular destination for those seeking a refreshing escape from the hustle and bustle of city life.\n" +
                "\n" +
                "Bomburu Ella Waterfalls is a must-see place that will leave you in awe, whether you like nature, want to try new things or enjoy a quiet place to relax and unwind. So pack your bags, grab your camera, and prepare for an unforgettable adventure in the heart of Sri Lanka's stunning hill country.\n\n" +
                "Activities to Enjoy at Bomburu Ella Waterfalls\n" +
                "\n" +
                "There are plenty of activities to enjoy at Bomburu Ella Waterfalls, making it a destination perfect for adventure seekers and nature lovers. Here are some of the top activities to consider:\n" +
                "\n" +
                "Hiking: The area around the falls is crisscrossed with hiking trails, allowing visitors to explore the lush forest and stunning vistas on foot. The most popular trail is the Bomburu Ella Trail, which takes you through a tea plantation and past a series of smaller waterfalls before reaching the central falls.\n" +
                "\n" +
                "Swimming: The natural pools at the base of the falls are perfect for a refreshing swim on a hot day. The water is clean and clear, and the scenery is truly breathtaking.\n" +
                "\n" +
                "Wildlife Watching: The forest around Bomburu Ella Waterfalls is home to various wildlife, including monkeys, lizards, and bird species. Visitors can spend hours observing nature and taking photographs.\n" +
                "\n" +
                "Photography: The falls and the surrounding landscape offer endless opportunities for photography enthusiasts. From the mist rising off the falls to the lush greenery and tea plantations, there is no shortage of stunning subjects to capture on camera.\n" +
                "\n" +
                "Picnicking: The area around the falls is ideal for a relaxing picnic. Visitors can bring food and drinks or purchase snacks and beverages from local vendors.\n" +
                "\n" +
                "Camping: Camping is an excellent option for those looking to immerse themselves in nature. Visitors can pitch a tent in the forest near the falls and spend the night under the stars, listening to the soothing sounds of the waterfalls.";
        String journeyToPlace = "Reaching Bomburu Ella Waterfalls is reasonably straightforward, with several visitor transportation options.\n" +
                "\n" +
                "By Car: The most convenient way to reach the falls is by car. Visitors can rent a car with a driver or drive themselves from the nearby town of Welimada. The drive takes about 30 minutes and offers stunning views of the surrounding hills and tea plantations.\n" +
                "\n" +
                "By Public Transport: Visitors can also reach the falls by public transport. From Welimada, take a bus to Uva-Paranagama and get off at the village centre. You can take a tuk-tuk or walk about 2 km away to the falls.\n" +
                "\n" +
                "By Train: The train to Ohiya or Haputale is an excellent choice for those looking for a more scenic option. From either station, visitors can take a tuk-tuk or hire a car to reach Bomburu Ella Waterfalls, which is about an hour's drive away.\n" +
                "\n" +
                "Entrance Fee\n" +
                "\n" +
                "The entrance fee to the cascades is 500 Rupees ($2-3) per individual. You can also anticipate paying approximately 100 rupees ($0.30) for parking at the trailhead.\n" +
                "\n" +
                "No matter your transportation option, the journey to Bomburu Ella Waterfalls will surely be memorable, with stunning views and picturesque landscapes at every turn.";

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
        waterfalls.add(new WaterFall("Ravana Falls", 52,"https://www.lovesrilanka.org/wp-content/uploads/2020/04/LS_RavanaFalls_Mob_800x1000.jpg"));
        waterfalls.add(new WaterFall("Lover's Leap Falls", 26,"https://www.lovesrilanka.org/wp-content/uploads/2020/06/LSL_B2_Lovers-Leap-Waterfall_800x1000.jpg"));
        waterfalls.add(new WaterFall("Dunhinda Ella", 50,"https://upload.wikimedia.org/wikipedia/commons/f/f8/Dunhinda.jpg"));
        waterfalls.add(new WaterFall("Baker's Falls", 33,"https://www.birdingsrilanka.com/assets/images/waterfall/cfa6e67b5e7b09696cdd3b5f802f4275.jpg"));
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
        double latitude = 6.94;
        double longitude =  80.83;

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
        double latitude = 6.94;
        double longitude = 80.83;

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

            String placeId = "Bomburu Waterfall";

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
        String placeId = "Bomburu Waterfall";

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