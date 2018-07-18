package com.app.square.moviedetail.di;

import com.app.square.moviedetail.MovieDetailActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = { MovieDetailModule.class })
public interface MovieDetailComponent {
    void inject(MovieDetailActivity movieDetailActivity);
}
