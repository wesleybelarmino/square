package com.app.square.moviedetail.reviews.list;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.app.square.R;
import com.app.square.common.base.BaseViewHolder;
import com.app.square.moviedetail.reviews.pojo.Review;

public class ReviewViewHolder extends BaseViewHolder {
    @BindView(R.id.movies_detail_review_item_author) TextView author;
    @BindView(R.id.movies_detail_review_item_text) TextView content;

    View view;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        ButterKnife.bind(this, view);
    }

    @Override public void bind(Object object) {
        Review review = (Review) object;
        author.setText(review.getAuthor());
        content.setText(review.getContent());
    }
}
