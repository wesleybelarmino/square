package com.app.square.movies.list;

import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.app.square.R;
import com.app.square.common.base.BaseViewHolder;

public class ProgressViewHolder extends BaseViewHolder {

    @BindView(R.id.list_progressbar) ProgressBar progressBar;

    public ProgressViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override public void bind(Object object) {
        progressBar.setIndeterminate(true);
    }
}
