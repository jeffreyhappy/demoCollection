package com.jeffrey.demo.throttle;

import android.os.Looper;
import android.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

/**
 * Created by Li on 2016/10/19.
 */


final class ViewClickOnSubscribe implements Observable.OnSubscribe<Integer> {
    final View view;

    ViewClickOnSubscribe(View view) {
        this.view = view;
    }

    @Override public void call(final Subscriber<? super Integer> subscriber) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException(
                    "Must be called from the main thread. Was: " + Thread.currentThread());
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(v.getId());
                }
            }
        };
        view.setOnClickListener(listener);

        subscriber.add(new MainThreadSubscription() {
            @Override protected void onUnsubscribe() {
                view.setOnClickListener(null);
            }
        });
    }
}