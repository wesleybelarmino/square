package com.app.square.networking.interfaces;

import com.app.square.common.pojo.MoviesList;
import io.reactivex.Flowable;
import java.util.Map;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;

public interface IApiServiceGet {

    @GET("3/discover/movie") Flowable<MoviesList> getMoviesList(@FieldMap Map<String, String> params);

}
