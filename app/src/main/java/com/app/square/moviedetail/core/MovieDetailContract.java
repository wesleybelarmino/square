package com.app.square.moviedetail.core;

import com.app.square.common.base.BasePresenter;
import com.app.square.moviedetail.reviews.pojo.Review;
import com.app.square.moviedetail.trailers.pojo.Trailer;
import io.reactivex.Observable;
import java.util.List;

public interface MovieDetailContract {

    interface View {
        void showTrailers(List<Trailer> trailers);
        Observable<Integer> trailerClick();
        void goToTrailer(Trailer trailer);
        void showReviews(List<Review> reviews);

    }

    interface Presenter extends BasePresenter{
        void loadTrailers(int movieId);
        void loadReviews(int movieId);
        boolean isFavMovie(int movieId);
        void checkIfNeedRetry(int movieId);
    }
}
