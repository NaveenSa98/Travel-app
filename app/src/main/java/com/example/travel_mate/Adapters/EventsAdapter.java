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

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    private List<TravelPlan> travelPlans;

    public EventsAdapter(List<TravelPlan> travelPlans) {
        this.travelPlans = travelPlans;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        TravelPlan travelPlan = travelPlans.get(position);

        if (holder != null) {
            holder.destinationTextView.setText(travelPlan.getDestination());
            holder.dateTextView.setText(travelPlan.getTravelDate());
            holder.detailTextView.setText(travelPlan.getDetails());
        }
    }

    @Override
    public int getItemCount() {
        return travelPlans.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView destinationTextView;
        TextView dateTextView;
        TextView detailTextView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            destinationTextView = itemView.findViewById(R.id.textViewDestination);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            detailTextView = itemView.findViewById(R.id.textViewDetails);

        }
    }
}
