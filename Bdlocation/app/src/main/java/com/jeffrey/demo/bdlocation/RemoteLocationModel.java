package com.jeffrey.demo.bdlocation;

import android.content.Context;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 百度的定位
 * Created by Li on 2016/6/8.
 */
public class RemoteLocationModel implements ILocationModel {

    private CustomerLocationClient mClient;
    private Context mContext;
    private ICallBack<LocationResult> mCallBack;
    public RemoteLocationModel(Context context, ICallBack<LocationResult> callBack){
        this.mContext = context;
        this.mCallBack = callBack;
        mClient        = new CustomerLocationClient(context);
    }


    @Subscribe
    public void onLocationResult(LocationResult result) {
        if (result.isErr()){
            mCallBack.onErrorResponse(new Error(result.getErrMsg()));
        }else {
            mCallBack.onReturnData(result);
        }
    }
    @Override
    public void startLocation() {
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        mClient.start();
    }

    @Override
    public void stopLocation() {
        mClient.stop();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }

    }


}
