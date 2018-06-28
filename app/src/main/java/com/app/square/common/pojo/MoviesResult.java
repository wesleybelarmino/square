package com.app.square.common.pojo;

import java.util.List;

public class MoviesResult {

    private int page;
    private int total_results;
    private int total_pages;
    private List<Movie> results;

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }
}
