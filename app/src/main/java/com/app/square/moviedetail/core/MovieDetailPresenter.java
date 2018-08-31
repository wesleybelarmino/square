package com.app.square.moviedetail.core;

import android.util.Log;
import com.app.square.data.DataManager;
import com.app.square.moviedetail.reviews.pojo.Review;
import com.app.square.moviedetail.reviews.pojo.ReviewsResult;
import com.app.square.moviedetail.trailers.pojo.Trailer;
import com.app.square.moviedetail.trailers.pojo.TrailersResult;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View mMovieDetailView;
    private DataManager dataManager;
    private CompositeDisposable subscriptionsTrailer;
    private List<Trailer> trailerList;
    private List<Review> reviewList;

    public MovieDetailPresenter(MovieDetailContract.View movieDetailView, DataManager dataManager,
        CompositeDisposable subs) {
        this.mMovieDetailView = movieDetailView;
        this.subscriptionsTrailer = subs;
        this.dataManager = dataManager;
        this.trailerList = new ArrayList<>();
        this.reviewList = new ArrayList<>();
    }

    @Override public void loadTrailers(int movieId) {
        Log.d("MovieDetailPresenter", "loadTrailers: " + movieId);
        subscriptionsTrailer.add(respondToTrailerClick());
        Observable<TrailersResult> trailersResultObservable =
            dataManager.getTrailersByMovie(movieId);

        trailersResultObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<TrailersResult>() {
                @Override public void onSubscribe(Disposable d) {
                    Log.d("MovieDetailPresenter", "onSubscribe");
                }

                @Override public void onNext(TrailersResult trailersResult) {
                    Log.d("MovieDetailPresenter", "onNext");
                    trailerList.addAll(trailersResult.getResults());
                }

                @Override public void onError(Throwable e) {
                    Log.d("MovieDetailPresenter", "onError: " + e.toString());
                }

                @Override public void onComplete() {
                    Log.d("MovieDetailPresenter", "onComplete");
                    mMovieDetailView.showTrailers(trailerList);
                }
            });
    }

    @Override public void loadReviews(int movieId) {
        Log.d("MovieDetailPresenter", "loadReviews: " + movieId);
        Observable<ReviewsResult> reviewsResultObservable =
            dataManager.getReviewsByMovie(movieId, 1);

        reviewsResultObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<ReviewsResult>() {
                @Override public void onSubscribe(Disposable d) {
                    Log.d("MovieDetailPresenter", "onSubscribe");

                }

                @Override public void onNext(ReviewsResult reviewsResult) {
                    Log.d("MovieDetailPresenter", "onNext");
                    if(reviewsResult != null && reviewsResult.getResults() != null){
                        reviewList.addAll(reviewsResult.getResults());
                    }

                }

                @Override public void onError(Throwable e) {
                    Log.d("MovieDetailPresenter", "onError: " + e.toString());

                }

                @Override public void onComplete() {
                    Log.d("MovieDetailPresenter", "onComplete: "+reviewList.size());
                    mMovieDetailView.showReviews(reviewList);

                }
            });
    }

    private Disposable respondToTrailerClick() {
        return mMovieDetailView.trailerClick().subscribe(new Consumer<Integer>() {
            @Override public void accept(Integer integer) throws Exception {
                Log.d("MovieDetailPresenter", "trailer: " + integer);
                mMovieDetailView.goToTrailer(trailerList.get(integer));
            }
        });
    }

    @Override public void onDestroy() {
        subscriptionsTrailer.clear();
        trailerList = null;
        reviewList = null;
    }
}
