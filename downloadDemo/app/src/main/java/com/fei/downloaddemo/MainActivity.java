package com.fei.downloaddemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.fei.downloaddemo.download.DownloadService;
import com.fei.downloaddemo.home.HomeListAdapter;
import com.fei.downloaddemo.home.HomeListClickListener;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRv;
    HomeListAdapter mAdapter;
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
                //todo
                Toast.makeText(MainActivity.this,"click " + position ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildItemClick(View view, int position) {
                Toast.makeText(MainActivity.this,"click " +view.getClass()+" " + position ,Toast.LENGTH_SHORT).show();
                String url = mAdapter.getItem(position).getUrl();
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                intent.putExtra(DownloadService.PARAM_URL,url);
                startService(intent);

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
}
