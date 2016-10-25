package com.jeffrey.demo.throttle;

import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

/**
 * Created by Li on 2016/10/19.
 */


final class ListviewClickOnSubscribe implements Observable.OnSubscribe<Integer> {
    final ListView view;

    ListviewClickOnSubscribe(ListView view) {
        this.view = view;
    }

    @Override public void call(final Subscriber<? super Integer> subscriber) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException(
                    "Must be called from the main thread. Was: " + Thread.currentThread());
        }
        final AdapterView.OnItemClickListener onItemChildClickListener = new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(position);
                }
            }
        };


        view.setOnItemClickListener(onItemChildClickListener);



        subscriber.add(new MainThreadSubscription() {
            @Override protected void onUnsubscribe() {
                view.setOnItemClickListener(null);
            }
        });
    }
}