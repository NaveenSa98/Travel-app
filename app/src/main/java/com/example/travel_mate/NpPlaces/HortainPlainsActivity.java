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

public class HortainPlainsActivity extends AppCompatActivity {

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
        setContentView(R.layout.np_hortain_plains);

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

        slideModels.add(new SlideModel("https://upload.wikimedia.org/wikipedia/commons/d/df/Horton_Plains_National_Park_in_Sri_Lanka.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.trawell.in/admin/images/upload/140958692Nuwara_Eliya_Harton_Plains.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://srilankafinder.com/wp-content/uploads/2017/10/horton-plains-national-park-2-700x500.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://cdn.kimkim.com/files/a/images/6938618d2531bcbdd7520f36cf0276d11295e6a9/big-5a9c92d22093a46f6a55221ad7b52600.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageButton = findViewById(R.id.imageButtonX_E);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionDetails_E);
        journeyToPlaceTextView = findViewById(R.id.journeyToPlaceDetails_E);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places").child("NationalParks");

        // Get the place's ID from your intent or any other method
        String placeId = "Horton Plains National Park";
        // Example description and journeyToPlace strings with paragraphs
        String description = "Horton plain, its surroundings forests, and the neighbouring Peak Wilderness connect Sri Lanka’s most significant catchment area of almost all the main rives. The tables are also outstanding the environments and endemic plants and animals representatives of the land wet and montane zones.\n" +
                "Horton plains comprise a gently fluctuating highland hill at the southern end of the central mountains massif of Sri Lanka. It is managed to the north by Mount Totupola Kanda (2,357m) also to the west by Mount Kirigalpotta (2,389m). Two mountains filling from the Horton Plain have added immensely to its awe-inspiring physiognomy, “big worlds end” by 884m. The beauty of the foliage of the peaks encircling the plains as intermittently covered by mist is emphasised by the sparking Baker’s fall. The altitude of the park covers from about 1,800m to 2,389m at the peak of Kirigalpotta. The plateau at 2,100m is the most distinguished tableland in Sri Lanka. The yearly rainfall in the region is about 2540mm, but for Horton Plains, it may exceed 5000mm. Rain happens throughout most of the year, although there is a dry season from January to March. Temperatures are moderate, with an annual mean temperature of 15ºC and ground frost is anticipated from December to February.\n" +
                "Horton Plains is well recognised for its rich biodiversity; its flora gave a high endemism level. 5% of varieties are determined to be endemic to Sri Lanka.";
        String journeyToPlace = "Horton Plains is renowned for its rich biodiversity and high level of endemism. The park's flora exhibits remarkable diversity, with approximately 750 recorded plant species belonging to 20 families. The dominant tree species is Rhododendron arboreum, creating a vibrant display of colours amidst the lush greenery. The park's nearly 54 woody plant species are endemic to Sri Lanka, representing its unique and irreplaceable botanical heritage. The grasslands, covering approximately 2,000 hectares of the park, support various specialized plant species adapted to the montane environment. These grasslands play a crucial role in the water catchment area, as they help regulate water flow to the rivers and streams.\n" +
                "\n" +
                "The biodiversity of Horton Plains extends beyond its flora, as it is also home to a diverse array of wildlife. Several mammal species inhabit the park, including sambar deer, Sri Lankan leopard, wild boar, purple-faced langur, and rusty-spotted cat. The elusive Sri Lankan leopard is a significant attraction for wildlife enthusiasts, although spotting one requires patience and a bit of luck.\n" +
                "\n" +
                "Avifauna enthusiasts will be delighted to know that Horton Plains is a paradise for birdwatching. The park boasts an affluent bird population with over 100 species recorded, including several endemic species such as Sri Lanka whistling thrush, Sri Lanka wood pigeon, and Sri Lanka white-eye. The grasslands and surrounding forests provide an ideal habitat for these feathered creatures, making it a haven for bird lovers.\n" +
                "\n" +
                "The reptile and amphibian diversity in Horton Plains is equally impressive. The park is home to several endemic reptile species, including the rare Sri Lankan green pit viper and the rhinoceros-horned lizard. Amphibians such as the Sri Lankan rock frog and the endemic torrent toad can also be found in the streams and water bodies within the park.\n" +
                "\n" +
                "Importance of Horton Plains\n" +
                "Horton Plains plays a vital role in the water catchment area of Sri Lanka. The park is the originating point for three major rivers: Mahaweli, Kelani, and Walawe. These rivers provide water for agriculture, hydropower generation, and domestic use, making Horton Plains a crucial watershed area.\n" +
                "\n" +
                "The cultural and historical significance of Horton Plains is also noteworthy. The park is home to the Pattipola Archaeological Reserve, which contains evidence of early human settlements dating back thousands of years. In addition, the area is believed to have been inhabited by prehistoric communities, and archaeological studies have revealed ancient burial sites, tools, and artifacts.\n" +
                "\n" +
                "As a popular tourist destination, Horton Plains offers several attractions for visitors. The most famous among them is the World's End viewpoint, where visitors can marvel at the dramatic 884-meter drop-off, offering breathtaking views of the surrounding landscape. Another notable attraction is Baker's Falls, a picturesque waterfall nestled amidst the lush greenery.";


        placesRef.child(placeId).child("Description").setValue(description);
        placesRef.child(placeId).child("Biodiversity of Horton Plain").setValue(journeyToPlace);


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
                    String journeyToPlace = dataSnapshot.child("Biodiversity of Horton Plain").getValue(String.class);

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
                String bookingUrl = "https://www.booking.com/searchresults.en-gb.html?ss=Horton+Plains+National+Park%2C+Ohiya%2C+Nuwara+Eliya+District%2C+Sri+Lanka&ssne=Kumana+National+Park&ssne_untouched=Kumana+National+Park&label=gog235jc-1DCAEoggI46AdIM1gDaIUBiAEBmAEJuAEXyAEM2AED6AEBiAIBqAIDuAKCwaeqBsACAdICJDVhZWYzZTRjLTQyY2QtNGNlNS1hOTk5LWU2Nzg3ODE2OTZjMNgCBOACAQ&sid=edf2329e549776c19fde29e46829dcdf&aid=397594&lang=en-gb&sb=1&src_elem=sb&src=searchresults&dest_id=900059449&dest_type=landmark&ac_position=1&ac_click_type=b&ac_langcode=en&ac_suggestion_list_length=5&search_selected=true&search_pageview_id=eb0e62a6f966007f&ac_meta=GhBlYjBlNjJhNmY5NjYwMDdmIAEoATICZW46BmhvcnRvbkAASgBQAA%3D%3D&checkin=2023-12-02&checkout=2023-12-03&group_adults=1&no_rooms=1&group_children=0";                // Create an Intent to open a web browser with the Booking.com URL
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
        double latitude = 6.80;
        double longitude =  80.80;

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
        double latitude = 6.80;
        double longitude =  80.80;

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

            String placeId = "Horton Plains National Park";

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
        String placeId = "Horton Plains National Park";

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