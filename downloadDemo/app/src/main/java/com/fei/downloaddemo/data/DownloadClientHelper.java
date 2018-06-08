package com.fei.downloaddemo.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.fei.downloaddemo.provider.DownloadContentProvider;

public class DownloadClientHelper {
    private final String TAG = DownloadClientHelper.class.getSimpleName();

    private Context mContext;

    public DownloadClientHelper(Context context){
        this.mContext = context;
    }

    public void testContentProvider(){
        ContentResolver resolver = this.mContext.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put("url","myurl");
        cv.put("total_size",2000);
        cv.put("status",200);
        resolver.insert(DownloadContentProvider.URI_DOWNLOAD,cv);


        Cursor cursor =  resolver.query(DownloadContentProvider.URI_DOWNLOAD,null,null,null,null);
        while (cursor.moveToNext()){
            int count = cursor.getColumnCount();
            String url = cursor.getString(cursor.getColumnIndex("url"));
            int totalSize  = cursor.getInt(cursor.getColumnIndex("total_size"));
            int status  = cursor.getInt(cursor.getColumnIndex("status"));

            Log.d(TAG,String.format("url = %s  totalSize= %d status = %d \n",url,totalSize,status));
        }
        cursor.close();
    }

    public void insertOneHistory(String key,int status){
        ContentResolver resolver = this.mContext.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put("url",key);
        cv.put("status",status);
        resolver.insert(DownloadContentProvider.URI_DOWNLOAD,cv);
    }

    public void updateTotalSize(String key,int totalSize){
        ContentResolver resolver = this.mContext.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put("url",key);
        cv.put("total_size",totalSize);
        int rows = resolver.update(DownloadContentProvider.URI_DOWNLOAD,cv,null,null);
    }

    public void updateStatus(String key,int status){
        ContentResolver resolver = this.mContext.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put("url",key);
        cv.put("status",status);
        int rows = resolver.update(DownloadContentProvider.URI_DOWNLOAD,cv,null,null);
    }


    public void printAllRecord(){
        ContentResolver resolver = this.mContext.getContentResolver();
        Cursor cursor =  resolver.query(DownloadContentProvider.URI_DOWNLOAD,null,null,null,null);
        while (cursor.moveToNext()){
            int count = cursor.getColumnCount();
            String url = cursor.getString(cursor.getColumnIndex("url"));
            int totalSize  = cursor.getInt(cursor.getColumnIndex("total_size"));
            int status  = cursor.getInt(cursor.getColumnIndex("status"));

            Log.d(TAG,String.format("url = %s  totalSize= %d status = %d \n",url,totalSize,status));
        }
        cursor.close();
    }
}
