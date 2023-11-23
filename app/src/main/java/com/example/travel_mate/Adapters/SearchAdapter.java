package com.example.travel_mate.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_mate.Classes.SearchPlace;
import com.example.travel_mate.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.PlaceViewHolder> {
    private static List<SearchPlace> placeList;
    private List<SearchPlace> filteredList;


    public interface OnItemClickListener {
        void onItemClick(SearchPlace place);
    }

    private OnItemClickListener onItemClickListener;




    public SearchAdapter(List<SearchPlace> placeList, OnItemClickListener onItemClickListener) {
        this.placeList = placeList;
        this.filteredList = new ArrayList<>(placeList);
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        SearchPlace place = placeList.get(position);
        holder.nameTextView.setText(place.getName());
        holder.districtTextView.setText(place.getDistrict());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(place);
            }
        });




    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public void setFilteredList(List<SearchPlace> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder  {


        TextView nameTextView;
        TextView districtTextView;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.placeNameTextView);
            districtTextView = itemView.findViewById(R.id.districtNameTextView);


        }


    }
}
