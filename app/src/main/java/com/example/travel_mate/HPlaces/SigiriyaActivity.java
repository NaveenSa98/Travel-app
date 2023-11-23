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

public class SigiriyaActivity extends AppCompatActivity {

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
        setContentView(R.layout.h_sigiriya);

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

        slideModels.add(new SlideModel("https://www.lankaprincess.com/wp-content/uploads/2016/04/Sigiriya-Rock-000902.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://ychef.files.bbci.co.uk/1280x720/p0b7n6dm.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.archaeology.lk/wp-content/uploads/2020/11/8.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.talesofceylon.com/wp-content/uploads/2019/10/Culture-1920-x-900.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        imageButton = findViewById(R.id.imageButtonX_E);
        placeDescriptionTextView = findViewById(R.id.placeDescriptionDetails_E);
        journeyToPlaceTextView = findViewById(R.id.journeyToPlaceDetails_E);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places").child("Heritages");

        // Get the place's ID from your intent or any other method
        String placeId = "Sigiriya";

        String description = "Sigiriya, also identified as the Lion's Rock, is a rock fortress and a castle found in the Matale district of Sri Lanka. Gardens, ponds and other structures surround this ruin. King Kassapa built Sigiriya, and it is included as a World Heritage site. Sigiriya is the best-preserved city centre in Asia.\n" +
                "Furthermore, Pidurangala Rock, located next to Sigiriya, gives equally grand panoramas of the surrounding region, a well-known cave complex of its own, several unimaginable views facing the legendary Sigiriya rock.\n\n" +
                "History \n\n" +
                "In that written history, the rock-shelter mountain was used as a monastery that Buddhist enthusiasts granted. Following this, King Kassapa restored it by building gardens and a castle. Later, following his passing, it was repeatedly utilized as a monastery.\n" +
                "At its most immediate, human habituation in Sigiriya was found to be approximately five thousand years throughout the Mesophilic period.\n" +
                "Rock inscriptions are carved near the drip edges on several of the shelters, recording the donation of the caves to the Buddhist monastic order as residences. These have been made within the period between the third century B.C and the first century A.D.\n" +
                "In 1831 Major Jonathan Forbes of the 78th Highlanders of the British army, while returning on horseback from a journey to Polonnaruwa, came across the \"bush covered summit of Sigiriya\". Sigiriya came to the attention of historians and following archaeologists.\n" +
                "The Sigiriya Complex consists of the central rock and two rectangular precincts, enclosed by moats and three walls. The centre is based on a conventional module.\n\n" +
                "Formation of The Lion Rock\n\n" +
                "The Sigiriya Rock is a compacted magma fitting from an ancient volcano. The most significant feature of the rock would be the Lion staircase leading to the castle garden. The Lion could be imagined as a prominent figure towering against the granite façade. The entrance of the Lion leads to the staircase constructed of bricks and wood. But, the only remains of these grand structures are the two paws and the masonry cliffs surrounding that. Although, the cuts and groves in the rock face present an impression of a lion shape.\n" +
                "\n" +
                "The Gardens\n\n" +
                "The gardens are amongst the most magnificent landscaped fields. The gardens are separated into three different but connected structures; water fields, cave and fieldstone gardens, and terraced patios.\n" +
                "\n" +
                "Frescos\n\n" +
                "There are just two pouches of art covering most of the western front of the rock. The females represented in the paintings have been recognized as Sigiri Apsaras. Unfortunately, though, several of these pictures were washed out when the Castle held again transformed into a monastery.\n" +
                "\n" +
                "The Mirror Walls\n\n" +
                "Initially, those walls were so glossy that the king could view himself whilst he walked beside Castle. Built from porcelain, the wall is now partly overlaid with lyrics written by visitors to the rock dating from the 8th century. Writing on the mirror surface has presently been forbidden.";


        String journeyToPlace = "Rent a Car or Taxi \n\n" +
                "It's the most reliable method to reach. On the road, you've got a couple of paths that drive you to Sigiriya. The popular tour guides the Kandy Road to the city of Ambepussa, where turn to Kurunegala on the A06 road around 3 hours drive should be able to reach Sigiriya. (around 165km from Colombo)\n" +
                "On the other road, use Katunayake Expressway. Drive-up from the Bandaranaike international airport, next drive straight through. After that, obey the drive to the city called Minuwangoda and go to Giriulla and subsequently Kurunegala. Of Kurunegala, proceed onward the A06 road towards Dambulla and extend 10km forward the A06 till reach Inamaluwa Junction. Then you can view signage that directs onto Sigiriya rock on the B162 route for about 10 minutes. Or, if you're arriving from Habarana, get over the Polonnaruwa Road A11 and proceed for 3km and turn-off to the B294 road and you can reach Sigiriya around 15 minutes.  \n" +
                "\n" +
                "By Train\n\n" +
                "The nearest railroad station to Sigiriya in Habarana and is around 15km away. Just one train runs daily, making conditions a tiny inconvenience except for the time of your journey respectively. However, thinking about the travel duration and comfort is a little pricey while thinking out your choices. ";


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
                String bookingUrl = "https://www.booking.com/searchresults.en-gb.html?ss=Sigiriya%2C+Matale+District%2C+Sri+Lanka&ssne=Kandy&ssne_untouched=Kandy&label=gog235jc-1DCAEoggI46AdIM1gDaIUBiAEBmAEJuAEXyAEM2AED6AEBiAIBqAIDuALf_rGqBsACAdICJDk3NzU0YjBiLWRmZmUtNDYyYy04ZGIwLWEzYWIzMWQ5ZjI4MNgCBOACAQ&sid=edf2329e549776c19fde29e46829dcdf&aid=397594&lang=en-gb&sb=1&src_elem=sb&src=searchresults&dest_id=-2235832&dest_type=city&ac_position=0&ac_click_type=b&ac_langcode=en&ac_suggestion_list_length=5&search_selected=true&search_pageview_id=2c8434ab80ca0088&ac_meta=GhAyYzg0MzRhYjgwY2EwMDg4IAAoATICZW46B3NpZ2lyaXlAAEoAUAA%3D&checkin=2023-12-02&checkout=2023-12-03&group_adults=1&no_rooms=1&group_children=0";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(bookingUrl));
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
        nearbyListings.add(new NearbyListing("Pidurangala Rock ", 2.8,"https://miro.medium.com/v2/resize:fit:632/1*9XfhiRN72oTNyC9P6CM3_A.jpeg","XQ86+8X7, Sigiriya"));
        nearbyListings.add(new NearbyListing("Dambulla Temple", 19,"https://www.srilankaclassytours.com/medias/activity/big/193/lankaclassytours-dambulla-cavetemple.jpeg","VM32+X6 Dambulla"));
        nearbyListings.add(new NearbyListing("Popham's Arboretum Tree Garden", 20,"https://www.srilankaecotourism.lk/location_img/15196303676.jpg","VM6G+76 Dambulla"));
        nearbyListings.add(new NearbyListing("Namal Uyana", 30,"https://elephassigiriya.com/wp-content/uploads/2023/03/namal-uyana-1024x576.jpg","WH89+HX Ulpathagama"));
        nearbyListings.add(new NearbyListing("Kaudulla National Park", 38,"https://www.srilankanexpeditions.lk/tour_img/1487753694Kaudulla_National_Park_4.jpg","4V6P+78Q, Galoya Rd, Galoya 50150"));
        nearbyListings.add(new NearbyListing("Ibbankatuwa Megalithic Tombs", 21,"https://tropicallifedambulla.com/wp-content/uploads/2019/06/ibbankatuwa.jpg","RJPJ+X2P, Ibbankatuwa, Dambulla"));
        nearbyListings.add(new NearbyListing("Sigiriya Museum", 0.14,"https://www.trawell.in/admin/images/upload/111543552Sigiriya_Museum.jpg","XQ42+RQ Sigiriya"));
        nearbyListings.add(new NearbyListing("Kaludiya Pokuna", 13,"https://www.srilankanexpeditions.com/images/sri-lanka-travel-guide/history-archaeology-sri-lanka/ancient-temple/kaludiya-pokuna-forest/kaludiya-pokuna-forest-02.jpg","VPGP+67F, Dambulla"));

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
        double latitude = 7.957;
        double longitude =   80.76;

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
        double latitude =7.957;
        double longitude =   80.76;

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

            String placeId = "Sigiriya";

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
        String placeId = "Sigiriya";

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