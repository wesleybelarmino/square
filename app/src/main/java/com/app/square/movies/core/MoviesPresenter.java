package com.app.square.movies.core;

import android.util.Log;
import com.app.square.common.pojo.Movie;
import com.app.square.common.pojo.MoviesResult;
import com.app.square.data.DataManager;
import com.app.square.util.Constants;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoviesPresenter implements MoviesContract.Presenter {

    private MoviesContract.View moviesView;

    DataManager dataManager;
    private int currentPage;
    private int totalPages;
    private List<Movie> moviesList;
    private boolean notLoadContentByNetProblem = false;
    private String discoverSortedBy;

    CompositeDisposable subscriptions;

    public MoviesPresenter(MoviesContract.View view, DataManager dataManager,
        CompositeDisposable subs) {
        this.moviesView = view;
        this.dataManager = dataManager;
        this.moviesList = new ArrayList<>();
        this.totalPages = 1000;
        this.subscriptions = subs;
        this.currentPage = 1;
    }

    @Override public void onCreate() {
        subscriptions.add(respondToClick());
        discoverSortedBy = Constants.MOVIES_SORT_BY_MOST_POPULAR;
        loadMoviesDiscoverList();
    }

    @Override public void onCreateSavedInstance(String discoverSortedBy, List<Movie> list) {
        Log.d("presenter", "onCreateSavedInstance discoverSortedBy:" + discoverSortedBy);
        subscriptions.add(respondToClick());
        this.discoverSortedBy = discoverSortedBy;
        this.currentPage = list.size() / 20;
        moviesList.addAll(list);
        notLoadContentByNetProblem = false;
        moviesView.showMoviesList(moviesList);
    }

    private void loadMoviesDiscoverList() {
        currentPage = 1;
        moviesList.clear();
        loadMovieDiscover(currentPage);
    }

    @Override public void loadNextPageMovieList() {
        Log.d("presenter", "loadNextPageMovieList - currentPage: " + currentPage);
        Log.d("presenter", "loadNextPageMovieList - totalPages: " + totalPages);
        if (currentPage < totalPages) {
            loadMovieDiscover(++currentPage);
        }
    }

    @Override public void checkIfNeedRetry() {
        if (notLoadContentByNetProblem) {
            if (currentPage <= 1) {
                loadMoviesDiscoverList();
            } else {
                currentPage--;
                loadNextPageMovieList();
            }
        }
    }

    @Override public void changeListToDiscoverRating() {
        discoverSortedBy = Constants.MOVIES_SORT_BY_BEST_RATING;
        loadMoviesDiscoverList();
    }

    @Override public void changeListToDiscoverPopularity() {
        discoverSortedBy = Constants.MOVIES_SORT_BY_MOST_POPULAR;
        loadMoviesDiscoverList();
    }

    @Override public void changeListToFavMovies() {
        moviesList.removeAll(moviesList);
        dataManager.getDaoFavMovie()
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<List<Movie>>() {
                @Override public void accept(List<Movie> movies) throws Exception {
                    moviesList.addAll(movies);
                    moviesView.showMoviesList(moviesList);
                }
            });
    }

    private void loadMovieDiscover(int page) {
        Log.d("presenter", "loadMovieDiscover page:" + page);
        Log.d("presenter", "loadDiscoverMovies :" + dataManager);
        Log.d("presenter", "loadDiscoverMovies discoverSortedBy:" + discoverSortedBy);
        Observable<MoviesResult> moviesResultObservable =
            dataManager.getMoviesList(discoverSortedBy, page);
        moviesResultObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<MoviesResult>() {
                @Override public void onSubscribe(Disposable d) {
                    Log.d("presenter", "onSubscribe");
                }

                @Override public void onNext(MoviesResult moviesResult) {
                    Log.d("presenter", "onNext");
                    moviesList.addAll(moviesResult.getResults());
                    totalPages = moviesResult.getTotalPages();
                    Log.d("presenter", "sizer: " + moviesList.size());
                }

                @Override public void onError(Throwable e) {
                    Log.d("presenter", "onError: " + e.toString());
                    if (e instanceof IOException) {
                        notLoadContentByNetProblem = true;
                    }
                }

                @Override public void onComplete() {
                    Log.d("presenter", "onComplete");
                    moviesView.showMoviesList(moviesList);
                    notLoadContentByNetProblem = false;
                }
            });
    }

    private Disposable respondToClick() {
        return moviesView.itemClicks().subscribe(new Consumer<Integer>() {
            @Override public void accept(Integer integer) throws Exception {
                Log.d("presenter", "Item: " + integer);
                moviesView.goToHeroDetailsActivity(moviesList.get(integer), integer);
            }
        });
    }

    @Override public void onDestroy() {
        subscriptions.clear();
    }
}
