package com.app.square.movies.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.app.square.R;
import com.app.square.common.base.BaseViewHolder;
import com.app.square.common.pojo.Movie;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final PublishSubject<Integer> itemClicks = PublishSubject.create();
    ArrayList<Movie> moviesList = new ArrayList<>();
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_PROGRESSBAR = 2;
    private boolean isFooterEnabled = true;


    int start = 0;

    public void addMovies(List<Movie> list){
        moviesList.clear();
        moviesList.addAll(list);
        notifyItemRangeChanged(start, moviesList.size() - 1);
        start = moviesList.size() - 1;
    }

    public Observable<Integer> observeClicks() {
        return itemClicks;
    }

    @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_PROGRESSBAR) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loader_item_layout, parent, false);
            return new ProgressViewHolder(view);

        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_list_item, parent, false);
            return new MovieViewHolder(view ,itemClicks);
        }
    }

    @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
        if(holder instanceof ProgressViewHolder){
            ((ProgressViewHolder)holder).bind(null);
        }else{
            Movie movie = moviesList.get(position);
            holder.bind(movie);
        }
    }

    @Override public int getItemCount() {
        return  (isFooterEnabled) ? moviesList.size() + 1 : moviesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (isFooterEnabled && position >= moviesList.size() ) ? VIEW_TYPE_PROGRESSBAR : VIEW_TYPE_ITEM;
    }

    public void enableFooter(boolean isEnabled){
        this.isFooterEnabled = isEnabled;
    }

    public void clear(){
        start = 0;
        moviesList.removeAll(moviesList);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getList(){
        return  moviesList;
    }


}
