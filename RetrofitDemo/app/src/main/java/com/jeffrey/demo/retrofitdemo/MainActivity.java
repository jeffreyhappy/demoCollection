package com.jeffrey.demo.retrofitdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       findViewById(R.id.button).setOnClickListener(this);
       findViewById(R.id.button2).setOnClickListener(this);
       findViewById(R.id.button3).setOnClickListener(this);
       findViewById(R.id.button4).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                simpleRequest();
                break;
            case R.id.button2:
//                paramsRequest();
                paramsFormatRequest();
                break;
            case R.id.button3:
//                objectRequest();
                objectFormatRequest();
                break;
            case R.id.button4:
//                MockRequestBean mockRequestBean = new MockRequestBean("jeffrey",18);
                Observable<MockBean>  resp =  RetrofitImp.getTestFormatService().postSimple();
                simpleSubscribe(resp);
                break;
        }
    }



    private void simpleRequest(){
        Observable<MockBean>  resp =  RetrofitImp.getTestService().postSimple();
        simpleSubscribe(resp);
    }


    private void paramsRequest(){
        Observable<MockBean>  resp =  RetrofitImp.getTestService().postWithParams("jeffrey");
        simpleSubscribe(resp);
    }

    private void objectRequest(){
        MockRequestBean mockRequestBean = new MockRequestBean("jeffrey","18");
        Observable<MockBean>  resp =  RetrofitImp.getTestService().postWithObject(mockRequestBean);
        simpleSubscribe(resp);
    }

    private void objectFormatRequest(){
        MockRequestBean mockRequestBean = new MockRequestBean("张三","e10adc3949ba59abbe56e057f20f883e");
        Observable<MockBean>  resp =  RetrofitImp.getTestFormatService().postWithObject(mockRequestBean);
        simpleSubscribe(resp);
    }

    private void paramsFormatRequest(){
        Observable<MockBean>  resp =  RetrofitImp.getTestFormatService().postWithParams("jeffrey");
        simpleSubscribe(resp);
    }


    private  void simpleSubscribe(Observable<MockBean> observable ){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MockBean>() {
                    @Override
                    public void call(MockBean mockBean) {
                        Log.d("fei","success " + mockBean.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("fei","error " + throwable.getMessage());
                    }
                });

    }

}
