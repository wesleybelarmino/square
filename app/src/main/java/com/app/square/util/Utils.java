package com.app.square.util;

public class Utils {

    public static String getReleaseYear(String releaseDate){
        String[] splitReleaseDate = releaseDate.split("\\-");
        return splitReleaseDate[0];
    }
}
