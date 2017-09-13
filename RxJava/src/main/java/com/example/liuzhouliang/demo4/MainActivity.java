package com.example.liuzhouliang.demo4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

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
                Log.e(TAG,"Subscriber==onCompleted");
                mComplete.setText("Subscriber===onCompleted");

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"Subscriber==onStart");
                mComplete.setText("Subscriber===onError");
            }

            @Override
            public void onNext(String o) {
                Log.e(TAG,"Subscriber==onNext");
                content.setText(o);

            }

            @Override
            public void onStart() {
                super.onStart();
                Log.e(TAG,"Subscriber==onStart");
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
     *Subscriber 实现 Observer 接口
     *
     *二者区别
     *
     * onStart(): 这是Subscriber增加的方法。它会在 subscribe 刚开始，而事件还未发送之前被调用，可以用于做一些准备工作，例如数据的清零或重置。这是一个可选方法，默认情况下它的实现为空。需要注意的是，如果对准备工作的线程有要求
     *
     * unsubscribe(): 这是Subscriber所实现的另一个接口Subscription
     * 的方法，用于取消订阅。在这个方法被调用后，Subscriber将不再接收事件。一般在这个方法调用前，可以使用isUnsubscribed()先判断一下状态。unsubscribe()这个方法很重要，因为在subscribe()之后，Observable会持有Subscriber的引用，这个引用如果不能及时被释放，将有内存泄露的风险。所以最好保持一个原则：要在不再使用的时候尽快在合适的地方（例如onPause()onStop()等方法中）调用unsubscribe()来解除引用关系，以避免内存泄露的发生。
     */
}
