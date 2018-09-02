package com.app.square.data;

import android.arch.persistence.room.Room;
import com.app.square.SquareApp;
import com.app.square.common.pojo.MoviesResult;
import com.app.square.data.local.room.DaoMovie;
import com.app.square.data.local.room.SquareDatabase;
import com.app.square.moviedetail.reviews.pojo.ReviewsResult;
import com.app.square.moviedetail.trailers.pojo.TrailersResult;
import com.app.square.data.di.DaggerDataComponent;
import com.app.square.data.networking.ApiRequest;
import io.reactivex.Observable;
import javax.inject.Inject;

public class DataManager {

    private final String API_KEY = "3b0cdf4420a0f312a0be75652d464c56";
    private static final String DB_NAME = "database-name";
    private SquareDatabase squareDatabase;

    @Inject ApiRequest apiRequest;

    public DataManager() {

        DaggerDataComponent.create().inject(this);

        squareDatabase = Room.databaseBuilder(SquareApp.getContext(),
            SquareDatabase.class, DB_NAME).build();

    }

    public Observable<MoviesResult> getMoviesList(String sort_by, int page) {
        return apiRequest.get().movieDiscover(API_KEY, "en-US", sort_by,
            "false","" + page);
    }

    public Observable<TrailersResult> getTrailersByMovie(int movie_id){
        return apiRequest.get().trailersByMovie(movie_id, API_KEY, "en-US");
    }

    public Observable<ReviewsResult> getReviewsByMovie(int movie_id, int page){
        return apiRequest.get().reviewsByMovie(movie_id, API_KEY, "en-US",""+page);
    }

    public DaoMovie getDaoMovie(){
        return squareDatabase.daoMovie();
    }

}
