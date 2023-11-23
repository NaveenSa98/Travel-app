package com.example.travel_mate.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_mate.Classes.TravelPlan;
import com.example.travel_mate.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<TravelPlan> travelPlans;

    public NotificationAdapter(List<TravelPlan> travelPlans) {
        this.travelPlans = travelPlans;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        TravelPlan travelPlan = travelPlans.get(position);
        holder.destinationTextView.setText(travelPlan.getDestination());
        holder.travelDateTextView.setText(travelPlan.getTravelDate());
    }

    @Override
    public int getItemCount() {
        return travelPlans.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView destinationTextView;
        TextView travelDateTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            destinationTextView = itemView.findViewById(R.id.destinationTextView);
            travelDateTextView = itemView.findViewById(R.id.travelDateTextView);
        }
    }
}