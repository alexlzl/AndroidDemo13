package com.example.liuzhouliang.demo4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * RXJAVA 一个在 Java VM 上使用可观测的序列来组成异步的、基于事件的程序的库
 * <p>
 * <p>
 * 优点
 * <p>
 * RxJava的好处就在于它的简洁性，逻辑简单的时候看不出RxJava的优势，想必大家都知道在调度过程比较复杂的情况下，异步代码经常会既难写也难被读懂。这时候RxJava的优势就来了，随着程序逻辑变得越来越复杂，它依然能够保持简洁。
 * <p>
 * <p>
 * <p>
 * 观察者：OnClickListener；
 * 被观察者：Button；
 * 订阅（或注册）：setOnClickListener()。
 * <p>
 * 在上面介绍了观察者模式，这里也将RxJava中的三个角色进行一下角色分配
 * <p>
 * 观察者：Observer；
 * 被观察者：Observable；
 * 订阅（或注册）：subscribe()。
 * <p>
 * 观察者==================1
 * Observer 即观察者，它决定事件触发的时候将有怎样的行为。
 * RxJava 观察者的事件回调方法除了普通事件onNext()（相当于onClick()/onEvent()）之外，还定义了两个特殊的事件：onCompleted()和onError()。
 * <p>
 * 被观察者================2
 * Observable 即被观察者，它决定什么时候触发事件以及触发怎样的事件。可以使用create()、just(T...)、from(T[])或from(Iterable<? extends T>)来创建一个 Observable ，并为它定义事件触发规则。
 * <p>
 * 订阅======================3
 * 创建了Observable和Observer之后，再用subscribe()方法将它们联结起来
 */

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private TextView content;
    private TextView mComplete;
    private TextView mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content = (TextView) findViewById(R.id.content);
        mComplete = (TextView) findViewById(R.id.complete);
        mError = (TextView) findViewById(R.id.error);

    }

    /**
     * Describe:RxJava基本使用，Observer作为观察者
     * <p>
     * Author:
     * <p>
     * Time:2017/9/13 17:08
     */
    public void test1(View view) {
        //创建一个观察者====================1
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onCompleted() {
                mComplete.setText("Observer===onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                mComplete.setText("Observer===onError");
            }

            @Override
            public void onNext(String s) {
                content.setText(s);
            }
        };

        //使用Observable.create()创建被观察者============================2
        Observable observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("第一次传递内容==1");
                subscriber.onNext("第二次传递内容====2");
                subscriber.onCompleted();
                subscriber.onError(new RuntimeException());
                subscriber.onStart();
            }
        });
        //订阅观察者==================================3
        observable1.subscribe(observer);
    }

    /**
     * Describe:使用Subscriber作为观察者
     * <p>
     * Author:
     * <p>
     * Time:2017/9/13 17:18
     */
    public void test2(View view) {
        //创建一个观察者====================1

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "Subscriber==onCompleted");
                mComplete.setText("Subscriber===onCompleted");

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Subscriber==onStart");
                mComplete.setText("Subscriber===onError");
            }

            @Override
            public void onNext(String o) {
                Log.e(TAG, "Subscriber==onNext");
                content.setText(o);

            }

            @Override
            public void onStart() {
                super.onStart();
                Log.e(TAG, "Subscriber==onStart");
            }
        };
        //使用Observable.create()创建被观察者============================2
        Observable observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onStart();
                subscriber.onNext("subscriber第一次传递内容==1");
                subscriber.onNext("subscriber第二次传递内容====2");
//                subscriber.onStart();
                subscriber.onCompleted();
            }
        });
        //订阅==================================3
        observable1.subscribe(subscriber);
    }

    /**
     * Subscriber 实现 Observer 接口
     * <p>
     * 二者区别
     * <p>
     * onStart(): 这是Subscriber增加的方法。它会在 subscribe 刚开始，而事件还未发送之前被调用，可以用于做一些准备工作，例如数据的清零或重置。这是一个可选方法，默认情况下它的实现为空。需要注意的是，如果对准备工作的线程有要求
     * <p>
     * unsubscribe(): 这是Subscriber所实现的另一个接口Subscription
     * 的方法，用于取消订阅。在这个方法被调用后，Subscriber将不再接收事件。一般在这个方法调用前，可以使用isUnsubscribed()先判断一下状态。unsubscribe()这个方法很重要，因为在subscribe()之后，Observable会持有Subscriber的引用，这个引用如果不能及时被释放，将有内存泄露的风险。所以最好保持一个原则：要在不再使用的时候尽快在合适的地方（例如onPause()onStop()等方法中）调用unsubscribe()来解除引用关系，以避免内存泄露的发生。
     */

    /**
     * Action的使用======================
     * <p>
     * 什么是Action
     * Action是RxJava 的一个接口，常用的有Action0和Action1
     * <p>
     * Action0： 它只有一个方法 call()，这个方法是无参无返回值的；由于 onCompleted() 方法也是无参无返回值的，因此 Action0 可以被当成一个包装对象，将 onCompleted() 的内容打包起来将自己作为一个参数传入 subscribe() 以实现不完整定义的回调。
     * Ation1：它同样只有一个方法 call(T param)，这个方法也无返回值，但有一个参数；与 Action0 同理，由于 onNext(T obj) 和 onError(Throwable error) 也是单参数无返回值的，因此 Action1 可以将 onNext(obj)和 onError(error) 打包起来传入 subscribe() 以实现不完整定义的回调
     *
     * @param view
     */
    public void test3(View view) {
        Observable.just("Hello", "World")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                        content.setText(s);
                    }
                });


    }

    /**
     *
     * 定义三个对象，分别打包onNext(obj)、onError(error) 、onCompleted()。
     * @param view
     */
    public void test4(View view) {


//        Observable observable = Observable.just("Hello", "World");
        //处理onNext()中的内容
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, s);
            }
        };
        //处理onError()中的内容
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "onErrorAction");
            }
        };
        //处理onCompleted()中的内容
        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {
                Log.e(TAG, "onCompletedAction");

            }
        };
        //使用 onNextAction 来定义 onNext()
        Observable.just("Hello", "World").subscribe(onNextAction);
        //使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        Observable.just("Hello", "World").subscribe(onNextAction, onErrorAction);
        //使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        Observable.just("Hello", "World").subscribe(onNextAction, onErrorAction, onCompletedAction);

    }

    /**
     * Map使用：对数据进行转换
     * @param view
     */
    public void test5(View view){
        final List list=new ArrayList();
        Student student1=new Student();
        Student student2=new Student();
        Student student3=new Student();
        student1.setName("student1");
        student2.setName("student2");
        student3.setName("student3");
        Observable.just(student1, student2, student2)
                //使用map进行转换，参数1：转换前的类型，参数2：转换后的类型
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student i) {
                        String name = i.getName();//获取Student对象中的name
                        return name;//返回name
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        list.add(s);
                        content.setText(list.toString());
                    }
                });



    }

    /**
     * 多次是使用Map进行数据转换
     * @param view
     */
    public void test6(View view){
        //多次使用map，想用几个用几个
        Observable.just("Hello", "World")
                .map(new Func1<String, Integer>() {//将String类型的转化为Integer类型的哈希码
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .map(new Func1<Integer, String>() {//将转化后得到的Integer类型的哈希码再转化为String类型
                    @Override
                    public String call(Integer integer) {
                        return integer.intValue() + "";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
//                        Log.i(TAG, s);
                        content.setText(content.getText()+"=="+s);
                    }
                });


    }
}
