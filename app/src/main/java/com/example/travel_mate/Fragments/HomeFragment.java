package com.example.travel_mate.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.travel_mate.Accommodation;
import com.example.travel_mate.Categories;
import com.example.travel_mate.Map.MapsActivity;
import com.example.travel_mate.R;
import com.example.travel_mate.SearchResultActivity;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    View view;
    CardView imageCard;
    EditText searchText;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_home, container, false);

        searchText = view.findViewById(R.id.searchText);

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    startSearch();
                    return true;
                }
                return false;
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Check if the search text is empty
                if (editable.toString().isEmpty()) {

                }
            }
        });

        searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    startSearch();
                }
            }
        });


        ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.ella, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.p_2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.h_2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.beachside, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.w_4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.galle, ScaleTypes.FIT));


        imageSlider.setImageList(slideModels,ScaleTypes.FIT);


        imageCard = view.findViewById(R.id.imageCard);
        imageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Categories.class);
                startActivity(intent);

            }
        });

        imageCard = view.findViewById(R.id.imageCard_2);
        imageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);

            }
        });

        imageCard = view.findViewById(R.id.imageCard_3);
        imageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Accommodation.class);
                startActivity(intent);


            }
        });

        return view;


    }



    private void startSearch() {
        String searchQuery = searchText.getText().toString().trim();

        // Hide the keyboard
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);

        // Open SearchResultActivity with the search query
        Intent intent = new Intent(getContext(), SearchResultActivity.class);
        intent.putExtra("SEARCH_QUERY", searchQuery);
        startActivity(intent);
    }




}