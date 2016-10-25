package com.jeffrey.demo.throttle;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import java.util.concurrent.TimeUnit;

import rx.Observable;


/**
 * 这个就是抄的rxViews ,区别就是观察者抛出的东西不为空
 * Created by Li on 2016/10/19.
 */

public class RxViews {
    private static final int THROTTLE_TIME = 1000;

    /**
     * 这个抛出的是view的id
     * @param view
     * @return
     */
    @CheckResult
    @NonNull
    public static Observable<Integer> clicks(@NonNull View view) {
        return Observable.create(new ViewClickOnSubscribe(view)).throttleFirst(THROTTLE_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 这个抛出的是recycler的position
     * @param view
     * @return
     */
    @CheckResult
    @NonNull
    public static  Observable<Integer> recyclerItemClicks(@NonNull RecyclerView view) {
        return Observable.create(new RecyclerViewClickOnSubscribe(view)).throttleFirst(THROTTLE_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 这个抛出的是ListView的position
     * @param view
     * @return
     */
    @CheckResult
    @NonNull
    public static  Observable<Integer> listViewItemClicks(@NonNull ListView view) {
        return Observable.create(new ListviewClickOnSubscribe(view)).throttleFirst(THROTTLE_TIME, TimeUnit.MILLISECONDS);
    }
}
