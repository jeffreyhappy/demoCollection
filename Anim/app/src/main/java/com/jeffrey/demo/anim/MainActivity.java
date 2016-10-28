package com.jeffrey.demo.anim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    TextView tv2;


    private final int DURATION = 2000;


    private int tv1X;
    private int tv2X;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);

        findViewById(R.id.btn_test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testAnimX(tv);

            }
        });
        findViewById(R.id.btn_test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testAnimTranslateX(tv2);
            }
        });
        findViewById(R.id.btn_test3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.animate().x(tv1X).setDuration(DURATION).start();
                tv2.animate().translationX(0).setDuration(DURATION).start();
            }
        });




        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {

                int[] locaiton1 = new int[2];
                tv.getLocationInWindow(locaiton1);


                tv1X = locaiton1[0];
                Log.d("fei","tv1x = " + tv1X);
                // 成功调用一次后，移除 Hook 方法，防止被反复调用
                // removeGlobalOnLayoutListener() 方法在 API 16 后不再使用
                // 使用新方法 removeOnGlobalLayoutListener() 代替
                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        ViewTreeObserver vto2 = tv2.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {

                int[] locaiton2 = new int[2];
                tv.getLocationInWindow(locaiton2);

                tv2X = locaiton2[0];
                Log.d("fei","tv2X = " + tv2X);
                // 成功调用一次后，移除 Hook 方法，防止被反复调用
                // removeGlobalOnLayoutListener() 方法在 API 16 后不再使用
                // 使用新方法 removeOnGlobalLayoutListener() 代替
                tv2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    private void testAnimX(View view ){
        //x是相对父控件
        view.animate().x(0).setDuration(DURATION).start();
    }

    private void testAnimTranslateX(View view ){
        //translationX是相对自己
        view.animate().translationX(-100).setDuration(DURATION).start();
    }





}
