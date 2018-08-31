package com.app.square.moviedetail.reviews.pojo;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class ReviewsResult implements Serializable{
    private int id;
    private int page;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;
    private List<Review> results;
}
