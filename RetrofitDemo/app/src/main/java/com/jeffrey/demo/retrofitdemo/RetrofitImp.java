package com.jeffrey.demo.retrofitdemo;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Li on 2016/9/23.
 */

public class RetrofitImp {
    public static final String key = "C31594ba@*2f5dT6";

    private static Retrofit retrofit ;



    public static TestService getTestService(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.160:3456/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        TestService  testService = retrofit.create(TestService.class);
        return testService;
    }


    private static  Retrofit  reqFormateRetrofit ;

    /**
     * 这个加上参数的格式化
     * @return
     */
    public static TestFormatService getTestFormatService(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Wz-Head", CustomerHeader());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        if (reqFormateRetrofit == null){
            reqFormateRetrofit = new Retrofit.Builder()
                    .baseUrl("http://test.api.12123.com/")
                    .addConverterFactory(FormatRequestConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
        }


        TestFormatService  testService = reqFormateRetrofit.create(TestFormatService.class);
        return testService;
    }

    private static String CustomerHeader(){
        HashMap<String,String> customHead = new HashMap<>();
        DeviceInfo deviceInfo = DeviceInfo.init(App.getInstance());
        customHead.put("Wz-Imei", deviceInfo.getImei());
        customHead.put("Wz-Channel", deviceInfo.getChannel());
        customHead.put("Wz-Model", deviceInfo.getDevice());
        customHead.put("Wz-Screen", deviceInfo.getScreenWidth()+"*"+ deviceInfo.getScreenHeight());
        customHead.put("Wz-Version",deviceInfo.getVersion());
        customHead.put("Wz-Mac", deviceInfo.getMac());
        customHead.put("Wz-OsVersionCode", String.valueOf(deviceInfo.osVersionCode()));
        customHead.put("Wz-OsVersionName", deviceInfo.osVersionName());
        customHead.put("Wz-Brand", Build.BRAND);
        customHead.put("Wz-Type", "2");
        customHead.put("Wz-Sid", "");

        JSONObject jsonObject = new JSONObject(customHead);
        String strCustomHead = jsonObject.toString();

        Log.d("MKStringRequest","getHeaders strCustomHead = " + strCustomHead);
        String encryptHead    = Security.encrypt(strCustomHead, key);
        Log.d("MKStringRequest","getHeaders encryptHead = " + encryptHead);

        return encryptHead;
    }

}
