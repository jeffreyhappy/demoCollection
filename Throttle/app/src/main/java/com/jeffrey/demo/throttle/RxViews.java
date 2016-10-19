package com.jeffrey.demo.throttle;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;

import rx.Observable;

/**
 * Created by Li on 2016/10/19.
 */

public class RxViews {
    @CheckResult
    @NonNull
    public static Observable<Integer> clicks(@NonNull View view) {
        return Observable.create(new ViewClickOnSubscribe(view));
    }
}
