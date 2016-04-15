package com.example.shitian.dagger2demo;

import javax.inject.Inject;

import dagger.Provides;

/**
 * Created by shitian on 2015-12-06.
 */
public class UserTest {
    @Named("user")
    @Inject
    User user;
    @Inject
    public UserTest(){

    }
}
