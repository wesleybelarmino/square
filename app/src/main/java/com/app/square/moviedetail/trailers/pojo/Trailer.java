package com.app.square.moviedetail.trailers.pojo;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.Data;

@Data public class Trailer implements Serializable {
    private String id;
    @SerializedName("iso_639_1") private String iso6391;
    @SerializedName("iso_3166_1") private String iso31661;
    private String key;
    private String name;
    private String site;
    private String size;
    private String type;
}
