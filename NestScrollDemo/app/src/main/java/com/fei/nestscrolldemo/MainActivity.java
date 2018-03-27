package com.fei.nestscrolldemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * recyclerView 要固定大小 不然的话 会绘制出所有的item
 * recyclerView的高度为 root的高度 减去头部textview的高度
 * 创建日期：2018/3/26 on 10:31
 * 描述:
 * 作者:Li
 */

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    RecyclerView rv;
    MyNestedScrollView nsv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        nsv = findViewById(R.id.nsv);


        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new SimpleTestAdapter());



        final View rootView = findViewById(android.R.id.content);

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                View topView1 = findViewById(R.id.top_1);
                View topView2 = findViewById(R.id.top_2);

                nsv.setMyScrollHeight(topView1.getHeight());
                int rvNewHeight = rootView.getHeight() - topView2.getHeight();

                rv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,rvNewHeight));

            }
        });

//        Log.d(TAG,"rootView height " + rootView.getHeight());

//        View topView = findViewById(R.id.top_1);
//        Log.d(TAG,"topView height " + topView.getHeight() + " top " + topView.getTop());
    }


}
