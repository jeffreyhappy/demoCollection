package com.jeffrey.demo.bdlocation;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class locationViewActivity extends AppCompatActivity {


        // 定位相关
        private MyLocationConfiguration.LocationMode mCurrentMode;
        BitmapDescriptor mCurrentMarker;
        private static final int accuracyCircleFillColor = 0xAAFFFF88;
        private static final int accuracyCircleStrokeColor = 0xAA00FF00;

        MapView mMapView;
        BaiduMap mBaiduMap;

        LocationModel  locationModel ;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            SDKInitializer.initialize(this.getApplicationContext());
            setContentView(R.layout.activity_location_view);
            mMapView = (MapView) findViewById(R.id.mapView);
            mBaiduMap = mMapView.getMap();

//                            requestLocButton.setText("跟随");
                            mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                            mBaiduMap
                                    .setMyLocationConfigeration(new MyLocationConfiguration(
                                            mCurrentMode, true, null));

//                            requestLocButton.setText("普通");
                            mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                            mBaiduMap
                                    .setMyLocationConfigeration(new MyLocationConfiguration(
                                            mCurrentMode, true, mCurrentMarker));

//                            requestLocButton.setText("罗盘");
                            mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                            mBaiduMap
                                    .setMyLocationConfigeration(new MyLocationConfiguration(
                                            mCurrentMode, true, mCurrentMarker));


                        // 传入null则，恢复默认图标
                        mCurrentMarker = null;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, null));
                        // 修改为自定义marker
                        mCurrentMarker = BitmapDescriptorFactory
                                .fromResource(R.mipmap.ic_launcher);
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker,
                                        accuracyCircleFillColor, accuracyCircleStrokeColor));

            // 地图初始化
            mMapView = (MapView) findViewById(R.id.mapView);
            mBaiduMap = mMapView.getMap();
            // 开启定位图层
            mBaiduMap.setMyLocationEnabled(true);
            //开启路况
            mBaiduMap.setTrafficEnabled(true);
            // 定位初始化

            locationModel = new LocationModel(this, new ICallBack<LocationResult>() {
                @Override
                public void onReturnData(LocationResult location) {
                    if (location == null || mMapView == null) {
                        return;
                    }

                    MyLocationData locData = new MyLocationData.Builder()
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                            .direction(100).latitude(location.getLatLng().latitude)
                            .longitude(location.getLatLng().longitude).build();
                    mBaiduMap.setMyLocationData(locData);

                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(location.getLatLng()).zoom(18.0f);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));


                }

                @Override
                public void onErrorResponse(Error error) {

                }
            },true);
            locationModel.startLocation();
//            mLocClient = new LocationClient(this);
//            mLocClient.registerLocationListener(myListener);
//            LocationClientOption option = new LocationClientOption();
//            option.setOpenGps(true); // 打开gps
//            option.setCoorType("bd09ll"); // 设置坐标类型
//            option.setScanSpan(1000);
//            mLocClient.setLocOption(option);
//            mLocClient.start();
        }


        @Override
        protected void onPause() {
            mMapView.onPause();
            super.onPause();
        }

        @Override
        protected void onResume() {
            mMapView.onResume();
            super.onResume();
        }

        @Override
        protected void onDestroy() {
            // 退出时销毁定位
            locationModel.stopLocation();
            // 关闭定位图层
            mBaiduMap.setMyLocationEnabled(false);
            mMapView.onDestroy();
            mMapView = null;
            super.onDestroy();
        }

}
