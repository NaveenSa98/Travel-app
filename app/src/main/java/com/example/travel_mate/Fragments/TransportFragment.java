package com.example.travel_mate.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.travel_mate.R;
import com.example.travel_mate.Transport.BusBookActivity;
import com.example.travel_mate.Transport.TrainBookActivity;
import com.example.travel_mate.Transport.UberActivity;

public class TransportFragment extends Fragment {

    CardView imageCard;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transport, container, false);



        imageCard = view.findViewById(R.id.imageCard_U);
        imageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UberActivity.class);
                startActivity(intent);

            }
        });



        imageCard = view.findViewById(R.id.imageCard_B);
        imageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BusBookActivity.class);
                startActivity(intent);


            }
        });

        imageCard = view.findViewById(R.id.imageCard_T);
        imageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TrainBookActivity.class);
                startActivity(intent);


            }
        });






        return view;
    }




}
