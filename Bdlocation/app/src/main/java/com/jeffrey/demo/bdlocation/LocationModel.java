package com.jeffrey.demo.bdlocation;

import android.content.Context;


/**
 * 定位model
 * 定位请使用这个类
 * Created by Li on 2016/6/8.
 */
public class LocationModel implements ILocationModel {

    private ILocationModel mLocationModel;

    public LocationModel(Context context, final ICallBack<LocationResult> iCallBack, boolean forceRemoteLocation){
        if (forceRemoteLocation || !isLocalUseful()){
            mLocationModel = new RemoteLocationModel(context, new ICallBack<LocationResult>() {
                @Override
                public void onReturnData(LocationResult data) {
                    //// TODO: 2016/6/8 保存到本地
                    iCallBack.onReturnData(data);
                }

                @Override
                public void onErrorResponse(Error error) {

                }
            });
        }else {
//            mLocationModel = new LocalLocationModel(context, new ICallBack<LocationResult>() {
//                @Override
//                public void onReturnData(LocationResult data) {
//
//                }
//
//                @Override
//                public void onErrorResponse(Error error) {
//
//                }
//            });
        }
    }


    /**
     * 本地定位信息还有用么
     * 小于1天就有效
     * @return true 有效
     */
    private boolean isLocalUseful(){
        //// TODO: 2016/6/8 先用远程的
        return false;
    }

    @Override
    public void startLocation() {
        mLocationModel.startLocation();

    }

    @Override
    public void stopLocation() {
        mLocationModel.stopLocation();
    }
}
