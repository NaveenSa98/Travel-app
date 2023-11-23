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

public class AnuradhapuraActivity extends AppCompatActivity {

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
        setContentView(R.layout.h_anuradhapura);

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

        slideModels.add(new SlideModel("https://sa.lakpura.com/cdn/shop/products/LK60200700-01-E-1280-720.jpg?v=1625472597.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://whc.unesco.org/uploads/thumbs/site_0200_0001-750-750-20150608110546.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media-cdn.tripadvisor.com/media/attractions-splice-spp-720x480/09/2f/b7/91.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://explore.vacations/wp-content/uploads/2020/06/Night-view-of-Anuradhapura-Sri-Lanka.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageButton = findViewById(R.id.imageButtonX_E);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionDetails_E);
        journeyToPlaceTextView = findViewById(R.id.journeyToPlaceDetails_E);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places").child("Heritages");

        // Get the place's ID from your intent or any other method
        String placeId = "Anuradhapura";

        String description = "Anuradhapura, Sri Lanka’s first capital city, Also known as the Sacred City of Anuradhapura, is found in Northern Central Province, Sri Lanka. Anuradhapura is famous for Archaeological sites, culture, food and attractions set upon an iconic inheritance backdrop.\n" +
                "Anuradhapura is a sacred city established in the 4th century B.C. It has been a UNESCO World Heritage Site since 1982 under the name of the Sacred City of Anuradhapura. The centre of Theravada Buddhism for several centuries. Theravada Buddhism is a fork of Buddhism resting on the oldest written Buddhist text as its faith.\n" +
                "The city of Anuradhapura held the capital city for the ethnic Sinhalese society from the 4th century B.C. till the beginning of the 11th century A.D. Throughout this time, it remained one of the most stable centres of political power in South Asia.\n" +
                "This early city is considered sacred to the Buddhist world. The town plays religious significance because it retains a cutting from the ‘tree of enlightenment, the Buddha’s fig tree which, was carried in the 3rd century B.C. by Sanghamitta – the originator of an array of Buddhist nuns.\n\n" +
                "Wonders of Anuradhapura\n" +
                "Artificial lakes extending along the horizon, stupas that touched the sky and performed as data communications, dwellings atop rocks decorated with elaborate sculpture, water gardens and futuristic settings of technologies and stargates are just a few of the construction festivals the ancient Sinhalese.\n" +
                "Enhanced with Buddhism brought down from India approximately three thousand years before Sri Lankan technicians and artisans built unique breath-taking constructions in the ancient society. Formulated with bricks and carved with stones, these productions observed in the ancient city of Anuradhapura continues to astound the world";


        String journeyToPlace = "By Train\n\n" +
                "There are roughly half a dozen trains operational on the Colombo-Anuradhapura Route and it is best to board the train from Colombo Fort Station. The first train out of Colombo Fort Station leaves at 5.45 am and arrives in Anuradhapura at 9.15 am. The approximate train fare is LKR 1000, which is ideally booked in advance, since it’s an air-conditioned train with comfortable seating, making it the fastest and best way to reach Anuradhapura.\n" +
                "If you’d like to experience the rail services as the locals do, then riding aboard a non -A/c train is wise, although these trains may run late at times. You will not only enjoy the scenic views along the route but also witness the local hawkers selling peanuts, corn on the cob, hot snacks and green mangoes through the journey. A 2nd class ticket to Anuradhapura would cost you about LKR 750.\n" +
                "\n\n" +
                "By Bus\n\n" +
                "A bus ride from Colombo to Anuradhapura is comparatively cheaper than taking a flight, and the best way to reach Anuradhapura in about 5 hours or by car/taxi in 4 hours. With a bus station in New Town of Anuradhapura, you can travel via the A9 highway from Kandy, which is 166 km away, with Puttalam, another close city to Anuradhapura and a little further ahead would be Negombo.\n" +
                "There’s a bus from Colombo Central Bus Terminal, Olcott Mawatha, which will drive you to Puttalam in 3 hours and 45 min with an hourly frequency. From Puttalam, you travel by cab to Anuradhapura, which will have you reach there in about 1 hour 24 minutes. The bus journey’s likely to cost LKR 65-90, while the cab may cost you LKR 2,300-2,800\n\n" +
                "Getting Around\n\n" +
                "Getting around in Anuradhapura is enjoyable both in buses and Tuk Tuks, which you’ll find in plenty. While the old town is large in area, tuk-tuks would be convenient. The new town being comparatively smaller, can be covered on foot in nearly ½ hour from one end to another!";


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
                String bookingUrl = "https://www.booking.com/searchresults.en-gb.html?ss=Anuradhapura%2C+Anuradhapura+District%2C+Sri+Lanka&ssne=Horton+Plains+National+Park&ssne_untouched=Horton+Plains+National+Park&label=gog235jc-1DCAEoggI46AdIM1gDaIUBiAEBmAEJuAEXyAEM2AED6AEBiAIBqAIDuALFy6-qBsACAdICJGM2NWQ4OGQ3LTZmMGQtNDFhZS1hMmIzLTUzYzY3MGE5ZGE5YtgCBOACAQ&sid=edf2329e549776c19fde29e46829dcdf&aid=397594&lang=en-gb&sb=1&src_elem=sb&src=index&dest_id=-2212786&dest_type=city&ac_position=0&ac_click_type=b&ac_langcode=en&ac_suggestion_list_length=5&search_selected=true&search_pageview_id=b0ab8b23eefa0106&ac_meta=GhBiMGFiOGIyM2VlZmEwMTA2IAAoATICZW46BWFudXJhQABKAFAA&checkin=2023-12-02&checkout=2023-12-03&group_adults=1&no_rooms=1&group_children=0&sb_travel_purpose=leisure";
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

        // Initialize your RecyclerView
        RecyclerView nearestLocationsRecyclerView = findViewById(R.id.nearestLocationsRecyclerView_E);
        nearestLocationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Create a list of Waterfall objects with data
        List<NearbyListing> nearbyListings = new ArrayList<>();
        nearbyListings.add(new NearbyListing("Isurumuniya", 5,"https://www.srilankanexpeditions.com/images/sri-lanka-travel-guide/history-archaeology-sri-lanka/ancient-temple/isurumuniya/slider1.jpg","89MR+R4J, Anuradhapura"));
        nearbyListings.add(new NearbyListing("Mihintalaya Rajamaha Viharaya", 19,"https://www.trawell.in/admin/images/upload/072089402Anuradhapura_Mihintale.jpg","9G29+76V, Mihintale"));
        nearbyListings.add(new NearbyListing("Ruwanweli maha Stupa", 6.2,"https://buzzer.lk/wp-content/uploads/2022/09/EModZL_X0AAKs58.jpg","992W+2G9, Abhayawewa Rd, Anuradhapura"));
        nearbyListings.add(new NearbyListing("Jayasiri Maha Bodiya", 5.3,"https://img.atlasobscura.com/-88a1nusWSF4vQ03v2OvUy2iNsbObMqAdFsDepiTBxs/rs:fill:780:520:1/g:ce/q:81/sm:1/scp:1/ar:1/aHR0cHM6Ly9hdGxh/cy1kZXYuczMuYW1h/em9uYXdzLmNvbS91/cGxvYWRzL3BsYWNl/X2ltYWdlcy9jYjFk/OGRhYi0zZjEyLTRk/MjktODhjYi01MjRi/OWIzZjgxMTIxMjZl/ZTZiYjYwMDAxNzI4/YjFfSEdIRUEzLmpw/Zw.jpg","89VW+WVH, Anuradhapura"));
        nearbyListings.add(new NearbyListing("Ranmasu Uyana", 5.3,"https://www.360view.lk/wp-content/uploads/2021/01/Ranmasu_Uyana_Photos_By_360viewlk-1.jpg","89QQ+9W9, Anuradhapura"));
        nearbyListings.add(new NearbyListing("Samadhi Buddha Statue", 8.7,"https://www.attractionsinsrilanka.com/wp-content/uploads/2020/09/Samadhi-Buddha-Statue.jpg","999X+X73, Anuradhapura"));
        nearbyListings.add(new NearbyListing("Thanthirimale Rajamaha Viharaya", 43,"https://www.attractionsinsrilanka.com/wp-content/uploads/2020/09/Thanthirimale-Raja-Maha-Viharaya1.jpg","H7F4+C8W, Thanthirimale Road, Thanthirimale"));
        nearbyListings.add(new NearbyListing("Jethawanaramaya Stupa", 6.7,"https://www.lakpura.com/images/LK94009853-06-E.JPG","9C23+JF Anuradhapura"));
        nearbyListings.add(new NearbyListing("Abhayagiriya Stupa", 8.8,"https://slsigiriya.com/wp-content/uploads/2021/04/Abhayagiriya-1.jpg","99CW+94C, Watawandana Road, Anuradhapura"));
        nearbyListings.add(new NearbyListing("Thuparamaya Stupa", 6.6,"https://www.attractionsinsrilanka.com/wp-content/uploads/2020/09/Thuparamaya.jpg","994W+4HQ, Anuradhapura"));
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
        double latitude = 8.311;
        double longitude =   80.40;

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
        double latitude = 8.311;
        double longitude =   80.40;

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

            String placeId = "Anuradhapura";

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
        String placeId = "Anuradhapura";

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