package com.example.travel_mate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travel_mate.Classes.PlaceA;
import com.example.travel_mate.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaceAdapter extends ArrayAdapter<PlaceA> {
    private Context context;
    private List<PlaceA> placeList;




    public PlaceAdapter(Context context, List<PlaceA> placeList) {
        super(context, R.layout.card_view_attraction, placeList);
        this.context = context;
        this.placeList = placeList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.card_view_attraction, parent, false);
        }

        ImageView placeImageView = convertView.findViewById(R.id.placeImageView);
        TextView placeNameTextView = convertView.findViewById(R.id.placeNameTextView);
        TextView placeDistrictTextView = convertView.findViewById(R.id.placeDistrictTextView);

        TextView weatherDescriptionTextView = convertView.findViewById(R.id.weatherDescriptionTextView);
        TextView temperatureTextView = convertView.findViewById(R.id.temperatureTextView);


        PlaceA place = placeList.get(position);




        Picasso.get().load(place.getImageURL()).into(placeImageView);

        placeNameTextView.setText(place.getName());
        placeDistrictTextView.setText(place.getdistrict());

        weatherDescriptionTextView.setText("Weather: " + place.getWeatherDescription());
        temperatureTextView.setText("Temperature: " + place.getTemperature());






        return convertView;






    }
}
