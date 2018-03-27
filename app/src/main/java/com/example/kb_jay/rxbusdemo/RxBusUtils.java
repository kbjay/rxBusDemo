package com.example.kb_jay.rxbusdemo;

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by kb_jay on 2018/3/27.
 */

public class RxBusUtils {
    private static RxBusUtils mInstance;

    //线程安全的观察者跟被观察者===》充当bus
    //Subject<T, R> extends Observable<R> implements Observer<T>
    private final SerializedSubject<Object, Object> mBus;

    //represents a group of Subscriptions that are unsubscribed together.
    private HashMap<String, CompositeSubscription> mSubscriptionMap;


    private RxBusUtils() {
        mBus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBusUtils getInstance() {
        if (mInstance == null) {
            synchronized (RxBusUtils.class) {
                if (mInstance == null) {
                    mInstance = new RxBusUtils();
                }
            }
        }
        return mInstance;
    }

    public <T> void send(T t) {
        if (mBus.hasObservers()) {
            mBus.onNext(t);
        }
    }


    /**
     * *
     * @param type 放在🚗上的实体，是🐂啊还是🐎呀，
     *             如果这是一辆🐂🚗那么会过滤掉🐎
     * @author kb_jay
     * created at 2018/3/27 下午2:52
     */
    public <T> Observable<T> tObservable(final Class<T> type) {
        return mBus.ofType(type);
    }

    public void addSubscription(Object o, Subscription subscription) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String k = o.getClass().getSimpleName();
        if (mSubscriptionMap.get(k) != null) {
            CompositeSubscription compositeSubscription = mSubscriptionMap.get(k);
            compositeSubscription.add(subscription);
        } else {
            CompositeSubscription compositeSubscription = new CompositeSubscription();
            compositeSubscription.add(subscription);
            mSubscriptionMap.put(k, compositeSubscription);
        }
    }

    /**
     * 取消注册
     * *
     * @author kb_jay
     * created at 2018/3/27 下午3:19
     */
    public <T> void unSubscribe(T t) {
        if (mSubscriptionMap == null) {
            return;
        }
        String k = t.getClass().getSimpleName();
        if (!mSubscriptionMap.containsKey(k)) {
            return;
        }
        if (mSubscriptionMap.get(k) != null) {
            mSubscriptionMap.get(k).unsubscribe();
        }
        mSubscriptionMap.remove(k);
    }
}
