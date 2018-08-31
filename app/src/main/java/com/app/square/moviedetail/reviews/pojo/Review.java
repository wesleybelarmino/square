package com.app.square.moviedetail.reviews.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class Review implements Serializable {
    private String author;
    private String content;
    private String id;
    private String url;
}
