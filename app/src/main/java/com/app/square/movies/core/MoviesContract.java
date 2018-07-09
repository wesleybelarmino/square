package com.app.square.movies.core;

import com.app.square.common.base.BasePresenter;
import com.app.square.common.pojo.Movie;
import io.reactivex.Observable;
import java.util.List;

public interface MoviesContract {
    interface View {
        public void showMoviesList(List<Movie> movies);
        public Observable<Integer> itemClicks();
    }

    interface Presenter extends BasePresenter {

        public void onCreate();

        public void loadMoviesList();

        public void loadNextPageMovieList();
    }
}
