package com.app.square.moviedetail.core;

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View mMovieDetailView;


    public MovieDetailPresenter(MovieDetailContract.View movieDetailView){
        this.mMovieDetailView = movieDetailView;
    }

    @Override public void onDestroy() {

    }
}
