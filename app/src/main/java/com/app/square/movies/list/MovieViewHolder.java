package com.app.square.movies.list;

import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.app.square.R;
import com.app.square.common.palette.PaletteBitmap;
import com.app.square.common.palette.PaletteBitmapTranscoder;
import com.app.square.common.pojo.Movie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import io.reactivex.subjects.PublishSubject;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    View view;

    @BindView(R.id.movies_list_item_poster) ImageView poster;
    @BindView(R.id.movies_list_item_card) CardView cardView;
    @BindView(R.id.movies_list_item_overview) TextView overview;

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

    public void bind(Movie movie){
        overview.setText(movie.getOverview());
        String url = "https://image.tmdb.org/t/p/w500"+movie.getPosterPath();
        Glide.with(view.getContext())
            .load(url)
            .into(poster);

    }
}
