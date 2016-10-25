package com.jeffrey.demo.throttle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class Main2Activity extends AppCompatActivity {

    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        rv = (RecyclerView) findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0 ; i < 100 ;i++){
            datas.add("position=" + i);
        }
        rv.setAdapter(new TestAdapter(datas));
//        rv.addOnItemTouchListener(new OnItemChildClickListener() {
//            @Override
//            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                Toast.makeText(Main2Activity.this,"当前点击位置 "+ i,Toast.LENGTH_LONG).show();
//            }
//        });

        RxViews.recyclerItemClicks(rv).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                Toast.makeText(Main2Activity.this,"当前点击位置 "+ i,Toast.LENGTH_LONG).show();
                Log.d("fei","click " + i);
            }
        });
    }


    class TestAdapter extends BaseQuickAdapter<String>{

        public TestAdapter(List<String> data) {
            super(R.layout.item_text,data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, String s) {
            baseViewHolder.setText(R.id.tv,s);
            baseViewHolder.addOnClickListener(R.id.item_content);
        }
    }

}
