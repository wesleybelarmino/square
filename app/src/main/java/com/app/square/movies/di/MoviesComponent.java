package com.app.square.movies.di;

import com.app.square.movies.MoviesActivity;
import dagger.Component;

@Component(modules = MoviesModule.class)
public interface MoviesComponent {
  void inject(MoviesActivity moviesActivity);
}
