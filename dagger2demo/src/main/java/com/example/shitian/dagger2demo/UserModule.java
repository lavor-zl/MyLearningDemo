package com.example.shitian.dagger2demo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shitian on 2015-12-06.
 */
@Module
public class UserModule {
    @Provides
    @Named("user")
    User provideUser() {
        User user=new User(0);
        return user;
    }
//    @Provides
//    @Named("userTest")
//    UserTest provideUserTest(UserTest userTest){
//        return userTest;
//    }
    @Provides
    @Named("coffce")
    Coffce provideCoffce(Coffce coffce){
        return coffce;
    }
}