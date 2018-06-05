package com.fei.downloaddemo.download;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    DownloadListener mListener;
    public DownloadWorker(DownloadObj downloadObj,DownloadListener listener){
        this.mInfo = downloadObj;
        this.mListener = listener;
        this.mListener.onDownloadPrepare(mInfo.getUrl());
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
                this.mListener.onDownloadError(mInfo.getUrl(),DownloadCode.DownloadErrorCode.ERROR_CODE_HTTP_ERROR);
                return;
            }
            int length = connection.getContentLength();
            String contentRange = connection.getHeaderField("Content-Range");
            Log.d(TAG,"length = "+length + " contentRange = "+ contentRange);
            if (TextUtils.isEmpty(contentRange)){
                Log.d(TAG,"done  contentRange =" + null);
                this.mListener.onDownloadDone(mInfo.getUrl());
                return;
            }

            if (!contentRange.contains("/")){
                Log.d(TAG,"done  contentRange not contain /" );
                this.mListener.onDownloadError(mInfo.getUrl(), DownloadCode.DownloadErrorCode.ERROR_CODE_HTTP_ERROR);
                return;
            }
            int thisFileSize = getFileSize(contentRange);
            Log.d(TAG,"thisFileSize = " + thisFileSize );

            //基准是自己 output 就自己输出到文件
            //基准是自己 input  就文件读出来到自己

            FileOutputStream outputStream = new FileOutputStream(mInfo.getFilePath(),true);
            InputStream httpInputStream = connection.getInputStream();

            //byte也是java的基本类型之一，一个byte占据8个字节。一个int占据32个字节
            //而KB,MB中的B是Byte，是字节的意思，坑比啊
            //update byte就是字节，文件是以字节来计算大小的
            byte[] readBytes = new byte[1024];
            int len = 0;
            int totalSize = (int) fileStartRange;
            while ((len = httpInputStream.read(readBytes)) != -1  ){
                outputStream.write(readBytes,0,len);
                totalSize+=len;
                mListener.onDownloadRunning(mInfo.getUrl(),calcProgress(totalSize,thisFileSize));
            }
            mListener.onDownloadDone(mInfo.getUrl());
            Log.d(TAG,"total size = " + totalSize);
            outputStream.close();
            httpInputStream.close();
            Log.d(TAG,"success  " );

        } catch (MalformedURLException e) {
            mListener.onDownloadDone(mInfo.getUrl());
            Log.d(TAG,"fail  e =" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            mListener.onDownloadDone(mInfo.getUrl());
            Log.d(TAG,"fail  e =" + e.getMessage());
            e.printStackTrace();
        }

    }

    int getFileSize(String contentRange){
        int size = 0;
        String fileSize = contentRange.substring(contentRange.indexOf("/") + 1);
        size = Integer.parseInt(fileSize);
        return size;
    }


    int calcProgress(int current,int total){

        BigDecimal currentBD = new BigDecimal(current);
        BigDecimal totalBD = new BigDecimal(total);
        int progress = currentBD.divide(totalBD,2, RoundingMode.DOWN).multiply(new BigDecimal(100)).toBigInteger().intValue();
//        Log.d(TAG,"current = " + current + " total =" + total);
        return progress;
    }
}
