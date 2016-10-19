package com.jeffrey.demo.throttle;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Li on 2016/10/19.
 */

public class ThrottleUtils {
    private   Subscriber<? super  Void > mSubscriber;





    public ThrottleUtils( Action1<Void> onNext){
        Observable.OnSubscribe<Void> onSubscribe = new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mSubscriber = subscriber;
            }
        };

        Observable.create(onSubscribe)
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe(onNext);
    }

    public void toNext(){
        mSubscriber.onNext(null);
    }
}
