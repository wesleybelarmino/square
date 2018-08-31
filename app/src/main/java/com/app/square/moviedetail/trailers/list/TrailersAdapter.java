package com.app.square.moviedetail.trailers.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.square.R;
import com.app.square.common.base.BaseViewHolder;
import com.app.square.moviedetail.trailers.pojo.Trailer;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final PublishSubject<Integer> itemClicks = PublishSubject.create();
    private ArrayList<Trailer> trailersList = new ArrayList<>();

    public void addTrailers(List<Trailer> trailers) {
        trailersList.clear();
        trailersList.addAll(trailers);
    }

    public Observable<Integer> observeClicks() {
        return itemClicks;
    }

    @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.movies_detail_item_trailer, parent, false);
        return new TrailerViewHolder(view, itemClicks);
    }

    @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
        Trailer trailer = trailersList.get(position);
        holder.bind(trailer);
    }

    public ArrayList<Trailer> getList() {
        return trailersList;
    }

    @Override public int getItemCount() {
        return trailersList.size();
    }
}
