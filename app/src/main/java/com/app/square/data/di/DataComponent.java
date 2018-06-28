package com.app.square.data.di;

import com.app.square.data.DataManager;
import dagger.Component;

@Component(modules = DataModule.class) public interface DataComponent {
    void inject(DataManager dataManager);
}
