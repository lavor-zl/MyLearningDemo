package com.example.shitian.rxandroiddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class RxAndroidMainActivity extends Activity {

    private android.widget.Button simple;
    private Button halfbaked;
    private Button scheduler;
    private Button map;
    private Button flatMap;
    private Button compose;
    private Button doOnSubscribe;
    private Button eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android_main);
        this.eventBus = (Button) findViewById(R.id.eventBus);
        this.doOnSubscribe = (Button) findViewById(R.id.doOnSubscribe);
        this.compose = (Button) findViewById(R.id.compose);
        this.flatMap = (Button) findViewById(R.id.flatMap);
        this.map = (Button) findViewById(R.id.map);
        this.scheduler = (Button) findViewById(R.id.scheduler);
        this.halfbaked = (Button) findViewById(R.id.half_baked);
        this.simple = (Button) findViewById(R.id.simple);
        RxJavaKnowledgeMerges rxJavaKnowledgeMaps=new RxJavaKnowledgeMerges();
        rxJavaKnowledgeMaps.switchFunc();



        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //定义观察者，也可以通过实现了Observer的抽象类Subscribe来定义观察者
                Observer<String> observer = new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("RxAndroid", s);

                    }
                };
                //定义被观察者
                /**
                 * 还可以通过这两种方式快速实现
                 * Observable observable = Observable.just("观察者开始执行1", "观察者开始执行2", "观察者开始执行3");
                 * String[] words = {"观察者开始执行1", "观察者开始执行2", "观察者开始执行3"};
                 * Observable observable = Observable.from(words);
                 */
                Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("观察者开始执行1");
                        subscriber.onNext("观察者开始执行2");
                        subscriber.onNext("观察者开始执行3");
                        subscriber.onCompleted();
                    }
                });
                //将观察者与被观察者关联起来，这里是被观察者订阅观察者，与我们常规理解应该是观察者订阅被观察者
                //RxJava这样设计是为了链式的使用
                observable.subscribe(observer);
            }
        });
        halfbaked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable observable = Observable.just("观察者开始执行1", "观察者开始执行2", "观察者开始执行3");
                //除了 subscribe(Observer) 和 subscribe(Subscriber) ，
                // subscribe() 还支持不完整定义的回调，RxJava 会自动根据定义创建出 Subscriber
                Action1<String> onNextAction=new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("RxAndroid",s);

                    }
                };
                Action1<Throwable> onErrorAction=new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                };
                Action0 OnCompeletedAction=new Action0() {
                    @Override
                    public void call() {
                        Log.d("RxAndroid", "completed");
                    }
                };
                //subscribe()方法支持1-3个参数的Action，其顺序依次对应onNext，onError,OnCompleted
                observable.subscribe(onNextAction);
            }
        });
        /**
         * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
         * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
         * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。
         * 行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，
         * 可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，
         * 可以避免创建不必要的线程。
         * Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，
         * 即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，
         * 大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
         * 另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
         * 有了这几个 Scheduler ，就可以使用 subscribeOn() 和 observeOn() 两个方法来对线程进行控制了。
         * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
         * observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
         */
        scheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(1, 2, 3, 4)
                        .subscribeOn(Schedulers.io())//指定 subscribe() 发生在 IO 线程
                        .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                Log.i("RxAndroid", integer + "");

                            }
                        });

            }
        });
        /**
         * RxJava 都不建议开发者自定义 Operator 来直接使用 lift()，
         * 而是建议尽量使用已有的 lift() 包装方法（如 map() flatMap() 等）
         */
        //map(): 事件对象的直接变换，具体功能上面已经介绍过。它是 RxJava 最常用的变换
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(1)
                        .map(new Func1<Integer, String>() {
                            @Override
                            public String call(Integer integer) {
                                return integer.toString();
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Log.i("RxAndroid","通过map将Integer转换成String");
                            }
                        });
            }
        });
        //flatMap(): 这是一个很有用但非常难理解的变换，它可以实现一对多的变换
        flatMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student=new Student();
                List<String> list=new ArrayList<String>();
                list.add("语文");
                list.add("数学");
                student.setCourses(list);
                Observable.just(student)
                        .flatMap(new Func1<Student, Observable<String>>() {
                            @Override
                            public Observable<String> call(Student student) {
                                return Observable.from(student.getCourses());
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Log.i("RxAndroid","该生学习的课程有："+s);
                            }
                        });
            }
        });
        //compose: 对 Observable 整体的变换
        //它和 lift() 的区别在于， lift() 是针对事件项和事件序列的，而 compose() 是针对 Observable 自身进行变换
        //假设Observable需要一组变换时，我们可以使用compose
        compose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiftAllTransformer liftAllTransformer=new LiftAllTransformer();
                Observable.just(1,2,3,4)
                        .compose(liftAllTransformer)
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Log.i("RxAndroid","compose变换："+s);

                            }
                        });
            }
        });
        /**
         *  Subscriber 的 onStart() 可以用作流程开始前的初始化。
         *  然而 onStart() 由于在 subscribe() 发生时就被调用了，因此不能指定线程，而是只能执行在 subscribe() 被调用时的线程。
         *  这就导致如果 onStart() 中含有对线程有要求的代码（例如在界面上显示一个 ProgressBar，这必须在主线程执行），
         *  将会有线程非法的风险，因为有时你无法预测 subscribe() 将会在什么线程执行。
         *  而与 Subscriber.onStart() 相对应的，有一个方法 Observable.doOnSubscribe() 。
         *  它和 Subscriber.onStart() 同样是在 subscribe() 调用后而且在事件发送前执行，但区别在于它可以指定线程。
         *  默认情况下， doOnSubscribe() 执行在 subscribe() 发生的线程；
         *  而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程。
         */
        doOnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(1,2,3,4)
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.i("RxAndroid","在subscribe()之前执行了");
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                Log.i("RxAndroid",integer+"");
                            }
                        });
            }
        });
        //RxJava实现eventBus
        eventBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RxAndroidMainActivity.this,EventBusActivity.class);
                startActivity(intent);

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rx_android_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
