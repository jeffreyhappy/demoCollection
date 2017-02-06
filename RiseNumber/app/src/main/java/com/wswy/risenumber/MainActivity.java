package com.wswy.risenumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RiseNumberTextView tv1 = (RiseNumberTextView) findViewById(R.id.tv1);
        NumTextView  tv2 = (NumTextView) findViewById(R.id.tv2);

        tv1.withNumber(20000);
        tv1.start();


        tv2.setTextWithNumber(20000);
    }
}
