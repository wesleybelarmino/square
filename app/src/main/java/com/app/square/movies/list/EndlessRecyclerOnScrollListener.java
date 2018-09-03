package com.app.square.movies.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true;
        // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 6;
        // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        //Log.d("main","onScrolled():totalItemCount: "+totalItemCount);
        //Log.d("main","onScrolled():previousTotal: "+previousTotal);
        //
        if (loading) {
            //Log.d("main","onScrolled():loading");
            if (totalItemCount > previousTotal) {
                //Log.d("main","totalItemCount > previousTotal");
                loading = false;
                previousTotal = totalItemCount;
                //Log.d("main","onScrolled():previousTotal = totalItemCount");
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem
            + visibleThreshold)) {
            // End has been reached

            //Log.d("main","onScrolled():onLoadMore()");
            onLoadMore();

            loading = true;
        }
    }

    public void resetpreviousTotal(){
        previousTotal = 0;
    }

    public abstract void onLoadMore();
}