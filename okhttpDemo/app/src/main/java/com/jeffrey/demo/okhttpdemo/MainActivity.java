package com.jeffrey.demo.okhttpdemo;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp的文档d地址 :http://square.github.io/okhttp/
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
    }


    OkHttpClient client = new OkHttpClient();

    String runSync(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try  {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            return "";
        }
    }

    void runAsync(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(MainActivity.this,"onFailure ret = " + e.getMessage() ,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    new Handler(Looper.getMainLooper()).post(new Thread(){
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(MainActivity.this,"ret = " + response.body().string() ,Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            });

    }

    void downloadFileSync(String url,File file){
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            InputStream inputStream = response.body().byteStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            int size = 1024;
            byte[] buffer = new byte[size];
            int readSize = 0;
            while ((readSize = inputStream.read(buffer)) > 0){
                fileOutputStream.write(buffer,0,readSize);
            }
            inputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    File createFile(){
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path,"download.apk");
        Log.d("fei","file path = " + file.getPath());
        return file;
    }
    String host = "http://192.168.0.160:3456/";

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                new AsyncTask<Void,Void,String>(){
                    @Override
                    protected String doInBackground(Void... voids) {
                        return  runSync(host+"test");
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        Toast.makeText(MainActivity.this,"ret = " + s ,Toast.LENGTH_LONG).show();
                    }
                }.execute();

                break;
            case R.id.button2:
                runAsync(host+"test");
                break;
            case R.id.button3:
                new AsyncTask<Void,Void,Void>(){

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Toast.makeText(MainActivity.this,"download finish" ,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        downloadFileSync(host+"myapp.apk",createFile());
                        return null;
                    }
                }.execute();
                break;
            case R.id.button4:
                new Thread(){
                    @Override
                    public void run() {
                        DownloadHelper.downloadUpdateFile(new DownloadHelper.DownloadListener() {
                            @Override
                            public void start() {
                                Log.d("downloadUpdateFile","downloadUpdateFile start");
                            }

                            @Override
                            public void finish(File file) {
                                Log.d("downloadUpdateFile","downloadUpdateFile finish file =" + file.getPath() );

                            }

                            @Override
                            public void error(String msg) {

                                Log.d("downloadUpdateFile","downloadUpdateFile error " + msg);
                            }

                            @Override
                            public void progress(int progress) {
                                Log.d("downloadUpdateFile","downloadUpdateFile progress " + progress);
                            }
                        });
                    }
                }.start();

                break;

            case R.id.button5:
                new DownloadHelperAsync(){
                    @Override
                    protected void onPostExecute(File file) {
                        if (file == null){
                            Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        Log.d("DownloadHelperAsync","progress = " + values[0]);
                    }
                }.execute();
                break;
            case R.id.button6:
                DownloadHelperSys.downloadBySys(MainActivity.this);
                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                            long enqueueId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                            Uri uri = DownloadHelperSys.getDownloadUriById(context, enqueueId);
                            Log.d("DownloadHelperSys","DownloadHelperSys uri= " + uri);
                        }
                    }
                },new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                break;
        }
    }
}
