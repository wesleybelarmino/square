package com.app.square.data.di;

import com.app.square.data.networking.ApiRequest;
import dagger.Module;
import dagger.Provides;

@Module public class DataModule {

    @Provides ApiRequest providesApiRequest() {
        return new ApiRequest();
    }
}
