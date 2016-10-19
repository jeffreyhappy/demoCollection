package com.jeffrey.demo.retrofitdemo;


import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Li on 2016/9/23.
 */

public interface TestFormatService {

    @POST("/api/test")
    Observable<MockBean> postSimple();

    @FormUrlEncoded
    @POST("/api/test")
    Observable<MockBean> postWithParams(@Field("name") String name);



    @POST("/api/test")
    Observable<MockBean> postWithObject(@Body MockRequestBean requestBean);

}
