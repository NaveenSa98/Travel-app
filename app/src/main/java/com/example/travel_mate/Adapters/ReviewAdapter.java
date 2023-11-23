package com.example.travel_mate.Adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_mate.Classes.Reviews;
import com.example.travel_mate.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Reviews> reviews = new ArrayList<>();

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Reviews review = reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView reviewUser;
        private RatingBar reviewRatingBar;
        private TextView reviewText;
        private TextView ratingLabel;


        ReviewViewHolder(View itemView) {
            super(itemView);
            reviewUser = itemView.findViewById(R.id.reviewUser);
            reviewRatingBar = itemView.findViewById(R.id.reviewRatingBar);
            reviewText = itemView.findViewById(R.id.reviewText);
            ratingLabel = itemView.findViewById(R.id.ratingLabel);


        }

        void bind(Reviews review) {
            reviewUser.setText("Name: " + review.getUsername());
            reviewRatingBar.setRating(review.getRating());
            ratingLabel.setText("Rating: ");
            reviewRatingBar.setIsIndicator(true);
            reviewText.setText("Review: " + review.getText());

        }
    }
}