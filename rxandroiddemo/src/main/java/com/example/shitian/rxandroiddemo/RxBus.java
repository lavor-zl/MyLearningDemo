package com.example.shitian.rxandroiddemo;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by shitian on 2015-12-08.
 */
public class RxBus {
    //创建单例类
    public static RxBus _rxBus=new RxBus();
    private RxBus(){

    }
    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return _bus;
    }
}
