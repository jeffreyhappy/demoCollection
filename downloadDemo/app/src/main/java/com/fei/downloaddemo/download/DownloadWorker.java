package com.fei.downloaddemo.download;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 创建日期：2018/6/4 on 15:28
 * 描述:
 * 作者:Li
 */

public class DownloadWorker implements Runnable {
    final String TAG = DownloadWorker.class.getSimpleName();
    DownloadObj  mInfo;
    public DownloadWorker(DownloadObj downloadObj){
        this.mInfo = downloadObj;
    }


    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(mInfo.getUrl());
            File targetFile  = new File(mInfo.getFilePath());

            long fileStartRange = 0;
            if (targetFile.exists()){

                fileStartRange = new FileInputStream(targetFile).available();
            }
            Log.d(TAG,"file exist and start range from = " + fileStartRange);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Range","bytes=" + fileStartRange+"-" );
            int code = connection.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK && code != HttpURLConnection.HTTP_PARTIAL){
                Log.d(TAG,"fail  code =" + code);
                return;
            }
            int length = connection.getContentLength();
            String contentRange = connection.getHeaderField("Content-Range");
            Log.d(TAG,"length = "+length + " contentRange = "+ contentRange);
            if (TextUtils.isEmpty(contentRange)){
                Log.d(TAG,"done  contentRange =" + null);
                return;
            }
            //基准是自己 output 就自己输出到文件
            //基准是自己 input  就文件读出来到自己

            FileOutputStream outputStream = new FileOutputStream(mInfo.getFilePath(),true);
            InputStream httpInputStream = connection.getInputStream();

            //byte也是java的基本类型之一，一个byte占据8个字节。一个int占据32个字节
            //而KB,MB中的B是Byte，是字节的意思，坑比啊
            byte[] readBytes = new byte[1024];
            int len = 0;
            int totalSize = 0;
            while ((len = httpInputStream.read(readBytes)) != -1  ){
                outputStream.write(readBytes,0,len);
                totalSize+=len;
            }
            Log.d(TAG,"total size = " + totalSize);
            outputStream.close();
            httpInputStream.close();
            Log.d(TAG,"success  " );

        } catch (MalformedURLException e) {
            Log.d(TAG,"fail  e =" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG,"fail  e =" + e.getMessage());
            e.printStackTrace();
        }

    }


}
