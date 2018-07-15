package com.app.square.movies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.app.square.R;
import com.app.square.common.base.BaseActivity;
import com.app.square.common.pojo.Movie;
import com.app.square.movies.core.MoviesContract;
import com.app.square.movies.di.DaggerMoviesComponent;
import com.app.square.movies.di.MoviesModule;
import com.app.square.movies.list.MoviesAdapter;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

public class MoviesActivity extends BaseActivity implements MoviesContract.View {

    @BindView(R.id.toolbar) Toolbar toolbar;
    //@BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.movies_list) RecyclerView recyclerView;

    @Inject MoviesContract.Presenter moviesPresenter;

    private MoviesAdapter moviesAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        //ButterKnife
        ButterKnife.bind(this);

        //dagger
        DaggerMoviesComponent.builder().moviesModule(new MoviesModule(this)).build().
            inject(this);

        //action bar
        setSupportActionBar(toolbar);


        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //            .setAction("Action", null)
        //            .show();
        //    }
        //});

        moviesAdapter = new MoviesAdapter();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(moviesAdapter);

        moviesPresenter.onCreate();
        moviesPresenter.loadMoviesList();

    }

    @Override public void hasConnection() {

    }

    @Override public void loseConnection() {

    }

    @Override public void showMoviesList(List<Movie> movies) {
        moviesAdapter.addMovies(movies);

    }

    @Override public Observable<Integer> itemClicks()
    {
        return moviesAdapter.observeClicks();
    }
}
