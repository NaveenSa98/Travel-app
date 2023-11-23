package com.example.travel_mate.HPlaces;

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
import com.example.travel_mate.Adapters.NearbyListingAdapter;
import com.example.travel_mate.Adapters.ReviewAdapter;
import com.example.travel_mate.Classes.NearbyListing;
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

public class PolonnaruwaActivity extends AppCompatActivity {

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
        setContentView(R.layout.h_polonnaruwa);

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

        slideModels.add(new SlideModel("https://media-cdn.tripadvisor.com/media/photo-s/16/a3/58/ae/polonnaruwa-the-medieval.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://live.staticflickr.com/1963/45389653991_a48c3f4097_b.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.cctsrilanka.com/wp-content/uploads/2017/10/cctsrilanka.polonnaruwa.AlahanaPirivena.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://youimg1.tripcdn.com/target/01054120008x1802a7BC9_D_1180_558.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageButton = findViewById(R.id.imageButtonX_E);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionDetails_E);
        journeyToPlaceTextView = findViewById(R.id.journeyToPlaceDetails_E);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places").child("Heritages");

        // Get the place's ID from your intent or any other method
        String placeId = "Polonnaruwa";

        String description = "Nestled in the heart of Sri Lanka lies the captivating city of Polonnaruwa, a UNESCO World Heritage Site that takes us on a journey through the island’sisland’s rich history. With its well-preserved ruins, ancient temples, and stunning landscapes, Polonnaruwa offers a mesmerising glimpse into the past.\n" +
                "\n" +
                "Origins and Foundation\n\n" +
                "The captivating city of Polonnaruwa, nestled in the heart of Sri Lanka, holds a history that traces back to the 10th century. During this time, King Vijayabahu I, a pivotal figure in Sri Lanka’s history, established Polonnaruwa as the island nation’s capital.\n" +
                "\n" +
                "Under the reign of King Vijayabahu I, Polonnaruwa underwent a significant transformation. The city was renamed “Vijayarajapura,” signifying its newfound status as the capital. This move marked a monumental shift in power and governance, as Polonnaruwa took over the reins from Anuradhapura, the former capital of Sri Lanka.\n" +
                "\n" +
                "With the establishment of Polonnaruwa as the capital, King Vijayabahu I solidified his position as the first Sinhala king of the Polonnaruwa Kingdom. His reign marked an era of change and growth, setting the stage for the city’s flourishing future.\n" +
                "\n" +
                "To commemorate this historic moment, a grand coronation ceremony was held. The ceremony took place in a purposefully constructed palace for this event in Anuradhapura, the erstwhile capital of Sri Lanka. This palace served as a testament to the significance of the occasion and the establishment of Polonnaruwa as the new centre of power.\n" +
                "\n" +
                "Polonnaruwa flourished as a centre of governance, trade, and culture with its newfound status. The city’s strategic location contributed to its prominence as a hub for trade and commerce. Merchants and traders from distant lands converged on its bustling streets, creating a vibrant marketplace that buzzed with activity.\n\n" +
                "Architectural Marvels Sacred Temples and Monuments\n\n" +
                "Polonnaruwa stands as a living testament to the architectural brilliance of ancient civilizations. The city’s landscape is adorned with architectural marvels that continue to captivate visitors worldwide. Each structure tells a story of craftsmanship, innovation, and a quest for aesthetic excellence. Explore our List of nearby places in Polonnaruwa with a Map";


        String journeyToPlace = "By Train\n\n" +
                "The best way to get to Polonnaruwa from Colombo is to board a train, with the onward journey in a cab or a bus. It takes about 2 hours with a train leaving from Colombo Fort Station for Habarana costing LKR 80-270. The frequency of the train is thrice a day. The journey from Habarana to Polonnaruwa can be covered in 45 minutes by taxi, costing LKR 1100-1400.\n" +
                "\n" +

                "By Bus\n\n" +
                "To travel by road, going by car is the easiest way to reach Polonnaruwa. The distance from Colombo to Polonnaruwa is 216.4 km by road and approx. 3.4 hours. It will cost you roughly LKR 1,200-1,800 for the road trip.\n" +
                "Another alternative is to board an Intercity bus which leaves from Colombo Fort and will have you in Polonnaruwa in 6-8 hours. Route no. 48, Colombo-Polonnaruwa leaves from Colombo CBT/ Bastian Mawatha Bus Stand. The Polonnaruwa bus terminal is located 4 km to the east of the tourist attractions, so you can ask to be dropped somewhere closer, like the Clock Tower.\n\n" +

                "Getting Around\n\n" +
                "To get around Polonnaruwa, riding a bicycle’s the best way! Most guesthouses rent out bikes, but be cautious of the bike condition, in terms of its tires, etc. Since most tourist spots are within close proximity of each other, you can cover them easily on a bicycle in a day. ";


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
                String bookingUrl = "https://www.booking.com/searchresults.en-gb.html?ss=Polonnaruwa%2C+Polonnaruwa+District%2C+Sri+Lanka&ssne=Horton+Plains+National+Park&ssne_untouched=Horton+Plains+National+Park&label=gog235jc-1DCAEoggI46AdIM1gDaIUBiAEBmAEJuAEXyAEM2AED6AEBiAIBqAIDuALf_rGqBsACAdICJDk3NzU0YjBiLWRmZmUtNDYyYy04ZGIwLWEzYWIzMWQ5ZjI4MNgCBOACAQ&sid=edf2329e549776c19fde29e46829dcdf&aid=397594&lang=en-gb&sb=1&src_elem=sb&src=index&dest_id=-2233982&dest_type=city&ac_position=0&ac_click_type=b&ac_langcode=en&ac_suggestion_list_length=5&search_selected=true&search_pageview_id=93162f2fb84101ab&ac_meta=GhA5MzE2MmYyZmI4NDEwMWFiIAAoATICZW46BXBvbG9uQABKAFAA&checkin=2023-12-02&checkout=2023-12-03&group_adults=1&no_rooms=1&group_children=0&sb_travel_purpose=leisure";                // Create an Intent to open a web browser with the Booking.com URL
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

        // Initialize your RecyclerView
        RecyclerView nearestLocationsRecyclerView = findViewById(R.id.nearestLocationsRecyclerView_E);
        nearestLocationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Create a list of Waterfall objects with data
        List<NearbyListing> nearbyListings = new ArrayList<>();
        nearbyListings.add(new NearbyListing("Pabalu Vehera", 3.7,"https://www.mahanuge.com/images/Things_to_do/photos/unusual-design-of-the4.jpg","W2X3+RP6, Gal Vihara Road, Polonnaruwa, Sri Lanka, Polonnaruwa"));
        nearbyListings.add(new NearbyListing("Nelum Pokuna", 7.7,"https://media-cdn.tripadvisor.com/media/photo-s/15/8e/d3/25/img-20181118-114338-largejpg.jpg","X2F3+WJR, Thivanka Pilimage Rd, Polonnaruwa"));
        nearbyListings.add(new NearbyListing("Sathmahal Prasadaya", 3.3,"https://www.lankatraveldirectory.com/wp-content/uploads/2017/12/Satmahal-Prasada-Polonnaruwa-2.jpg","W2X2+5PP, Polonnaruwa"));
        nearbyListings.add(new NearbyListing("Somawathiya Stupa", 38,"https://c8.alamy.com/zooms/9/c4ba4a26c72c4d7881225f73375d5901/kwyg8e.jpg","45C9+9G9, Polonnaruwa"));
        nearbyListings.add(new NearbyListing("Nissankalatha Mandapaya", 3,"https://upload.wikimedia.org/wikipedia/commons/7/7e/Nissanka_Latha_Mandapaya.-_Dalada_Maluva%2C_Polonn%C3%A2ruv%C3%A2_%282%29.jpg","W2X2+2F2, Sacred square, Ancient city, Polonnaruwa, Polonnaruwa"));
        nearbyListings.add(new NearbyListing("King Parakramabahu Statue", 4.3,"https://minneriyasafari.com/wp-content/uploads/2021/12/Statue-of-King-Parakramabahu.jpg","WXGV+GW8, Bund Rd, Polonnaruwa"));
        nearbyListings.add(new NearbyListing("Polonnaruwa Vatadage", 3.2,"https://www.attractionsinsrilanka.com/wp-content/uploads/2020/02/Polonnaruwa-Vatadage.jpg","W2W2+WGV, Polonnaruwa"));
        nearbyListings.add(new NearbyListing("Rankoth Vehera", 4.7,"https://www.lakpura.com/images/LK94009724-02-E.JPG","X253+78X, Polonnaruwa"));
        nearbyListings.add(new NearbyListing("Gal Vihara", 4.8,"https://i.redd.it/9l1hbtyw7rda1.jpg","X283+CXG, Nissankamallapura"));
        nearbyListings.add(new NearbyListing("Parakrama Samudraya", 9.3,"https://srilankatravelpages.com/media/2021/09/Parakrama-Samudraya-Reservoir-wewa-.jpg","WXRX+P2W, Polonnaruwa"));
        // Create the adapter and set it to the RecyclerView
        NearbyListingAdapter  adapter = new NearbyListingAdapter(this, nearbyListings);

        nearestLocationsRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(nearbyListing -> {
            // Handle item click here
            // Open Google Maps with the selected place's plus code address
            String plusCode = nearbyListing.getPlusCode();

            if (!TextUtils.isEmpty(plusCode)) {
                Uri gmmIntentUri = Uri.parse("https://maps.google.com/maps?q=" + Uri.encode(plusCode));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(this, "Google Maps app not installed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Plus code address not available", Toast.LENGTH_SHORT).show();
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
        double latitude = 7.94;
        double longitude =   81.01;

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
        double latitude = 7.94;
        double longitude =   81.01;

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

            String placeId = "Polonnaruwa";

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
        String placeId = "Polonnaruwa";

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