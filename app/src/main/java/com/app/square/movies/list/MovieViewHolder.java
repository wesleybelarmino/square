package com.app.square.movies.list;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.app.square.R;
import com.app.square.common.base.BaseViewHolder;
import com.app.square.common.pojo.Movie;
import com.app.square.util.Constants;
import com.app.square.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import io.reactivex.subjects.PublishSubject;

public class MovieViewHolder extends BaseViewHolder {

    View view;

    @BindView(R.id.movies_list_item_poster) ImageView poster;
    @BindView(R.id.movies_list_item_card) CardView cardView;
    @BindView(R.id.movies_list_item_year) TextView year;
    @BindView(R.id.movies_list_item_rating) TextView rating;
    @BindView(R.id.movies_list_item_ratingBar) RatingBar ratingBar;
    @BindView(R.id.movies_list_item_title) TextView title;

    public MovieViewHolder(View itemView, final PublishSubject<Integer> clickSubject) {
        super(itemView);
        this.view = itemView;
        ButterKnife.bind(this, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                clickSubject.onNext(getAdapterPosition());
            }
        });
        poster.setClipToOutline(true);
    }

    @Override public void bind(Object object) {
        Movie movie = (Movie) object;
        title.setText(movie.getTitle());
        rating.setText(movie.getVoteAverage()+"");
        ratingBar.setRating(movie.getVoteAverage()/2);
        year.setText(Utils.getReleaseYear(movie.getReleaseDate()));
        String url = Constants.BASE_URL_IMAGE+"/w500" + movie.getPosterPath();
        Glide.with(view.getContext())
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(poster);

    }
}
