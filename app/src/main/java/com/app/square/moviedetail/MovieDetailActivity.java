package com.app.square.moviedetail;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.app.square.R;
import com.app.square.common.base.BaseActivity;
import com.app.square.common.palette.BlurTransformation;
import com.app.square.common.palette.PaletteBitmap;
import com.app.square.common.palette.PaletteBitmapTranscoder;
import com.app.square.common.pojo.Movie;
import com.app.square.moviedetail.core.MovieDetailContract;
import com.app.square.moviedetail.di.DaggerMovieDetailComponent;
import com.app.square.moviedetail.di.MovieDetailModule;
import com.app.square.moviedetail.reviews.list.ReviewsAdapter;
import com.app.square.moviedetail.reviews.pojo.Review;
import com.app.square.moviedetail.trailers.list.TrailersAdapter;
import com.app.square.moviedetail.trailers.pojo.Trailer;
import com.app.square.util.Constants;
import com.app.square.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View {

    @BindView(R.id.detailToolbar) Toolbar toolbar;
    @BindView(R.id.movie_detail_bg_poster) ImageView bgPoster;
    @BindView(R.id.movie_detail_poster) ImageView poster;
    @BindView(R.id.movie_detail_title) TextView title;
    @BindView(R.id.movie_detail_average) TextView averge;
    @BindView(R.id.movie_detail_average_ratingBar) RatingBar avergeRating;
    @BindView(R.id.movie_detail_release_date) TextView releaseDate;
    @BindView(R.id.movie_detail_overview) TextView overview;
    @BindView(R.id.movies_detail_trailers_list) RecyclerView recyclerViewTrailers;
    @BindView(R.id.movies_detail_reviews_list) RecyclerView recyclerViewReviews;

    @Inject MovieDetailContract.Presenter movieDetailPresenter;

    private Movie mMovie;
    int posterPaletteColor;
    private String posterUrl;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportPostponeEnterTransition();

        setContentView(R.layout.activity_movie_detail);

        //ButterKnife
        ButterKnife.bind(this);

        //dagger
        DaggerMovieDetailComponent.builder()
            .movieDetailModule(new MovieDetailModule(this))
            .build()
            .inject(this);

        //action bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });

        mMovie = (Movie) getIntent().getExtras().get("movie");

        //trailers
        trailersAdapter = new TrailersAdapter();
        LinearLayoutManager trailersLayoutManager =
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTrailers.setLayoutManager(trailersLayoutManager);
        recyclerViewTrailers.setAdapter(trailersAdapter);

        //reviews
        reviewsAdapter = new ReviewsAdapter();
        LinearLayoutManager reviewsLayoutManager = new LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false);
        recyclerViewReviews.setLayoutManager(reviewsLayoutManager);
        recyclerViewReviews.setAdapter(reviewsAdapter);
        recyclerViewReviews.setNestedScrollingEnabled(false);

        showMovieInfo();
        showTrailers();
        showReviews();
    }

    private void showMovieInfo() {

        if (mMovie.getPosterPath() != null) {
            posterUrl = Constants.BASE_URL_IMAGE + "/w500" + mMovie.getPosterPath();

            posterPaletteColor = R.color.colorPrimary;

            Glide.with(this)
                .load(posterUrl)
                .asBitmap()
                .transcode(new PaletteBitmapTranscoder(this), PaletteBitmap.class)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new ImageViewTarget<PaletteBitmap>(poster) {
                    @Override protected void setResource(PaletteBitmap resource) {
                        super.view.setImageBitmap(resource.bitmap);
                        Palette p = resource.palette;
                        posterPaletteColor = p.getMutedColor(
                            ContextCompat.getColor(MovieDetailActivity.this, R.color.colorPrimary));

                        //set color action bar
                        toolbar.setBackground(
                            new ColorDrawable(Utils.manipulateColor(posterPaletteColor, 0.62f)));

                        //set color status bar
                        getWindow().setStatusBarColor(
                            Utils.manipulateColor(posterPaletteColor, 0.32f));

                        supportStartPostponedEnterTransition();
                    }
                });

            //String posterBgUrl = Constants.BASE_URL_IMAGE+"/w500" + mMovie.getBackdropPath();
            Glide.with(MovieDetailActivity.this)
                .load(posterUrl)
                .asBitmap()
                .transform(new BlurTransformation(MovieDetailActivity.this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(bgPoster);
        } else {
            supportStartPostponedEnterTransition();
        }

        getSupportActionBar().setTitle(mMovie.getTitle());
        title.setText(mMovie.getOriginalTitle());
        averge.setText(mMovie.getVoteAverage() + "");
        avergeRating.setRating(mMovie.getVoteAverage() / 2);
        releaseDate.setText(mMovie.getReleaseDate());
        overview.setText(mMovie.getOverview());

    }

    private void showTrailers() {
        movieDetailPresenter.loadTrailers(mMovie.getId());
    }

    private void showReviews() {
        movieDetailPresenter.loadReviews(mMovie.getId());
    }

    @Override public void hasConnection() {

    }

    @Override public void loseConnection() {

    }

    @Override public void showTrailers(List<Trailer> trailers) {
        if (trailers.size() > 0) {
            recyclerViewTrailers.setVisibility(View.VISIBLE);
            trailersAdapter.addTrailers(trailers);
            trailersAdapter.notifyDataSetChanged();
        }
    }

    @Override public Observable<Integer> trailerClick() {
        return trailersAdapter.observeClicks();
    }

    @Override public void goToTrailer(Trailer trailer) {
        if (trailer.getSite().toUpperCase().equals("YOUTUBE")) {
            Intent intent =
                new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));

            // Check if the youtube app exists on the device
            if (intent.resolveActivity(getPackageManager()) == null) {
                // If the youtube app doesn't exist, then use the browser
                intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
            }

            startActivity(intent);
        }
    }

    @Override public void showReviews(List<Review> reviews) {
        if (reviews.size() > 0){
            recyclerViewReviews.setVisibility(View.VISIBLE);
            reviewsAdapter.addReviews(reviews);
            reviewsAdapter.notifyDataSetChanged();
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        movieDetailPresenter.onDestroy();
    }

}
