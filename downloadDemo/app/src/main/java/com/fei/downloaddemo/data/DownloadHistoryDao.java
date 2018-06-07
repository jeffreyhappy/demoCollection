package com.fei.downloaddemo.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

@Dao
public interface DownloadHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(DownloadHistory history);


    @Query("select * from " + DownloadHistory.TABLE_NAME + " where url = :url")
    Cursor selectByUrl(String url);


    @Query("select * from " + DownloadHistory.TABLE_NAME)
    Cursor selectAll();

    @Query("update " + DownloadHistory.TABLE_NAME +" set total_size = :totalSize where url = :url"  )
    int updateTotalSize(String url,int totalSize);

    @Query("update " + DownloadHistory.TABLE_NAME +" set status = :status where url = :url"  )
    int updateStatus(String url,int status);
}
