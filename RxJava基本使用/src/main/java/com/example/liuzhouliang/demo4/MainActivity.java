package com.example.liuzhouliang.demo4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * RXJAVA 一个在 Java VM 上使用可观测的序列来组成异步的、基于事件的程序的库
 * <p>
 * <p>由Observable发起事件，经过中间的处理后由Observer消费
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
 * 观察者:Subscriber,继承自Observer，一般使用这个
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
    private String url = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3165960808,845187397&fm=27&gp=0.jpg";
    private ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content = (TextView) findViewById(R.id.content);
        mComplete = (TextView) findViewById(R.id.complete);
        mError = (TextView) findViewById(R.id.error);
        imageview = (ImageView) findViewById(R.id.iv);

    }

    public void test0(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
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
                        content.setText(content.getText().toString() + "==" + s);
                    }
                });
        /**
         *  MainActivity: A
         *  MainActivity: B
         *  MainActivity: C
         *  MainActivity: onCompleted
         */
        Observable.just("A", "B", "C").subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG, o.toString());
            }


        });

    }

    /**
     * 定义三个对象，分别打包onNext(obj)、onError(error) 、onCompleted()。
     *
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
     *
     * @param view
     */
    public void test5(View view) {
        final List list = new ArrayList();
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
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
     *
     * @param view
     */
    public void test6(View view) {
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
                        content.setText(content.getText() + "==" + s);
                    }
                });


    }

    /**
     * Describe:flatMap是一个比教难理解的一个转换，在这里先假设一个需求，需要打印多个Student所学的课程。这跟之前获取Student的name又不同了，这里先确定一下关系，一个Student类中只有一个name，而一个Student却有多门课程（Course），Student我们可以理解成这样：
     * <p>
     * Author:
     * <p>
     * Time:2017/9/15 10:34
     */
    public void test7(View view) {
        //        List<Student> students = new ArrayList<Student>();
        Student[] students = new Student[1];
        Student student = new Student();
        student.setName("alex");
        student.setAge(100);
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            if (i == 0)
                course.setName("语文");
            course.setId("100");
            if (i == 1)
                course.setName("数学");
            course.setId("100");
            if (i == 2)
                course.setName("化学 ");
            course.setId("100");
            courses.add(course);
        }

        student.setCourses(courses);
        //        students.add(student);
        students[0] = student;

        Action1<List<Course>> action1 = new Action1<List<Course>>() {
            @Override
            public void call(List<Course> courses) {
                //遍历courses，输出cuouses的name
                for (int i = 0; i < courses.size(); i++) {
                    Log.i(TAG, courses.get(i).getName());
                    content.setText(content.getText() + "==" + courses.get(i).getName());
                }
            }
        };
        /**
         *  from
         *  接收一个Iterable对象(集合List)或者是数组对象，或者是一个线程的Future，
         */
        Observable.from(students)
                .map(new Func1<Student, List<Course>>() {
                    @Override
                    public List<Course> call(Student student) {
                        //返回coursesList
                        return student.getCourses();
                    }
                })
                .subscribe(action1);


    }

    /**
     * Describe:其他操作符
     * <p>
     * 除了map和flatMap之外，还有其他操作符以供使用。这里就不一一列举他们的用法了，其他常用的操作符如下：
     * <p>
     * filter：集合进行过滤
     * each：遍历集合
     * take：取出集合中的前几个
     * skip：跳过前几个元素
     * <p>
     * Author:
     * <p>
     * Time:2017/9/15 11:55
     */

    public void test8(View view) {
        //        List<Student> students = new ArrayList<Student>();
        Student[] students = new Student[1];
        Student student = new Student();
        student.setName("alex");
        student.setAge(100);
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            if (i == 0)
                course.setName("语文");
            course.setId("100");
            if (i == 1)
                course.setName("数学");
            course.setId("100");
            if (i == 2)
                course.setName("化学 ");
            course.setId("100");
            courses.add(course);
        }

        student.setCourses(courses);
        //        students.add(student);
        students[0] = student;
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribe(new Action1<Course>() {
                    @Override
                    public void call(Course course) {
                        Log.i(TAG, course.getName());
                        content.setText(content.getText() + "==" + course.getName());
                    }
                });


    }

    /**
     * Describe:RxJava在不指定线程的情况下，发起时间和消费时间默认使用当前线程。所以之前的做法
     * <p>
     * 因为是在主线程中发起的，所以不管中间map的处理还是Action1的执行都是在主线程中进行的。若是map中有耗时的操作，这样会导致主线程拥塞，这并不是我们想看到的。
     * <p>
     * Scheduler：线程控制器，可以指定每一段代码在什么样的线程中执行。
     * 模拟一个需求：新的线程发起事件，在主线程中消费
     * <p>
     * subscribeOn()：指定subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
     * observeOn()：指定Subscriber 所运行在的线程。或者叫做事件消费的线程。
     * <p>
     * 以及参数Scheduler，RxJava已经为我们提供了一下几个Scheduler
     * <p>
     * Schedulers.immediate()：直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
     * Schedulers.newThread()：总是启用新线程，并在新线程执行操作。
     * Schedulers.io()： I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
     * Schedulers.computation()：计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
     * AndroidSchedulers.mainThread()：它指定的操作将在 Android 主线程运行。
     * Author:
     * <p>
     * Time:2017/9/15 14:12
     */
    public void test9(View view) {
        Observable.just("Hello", "Word")
                .subscribeOn(Schedulers.newThread())//指定 subscribe() 发生在新的线程
                .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, s);
                        content.setText(content.getText() + "==" + s);
                    }
                });


    }

    /**
     * Describe:若将observeOn(AndroidSchedulers.mainThread())去掉会怎么样？不为消费事件show(s)指定线程后，show(s)会在那里执行？
     * 其实，observeOn() 指定的是它之后的操作所在的线程。也就是说，map的处理和最后的消费事件show(s)都会在io线程中执行。
     * observeOn()可以多次使用，可以随意变换线程
     * <p>
     * Author:
     * <p>
     * Time:2017/9/15 14:59
     */
    public void test10(View view) {
        Observable.just("Hello", "Wrold")
                .subscribeOn(Schedulers.newThread())//指定：在新的线程中发起
                .observeOn(Schedulers.io())         //指定：在io线程中处理
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "add====";       //处理数据
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//指定：在主线程中处理
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        content.setText("切换到主线程==" + s);                   //消费事件
                    }
                });


    }

    public void test11(View view) {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(final Subscriber<? super Bitmap> subscriber) {
                OkHttpClient client = new OkHttpClient();
                client.newCall(new Request.Builder().url(url).build())
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {

                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                byte[] data = response.body().bytes();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                subscriber.onNext(bitmap);
                                subscriber.onCompleted();
                            }


                        });
            }
        }).subscribeOn(Schedulers.io())//工作线程中进行图片下载
                .observeOn(AndroidSchedulers.mainThread())//主线程中进行视图刷新
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        imageview.setImageBitmap(bitmap);
                    }
                });
    }

    /**
     * Describe:from
     * 接收一个Iterable对象(集合List)或者是数组对象，或者是一个线程的Future，例子如下
     * <p>
     * Author:
     * <p>
     * Time:2017/9/18 12:14
     */
    public void test12(View view) {
        Observable.from(new String[]{"A", "B", "C", "D"}).subscribe(new Action1<String>() {
            @Override
            public void call(String o) {
                content.setText(content.getText().toString() + "==" + o);
            }


        });
    }

    /**
     * Describe:Defer   直到有订阅者倍subscribe的时候才会创建
     * <p>
     * <p>
     * Author:
     * <p>
     * Time:2017/9/18 14:10
     */
    String str = "Hello，RxJava";
    String str1 = "Hello，RxJava";

    public void test13(View view) {
        str = "Hello，RxJava";
        Observable o = Observable.just(str);
        str = "Hello，RxJava====重新赋值";//在此处如果对str重新赋值,再进行订阅，消费者仍然接受到的是开始的值“Hello，RxJava”，如果想改变消费者接受的值可以调用defer进行消息发射
        o.subscribe(new Action1<String>() {
            @Override
            public void call(String str) {
                Log.e(TAG, "Action1 执行call====" + str);
            }
        });

        /**
         * =================================
         */
        Observable observable = Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable call() {
                Log.e(TAG, " defer==执行call====");
                return Observable.just(str1);
            }
        });
        str1 = "hi，RxJava===defer";

        observable.subscribe(new Action1<String>() {

            @Override
            public void call(String s) {
                content.setText(s);
                Log.e(TAG, " defer==执行subscribe====" + s);
            }
        });
    }

    /**
     * Scheduler ，就可以使用 subscribeOn() 和 observeOn() 两个方法来对线程进行控制了。 * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。 * observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
     */
    Subscription sub;

    public void test14(View view) {
        sub = Observable.interval(1, 1, TimeUnit.SECONDS, Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {

            @Override
            public void call(Long aLong) {
                Log.e(TAG, String.valueOf(aLong));
                content.setText(content.getText() + aLong.toString() + "==");
                if (aLong == 5) {
                    if (!sub.isUnsubscribed()) {
                        sub.unsubscribe();
                    }

                    Toast.makeText(MainActivity.this, "取消订阅", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
