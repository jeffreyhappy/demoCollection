# BdLocation
## 百度地图定位的基本使用  
  
    初始化
    LocationModel  locationModel ;
    locationModel = new LocationModel(this, new ICallBack<LocationResult>() {
                @Override
                public void onReturnData(LocationResult data) {
                    TextView  tvResult = (TextView) findViewById(R.id.textView3);
                    tvResult.setText("city " +data.getCity() + " \n " +
                                        " address " + data.getAddress() + " \n"+
                                        "latlng " + data.getLatLng().toString());
                }
    
                @Override
                public void onErrorResponse(Error error) {
                    TextView  tvResult = (TextView) findViewById(R.id.textView3);
                    tvResult.setText(error.getMessage());
                }
            },false);
            
     最后使用
     locationModel.startLocation();       
 
 
 


####您当前所选择下载的开发包包含如下功能

 离线定位：在基础定位能力基础之上，提供离线定位能力，可在网络环境不佳时，进行精准定位；
 基础地图：包括基础地图、卫星图、路况图、室内图和各种覆盖物，及与地图相关的操作、事件监听，支持个性化地图，适配Android Wear；
 计算工具：包括距离计算、坐标转换、调起百度地图导航等功能；
 
####产品说明  
 基础定位+离线定位+室内定位 = 全量定位 -> 原Android定位SDK，当前版本v7.0
 基础地图+检索功能+LBS云检索+计算工具 -> 原Android SDK（地图SDK），当前版本v4.0.0
 导航功能（含TTS） -> 原导航SDK，当前版本v3.2.0
 全景图功能 -> 原全景图SDK，当前版本v2.4.0
 周边雷达功能-->当前版本v1.0.0

 
##注意

切换自己的百度地图key  

在Android 6.0系统中，需要动态获取的权限涉及到：
1  获取手机状态：
   Manifest.permission.READ_PHONE_STATE
 
2 获取位置信息：
 Manifest.permission.ACCESS_COARSE_LOCATION
 Manifest.permission.ACCESS_FINE_LOCATION
 
3 读写SD卡：
 Manifest.permission.READ_EXTERNAL_STORAGE
 Manifest.permission.WRITE_EXTERNAL_STORAGE
 

