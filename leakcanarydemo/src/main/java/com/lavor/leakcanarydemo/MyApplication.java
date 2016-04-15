package com.lavor.leakcanarydemo;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by shitian on 2016-04-10.
 */
public class MyApplication extends Application{
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher=LeakCanary.install(this);
    }
    public static RefWatcher getRefWatcher(Context context){

        return ((MyApplication)context.getApplicationContext()).refWatcher;
    }
}
