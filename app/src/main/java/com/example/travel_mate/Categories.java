package com.example.travel_mate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.example.travel_mate.Places.AttractionActivity;
import com.example.travel_mate.Places.BeachActivity;
import com.example.travel_mate.Places.HeritageActivity;
import com.example.travel_mate.Places.ParkActivity;
import com.example.travel_mate.Places.WaterfallActivity;

public class Categories extends AppCompatActivity {

    ImageView attractionImageView;
    ImageView beachImageView;
    ImageView heritageImageView;
    ImageView waterfallImageView;
    ImageView parkImageView;


    CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        attractionImageView = findViewById(R.id.attractionImage);
        beachImageView = findViewById(R.id.beachImage);
        heritageImageView = findViewById(R.id.heritageImage);
        waterfallImageView = findViewById(R.id.waterfallImage);
        parkImageView = findViewById(R.id.parkImage);





        attractionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, AttractionActivity.class);
                startActivity(intent);
            }
        });

        beachImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, BeachActivity.class);
                startActivity(intent);
            }
        });


       heritageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, HeritageActivity.class);
                startActivity(intent);
            }
        });

        waterfallImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, WaterfallActivity.class);
                startActivity(intent);
            }
        });

        parkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, ParkActivity.class);
                startActivity(intent);
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();




    }


}
