package com.example.travel_mate.BPlaces;

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

public class HiriketiyaBeachActivity extends AppCompatActivity {

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
        setContentView(R.layout.b_hiriketiyabeach);

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

        slideModels.add(new SlideModel("https://www.taruvillas.com/wp-content/uploads/2021/11/inner-banner_Mob_Hiriketiya-Bay-720x720-1-1.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://island-spirit.org/wp-content/uploads/2020/06/Hiriketiya-Sri-Lanka.44-scaled-e1614883119701.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://littlewanderbook.com/wp-content/uploads/2020/02/Hiriketiya-Sri-Lanka-15.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.thecoastalcampaign.com/wp-content/uploads/2019/06/DJI_0026-3-1080x675.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageButton = findViewById(R.id.imageButtonX_E);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionDetails_E);
        journeyToPlaceTextView = findViewById(R.id.journeyToPlaceDetails_E);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places").child("BeachSides");

        // Get the place's ID from your intent or any other method
        String placeId = "Hiriketiya Beach";

        String description = "  Hiriketiya Beach is one of the best beaches in Sri Lanka. Also known as Hiriketiya bay or written as Hirikiteya, this horseshoe-shaped bay is located on the south coast of Sri Lanka. It is one of the best surf and yoga Sri Lanka spots with hipster cafes, a laidback vibe and coconut trees hanging over the beach. In addition, Hiriketiya Beach is a jungle paradise.\n" +
                "  Situated just a few kilometres from the vibrant town of Dickwella, Hiriketiya Beach is easily accessible yet retains its unspoiled charm. Surrounded by lush green hills and swaying palm trees, the beach boasts a postcard-perfect setting that captivates visitors. Its crescent shape creates a sheltered bay, making it ideal for swimming, snorkelling, and, most notably, surfing.";

        String journeyToPlace = "  Reaching Hiriketiya Beach is relatively straightforward. The closest major city is Matara; from there, it's a short and scenic drive along the coastal road. Public transport makes The beach well-connected, and private taxis and tuk-tuks are readily available. If you're arriving from abroad, the closest international airport is Bandaranaike International Airport in Colombo, where you can arrange transportation to Hiriketiya Beach.";

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
                String bookingUrl = "https://www.booking.com/searchresults.en-gb.html?ss=Hiriketiya+Beach%2C+Dickwella%2C+Sri+Lanka&ssne=Dalawella+Beach%2C+Unawatuna%2C+Sri+Lanka&ssne_untouched=Dalawella+Beach%2C+Unawatuna%2C+Sri+Lanka&label=gog235jc-1DCAEoggI46AdIM1gDaIUBiAEBmAEJuAEXyAEM2AED6AEBiAIBqAIDuAL5z_qpBsACAdICJDgxMTdkMzk1LTNkM2YtNGViNC04OTNjLTUyZTVlMTdjOGNiZdgCBOACAQ&sid=edf2329e549776c19fde29e46829dcdf&aid=397594&lang=en-gb&sb=1&src_elem=sb&src=searchresults&dest_id=16330&dest_type=district&ac_position=1&ac_click_type=b&ac_langcode=en&ac_suggestion_list_length=5&search_selected=true&search_pageview_id=c82488c80efb00f2&ac_meta=GhBjODI0ODhjODBlZmIwMGYyIAEoATICZW46CmhpcmlrZXRpeWFAAEoAUAA%3D&checkin=2023-11-03&checkout=2023-11-04&group_adults=1&no_rooms=1&group_children=0";
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
        double latitude = 5.96;
        double longitude =  80.70;

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
        double latitude = 5.96;
        double longitude =  80.70;

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

            String placeId = "Hiriketiya Beach";

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
        String placeId = "Hiriketiya Beach";

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