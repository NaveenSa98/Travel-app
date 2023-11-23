package com.example.travel_mate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_mate.APlaces.EllaRockActivity;
import com.example.travel_mate.APlaces.GreenMountainActivity;
import com.example.travel_mate.APlaces.KnucklesMountainActivity;
import com.example.travel_mate.APlaces.MadolsimActivity;
import com.example.travel_mate.APlaces.NineArchActivity;
import com.example.travel_mate.Adapters.SearchAdapter;
import com.example.travel_mate.BPlaces.DallwellaBeachActivity;
import com.example.travel_mate.BPlaces.HiriketiyaBeachActivity;
import com.example.travel_mate.BPlaces.JungleBeachActivity;
import com.example.travel_mate.BPlaces.MirissaBeachActivity;
import com.example.travel_mate.BPlaces.NilaveliBeachActivity;
import com.example.travel_mate.Classes.SearchPlace;
import com.example.travel_mate.HPlaces.AnuradhapuraActivity;
import com.example.travel_mate.HPlaces.KandyActivity;
import com.example.travel_mate.HPlaces.PolonnaruwaActivity;
import com.example.travel_mate.HPlaces.SigiriyaActivity;
import com.example.travel_mate.NpPlaces.HortainPlainsActivity;
import com.example.travel_mate.NpPlaces.KumanaParkActivity;
import com.example.travel_mate.NpPlaces.MinneriyaParkActivity;
import com.example.travel_mate.NpPlaces.WilpattuParkActivity;
import com.example.travel_mate.NpPlaces.YalaParkActivity;
import com.example.travel_mate.WPlaces.BambarakandaWaterfallActivity;
import com.example.travel_mate.WPlaces.BomburuWaterfallActivity;
import com.example.travel_mate.WPlaces.DunhindaWaterfallActivity;
import com.example.travel_mate.WPlaces.DunsinaneWaterfallActivity;
import com.example.travel_mate.WPlaces.GartmoreWaterfallActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity  {

    private EditText searchResultText;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private List<SearchPlace> placeList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        searchResultText = findViewById(R.id.searchResultsText);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        placeList = new ArrayList<>();
        searchAdapter = new SearchAdapter(placeList, new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SearchPlace place) {

                Intent intent;
                switch (place.getName()) {
                    case "Ella Rock":
                        intent = new Intent(SearchResultActivity.this, EllaRockActivity.class);
                        break;
                    case "Green Mountain":
                        intent = new Intent(SearchResultActivity.this, GreenMountainActivity.class);
                        break;
                    case "Knuckles Mountain":
                        intent = new Intent(SearchResultActivity.this, KnucklesMountainActivity.class);
                        break;
                    case "Madulsima":
                        intent = new Intent(SearchResultActivity.this, MadolsimActivity.class);
                        break;
                    case "Nine Arch Bridge":
                        intent = new Intent(SearchResultActivity.this, NineArchActivity.class);
                        break;
                    case "Dalawella Beach":
                        intent = new Intent(SearchResultActivity.this, DallwellaBeachActivity.class);
                        break;
                    case "Hiriketiya Beach":
                        intent = new Intent(SearchResultActivity.this, HiriketiyaBeachActivity.class);
                        break;
                    case "Jungle Beach":
                        intent = new Intent(SearchResultActivity.this, JungleBeachActivity.class);
                        break;
                    case "Mirissa Beach":
                        intent = new Intent(SearchResultActivity.this, MirissaBeachActivity.class);
                        break;
                    case "Nilaveli Beach":
                        intent = new Intent(SearchResultActivity.this, NilaveliBeachActivity.class);
                        break;
                    case "Anuradhapura":
                        intent = new Intent(SearchResultActivity.this, AnuradhapuraActivity.class);
                        break;
                    case "Kandy":
                        intent = new Intent(SearchResultActivity.this, KandyActivity.class);
                        break;
                    case "Polonnaruwa":
                        intent = new Intent(SearchResultActivity.this, PolonnaruwaActivity.class);
                        break;
                    case "Sigiriya":
                        intent = new Intent(SearchResultActivity.this, SigiriyaActivity.class);
                        break;
                    case "Horton Plains":
                        intent = new Intent(SearchResultActivity.this, HortainPlainsActivity.class);
                        break;
                    case "Kumana Park":
                        intent = new Intent(SearchResultActivity.this, KumanaParkActivity.class);
                        break;
                    case "Minneriya Park":
                        intent = new Intent(SearchResultActivity.this, MinneriyaParkActivity.class);
                        break;
                    case "Wilpattu Park":
                        intent = new Intent(SearchResultActivity.this, WilpattuParkActivity.class);
                        break;
                    case "Yala Park":
                        intent = new Intent(SearchResultActivity.this, YalaParkActivity.class);
                        break;
                    case "Bambarakanda Falls":
                        intent = new Intent(SearchResultActivity.this, BambarakandaWaterfallActivity.class);
                        break;
                    case "Bomburu Falls":
                        intent = new Intent(SearchResultActivity.this, BomburuWaterfallActivity.class);
                        break;
                    case "Dunhinda Falls":
                        intent = new Intent(SearchResultActivity.this, DunhindaWaterfallActivity.class);
                        break;
                    case "Dunsinane Falls":
                        intent = new Intent(SearchResultActivity.this, DunsinaneWaterfallActivity.class);
                        break;
                    case "Gartmore Falls":
                        intent = new Intent(SearchResultActivity.this, GartmoreWaterfallActivity.class);
                        break;

                    default:
                        // If the place doesn't match any case, you can have a default activity
                        intent = new Intent(SearchResultActivity.this, Categories.class);
                        break;
                }
                intent.putExtra("PLACE_NAME", place.getName());
                intent.putExtra("PLACE_DISTRICT", place.getDistrict());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(searchAdapter);

        searchResultText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!editable.toString().isEmpty()) {
                    performSearch(editable.toString().trim());
                }
                // Handle text changes
                filterResults(editable.toString());


            }
        });




        // Retrieve search query from intent
        String searchQuery = getIntent().getStringExtra("SEARCH_QUERY");

        // Set search query to the EditText
        searchResultText.setText(searchQuery);

    }



    private void performSearch(String searchQuery) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Places");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                placeList.clear();

                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot placeSnapshot : categorySnapshot.getChildren()) {
                        SearchPlace place = placeSnapshot.getValue(SearchPlace.class);

                        // Check if the place name or district contains the search query
                        if (place != null && (place.getName().toLowerCase().contains(searchQuery.toLowerCase())
                                || place.getDistrict().toLowerCase().contains(searchQuery.toLowerCase()))) {
                            placeList.add(place);
                        }
                    }
                }

                searchAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
    private void filterResults(String query) {
        List<SearchPlace> filteredList = new ArrayList<>();

        for (SearchPlace place : placeList) {
            // Check if the place name or district contains the search query
            if (place.getName().toLowerCase().contains(query.toLowerCase())
                    || place.getDistrict().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(place);
            }
        }

        // Update the RecyclerView with the filtered results
        searchAdapter.setFilteredList(filteredList);
    }




}
