package com.example.shitian.rxandroiddemo;

import android.content.Intent;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by shitian on 2015-12-07.
 */
public class LiftAllTransformer implements Observable.Transformer<Integer,String> {
    @Override
    public Observable<String> call(Observable<Integer> integerObservable) {
        return integerObservable
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer.toString();
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s.toString();
                    }
                });
    }
}
