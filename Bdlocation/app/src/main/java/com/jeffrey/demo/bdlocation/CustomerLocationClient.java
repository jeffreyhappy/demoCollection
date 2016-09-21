package com.jeffrey.demo.bdlocation;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;

import org.greenrobot.eventbus.EventBus;

/**
 * 用EventBus来传送消息
 * Created by Li on 2016/6/8.
 */
public class CustomerLocationClient {
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener ;

    public CustomerLocationListener mSfbmListener;

    public CustomerLocationClient(Context context) {

        mSfbmListener = new DefaultCustomerLocationListener();
        myListener    = new DefaultLocationListener(mSfbmListener);


        mLocationClient = new LocationClient(context);     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        initLocation();
    }


    public void start(){
        mLocationClient.start();
    }
    public void stop(){
        mLocationClient.stop();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(true);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }



    public class DefaultLocationListener implements BDLocationListener {

        CustomerLocationListener mListener;
        public DefaultLocationListener(CustomerLocationListener listener){
            this.mListener = listener;
        }
        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                mListener.onLocationSuccess(location.getCity(),new LatLng(location.getLatitude(),location.getLongitude()),location.getAddrStr());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                mListener.onLocationSuccess(location.getCity(),new LatLng(location.getLatitude(),location.getLongitude()),location.getAddrStr());
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                mListener.onLocationSuccess(location.getCity(),new LatLng(location.getLatitude(),location.getLongitude()),location.getAddrStr());
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                mListener.onLocationError("服务端网络定位失败");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                mListener.onLocationError("网络不同导致定位失败，请检查网络是否通畅");

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                mListener.onLocationError("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }else {
                mListener.onLocationError("未知错误，没有返回错误码");
            }
        }
    }


    /**
     * 用eventBus把消息发出去
     */
    private class DefaultCustomerLocationListener implements CustomerLocationListener {

        @Override
        public void onLocationError(String errMsg) {
            LocationResult loginResult = new LocationResult("",null,true,errMsg,null);
            EventBus.getDefault().post(loginResult);
        }

        @Override
        public void onLocationSuccess(String city, LatLng latLng, String address) {
            LocationResult loginResult = new LocationResult(city,latLng,false,"",address);
            EventBus.getDefault().post(loginResult);
        }

    }

    /**
     * 定位界面的结果，只需要错误，城市 坐标就够了
     * Created by Li on 2016/6/8.
     */
    public interface CustomerLocationListener {

        /**
         * 错误信息
         * @param errMsg
         */
        void onLocationError(String errMsg);

        /**
         * 定位到的城市,经纬度,以后有需要再加
         * @param city
         * @param latLng
         */
        void onLocationSuccess(String city, LatLng latLng, String address);

    }

}
