package com.app.square.movies.core;

public class MoviesPresent implements MoviesContract.Presenter {

  private MoviesContract.View moviesView;

  public MoviesPresent(MoviesContract.View view){
    this.moviesView = view;
  }

  @Override public void destroy() {

  }
}
