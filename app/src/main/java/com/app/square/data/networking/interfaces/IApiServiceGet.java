package com.app.square.data.networking.interfaces;

import com.app.square.common.pojo.MoviesResult;
import com.app.square.moviedetail.reviews.pojo.ReviewsResult;
import com.app.square.moviedetail.trailers.pojo.TrailersResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiServiceGet {

    @GET("discover/movie") Observable<MoviesResult> movieDiscover(
        @Query("api_key") String api_key, @Query("language") String language,
        @Query("sort_by") String sort_by, @Query("include_adult") String include_adult,
        @Query("page") String page);

    @GET("movie/{id}/videos") Observable<TrailersResult> trailersByMovie(@Path("id") int movie_id,
        @Query("api_key") String api_key, @Query("language") String language);

    @GET("movie/{id}/reviews") Observable<ReviewsResult> reviewsByMovie(@Path("id") int movie_id,
        @Query("api_key") String api_key, @Query("language") String language,
        @Query("page") String page);
}