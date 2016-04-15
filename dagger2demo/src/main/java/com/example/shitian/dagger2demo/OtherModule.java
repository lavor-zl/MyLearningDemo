package com.example.shitian.dagger2demo;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

/**
 * Created by shitian on 2016-04-10.
 */
@Module
public class OtherModule {
    @Provides
    @Named("userTest")
    UserTest provideUserTest(UserTest userTest){
        return userTest;
    }
}
