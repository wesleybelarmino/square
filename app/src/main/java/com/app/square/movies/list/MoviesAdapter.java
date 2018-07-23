package com.app.square.movies.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.square.R;
import com.app.square.common.pojo.Movie;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final PublishSubject<Integer> itemClicks = PublishSubject.create();
    ArrayList<Movie> moviesList = new ArrayList<>();

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

    @Override public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_list_item, parent, false);
        return new MovieViewHolder(view ,itemClicks);
    }

    @Override public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.bind(movie);
    }

    @Override public int getItemCount() {
        return moviesList.size();
    }

    public void clear(){
        start = 0;
        moviesList.clear();
        notifyDataSetChanged();
    }
}
