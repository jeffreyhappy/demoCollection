package com.jeffrey.demo.bdlocation;


/**
 * Created by X-FAN on 2016/5/20.
 */
public interface ICallBack<T> {
    void onReturnData(T data);

    void onErrorResponse(Error error);
}
