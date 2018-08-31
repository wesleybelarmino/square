package com.app.square.moviedetail.trailers.list;

import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.app.square.R;
import com.app.square.common.base.BaseViewHolder;
import com.app.square.moviedetail.trailers.pojo.Trailer;
import com.app.square.util.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import io.reactivex.subjects.PublishSubject;

public class TrailerViewHolder extends BaseViewHolder {

    View view;

    @BindView(R.id.trailer_thumbnail) ImageView thumbnail;

    public TrailerViewHolder(View itemView, final PublishSubject<Integer> clickSubject) {
        super(itemView);
        this.view = itemView;
        ButterKnife.bind(this, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                clickSubject.onNext(getAdapterPosition());
            }
        });

    }

    @Override public void bind(Object object) {
        Trailer trailer = (Trailer) object;
        if(trailer.getSite().toUpperCase().equals("YOUTUBE")){
            String url = Constants.BASE_YOUTUBE_URL_IMAGE+trailer.getKey()+"/mqdefault.jpg";
            Glide.with(view.getContext())
                .load(url)
                .into(thumbnail);
        }
    }
}
