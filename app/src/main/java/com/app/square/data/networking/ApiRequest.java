package com.app.square.data.networking;

import com.app.square.data.networking.interfaces.IApiRequests;
import com.app.square.data.networking.interfaces.IApiServiceGet;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequest implements IApiRequests {

    private final String BASE_URL = "https://api.themoviedb.org/3/";
    private Retrofit retrofit;

    public ApiRequest() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    }

    @Override public IApiServiceGet get() {
        return retrofit.create(IApiServiceGet.class);
    }
}
