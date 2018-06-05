package com.fei.downloaddemo;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fei.downloaddemo.download.DownloadCode;
import com.fei.downloaddemo.download.DownloadService;
import com.fei.downloaddemo.home.HomeListAdapter;
import com.fei.downloaddemo.home.HomeListClickListener;
import com.fei.downloaddemo.home.HomeListItem;
import com.fei.downloaddemo.home.HomeView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements HomeView{
    private final String TAG = MainActivity.class.getSimpleName();
    RecyclerView mRv;
    HomeListAdapter mAdapter;
    IDownloadService mService;
    private Handler handler = new Handler();
    private IDownloadCallback mCallback = new IDownloadCallback.Stub() {
        @Override
        public void onDownloadPrepare(String key) throws RemoteException {
            onItemPrepare(key);
        }

        @Override
        public void onDownloadRunning(String key, int progress) throws RemoteException {
            onItemProgress(key,progress);
        }

        @Override
        public void onDownloadDone(String key) throws RemoteException {
            onItemDone(key);
        }

        @Override
        public void onDownloadError(String key, int errorCode) throws RemoteException {

        }

        @Override
        public String getKey() throws RemoteException {
            return null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRv = findViewById(R.id.rv);
        mAdapter = new HomeListAdapter(DataCenter.getHomeListItems());
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addOnItemTouchListener(new HomeListClickListener(this, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(MainActivity.this,"click " + position ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildItemClick(View view, int position) {
                Toast.makeText(MainActivity.this,"click " +view.getClass()+" " + position ,Toast.LENGTH_SHORT).show();
                String url = mAdapter.getItem(position).getUrl();
                startTask(url);

            }

            @Override
            public int[] listenChildID() {
                return new int[]{R.id.btn};
            }
        }));
        mRv.setAdapter(mAdapter);
        enSurePermission();
    }

    private void enSurePermission(){
        int result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    private void startTask(String url){
        Intent intent = new Intent(MainActivity.this, DownloadService.class);
        intent.putExtra(DownloadService.PARAM_URL,url);
        //先start
        startService(intent);

        //然后绑定监听回调
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = IDownloadService.Stub.asInterface(service);
                try {
                    mService.addListener(mCallback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                if (mService != null){
                    try {
                        mService.removeListener(mCallback);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        },BIND_AUTO_CREATE);
    }

    @Override
    public void onItemProgress(String key, int progress) {
        Log.d(TAG,"onItemProgress");
        ArrayList<HomeListItem> items = mAdapter.getList();
        for (int i = 0 ; i < items.size() ;i++){
            if (items.get(i).getUrl().equals(key)){
                items.get(i).setProgress(progress);
                items.get(i).setStatus(DownloadCode.DownloadStatus.DOWNLOAD);
                notifyItemChanged(i);
            }
        }
    }

    @Override
    public void onItemPrepare(String key) {
        Log.d(TAG,"onItemPrepare");
        ArrayList<HomeListItem> items = mAdapter.getList();
        for (int i = 0 ; i < items.size() ;i++){
            if (items.get(i).getUrl().equals(key)){
                items.get(i).setStatus(DownloadCode.DownloadStatus.PREPARE);
                notifyItemChanged(i);
            }
        }
    }

    @Override
    public void onItemDone(String key) {
        Log.d(TAG,"onItemDone");
        ArrayList<HomeListItem> items = mAdapter.getList();
        for (int i = 0 ; i < items.size() ;i++){
            if (items.get(i).getUrl().equals(key)){
                items.get(i).setStatus(DownloadCode.DownloadStatus.FINISH);
                notifyItemChanged(i);
            }
        }
    }

    private void notifyItemChanged(final int i){
        handler.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyItemChanged(i);
            }
        });
    }
}
