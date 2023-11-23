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

public class KnucklesMountainActivity extends AppCompatActivity {

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
        setContentView(R.layout.a_knucklesmountain);

        // Initialize Firebase Database reference
        placesRef = FirebaseDatabase.getInstance().getReference("Reviews");


        // Initialize views
        ratingBar = findViewById(R.id.ratingBar_K);
        reviewInput = findViewById(R.id.reviewInput_K);
        nameInput = findViewById(R.id.nameInput_K);
        submitReviewButton = findViewById(R.id.submitReviewButton_K);
        reviewRecyclerView = findViewById(R.id.reviewRecyclerView_K);
        reviewTitle = findViewById(R.id.reviewTitle_K);

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



        ImageSlider imageSlider = findViewById(R.id.imageSlider_K);

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://www.attractionsinsrilanka.com/wp-content/uploads/2019/07/Knuckles-Mountain-Range.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.reddottours.com/uploads/Activities/Knuckles-Mountain-Range/Knuckles-Mountain-Range-gallery-pop-up-(10)-min.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://tlc.lk/wp-content/uploads/2017/06/Knuckles-canstockphoto16606918-1.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.attractionsinsrilanka.com/wp-content/uploads/2020/01/Knuckles-Infinity-Pool.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageButton = findViewById(R.id.imageButtonX_K);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionDetails_K);
        journeyToPlaceTextView = findViewById(R.id.journeyToPlaceDetails_K);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places").child("Attractions");



        // Get the place's ID from your intent or any other method
        String placeId = "Knuckles Mountain";

        String description = "  The Knuckles Mountain Range, known as Dumbara Kanduvetiya, is a stunning natural marvel in central Sri Lanka. Stretching across the picturesque districts of Matale and Kandy, this mountain range captivates visitors with its unique landscapes, abundant biodiversity, and breathtaking views. Named after the series of recumbent folds and peaks in the western part of the range, which resembles the knuckles of a clenched fist, the Knuckles Mountain Range is a paradise for nature enthusiasts and trekkers alike. This writing will delve into the wonders of the Knuckles Mountain Range, its accessibility, trekking opportunities, notable attractions, weather conditions, and rich biodiversity.\n" +
                "  The Knuckles Mountain Range is renowned for its majestic peaks, adding to the allure and scenic beauty of the region. These peaks, resembling clenched fists, create a breathtaking panorama that captivates visitors' and trekkers' imaginations. With 34 peaks ranging in height from 900 meters to 1,900 meters, the Knuckles Mountain Range offers a spectacular playground for adventure enthusiasts and nature lovers.\n" +
                "  Among the notable peaks within the range, Gombaniya stands tall as the highest peak, soaring to an impressive height of 1,906 meters. This magnificent summit claims the distinction of being the sixth-highest peak in Sri Lanka, and its commanding presence offers a panoramic view that leaves visitors in awe.\n" +
                "  Another prominent peak within the Knuckles Mountain Range is Knuckles-Kirigalpotta, which reaches a height of 1,647 meters. This majestic peak rewards trekkers with sweeping vistas of the surrounding landscapes, lush forests, and verdant valleys. As you ascend to the summit, the beauty of the Knuckles Mountain Range unfolds before you, leaving you with unforgettable memories.\n" +
                "  Aliyawetunaela and Dumbanagala, reaching heights of 1,644 meters, are among the other notable peaks within the range. These peaks allow trekkers to challenge themselves while being rewarded with breathtaking views that stretch as far as the eye can see.\n" +
                "  Telambugala (1,331m), Dothalugala (1,575m), Wamarapugala (1,559m), Koboneelagala (1,555m), Lahumanagala (1,114m), Kalupahana (1,628m), Rilagala (1,605m), Nawanagala (1,488m), Lakegala (1,310m), Maratuwegala (1,190m), Balagiriya (1,148m), Velangala (1,180m), Kinihirigala (1,068m), and Lunumadalla (1,060m) are some of the other peaks that grace the Knuckles Mountain Range. Each of these peaks offers its unique perspective and rewards those who dare to explore their heights.\n" +
                "  With its location in the wet zone of the country, the Knuckles Mountain Range receives abundant rainfall from both the Southwest and Northwest Monsoons. As a result, the region showcases a spectacular array of breathtaking waterfalls and streams, including the origins of the Hulu Ganga, Heen Ganga, and Kalu Ganga rivers.";

