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

public class KandyActivity extends AppCompatActivity {

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
        setContentView(R.layout.h_kandy);

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

        slideModels.add(new SlideModel("https://www.destguides.com/dynamic-files/itinerary/2424/background-image.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.crowdsourcedexplorer.com/wp-content/uploads/2021/04/Kandy-Central-Sri-Lanka-103808.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://s1.1zoom.ru/big0/488/Sri_Lanka_Lake_Houses_Sunrises_and_sunsets_Kandy_579821_1280x853.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://images.squarespace-cdn.com/content/v1/5a3bb03b4c326d76de73ddaa/6f99e426-8b88-4417-bfa1-390e9bb97637/The_Common_Wanderer_-8976.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageButton = findViewById(R.id.imageButtonX_E);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionDetails_E);
        journeyToPlaceTextView = findViewById(R.id.journeyToPlaceDetails_E);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places").child("Heritages");

        // Get the place's ID from your intent or any other method
        String placeId = "Kandy";

        String description = "Kandy is Sri Lanka’s last Singhalese Royal Kingdom, with panoramic geography surrounding magnificent peaks in the aisle, 1,600 feet above sea level. The Sacred Tooth Relic Temple, the most respected Buddhist Temple, and The Royal Botanic Gardens, with over 4,000 designated types of flora, are highly known in the city. Kandy was also a well-planned metropolis during Ceylon’s British colonial period (Sri Lanka).\n" +
                "Considering these considerations, UNESCO designated Kandy as a UNESCO World Heritage Site. Kandy, the second most populous city in Sri Lanka, has a total area of 1,940 square kilometres and is a popular tourist destination. In addition, Kandy is essential in the different cultures of the Sinhala and Tamil populations. Kandy is one of Sri Lanka’s largest and most culturally significant cities, having a population of roughly 110,000 people.\n" +
                "Kandy comes alive in August because of the Kandy Esala Perehera, an annual ceremony of the Temple of the Sacred Tooth. It generally draws many local and foreign tourists who appreciate cultural monomials and Buddhist origins." +
                "With so many attractions and intriguing locations, these are the most well-known spots in Kandy. There is plenty for all sorts of tourists, from galleries to vistas.\n\n" +
                "History of Kandy\n\n" +
                "The English bestowed the name “Kandy,” a derivative of “Kanda Uda Rata,” reflecting the city’s elevated geographical setting. Presently known as “Maha Nuwara” in Sinhala, Kandy’s historical essence is “Senkadagalapura.” The roots of this intriguing name lead us down the paths of folklore and legend.\n" +
                "\n" +
                "Origins of Senkadagala\n\n" +
                "Senkadagala’s origins are shrouded in three distinct narratives. It is believed to have been named after a Brahmin named Senkadagala, a queen named Senkada associated with King Wickramabahu, or a precious coloured stone called Senkada gala. These legends intertwine, creating a mystique that adds to the city’s allure.\n" +
                "\n" +
                "The Birth of a Regal Capital\n\n" +
                "During the Gampola Era, the illustrious King Wickramabahu III established Senkadagalapura as a city from 1357 to 1374 AD. However, it was Senasammata Wickremabahu, ascending the throne in the 15th century (1473-1511), who elevated Kandy to the status of the capital of the Kandyan Kingdom. The city’s grandeur was further enhanced by the presence of the Royal Palace and the revered “Dalada Maligawa,” the Temple of the Tooth Relic.\n" +
                "\n" +
                "Triumph Amidst Turmoil\n\n" +
                "Kandy’s history is marked by its resilience against foreign invaders. Despite coastal regions succumbing to Portuguese, Dutch, and English influences, Kandy stood firm, safeguarding its independence. The treacherous mountain routes that led to the city became its shield, thwarting numerous invasions. It was in 1815 that Kandy eventually yielded to British authority, symbolized by the signing of a pact at the historic “Magul Maduwa,” near the Dalada Maligawa by the serene Kandy lakeside.\n" +
                "\n" +
                "The Sunset of a Royal Dynasty\n\n" +
                "The poignant tale of Kandy’s last king, Sri Wickrama Rajasinhe, adds a melancholic note to its history. Imprisoned by the British and exiled to India, he endured a life of captivity until his passing. With his fall, the illustrious Royal Dynasty, which had suffered since the 6th century BC—spanning over 2350 years—came to a poignant end, marking a turning point in the annals of time.\n" +
                "\n" +
                "A Spiritual Haven\n\n" +
                "Kandy’s enduring significance as the religious capital of Sri Lanka is an indelible thread woven into its narrative. Through invasions and upheavals, the city retained its status as a pilgrimage site for devout Buddhists, practising the purest form of the faith. The legacy of Kandy lives on, a testament to its unwavering devotion to spirituality.";


        String journeyToPlace = "The City of Kandy is accessible by road from all Sri Lankan cities and by rail from Colombo. The most usual way to get to Kandy is to fly to Colombo and then take either the road or the rail, depending on your comfort level. Travelling within the city is very simple, with several taxis and tuk-tuks patrolling throughout the day.\n" +
                "\n" +
                "By Train\n\n" +
                "The Inter-City Express is a low-cost and quick method to go to Kandy. Three trains arrive in the city from Badulla and two from Colombo. The trains contain observation saloons and second-class rooms that must be reserved in advance. In addition, third-class compartments need to be secured. The train travels past gorgeous green hills and villages, adding to the excitement of the voyage.\n" +
                "\n" +
                "By Road\n\n" +
                "Every day, several air-conditioned interstate buses and other public and private buses go from Colombo to Kandy. In reality, several buses stop at the international airport. Taxis are frequently used because they are readily accessible and provide a safe mode of transportation, despite being relatively expensive. If you travel to a large party, minivans may transport you to Kandy comfortably. The trip should take roughly three and a half hours in total.\n" +
                "Kandy is a small city; therefore, strolling or hiring a scooter is one of the finest ways to explore it. Tuk-tuk taxis, on the other hand, are the most common mode of transportation. They are quick and inexpensive and travel to the city’s outskirts. Within the municipal borders, there are several public and private buses. In addition, there is a bus stop directly outside the city railway station for those coming by train. ";


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
                String bookingUrl = "https://www.booking.com/searchresults.en-gb.html?ss=Kandy%2C+Kandy+District%2C+Sri+Lanka&ssne=Polonnaruwa&ssne_untouched=Polonnaruwa&label=gog235jc-1DCAEoggI46AdIM1gDaIUBiAEBmAEJuAEXyAEM2AED6AEBiAIBqAIDuALf_rGqBsACAdICJDk3NzU0YjBiLWRmZmUtNDYyYy04ZGIwLWEzYWIzMWQ5ZjI4MNgCBOACAQ&sid=edf2329e549776c19fde29e46829dcdf&aid=397594&lang=en-gb&sb=1&src_elem=sb&src=searchresults&dest_id=-2222251&dest_type=city&ac_position=0&ac_click_type=b&ac_langcode=en&ac_suggestion_list_length=5&search_selected=true&search_pageview_id=a5bc2f35244e014d&ac_meta=GhBhNWJjMmYzNTI0NGUwMTRkIAAoATICZW46BWthbmR5QABKAFAA&checkin=2023-12-02&checkout=2023-12-03&group_adults=1&no_rooms=1&group_children=0";
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
        nearbyListings.add(new NearbyListing("Sri Dalada Maligaya ", 0.65,"https://t3.ftcdn.net/jpg/03/64/95/12/360_F_364951258_xLzCW41kr5AV7OXdVv49ufv6u0XL3nqq.jpg","7JVR+CG Kandy"));
        nearbyListings.add(new NearbyListing("Embekka Dewalaya", 15,"https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/Embekka_Devalaya_01.JPG/1200px-Embekka_Devalaya_01.JPG","6H99+53 Embekka"));
        nearbyListings.add(new NearbyListing("Ambuluwawa Temple", 26,"https://meetinsrilanka.com/wp-content/uploads/2021/05/ambuluwawa-tower-sri-lanka-the-travelizer-featured.jpg","5G6W+CR Gampola"));
        nearbyListings.add(new NearbyListing("Pothgul Maliga Maha Viharaya", 31,"https://dayouting.lk/img_uploads/tourist-places/5396601671811447.jpg","5QHG+29G, Hanguranketa - Galauda Road, Hanguranketa"));
        nearbyListings.add(new NearbyListing("Seetha Kotuwa & Seetha Ella", 64,"https://dayouting.lk/img_uploads/tourist-places/8195161670659728.jpg","8WVH+QRQ, A26, Hasalaka"));
        nearbyListings.add(new NearbyListing("National Museum of Kandy", 3.1,"https://dayouting.lk/img_uploads/tourist-places/6973681662631304.jpg","7JVR+8RJ, Kandy"));
        nearbyListings.add(new NearbyListing("Nelligala International Buddhist Center", 13,"https://t4.ftcdn.net/jpg/05/09/63/71/360_F_509637149_p6uDu89oo3ez5EuEhxQcpe8wnNN4Kdxr.jpg","8H25+RV9, Nelligala Rd, Muruthalawa"));
        nearbyListings.add(new NearbyListing("Victoria Dam", 45,"https://www.attractionsinsrilanka.com/wp-content/uploads/2020/05/Victoria-Dam1.jpg","6QRQ+V4 Thennelanda"));
        nearbyListings.add(new NearbyListing("Peradeniya Botanical Gardens", 6.4,"https://www.attractionsinsrilanka.com/wp-content/uploads/2019/07/Royal-Botanical-Gardens-Peradeniya.jpg","7HCV+RW6, Kandy"));
        nearbyListings.add(new NearbyListing("Bahirawakanda Temple", 1,"https://www.kandyescapes.com/wp-content/uploads/2020/04/bahirawakanda-mob.jpg","7JWJ+5GR, Bahirawa Kanda Rd, Kandy"));
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
        double latitude = 7.29;
        double longitude =   80.635;

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
        double latitude = 7.29;
        double longitude =   80.635;

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

            String placeId = "Kandy";

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
        String placeId = "Kandy";

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