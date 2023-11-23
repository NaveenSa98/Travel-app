package com.example.travel_mate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_mate.Classes.NearbyListing;
import com.example.travel_mate.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NearbyListingAdapter extends RecyclerView.Adapter<NearbyListingAdapter.ViewHolder> {
    private List<NearbyListing> nearbyListings;
    private Context context;

    public NearbyListingAdapter(Context context, List<NearbyListing> nearbyListings) {
        this.context = context;
        this.nearbyListings = nearbyListings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_waterfall_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NearbyListing nearbyListing = nearbyListings.get(position);
        holder.waterfallName.setText(nearbyListing.getName());
        holder.distance.setText(String.format("%.2f km", nearbyListing.getDistance()));

        Picasso.get().load(nearbyListing.getImageUrl()).into(holder.waterfallImage);

        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(nearbyListing);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nearbyListings.size();
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

    public interface OnItemClickListener {
        void onItemClick(NearbyListing nearbyListing);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }



}
