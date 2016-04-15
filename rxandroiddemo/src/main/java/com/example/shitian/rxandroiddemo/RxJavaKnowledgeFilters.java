package com.example.shitian.rxandroiddemo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by shitian on 2016-04-14.
 */
public class RxJavaKnowledgeFilters {
    private List<String> list;//原始的list
    private List<String> filteredList;//过滤后的list
    private List<String> takedList;//tack操作后的list
    public RxJavaKnowledgeFilters() {
        init();
    }

    public void init(){
        list=new ArrayList<>();
        list.add("hello");
        list.add("lavor");
        list.add("list");
        list.add("hi");
        filteredList=new ArrayList<>();
        takedList=new ArrayList<>();
    }

    /**
     * RxJava让我们使用filter()方法来过滤我们观测序列中不想要的值
     * 过滤list中不想要的字符串
     */
    public void filterList(){
        Observable.from(list).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                //只要条件符合filter()函数就会返回true。此时，值会发射出去并且所有的观察者都会接收到。
                if(s.startsWith("l")){
                    return true;
                }
                return false;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                filteredList.add(s);
                System.out.println(s);
            }
        });
    }

    /**
     * 当我们不需要整个序列时，而是只想取开头或结尾的几个元素，我们可以用take()或takeLast()。
     */
    public void tackList(){
        Observable.from(list).take(2).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                takedList.add(s);
                System.out.println(s);
            }
        });

    }

    /**
     * repeat()：创建一个有重复的大的序列，一般会设置了重复的次数。
     * 一个可观测序列会在出错时重复发射或者被设计成重复发射。
     * distinct()和distinctUntilChanged()函数可以方便的让我们处理这种重复问题。
     */
    public void repeatDistinctList(){
        Observable.from(list).repeat(2).distinct().subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });

    }

    /**
     * first()方法和last()方法很容易弄明白。它们从Observable中只发射第一个元素或者最后一个元素。
     */
    public void firstLast(){

    }

    /**
     * skip(2)来创建一个不发射前两个元素而是发射它后面的那些数据的序列。
     * skip()和skipLast()函数与take()和takeLast()相对应。
     * 它们用整数N作参数，从本质上来说，它们不让Observable发射前N个或者后N个值。
     */
    public void  skip(){

    }

    /**
     * elementAt()函数仅从一个序列中发射第n个元素然后就完成了。
     */
    public void elementAt(){

    }

    /**
     * 在Observable后面加一个sample()，我们将创建一个新的可观测序列，
     * 它将在一个指定的时间间隔里由Observable发射最近一次的数值
     */
    public void sample(){
        Observable.from(list).sample(30, TimeUnit.SECONDS).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });

    }

    /**
     * 我们想让观察序列每隔两秒至少发射一个，
     * 我们可以使用timeout()函数来监听源可观测序列,
     * 就是在我们设定的时间间隔内如果没有得到一个值则发射一个错误。
     */
    public void timeout(){

    }

    /**
     * debounce()函数过滤掉由Observable发射的速率过快的数据；
     * 如果在一个指定的时间间隔过去了仍旧没有发射一个，那么它将发射最后的那个。
     */
    public void debounce(){
        Observable.from(list).debounce(3,TimeUnit.SECONDS).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });
    }
}
