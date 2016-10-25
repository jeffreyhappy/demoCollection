package com.jeffrey.demo.throttle;

import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

/**
 * Created by Li on 2016/10/19.
 */


final class RecyclerViewClickOnSubscribe implements Observable.OnSubscribe<Integer> {
    final RecyclerView view;

    RecyclerViewClickOnSubscribe(RecyclerView view) {
        this.view = view;
    }

    @Override public void call(final Subscriber<? super Integer> subscriber) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException(
                    "Must be called from the main thread. Was: " + Thread.currentThread());
        }

        final OnItemChildClickListener onItemChildClickListener = new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(i);
                }
            }
        };

        view.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(i);
                }
            }
        });


        subscriber.add(new MainThreadSubscription() {
            @Override protected void onUnsubscribe() {
                view.removeOnItemTouchListener(onItemChildClickListener);
            }
        });
    }
}