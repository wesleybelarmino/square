package com.app.square.movies;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.app.square.R;
import com.app.square.common.base.BaseActivity;
import com.app.square.movies.core.MoviesContract;
import com.app.square.movies.di.DaggerMoviesComponent;
import com.app.square.movies.di.MoviesModule;
import javax.inject.Inject;

public class MoviesActivity extends BaseActivity implements MoviesContract.View{

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.fab) FloatingActionButton fab;

  @Inject
  MoviesContract.Presenter moviesPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movies);

    //ButterKnife
    ButterKnife.bind(this);

    //dagger
    DaggerMoviesComponent.builder()
        .moviesModule(new MoviesModule(this))
        .build()
        .inject(this);

    //action bar
    setSupportActionBar(toolbar);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
      }
    });




  }

  @Override public void hasConnection() {

  }

  @Override public void loseConnection() {

  }
}
