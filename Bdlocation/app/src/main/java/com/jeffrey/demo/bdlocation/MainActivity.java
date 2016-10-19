package com.jeffrey.demo.bdlocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements  ActivityCompat.OnRequestPermissionsResultCallback {
    private int REQUEST_PERMISSION = 2;

    LocationModel  locationModel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                locationModel.startLocation();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,locationViewActivity.class));
            }
        });



        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION);
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            //这里我们就请求一个权限  所以grantResult的大小应该为1
            if (grantResults.length == grantResults.length) {
                locationModel = new LocationModel(this, new ICallBack<LocationResult>() {
                    @Override
                    public void onReturnData(LocationResult data) {
                        TextView  tvResult = (TextView) findViewById(R.id.textView3);
                        tvResult.setText("city " +data.getCity() + " \n " +
                                " address " + data.getAddress() + " \n"+
                                "latlng " + data.getLatLng());
                        locationModel.stopLocation();
                    }

                    @Override
                    public void onErrorResponse(Error error) {
                        TextView  tvResult = (TextView) findViewById(R.id.textView3);
                        tvResult.setText(error.getMessage());
                        locationModel.stopLocation();

                    }
                },false);
            }
        }
    }

}
