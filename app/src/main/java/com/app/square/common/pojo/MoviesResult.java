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

    public int getTotalResults() {
        return total_results;
    }

    public int getTotalPages() {
        return total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }
}
