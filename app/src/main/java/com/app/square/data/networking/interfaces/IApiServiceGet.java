package com.app.square.data.networking.interfaces;

import com.app.square.common.pojo.MoviesResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApiServiceGet {

    @GET("3/discover/movie") Observable<MoviesResult> movieDiscover(
        @Query("api_key") String api_key, @Query("language") String language,
        @Query("sort_by") String sort_by, @Query("include_adult") String include_adult,
        @Query("page") String page);
}
