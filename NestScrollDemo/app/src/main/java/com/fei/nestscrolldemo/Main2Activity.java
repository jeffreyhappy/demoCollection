package com.fei.nestscrolldemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

/**
 * recyclerView 要固定大小 不然的话 会绘制出所有的item
 * recyclerView的高度为 root的高度 减去头部textview的高度
 * 创建日期：2018/3/26 on 10:31
 * 描述:
 * 作者:Li
 */

public class Main2Activity extends AppCompatActivity {
    private String TAG = Main2Activity.class.getSimpleName();

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
                nsv.scrollTo(0,topView1.getHeight());
                int rvNewHeight = rootView.getHeight() - topView2.getHeight();

                rv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,rvNewHeight));

            }
        });

    }


}
