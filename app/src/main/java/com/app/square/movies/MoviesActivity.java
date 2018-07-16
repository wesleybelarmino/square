package com.app.square.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.app.square.R;
import com.app.square.common.base.BaseActivity;
import com.app.square.common.pojo.Movie;
import com.app.square.moviedetail.MovieDetailActivity;
import com.app.square.movies.core.MoviesContract;
import com.app.square.movies.di.DaggerMoviesComponent;
import com.app.square.movies.di.MoviesModule;
import com.app.square.movies.list.MoviesAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

public class MoviesActivity extends BaseActivity implements MoviesContract.View {

    @BindView(R.id.toolbar) Toolbar toolbar;
    //@BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.movies_list) RecyclerView recyclerView;
    @BindView(R.id.movies_shimmer_content) LinearLayout shimmerContent;
    @BindView(R.id.movies_shimmer_item_1) ShimmerFrameLayout shimmerItem1;
    @BindView(R.id.movies_shimmer_item_2) ShimmerFrameLayout shimmerItem2;
    @BindView(R.id.movies_shimmer_item_3) ShimmerFrameLayout shimmerItem3;
    @BindView(R.id.movies_shimmer_item_4) ShimmerFrameLayout shimmerItem4;
    @BindView(R.id.movies_list_connection_info) RelativeLayout connectionLayout;

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

        initShimmer();

        moviesAdapter = new MoviesAdapter();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(moviesAdapter);

        moviesPresenter.onCreate();
    }

    private void initShimmer() {
        recyclerView.setVisibility(View.GONE);
        shimmerContent.setVisibility(View.VISIBLE);
        shimmerItem1.startShimmer();
        shimmerItem2.startShimmer();
        shimmerItem3.startShimmer();
        shimmerItem4.startShimmer();
    }

    private void stopShimmer() {
        shimmerItem1.stopShimmer();
        shimmerItem2.stopShimmer();
        shimmerItem3.stopShimmer();
        shimmerItem4.stopShimmer();
        shimmerContent.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override public void hasConnection() {
        connectionLayout.setVisibility(View.GONE);
        moviesPresenter.checkIfNeedRetry();
    }

    @Override public void loseConnection() {
        connectionLayout.setVisibility(View.VISIBLE);
    }

    @Override public void showMoviesList(List<Movie> movies) {
        moviesAdapter.addMovies(movies);
        stopShimmer();
    }

    @Override public Observable<Integer> itemClicks() {
        return moviesAdapter.observeClicks();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        moviesPresenter.onDestroy();
    }

    public void goToHeroDetailsActivity(Movie movie) {

        //Intent in = new Intent(this, MovieDetailActivity.class);
        //in.putExtra("hero", (Serializable) movie);
        //startActivity(in);

    }
}
