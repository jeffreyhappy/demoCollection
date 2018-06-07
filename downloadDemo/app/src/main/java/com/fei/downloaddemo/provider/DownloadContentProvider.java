package com.fei.downloaddemo.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fei.downloaddemo.data.DownloadDatabase;
import com.fei.downloaddemo.data.DownloadHistory;
import com.fei.downloaddemo.data.DownloadHistoryDao;

public class DownloadContentProvider extends ContentProvider{

    public static final String  AUTHORITY = "com.fei.downloaddemo.provider";

    public static final Uri URI_DOWNLOAD= Uri.parse("content://"+AUTHORITY+"/"+ DownloadHistory.TABLE_NAME);

    private static final int CODE_DOWNLOAD_HISTORY = 1;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY,DownloadHistory.TABLE_NAME,CODE_DOWNLOAD_HISTORY);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int code = MATCHER.match(uri);
        if (code == CODE_DOWNLOAD_HISTORY && getContext() != null){
            DownloadHistoryDao dao = DownloadDatabase.getInstance(getContext()).downloadHistoryDao();
            //这里用不了parseID ，因为为用的url为主键
//            if (ContentUris.parseId(uri) != -1 )
            if (selectionArgs != null && selectionArgs.length >0){
                return dao.selectByUrl(selectionArgs[0]);
            }else {
                return dao.selectAll();
            }
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int code = MATCHER.match(uri);
        //返回对于的表
        if (code == CODE_DOWNLOAD_HISTORY && getContext() != null){
            return DownloadHistory.TABLE_NAME;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int code = MATCHER.match(uri);
        //返回对于的表
        if (code == CODE_DOWNLOAD_HISTORY && getContext() != null){
            DownloadHistoryDao dao = DownloadDatabase.getInstance(getContext()).downloadHistoryDao();
            dao.insert(DownloadHistory.fromContentValue(values));
            return uri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = MATCHER.match(uri);
        if (code == CODE_DOWNLOAD_HISTORY && getContext() != null
                &&values.containsKey("total_size") && values.containsKey("url")){
            DownloadHistoryDao dao = DownloadDatabase.getInstance(getContext()).downloadHistoryDao();
            return dao.updateTotalSize(values.getAsString("url"),values.getAsInteger("total_size"));
        }

        if (code == CODE_DOWNLOAD_HISTORY && getContext() != null
                &&values.containsKey("status") && values.containsKey("url")){
            DownloadHistoryDao dao = DownloadDatabase.getInstance(getContext()).downloadHistoryDao();
            return dao.updateStatus(values.getAsString("url"),values.getAsInteger("status"));
        }
        return 0;
    }
}
