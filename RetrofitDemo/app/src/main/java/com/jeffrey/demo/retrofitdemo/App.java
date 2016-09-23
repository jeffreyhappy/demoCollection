package com.jeffrey.demo.retrofitdemo;

import android.app.Application;

/**
 * Created by Li on 2016/9/23.
 */

public class App extends Application {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }


    public static final App getInstance(){
        return app;
    }
}
