package com.app.square.movies.di;

import com.app.square.movies.MoviesActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = { MoviesModule.class })
public interface MoviesComponent {
    void inject(MoviesActivity moviesActivity);
}
