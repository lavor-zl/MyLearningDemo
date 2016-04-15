package com.example.shitian.sugarormdemo;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by shitian on 2015-12-26.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
