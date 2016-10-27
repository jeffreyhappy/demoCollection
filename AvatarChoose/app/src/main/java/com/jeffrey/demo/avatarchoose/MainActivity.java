package com.jeffrey.demo.avatarchoose;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *  <p>1 继承ChooseActivity</p>
 *  <p>2 调用toLocalChoose()或者toCameraChoose()</p>
 *  <p>3 获取返回
 *      public void onChooseDone(Uri uri) {
 Glide.with(this).load(uri).into(mIv);
 }</p>
 *
 */
public class MainActivity extends ChooseActivity implements View.OnClickListener {

    private ImageView  mIv;
    private Uri    choosedUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        mIv = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                toLocalChoose();
                break;
            case R.id.button2:
                toCameraChoose();
                break;
            case R.id.button3:
                /**
                 * 上传只是演示 不集成了
                 */
                new Thread(){
                    @Override
                    public void run() {
                        toUpload();
                    }
                }.start();
                break;
        }
    }


    @Override
    public void onChooseDone(Bitmap bitmap) {
//        Glide.with(this).load(uri).into(mIv);
//        choosedUri = uri;
        mIv.setImageBitmap(bitmap);
    }


    /**
     * okhttp 文件上传见https://github.com/square/okhttp/wiki/Recipes
     */
    String url = "http://192.168.0.105:3000/upload/img";
    private void toUpload(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();
        File file = new File(URI.create(choosedUri.toString()));
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                    .addFormDataPart("avatar","avatar",RequestBody.create(MEDIA_TYPE_PNG,file)).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            Log.d("fei","toUpload " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("fei","toUpload error" + e.getMessage());

        }
    }

}
