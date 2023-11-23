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

public class YalaParkActivity extends AppCompatActivity {

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
        setContentView(R.layout.np_yala_park);

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

        slideModels.add(new SlideModel("https://thumbs.dreamstime.com/z/safari-yala-national-park-sri-lanka-entry-famous-nature-jeep-coming-out-75585844.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://adventuresnolimits.com/wp-content/uploads/2023/05/Yala_National_Park_Sri_Lanka_2012-przerobione.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://andiamoamigos.com/wp-content/uploads/2019/01/DSCN6666-1024x768.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media-cdn.tripadvisor.com/media/attractions-splice-spp-720x480/09/f2/0f/25.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageButton = findViewById(R.id.imageButtonX_E);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionDetails_E);
        journeyToPlaceTextView = findViewById(R.id.journeyToPlaceDetails_E);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places").child("NationalParks");

        // Get the place's ID from your intent or any other method
        String placeId = "Yala National Park";
        // Example description and journeyToPlace strings with paragraphs
        String description = "Yala National Park is a wildlife sanctuary in the southeastern region of Sri Lanka. The park is the second-largest national park in the country for the best Safari experience, spanning over 978.8 square kilometres. It is home to diverse flora and fauna, including Sri Lankan elephants, leopards, sloth bears, and over 200 bird species. The park was first established as a wildlife sanctuary in 1900 and was later declared a national park in 1938. This article will provide an ultimate guide to visiting Yala National Park and Safari.\n\nIt is considered one of the best places in the country for observing and photographing wildlife in its natural habitat. The park spans over 979 square kilometres and is home to many flora and fauna.\n" +
                "\n" +
                "Flora: The vegetation in Yala National Park is characterized by dry monsoon forest, thorny scrub, and savannah grasslands. The park has over 215 species, including trees, shrubs, herbs, and grasses. The most common trees found in the park are Satin, Palu, Weera, Ehala, and Kumbuk.\n" +
                "\n" +
                "Fauna: Yala National Park is home to a diverse range of wildlife, including 44 species of mammals and 215 species of birds. The park is most famous for its large population of Sri Lankan leopards, considered the highest density of leopards in the world. Visitors to the park can also see Asian elephants, sloth bears, spotted deer, sambar deer, wild boar, water buffalo, and the elusive Sri Lankan leopard.\n" +
                "\n" +
                "The park is also a bird watcher’s paradise, with over 215 species of birds recorded within its boundaries. The most common birds found in the park are the Sri Lanka Junglefowl, Sri Lanka Grey Hornbill, Black-capped Bulbul, Indian Peafowl, and the Greater Racket-tailed Drongo.\n" +
                "\n" +
                "Visitors can explore the park on safari jeeps accompanied by experienced guides knowledgeable about the park’s wildlife and vegetation. The park has five zones, each offering a different wildlife experience. Visitors can also enjoy camping, bird watching, and nature walks in the park.\n" +
                "\n" +
                "However, it’s important to note that many of the species in Yala National Park are endangered due to human-wildlife conflict and habitat destruction. Therefore, visitors should not disturb the wildlife or damage their habitats.\n\nExpect to be seen five main animals at Yala National Park.\n" +
                "\n" +
                "Leopards: Yala National Park is famous for its leopard population, and it’s one of the best places in the world to spot these elusive cats. The park has a high density of leopards; visitors can spot them during their safari tours. Leopards are most active during the early morning and late afternoon, making these times the best to go on a safari.\n" +
                "\n" +
                "Elephants: Yala National Park is also home to many elephants, and visitors can see herds of them grazing in the park’s grasslands. The park has around 300 to 400 elephants; visitors can spot them during their safari tours. Elephants are most active during the early morning and late afternoon, making these times the best to go on a safari.\n" +
                "\n" +
                "Sloth Bears: Yala National Park is among the few places visitors can see sloth bears. The park has a small population of these bears, and visitors can spot them during their safari tours. Sloth bears are most active during the early morning and late afternoon, making these times the best safari.\n" +
                "\n" +
                "Water Buffalo: Water buffalo are commonly found in Yala National Park; visitors can see them grazing in the grasslands. The park has a large water buffalo population; visitors can spot them during their safari tours. Water buffalo are most active during the early morning and late afternoon, making these times the best to go on a safari.\n" +
                "\n" +
                "Crocodiles: Yala National Park is home to two species of crocodiles: the mugger crocodile and the saltwater crocodile. Visitors can see these crocodiles basking in the sun near the park’s water bodies. The park has a large population of crocodiles, and visitors have a good chance of spotting them during their safari tours.";
        String journeyToPlace = "Yala National Park is one of Sri Lanka’s most famous national parks, known for its diverse range of wildlife, including leopards, elephants, and sloth bears. The park is situated in the country’s southeastern region and is easily accessible by various modes of transportation. This article will discuss the different ways of getting to Yala National Park.\n" +
                "\n" +
                "\n" +
                "By Air: The nearest airport to Yala National Park is the Mattala Rajapaksa International Airport, approximately 45 kilometres away. Several international airlines operate flights to the airport from various destinations, including Dubai, Doha, and Mumbai. In addition, you can hire a taxi or a car from the airport to the park.\n" +
                "\n" +
                "By Train: Travelling from Colombo, you can take a train to Matara, the nearest city to Yala National Park. The train journey takes approximately six hours and offers beautiful views of Sri Lanka’s countryside. In addition, you can take a bus or a taxi from Matara to the park.\n" +
                "\n" +
                "By Bus: Several buses operate from major cities in Sri Lanka to Tissamaharama, the nearest town to Yala National Park. From Tissamaharama, you can hire a taxi or a jeep to reach the park. The bus journey from Colombo to Tissamaharama takes approximately eight hours.\n" +
                "\n" +
                "By Car: If you prefer a more comfortable and convenient mode of transportation, you can hire a car or a taxi to reach Yala National Park. Several car rental companies operate in Sri Lanka, and you can book a car in advance to avoid any last-minute hassle. The journey from Colombo to Yala National Park takes approximately five hours.\n" +
                "\n" +
                "Once you reach the park, you can hire a jeep with a driver to explore the park’s various attractions. The entrance fee for foreigners is approximately $30 per person, which includes the cost of the jeep and the driver. You can also hire a private jeep for a more personalized experience, which costs around $50 to $70 for half a day.";

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
                String bookingUrl = "https://www.booking.com/searchresults.en-gb.html?ss=Yala+National+Park%2C+Sri+Lanka&ssne=Wilpattu+National+Park&ssne_untouched=Wilpattu+National+Park&label=gog235jc-1DCAEoggI46AdIM1gDaIUBiAEBmAEJuAEXyAEM2AED6AEBiAIBqAIDuAKCwaeqBsACAdICJDVhZWYzZTRjLTQyY2QtNGNlNS1hOTk5LWU2Nzg3ODE2OTZjMNgCBOACAQ&sid=edf2329e549776c19fde29e46829dcdf&aid=397594&lang=en-gb&sb=1&src_elem=sb&src=searchresults&dest_id=7591&dest_type=region&ac_position=0&ac_click_type=b&ac_langcode=en&ac_suggestion_list_length=5&search_selected=true&search_pageview_id=d0fd3147e411017a&ac_meta=GhBkMGZkMzE0N2U0MTEwMTdhIAAoATICZW46EnlhbGEgTmF0aW9uYWwgUGFya0AASgBQAA%3D%3D&checkin=2023-12-02&checkout=2023-12-03&group_adults=1&no_rooms=1&group_children=0";                // Create an Intent to open a web browser with the Booking.com URL
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
        double latitude = 6.46;
        double longitude =  81.47;

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
        double latitude = 6.46;
        double longitude =  81.47;

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

            String placeId = "Yala National Park";

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
        String placeId = "Yala National Park";

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