        String journeyToPlace = "  Embarking on a remarkable journey through the Knuckles Mountain Range involves navigating the picturesque roads that lead to this natural wonder. Travellers often begin their adventure by taking the A26 central motorway east of Kandy, the gateway to this magnificent region. As they make their way along this well-connected motorway, they will be captivated by the scenic landscapes that unfold before them.\n" +
                "  From Hunnasiriya, travellers can access the Knuckles Mountain Range via a B-grade road. This road serves as a guide, leading them towards the enchanting Corbett's Gap. This vantage point offers breathtaking panoramic views of the Knuckles range, providing a glimpse of the awe-inspiring beauty that awaits within.\n" +
                "  Alternatively, another entry point to the Knuckles Range can be accessed from Matale. Travellers can follow the route through Rattota and Riversten, immersing themselves in the natural splendour that surrounds them. The journey from Matale to the Knuckles Range presents an opportunity to witness the diverse landscapes and experience the region's tranquillity.\n" +
                "  Another entry option is via Wattegama, which can be reached through the scenic route of Panwila. This route allows travellers to soak in the beauty of their surroundings as they approach the Knuckles Mountain Range. The road gradually winds through the captivating landscapes, leading adventurers closer to the heart of this natural paradise.\n" +
                "  As travellers venture deeper into the Knuckles Mountain Range, they will be greeted by the rugged peaks that have earned the range its distinctive name. These peaks, reminiscent of clenched fists, stand proudly and majestically, showcasing the breathtaking beauty of nature's artistry. The mountain terrain within the range is characterized by its steep slopes and reaches an elevation of up to 1,863 meters. Among the remarkable features of the Knuckles Range is a rare dwarf cloud forest, adding to the uniqueness and allure of this natural treasure.\n" +
                "  Prepare yourself for an unforgettable journey as you enter the Knuckles Mountain Range. Embrace the serenity of the roads that lead you there, and allow the captivating landscapes to captivate your senses. The Knuckles Range awaits, ready to immerse you in its awe-inspiring beauty and provide you with an experience that will leave a lasting impression.";

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

        Button accommodationButton = findViewById(R.id.accommodationButton_K);

        accommodationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the URL for Booking.com
                String bookingUrl = "https://www.booking.com/searchresults.en-gb.html?ss=Knuckles+Mountain+Range%2C+Sri+Lanka&ssne=Ella+Rock&ssne_untouched=Ella+Rock&label=gog235jc-1DCAEoggI46AdIM1gDaIUBiAEBmAEJuAEXyAEM2AED6AEBiAIBqAIDuAKuwfmpBsACAdICJDYxMzkwOTRiLTlkZTEtNDY3NS1iY2NiLWNlOTIwYjVjYzBiNtgCBOACAQ&sid=edf2329e549776c19fde29e46829dcdf&aid=397594&lang=en-gb&sb=1&src_elem=sb&src=searchresults&dest_id=15201&dest_type=region&ac_position=0&ac_click_type=b&ac_langcode=en&ac_suggestion_list_length=5&search_selected=true&search_pageview_id=2bd860303c5e00a6&ac_meta=GhAyYmQ4NjAzMDNjNWUwMGE2IAAoATICZW46BGtudWNAAEoAUAA%3D&checkin=2023-11-03&checkout=2023-11-04&group_adults=1&no_rooms=1&group_children=0";

                // Create an Intent to open a web browser with the Booking.com URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(bookingUrl));

                // Start the activity to open the web browser
                startActivity(intent);
            }
        });
        emergencyButton1 = findViewById(R.id.hospitalButton_K);
        emergencyButton2 = findViewById(R.id.policeStationButton_K);

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
        ImageButton backButton = findViewById(R.id.backButton_K);
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
        double latitude = 7.39;
        double longitude = 80.80;

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
        double latitude = 7.39;
        double longitude = 80.80;

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

            String placeId = "Knuckles Mountain";

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
        String placeId = "Knuckles Mountain";

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