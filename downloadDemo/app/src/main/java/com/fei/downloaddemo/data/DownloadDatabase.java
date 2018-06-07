package com.fei.downloaddemo.data;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {DownloadHistory.class} ,version = 1)
public abstract class DownloadDatabase extends RoomDatabase{
    public abstract DownloadHistoryDao downloadHistoryDao();


    private static DownloadDatabase db;


    public static DownloadDatabase getInstance(Context context){
        if (db == null){
            synchronized (DownloadDatabase.class){
                if (db == null){
                    db  = Room.databaseBuilder(context,DownloadDatabase.class,"downloadDatabase").build();
                }
            }
        }

        return db;
    }
}
