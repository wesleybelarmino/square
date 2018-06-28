package com.app.square.movies.core;

import android.util.Log;
import com.app.square.common.pojo.Movie;
import com.app.square.common.pojo.MoviesResult;
import com.app.square.data.DataManager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

public class MoviesPresenter implements MoviesContract.Presenter {

    private MoviesContract.View moviesView;

    DataManager dataManager;
    private int currentPage;
    private List<Movie> movies;

    public MoviesPresenter(MoviesContract.View view, DataManager dataManager) {
        this.moviesView = view;
        this.dataManager = dataManager;
        movies = new ArrayList<>();
    }

    @Override public void loadDiscoverMoviesList() {
        currentPage = 1;
        movies.clear();
        loadDiscoverMovies(currentPage);
    }

    @Override public void loadNextPageDicoverMovieList() {
        loadDiscoverMovies(currentPage++);
    }

    private void loadDiscoverMovies(int page) {
        Log.d("presenter", "loadDiscoverMovies :" + dataManager);
        Observable<MoviesResult> moviesResultObservable = dataManager.getMoviesList(page);
        moviesResultObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<MoviesResult>() {
                @Override public void onSubscribe(Disposable d) {
                    Log.d("presenter", "onSubscribe");
                }

                @Override public void onNext(MoviesResult moviesResult) {
                    Log.d("presenter", "onNext");
                    movies.addAll(moviesResult.getResults());
                    Log.d("presenter", "sizer: " + movies.size());
                }

                @Override public void onError(Throwable e) {
                    Log.d("presenter", "onError: " + e.toString());
                }

                @Override public void onComplete() {
                    Log.d("presenter", "onComplete");
                }
            });
    }

    @Override public void destroy() {

    }
}
