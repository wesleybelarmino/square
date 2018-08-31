package com.app.square.moviedetail.trailers.pojo;

import java.util.List;
import lombok.Data;

@Data
public class TrailersResult {
    private int id;
    private List<Trailer> results;
}
