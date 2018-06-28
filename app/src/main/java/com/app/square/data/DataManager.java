package com.app.square.data;

import com.app.square.common.pojo.MoviesResult;
import com.app.square.data.di.DaggerDataComponent;
import com.app.square.data.networking.ApiRequest;
import io.reactivex.Observable;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

public class DataManager {

    private final String API_KEY = "3b0cdf4420a0f312a0be75652d464c56";

    @Inject ApiRequest apiRequest;

    public DataManager() {
        DaggerDataComponent.create().inject(this);
    }

    public Observable<MoviesResult> getMoviesList(int page) {
        return apiRequest.get().getDiscoverMovies(API_KEY, "en-US", "popularity.desc",
            "false","" + page);
    }
}
