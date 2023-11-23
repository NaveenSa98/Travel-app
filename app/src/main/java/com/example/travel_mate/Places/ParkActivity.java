package com.example.travel_mate.Places;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.travel_mate.Adapters.PlaceAdapter;
import com.example.travel_mate.Classes.PlaceA;
import com.example.travel_mate.NpPlaces.HortainPlainsActivity;
import com.example.travel_mate.NpPlaces.KumanaParkActivity;
import com.example.travel_mate.NpPlaces.MinneriyaParkActivity;
import com.example.travel_mate.NpPlaces.WilpattuParkActivity;
import com.example.travel_mate.NpPlaces.YalaParkActivity;
import com.example.travel_mate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParkActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ListView placeListView;

    private static final String API_KEY = "f3ca99b545154312ace633912edee4af";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_park);



        databaseReference = FirebaseDatabase.getInstance().getReference("Places").child("NationalParks");
        placeListView = findViewById(R.id.placeListView_5);



        final List<PlaceA> placeList = new ArrayList<>();
        final PlaceAdapter adapter = new PlaceAdapter(this, placeList);
        placeListView.setAdapter(adapter);


        // Listen for changes in the Firebase data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                placeList.clear();

                for (DataSnapshot placeSnapshot : dataSnapshot.getChildren()) {
                    String placeName = placeSnapshot.getKey();
                    String placeDistrict = placeSnapshot.child("district").getValue(String.class);
                    String placeImageURL = placeSnapshot.child("imageURL").getValue(String.class);

                    PlaceA place = new PlaceA(placeName, placeDistrict, placeImageURL);
                    placeList.add(place);


                }

                for (final PlaceA place : placeList) {
                    String district = place.getdistrict ();
                    String weatherApiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + district  + "&appid=" + API_KEY;

                    // Make an HTTP request to fetch weather data
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, weatherApiUrl, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONObject main = response.getJSONObject("main");
                                        JSONArray weatherArray = response.getJSONArray("weather");
                                        JSONObject weather = weatherArray.getJSONObject(0);




                                        double kelvinTemperature = main.getDouble("temp");


                                        double celsiusTemperature = kelvinTemperature - 273.15;

                                        String description = weather.getString("description");
                                        String temperature = String.format("%.1f°C", celsiusTemperature);

                                        // Update the PlaceA object with weather data
                                        place.setWeatherDescription(description);
                                        place.setTemperature(temperature);

                                        adapter.notifyDataSetChanged();
                                        // }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                }
                            });

                    // Add the request to the request queue
                    RequestQueue requestQueue = Volley.newRequestQueue(ParkActivity.this);
                    requestQueue.add(jsonObjectRequest);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Set a click listener for the ListView items
        placeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle the click action here, e.g., open a new activity for the selected place
                PlaceA selectedPlace = placeList.get(position);

                // Determine which Activity to start based on the place name
                Class<?> destinationActivity = null;
                switch (selectedPlace.getName()) {
                    case "Wilpattu National Park":
                        destinationActivity = WilpattuParkActivity.class;
                        break;
                    case "Yala National Park":
                        destinationActivity = YalaParkActivity.class;
                        break;
                    case "Minneriya National Park":
                        destinationActivity = MinneriyaParkActivity.class;
                        break;
                    case "Kumana National Park":
                        destinationActivity = KumanaParkActivity.class;
                        break;
                    case "Horton Plains National Park":
                        destinationActivity = HortainPlainsActivity.class;
                        break;
                }

                // Start the corresponding Activity
                if (destinationActivity != null) {
                    Intent intent = new Intent(ParkActivity.this, destinationActivity);
                    intent.putExtra("placeName", selectedPlace.getName());
                    startActivity(intent);
                }

            }
        });


    }


}