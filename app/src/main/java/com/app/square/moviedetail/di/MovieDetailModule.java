package com.app.square.moviedetail.di;

import com.app.square.data.DataManager;
import com.app.square.moviedetail.core.MovieDetailContract;
import com.app.square.moviedetail.core.MovieDetailPresenter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module public class MovieDetailModule {
    private MovieDetailContract.View movieDetailView;

    public MovieDetailModule(MovieDetailContract.View viewDetail) {
        this.movieDetailView = viewDetail;
    }

    @Provides MovieDetailContract.Presenter providesMovieDetailPresenter() {
        return new MovieDetailPresenter(movieDetailView, new DataManager(), new CompositeDisposable());
    }
}
