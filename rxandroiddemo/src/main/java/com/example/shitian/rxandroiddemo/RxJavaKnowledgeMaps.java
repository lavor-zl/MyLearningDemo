package com.example.shitian.rxandroiddemo;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

/**
 * RxJava提供了几个mapping函数：map(),flatMap(),concatMap(),flatMapIterable()以及switchMap().
 * 所有这些函数都作用于一个可观测序列，然后变换它发射的值，最后用一种新的形式返回它们。
 */
public class RxJavaKnowledgeMaps {
    private List<String> list;

    public RxJavaKnowledgeMaps() {
        init();
    }

    public void init() {
        list = new ArrayList<>();
        list.add("hello");
        list.add("list");
        list.add("lavor");
        list.add("hi");
    }

    /**
     * map()是对观察序列的直接变换将一个对象转换成另一个对象发射出去
     */
    public void map() {
        Observable.from(list).map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return s.length();
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });

    }

    /**
     * 在复杂的场景中，我们有一个这样的Observable：
     * 它发射一个数据序列，这些数据本身也可以发射Observable。
     * RxJava的flatMap()函数提供一种铺平序列的方式，然后合并这些Observables发射的数据，
     * 最后将合并后的结果作为最终的Observable。
     * 注意：flatMap()允许交叉，这意味着flatMap()不能够保证在最终生成的Observable中源Observables确切的发射顺序。
     */
    public void flatMap() {
        Observable.from(list).flatMap(new Func1<String, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(String s) {
                List<Integer> list = new ArrayList<Integer>();
                list.add(0);
                list.add(s.length());
                return Observable.from(list);
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });
    }

    /**
     * concatMap()函数解决了flatMap()的交叉问题，提供了一种能够把发射的值连续在一起的铺平函数，而不是合并它们
     */
    public void concatMap() {
        Observable.from(list).concatMap(new Func1<String, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(String s) {
                List<Integer> list = new ArrayList<Integer>();
                list.add(0);
                list.add(s.length());
                return Observable.from(list);
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });

    }

    /**
     * 作为*map家族的一员，flatMapInterable()和flatMap()很像。
     * 仅有的本质不同是它将源数据两两结成对并生成Iterable，而不是原始数据项和生成的Observables。
     */
    public void flatMapInterable() {
        Observable.from(list).flatMapIterable(new Func1<String, Iterable<Integer>>() {
            @Override
            public Iterable<Integer> call(String s) {
                List<Integer> list = new ArrayList<Integer>();
                list.add(0);
                list.add(s.length());
                return list;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });

    }

    /**
     * switchMap()和flatMap()很像，除了一点：每当源Observable发射一个新的数据项（Observable）时，
     * 它将取消订阅并停止监视之前那个数据项产生的Observable，并开始监视当前发射的这一个。
     */
    public void switchMap() {
        Observable.from(list).switchMap(new Func1<String, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(String s) {
                List<Integer> list = new ArrayList<Integer>();
                list.add(0);
                list.add(s.length());
                return Observable.from(list);
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });

    }

    /**
     * scan()函数可以看做是一个累积函数。scan()函数对原始Observable发射的每一项数据都应用一个函数，
     * 计算出函数的结果值，并将该值填充回可观测序列，等待和下一次发射的数据一起使用。
     * 第一次应用这个函数时call中的第一个参数都是有默认值的
     * 当类型为Integer时默认值是0，当类型是String是默认值是""
     */
    public void scan() {
        Observable.from(list).scan(new Func2<String, String, String>() {
            @Override
            public String call(String s, String s2) {
                return s + s2;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
    }

    /**
     * RxJava提供了一个有用的函数从列表中按照指定的规则：groupBy()来分组元素。
     */
    public void groupBy() {
        //按照观察序列中字符串的长度将字符串分组
        Observable<GroupedObservable<Integer, String>> groupedItems =
                Observable.from(list).groupBy(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.length();
                    }
                });
        //我们将创建一个新的Observable将所有的观察序列联系起来，像往常一样然后订阅它
        Observable.concat(groupedItems).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
    }

    /**
     * buffer()函数将源Observable变换一个新的Observable，
     * 这个新的Observable每次发射一组列表值而不是一个一个发射。
     */
    public void buffer(){
        //buffer(count)，将list中的数据每count个作为一组发送
        Observable.from(list).buffer(2).subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> strings) {
                System.out.println("利用buffer发送一次数据");
                for (int i=0;i<strings.size();i++){
                    System.out.println(strings.get(i));
                }

            }
        });
        //buffer(count,skip)，将list中的数据每间隔skip个数据发送一次count个数据
        Observable.from(list).buffer(2,1).subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> strings) {
                System.out.println("利用buffer发送一次数据");
                for (int i=0;i<strings.size();i++){
                    System.out.println(strings.get(i));
                }

            }
        });

    }

    /**
     * window()函数和buffer()很像，但是它发射的是Observable而不是列表
     */
    public void window(){
        Observable.from(list).window(2).subscribe(new Action1<Observable<String>>() {
            @Override
            public void call(Observable<String> stringObservable) {
                stringObservable.subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                });
            }
        });

    }

    /**
     * cast()函数是map()操作符的特殊版本。
     * 它将源Observable中的每一项数据都转换为新的类型，把它变成了不同的Class。
     */
    public void cast(){
        List<String> list=new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        Observable.from(list).cast(Integer.class).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });

    }

}
