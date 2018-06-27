package com.app.square.networking;

import com.app.square.networking.interfaces.IApiRequests;
import com.app.square.networking.interfaces.IApiServiceGet;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequest implements IApiRequests {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private  Retrofit retrofit;

    public ApiRequest(){
        retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    }

    @Override public IApiServiceGet get() {
        return retrofit.create(IApiServiceGet.class);
    }
}
