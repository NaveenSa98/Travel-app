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

public class MinneriyaParkActivity extends AppCompatActivity {

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
        setContentView(R.layout.np_minneriya_park);

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

        slideModels.add(new SlideModel("https://www.bigworldsmallpockets.com/wp-content/uploads/2023/09/Sri-Lanka-Minneriya-National-Park-Entrance.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.yovoyagin.com/uploads/0000/76/2022/04/20/colorful-coral-reef824x0-q71-crop-scale-pe4y23nmp6ttov0i1vnytqe5txmvlzfo9o2h747snm.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.tacdn.com/media/attractions-splice-spp-674x446/06/e4/a8/d0.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.srilankanexpeditions.lk/tour_img/1487133234minneriya_national_park_16.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageButton = findViewById(R.id.imageButtonX_E);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionDetails_E);
        journeyToPlaceTextView = findViewById(R.id.journeyToPlaceDetails_E);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places").child("NationalParks");

        // Get the place's ID from your intent or any other method
        String placeId = "Minneriya National Park";
        // Example description and journeyToPlace strings with paragraphs
        String description = "The Minneriya National Park is found in the District of Polonnaruwa in the North Central Province. Minneriya tank, with its surroundings, plays a vital role as a wetland. Therefore it has high biodiversity.\n" +
                "The main entrance to the Park is Ambagaswewa, 8.8km from Habarana on the Colombo - Polonnaruwa route. Following receiving a permit from the Ambagaswewa wildlife conservation office, one could enter the Park.\n" +
                "Minneriya is an old irrigation tank with 22,550ha when complete and a catchment area of 24,000ha. The primary source of the river is from a deviation of the Amban Ganga forward the Elahera channel. The Park covers an area of 8,889ha. The altitude ranges from around 100m to 8,885m at the summit of the Nilgala peak. The situation is tropical monsoon climate; anticipated annual rainfall is about 1,146mm and means yearly temperature of 27.5 ° C. Minneriya National Park, a treasure trove of biodiversity and natural wonders, was officially declared a national park in 1997.\n\nThe Gathering of Asian Elephants\n" +
                "The gathering of Asian elephants in Minneriya National Park is a captivating event showcasing these magnificent creatures' remarkable social dynamics. During this period, herds of up to 350 elephants, sometimes even 700, converge within a few square kilometres of the Minneriya Tank. It is a sight that leaves visitors in awe as they witness these gentle giants peacefully coexisting and engaging in various behaviours such as bathing, feeding, and socializing.\n" +
                "\n" +
                "The migration of elephants from Wasgamuwa National Park to Minneriya National Park is primarily driven by the need for sustenance during the dry season. The scarcity of water and vegetation in their original habitat prompts these intelligent creatures to journey to find greener pastures. The Minneriya Tank acts as a vital water source, ensuring the survival of these elephants and providing them with a haven during this challenging period.\n" +
                "\n" +
                "Other Wildlife in Minneriya National Park\n" +
                "While the gathering of Asian elephants steals the spotlight, Minneriya National Park is also home to a diverse range of other wildlife. Two endemic monkey species, the purple-faced langur and the toque macaque, thrive in the park's abundant forests. Additionally, large herbivorous mammals such as the Sri Lankan sambar deer and the Sri Lankan axis deer can be frequently spotted. The park also serves as a sanctuary for rare and endangered species like the Sri Lankan leopard and the Sri Lankan sloth bear. Notably, Minneriya is known to be one of the few areas where the slender grey loris, a fascinating nocturnal primate, is found in Sri Lanka.";
        String journeyToPlace = "How to Reach Minneriya National Park\n" +
                "There are several ways to reach Minneriya National Park:\n" +
                "\n" +
                "By Bus: From Colombo, Kandy, Trincomalee, or Anuradhapura, you can take a bus heading towards Polonnaruwa, Batticaloa, Medirigiriya, or Dehiaththakandiya. Get off at Minneriya National Park after a journey of approximately 2 to 5 hours, depending on your location.\n" +
                "By Car or Taxi: Traveling by car or taxi provides a convenient and comfortable option to reach the park. The journey time may vary depending on your starting point.\n" +
                "By Train: You can disembark at Habarana or Minneriya Train Station if you prefer train travel. From there, you can hire a taxi to reach Minneriya National Park.\n" +
                "Visiting Minneriya National Park offers a unique opportunity to witness the world's largest gathering of Asian elephants and explore the diverse wildlife in this remarkable ecosystem.\n" +
                "\n" +
                "Minneriya National Park is a testament to the captivating beauty and extraordinary wildlife Sri Lanka offers. With the largest gathering of Asian elephants, the park draws visitors from around the world who are enthralled by the sight of hundreds of these majestic creatures peacefully coexisting nearby. Beyond the elephants, Minneriya National Park boasts a rich array of wildlife, including endemic monkeys, diverse bird species, fascinating amphibians and reptiles, and various plant life, creating a thriving ecosystem. The park's natural wonders and breathtaking scenery make it a must-visit destination for nature enthusiasts and wildlife lovers.";

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

        Button accommodationButton = findViewById(R.id.accommodationButton_E);

        accommodationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the URL for Booking.com
                String bookingUrl = "https://www.booking.com/searchresults.en-gb.html?ss=Minneriya+National+Park%2C+Sigiriya%2C+Matale+District%2C+Sri+Lanka&ssne=Yala+National+Park&ssne_untouched=Yala+National+Park&label=gog235jc-1DCAEoggI46AdIM1gDaIUBiAEBmAEJuAEXyAEM2AED6AEBiAIBqAIDuAKCwaeqBsACAdICJDVhZWYzZTRjLTQyY2QtNGNlNS1hOTk5LWU2Nzg3ODE2OTZjMNgCBOACAQ&sid=edf2329e549776c19fde29e46829dcdf&aid=397594&lang=en-gb&sb=1&src_elem=sb&src=searchresults&dest_id=900053659&dest_type=landmark&ac_position=0&ac_click_type=b&ac_langcode=en&ac_suggestion_list_length=2&search_selected=true&search_pageview_id=158539fa156e00b3&ac_meta=GhAxNTg1MzlmYTE1NmUwMGIzIAAoATICZW46F21pbm5lcml5YSBOYXRpb25hbCBQYXJrQABKAFAA&checkin=2023-12-02&checkout=2023-12-03&group_adults=1&no_rooms=1&group_children=0";                // Create an Intent to open a web browser with the Booking.com URL
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
        double latitude = 8.01;
        double longitude =  80.84;

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
        double latitude = 8.01;
        double longitude =  80.84;

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

            String placeId = "Minneriya National Park";

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
        String placeId = "Minneriya National Park";

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