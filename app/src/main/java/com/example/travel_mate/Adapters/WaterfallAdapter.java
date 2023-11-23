package com.example.travel_mate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_mate.Classes.WaterFall;
import com.example.travel_mate.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WaterfallAdapter extends RecyclerView.Adapter<WaterfallAdapter.ViewHolder> {
    private List<WaterFall> waterfalls;
    private Context context;

    public WaterfallAdapter(Context context, List<WaterFall> waterfalls) {
        this.context = context;
        this.waterfalls = waterfalls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_waterfall_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WaterFall waterfall = waterfalls.get(position);
        holder.waterfallName.setText(waterfall.getName());
        holder.distance.setText(String.format("%.2f km", waterfall.getDistance()));

        Picasso.get().load(waterfall.getImageUrl()).into(holder.waterfallImage);


    }

    @Override
    public int getItemCount() {
        return waterfalls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView waterfallImage;
        TextView waterfallName;
        TextView distance;

        public ViewHolder(View itemView) {
            super(itemView);
            waterfallName = itemView.findViewById(R.id.waterfallName);
            distance = itemView.findViewById(R.id.distance);
            waterfallImage = itemView.findViewById(R.id.waterfallImage);
        }
    }





}
