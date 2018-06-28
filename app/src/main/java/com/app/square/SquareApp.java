package com.app.square;

import android.app.Application;
import android.content.Context;

public class SquareApp extends Application {

    private static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
