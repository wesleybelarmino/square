package com.app.square.moviedetail.reviews.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.square.R;
import com.app.square.common.base.BaseViewHolder;
import com.app.square.moviedetail.reviews.pojo.Review;
import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<Review> reviewList = new ArrayList<>();

    public void addReviews(List<Review> reviews) {
        reviewList.clear();
        reviewList.addAll(reviews);
    }

    @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.movies_detail_review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.bind(review);
    }

    public ArrayList<Review> getList() {
        return reviewList;
    }

    @Override public int getItemCount() {
        return reviewList.size();
    }
}
