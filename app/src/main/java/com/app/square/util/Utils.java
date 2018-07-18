package com.app.square.util;

import android.graphics.Color;

public class Utils {

    public static String getReleaseYear(String releaseDate){
        String[] splitReleaseDate = releaseDate.split("\\-");
        return splitReleaseDate[0];
    }


    /**
     * https://stackoverflow.com/questions/33072365/how-to-darken-a-given-color-int
     * @param color color provided
     * @param factor factor to make color darker
     * @return int as darker color
     */
    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
            Math.min(r, 255),
            Math.min(g, 255),
            Math.min(b, 255));
    }
}
