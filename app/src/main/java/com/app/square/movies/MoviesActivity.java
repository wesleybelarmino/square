package com.app.square.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.app.square.movies.list.EndlessRecyclerOnScrollListener;
import com.app.square.movies.list.MoviesAdapter;
import com.app.square.util.Constants;
import com.facebook.shimmer.ShimmerFrameLayout;
import io.reactivex.Observable;
import java.io.Serializable;
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
    @BindView(R.id.movies_drawer) DrawerLayout drawerLayout;
    @BindView(R.id.movies_navigation_view) NavigationView navigationView;

    @Inject MoviesContract.Presenter moviesPresenter;

    private MoviesAdapter moviesAdapter;
    private boolean isDrawerClosed = true;
    private int currentDrawerId;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //            .setAction("Action", null)
        //            .show();
        //    }
        //});

        initShimmer();
        moviesAdapter = new MoviesAdapter();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int i = moviesAdapter.getItemViewType(position);
                if (i == moviesAdapter.VIEW_TYPE_ITEM) {
                    return 1;
                } else if (i == moviesAdapter.VIEW_TYPE_PROGRESSBAR) {
                    return 2; //number of columns of the grid
                } else {
                    return -1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(moviesAdapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override public void onLoadMore() {
                loadMoreItems();
            }
        });

        drawerToggle();
        navigationAction();

        Log.d("main", "savedInstanceState: "+savedInstanceState);

        if(savedInstanceState == null){
            getSupportActionBar().setTitle(getString(R.string.most_popular));
            moviesPresenter.onCreate();

        }else{
            String discoverSortedBy = savedInstanceState.getString(Constants
                .MOVIES_SAVED_INSTANCE_DISCOVER_SORTED_BY_KEY);

            if(discoverSortedBy.equals(Constants.MOVIES_SORT_BY_MOST_POPULAR)){
                getSupportActionBar().setTitle(getString(R.string.most_popular));
                currentDrawerId = R.id.drawer_item_most_popular;
            }else{
                getSupportActionBar().setTitle(getString(R.string.best_rating));
                currentDrawerId = R.id.drawer_item_best_rating;
            }

            moviesPresenter.onCreateSavedInstance(discoverSortedBy, (List<Movie>)savedInstanceState.getSerializable(Constants
                .MOVIES_SAVED_INSTANCE_LIST_KEY));
        }



    }

    private void drawerToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle =
            new ActionBarDrawerToggle(this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */) {

                /** Called when a drawer has settled in a completely closed state. */
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    isDrawerClosed = true;
                }

                /** Called when a drawer has settled in a completely open state. */
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    isDrawerClosed = false;
                }
            };

        toolbar.setNavigationIcon(R.drawable.ic_menu);

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void navigationAction() {
        currentDrawerId = R.id.drawer_item_most_popular;
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    if (item.getItemId() != currentDrawerId) {
                        initShimmer();
                        moviesAdapter.clear();

                        if (item.isChecked()) {
                            item.setChecked(false);
                        } else {
                            item.setChecked(true);
                        }

                        switch (item.getItemId()) {
                            case R.id.drawer_item_most_popular:
                                moviesPresenter.changeListToDiscoverPopularity();
                                getSupportActionBar().setTitle(getString(R.string.most_popular));
                                break;

                            case R.id.drawer_item_best_rating:
                                moviesPresenter.changeListToDiscoverRating();
                                getSupportActionBar().setTitle(getString(R.string.best_rating));
                                break;
                        }

                        drawerLayout.closeDrawers();
                        currentDrawerId = item.getItemId();
                        return true;
                    } else {
                        return false;
                    }
                }
            });
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

    private void loadMoreItems() {
        moviesAdapter.enableFooter(true);
        moviesPresenter.loadNextPageMovieList();
    }


    @Override public void showMoviesList(List<Movie> movies) {
        moviesAdapter.addMovies(movies);
        moviesAdapter.enableFooter(false);
        stopShimmer();
    }

    @Override public Observable<Integer> itemClicks() {
        return moviesAdapter.observeClicks();
    }

    @Override public void goToHeroDetailsActivity(Movie movie, int position) {
        Log.d("main","goToHeroDetailsActivity()");
        Intent in = new Intent(this, MovieDetailActivity.class);
        in.putExtra("movie", (Serializable) movie);

        startActivity(in);

        //// Locate the ViewHolder for the clicked position.
        //RecyclerView.ViewHolder selectedViewHolder =
        //    recyclerView.findViewHolderForAdapterPosition(position);
        //if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
        //    return;
        //}
        //
        //ActivityOptionsCompat options = ActivityOptionsCompat.
        //    makeSceneTransitionAnimation(this,
        //        selectedViewHolder.itemView.findViewById(R.id.movies_list_item_poster),
        //        "movie_poster");
        //startActivity(in, options.toBundle());
    }

    @Override public void hasConnection() {
        connectionLayout.setVisibility(View.GONE);
        moviesPresenter.checkIfNeedRetry();
    }

    @Override public void loseConnection() {
        connectionLayout.setVisibility(View.VISIBLE);
    }

    @Override public void onBackPressed() {
        if (isDrawerClosed) {
            super.onBackPressed();
        } else {
            drawerLayout.closeDrawers();
        }
    }

    @Override protected void onSaveInstanceState(Bundle savedInstanceState) {

        String discoverSortedBy = (currentDrawerId == R.id.drawer_item_most_popular)? Constants
            .MOVIES_SORT_BY_MOST_POPULAR: Constants.MOVIES_SORT_BY_BEST_RATING;

        savedInstanceState.putSerializable(Constants.MOVIES_SAVED_INSTANCE_LIST_KEY, moviesAdapter.getList());
        savedInstanceState.putString(Constants.MOVIES_SAVED_INSTANCE_DISCOVER_SORTED_BY_KEY, discoverSortedBy);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        moviesPresenter.onDestroy();
    }
}
