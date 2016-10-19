package com.jeffrey.demo.throttle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    ThrottleUtils mThrottleUtils = new ThrottleUtils(new Action1<Void>() {
        @Override
        public void call(Void aVoid) {
            startActivity(new Intent(MainActivity.this,Main2Activity.class));

        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RxViews.clicks(findViewById(R.id.btn)).subscribe(new MyAction());
        RxViews.clicks(findViewById(R.id.btn2)).subscribe(new MyAction());
    }

//    public void next(View view){
//        mThrottleUtils.toNext();
//        mThrottleUtils.toNext();
//    }


    class MyAction implements Action1<Integer>{

        @Override
        public void call(Integer integer) {
            switch (integer){
                case R.id.btn:
                    startActivity(new Intent(MainActivity.this,Main2Activity.class));

                    break;
                case R.id.btn2:
                    startActivity(new Intent(MainActivity.this,Main3Activity.class));

                    break;
            }
        }
    }
}
