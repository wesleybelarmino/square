package com.app.square.common.pojo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity(tableName = "movie")
public class Movie implements Serializable{
    @PrimaryKey
    private int id;
    private String title;
    @SerializedName("vote_count")
    private int voteCount;
    private boolean video;
    @SerializedName("vote_average")
    private float voteAverage;
    private float popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    //@SerializedName("genre_ids")
    //private List<Integer> genreIds;
    @SerializedName("backdrop_path")
    private String backdropPath;
    private boolean adult;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;

}
