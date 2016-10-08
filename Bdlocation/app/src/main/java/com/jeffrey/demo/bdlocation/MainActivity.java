package com.jeffrey.demo.bdlocation;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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

        locationModel = new LocationModel(this, new ICallBack<LocationResult>() {
            @Override
            public void onReturnData(LocationResult data) {
                TextView  tvResult = (TextView) findViewById(R.id.textView3);
                tvResult.setText("city " +data.getCity() + " \n " +
                                    " address " + data.getAddress() + " \n"+
                                    "latlng " + data.getLatLng().toString());
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
