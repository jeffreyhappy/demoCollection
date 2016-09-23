package com.jeffrey.demo.retrofitdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Build;


/**
 * 手机设备参数的类，例如 屏幕，ip，imei 等等
 *
 * @author yanqi1
 */
public class DeviceInfo {
    private  String imei = "";//设备imei
    private  String mac = "";
    private  String channel = "";
    private  String version = "";//versionName
    private  int versionCode;//versionCode
    private  String device = "";//设备信息

    private  int screenHeight;
    private  int screenWidth;

    private static DeviceInfo deviceInfo;

    public static DeviceInfo init(Context context) {
        if (deviceInfo == null) {
            deviceInfo = new DeviceInfo(context);
        }
        return deviceInfo;
    }

    /**
     * 初始化各项值
     */
    private DeviceInfo(Context context) {
        mac = DeviceUtils.getMac(context);
        channel = DeviceUtils.getAppChannel(context);
        version = DeviceUtils.getVersionName(context);
        versionCode = DeviceUtils.getVersionCode(context);
        device = android.os.Build.MODEL;

        screenHeight = DeviceUtils.getDeviceHeight(context);
        screenWidth = DeviceUtils.getDeviceWidth(context);


        setMac(mac);
        setChannel(channel);

        setScreenHeight(screenHeight);
        setScreenWidth(screenWidth);
    }

    /**
     * IMEI需要获取权限，且需要Activity的context
     *
     * @param context
     */
    public  String initIMEI(Activity context) {
        imei = DeviceUtils.getImei(context);
        setImei(imei);
        return imei;

    }

    public  void initIMEI(String strIMEI) {
        imei = strIMEI;
        setImei(imei);

    }

    public  String getImei() {
        return imei;
    }

    public  void setImei(String imei) {
        this.imei = imei;
    }

    public  String getMac() {
        return mac;
    }

    public  void setMac(String mac) {
        this.mac = mac;
    }

    public  String getChannel() {
        return channel;
    }

    public  void setChannel(String channel) {
        this.channel = channel;
    }

    public  String getVersion() {
        return version;
    }

    public  void setVersion(String version) {
        this.version = version;
    }

    public  int getVersionCode() {
        return versionCode;
    }

    public  void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public  String getDevice() {
        return device;
    }

    public  void setDevice(String device) {
        this.device = device;
    }

    public  int getScreenHeight() {
        return screenHeight;
    }

    public  void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public  int getScreenWidth() {
        return screenWidth;
    }

    public  void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }


    public  int osVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    public  String osVersionName() {
        return Build.VERSION.RELEASE;
    }
}
