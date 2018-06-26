package com.app.square.movies.di;

import com.app.square.movies.core.MoviesContract;
import com.app.square.movies.core.MoviesPresent;
import dagger.Module;
import dagger.Provides;

@Module
public class MoviesModule {
  private MoviesContract.View moviesView;

  public MoviesModule(MoviesContract.View view){
    this.moviesView = view;
  }

  @Provides
  MoviesContract.Presenter providesMoviesPresent(){ return  new MoviesPresent(this.moviesView);}

}
