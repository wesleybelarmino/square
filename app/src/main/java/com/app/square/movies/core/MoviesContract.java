package com.app.square.movies.core;

import com.app.square.common.base.BasePresenter;
import com.app.square.common.pojo.Movie;
import io.reactivex.Observable;
import java.util.List;

public interface MoviesContract {
    interface View {
        void showMoviesList(List<Movie> movies);
        Observable<Integer> itemClicks();
        void goToHeroDetailsActivity(Movie movie, int position);
    }

    interface Presenter extends BasePresenter {
        void onCreate();
        void onCreateSavedInstance(String discoverSortedBy, List<Movie> list);
        void loadNextPageMovieList();
        void checkIfNeedRetry();
        void changeListToDiscoverRating();
        void changeListToDiscoverPopularity();
        void changeListToFavMovies();
    }
}
