package com.example.shitian.rxandroiddemo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.joins.JoinObserver;
import rx.joins.Pattern2;
import rx.joins.Plan0;
import rx.observables.JoinObservable;

/**
 * Created by shitian on 2016-04-14.
 */
public class RxJavaKnowledgeMerges {
    private List<String> list;
    private List<String> list2;
    private long temp;

    public RxJavaKnowledgeMerges() {
        init();
    }

    public void init() {
        list = new ArrayList<>();
        list.add("hello");
        list.add("list");
        list.add("lavor");
        list.add("hi");
        list2 = new ArrayList<>();
        list.add("lavor");
        list.add("hi");
        list.add("hello");
        list.add("list");
    }

    /**
     * 在“异步的世界”中经常会创建这样的场景，我们有多个来源但是又只想有一个结果：多输入，单输出。
     * RxJava的merge()方法将帮助你把两个甚至更多的Observables合并到他们发射的数据项里
     */
    public void merge() {
        Observable<String> observable = Observable.from(list);
        Observable<String> observable2 = Observable.from(list2);
        Observable<String> mergedObserbable = Observable.merge(observable, observable2);
        mergedObserbable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });

    }

    /**
     * 从多个Observables接收数据，处理它们，然后将它们合并成一个新的可观测序列来使用。
     * RxJava有一个特殊的方法可以完成：zip()合并两个或者多个Observables发射出的数据项
     * 与merge的区别：
     * merge是将相同类型的观察序列合并
     * zip是将多种观察序列（类型可以相同可以不同）合并成另一种类型的观察序列
     */
    public void zip() {
        Observable<String> observable = Observable.from(list);
        Observable<String> observable2 = Observable.from(list2);
        Observable.zip(observable, observable2, new Func2<String, String, Object>() {
            @Override
            public Object call(String s, String s2) {
                return null;
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {

            }
        });

    }

    /**
     * zip()和merge()方法作用在发射数据的范畴内，在决定如何操作值之前有些场景我们需要考虑时间的。
     * RxJava的join()函数基于时间窗口将两个Observables发射的数据结合在一起。
     * join方法的用法如下：
     * observableA.join(observableB,
     * observableA产生结果生命周期控制函数，
     * observableB产生结果生命周期控制函数，
     * observableA产生的结果与observableB产生的结果的合并规则）
     */
    public void join() {
        Observable<String> sequence = Observable.interval(1, TimeUnit.SECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long aLong) {
                long position = aLong;
                return list.get((int) position);
            }
        }).take(4);
        //Interval操作符返回一个Observable，它按固定的时间间隔发射一个无限递增的整数序列。
        Observable<Long> tictoc = Observable.interval(1, TimeUnit.SECONDS).take(4);
        sequence.join(tictoc, new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                //使Observable延迟30毫秒执行
//                System.out.println("String:"+s);
                return Observable.just(s).delay(30, TimeUnit.MILLISECONDS);
            }
        }, new Func1<Long, Observable<String>>() {
            @Override
            public Observable<String> call(Long aLong) {
//                System.out.println("Long:"+aLong);
                return Observable.just(aLong + "").delay(30, TimeUnit.MILLISECONDS);
            }
        }, new Func2<String, Long, String>() {
            @Override
            public String call(String s, Long aLong) {
                return s + aLong;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);

            }
        });

    }

    /**
     * RxJava的combineLatest()函数有点像zip()函数的特殊形式。
     * 正如我们已经学习的，zip()作用于最近未打包的两个Observables。
     * 相反，combineLatest()作用于最近发射的数据项
     */
    public void combineLasted() {
        Observable<String> sequence = Observable.interval(1500, TimeUnit.MILLISECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long aLong) {
                long position = aLong;
                return list.get((int) position);
            }
        }).take(4);
        //Interval操作符返回一个Observable，它按固定的时间间隔发射一个无限递增的整数序列。
        Observable<Long> tictoc = Observable.interval(1000, TimeUnit.MILLISECONDS).take(5);
        Observable.combineLatest(sequence, tictoc, new Func2<String, Long, String>() {
            @Override
            public String call(String s, Long aLong) {
                return s + aLong;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }

    /**
     * 在将来还有一些zip()满足不了的场景。如复杂的架构，或者是仅仅为了个人爱好，
     * 你可以使用And/Then/When解决方案。(它们属于rxjava-joins模块，不是核心RxJava包的一部分。)
     * 它们在RxJava的joins包下，使用Pattern和Plan作为中介，将发射的数据集合并到一起。
     */
    public void andThenWhen() {
        Observable<String> sequence = Observable.interval(1500, TimeUnit.MILLISECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long aLong) {
                long position = aLong;
                return list.get((int) position);
            }
        }).take(5);
        //Interval操作符返回一个Observable，它按固定的时间间隔发射一个无限递增的整数序列。
        Observable<Long> tictoc = Observable.interval(1000, TimeUnit.MILLISECONDS).take(5);
        //现在我们用and()连接源Observable和第二个Observable。
        Pattern2<String, Long> pattern = JoinObservable.from(sequence).and(tictoc);
        //我们创建一个Plan对象:"我们有两个发射数据的Observables,then()是做什么的？"
        Plan0<String> plan = pattern.then(new Func2<String, Long, String>() {
            @Override
            public String call(String s, Long aLong) {
                return s + aLong;
            }
        });
        //现在我们有了一个Plan对象并且当plan发生时我们可以决定接下来发生的事情。
        JoinObservable.when(plan).toObservable().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }

    /**
     * switch：在RxJava中是switchOnNext，因为switch是Java关键字
     * 将一个发射多个Observables的Observable转换成另一个单独的Observable，
     * 后者发射那些Observables最近发射的数据项
     */
    public void switchFunc() {
        Observable<String> observable = Observable.from(list);
        observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
        Observable<String> observable2 = Observable.from(list2);
        List<Observable<String>> observableList = new ArrayList<>();
        observableList.add(observable);
        observableList.add(observable2);
        Observable<Observable<String>> observables = Observable.from(observableList);
        Observable.switchOnNext(observables).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });

    }

    /**
     * RxJava的startWith()是concat()的对应部分。正如concat()向发射数据的Observable追加数据那样，
     * 在Observable开始发射他们的数据之前， startWith()通过传递一个参数来先发射一个数据序列。
     */
    public void startWith(){
        Observable.from(list).startWith(list2).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });

    }
}
