package com.example.shitian.dagger2demo;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by shitian on 2015-12-07.
 */

@Component(modules={UserModule.class,OtherModule.class})
public interface UserComponent {
    @Named("user")
    User getUser();
    void inject(Dagger2MainActivity dagger2MainActivity);
}
