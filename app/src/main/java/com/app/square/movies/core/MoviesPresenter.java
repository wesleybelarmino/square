package com.app.square.movies.core;

import android.util.Log;
import com.app.square.common.pojo.Movie;
import com.app.square.common.pojo.MoviesResult;
import com.app.square.data.DataManager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.reactivestreams.Subscription;

public class MoviesPresenter implements MoviesContract.Presenter {

    private MoviesContract.View moviesView;

    DataManager dataManager;
    private int currentPage;
    private int totalPages;
    private List<Movie> moviesList;

    CompositeDisposable subscriptions;


    public MoviesPresenter(MoviesContract.View view, DataManager dataManager, CompositeDisposable subs) {
        this.moviesView = view;
        this.dataManager = dataManager;
        this.moviesList = new ArrayList<>();
        this.totalPages = 1;
        this.subscriptions = subs;
    }

    @Override public void onCreate() {
        subscriptions.add(respondToClick());
    }

    @Override public void loadMoviesList() {
        currentPage = 1;
        moviesList.clear();
        loadMovieDiscover(currentPage);
    }

    @Override public void loadNextPageMovieList() {
        if(currentPage < totalPages){
            loadMovieDiscover(currentPage++);
        }
    }

    private void loadMovieDiscover(int page) {
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
                    moviesList.addAll(moviesResult.getResults());
                    totalPages = moviesResult.getTotalPages();
                    Log.d("presenter", "sizer: " + moviesList.size());
                }

                @Override public void onError(Throwable e) {
                    Log.d("presenter", "onError: " + e.toString());
                }

                @Override public void onComplete() {
                    Log.d("presenter", "onComplete");
                    moviesView.showMoviesList(moviesList);
                }
            });
    }


    private Disposable respondToClick() {
        return moviesView.itemClicks().subscribe(new Consumer<Integer>() {
            @Override public void accept(Integer integer) throws Exception {
                Log.d("presenter", "Item: "+integer);
            }
        });

    }

    @Override public void destroy() {
        subscriptions.clear();
    }
}
