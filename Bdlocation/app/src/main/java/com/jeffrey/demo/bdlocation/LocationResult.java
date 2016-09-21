package com.jeffrey.demo.bdlocation;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

/**
 * 百度定位 我们需要的信息
 * Created by Li on 2016/6/8.
 */
public class LocationResult implements Parcelable {
    private boolean err;
    private String errMsg;
    private String city;
    private LatLng latLng;
    private String address;


    public LocationResult(String city, LatLng latlng, boolean err, String errMsg, String address){
        this.city   = city;
        this.latLng = latlng;
        this.err    = err;
        this.errMsg = errMsg;
        this.address = address;
    }

    protected LocationResult(Parcel in) {
        err = in.readByte() != 0;
        errMsg = in.readString();
        city = in.readString();
        latLng = in.readParcelable(LatLng.class.getClassLoader());
        address = in.readString();
    }

    public static final Creator<LocationResult> CREATOR = new Creator<LocationResult>() {
        @Override
        public LocationResult createFromParcel(Parcel in) {
            return new LocationResult(in);
        }

        @Override
        public LocationResult[] newArray(int size) {
            return new LocationResult[size];
        }
    };

    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (err ? 1 : 0));
        dest.writeString(errMsg);
        dest.writeString(city);
        dest.writeParcelable(latLng, flags);
        dest.writeString(address);
    }
}
