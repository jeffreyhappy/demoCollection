package com.wswy.exoplayerdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/12/15.
 */

public class TimeUtils {
    private final static String VIDEO_URL = "http://7d9qw5.com1.z0.glb.clouddn.com/wyrz_bd.mp4";
    private final static String VIDEO_URL2 = "http://7d9qw5.com1.z0.glb.clouddn.com/hzw_bd.mp4";
    public static final int REQUEST_CODE_TIME = 11;
    private final static String[] videos = {VIDEO_URL,VIDEO_URL2};

    private static int ticketCount = 0;
    public static void  startNextTimeTicket(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        // 根据用户选择的时间来设置Calendar对象
        int minute = c.get(Calendar.MINUTE);
        if (minute<59){
            c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
            c.set(Calendar.MINUTE, minute+1);
            c.set(Calendar.SECOND,0);
        }else {
            c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY)+1);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
        }
        Intent intent = new Intent("com.test.time");
        intent.putExtra("video_url",ticketCount%2 == 0 ? videos[0]:videos[1]);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,REQUEST_CODE_TIME,intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
//        alarmManager.set(AlarmManager.RTC,System.currentTimeMillis()+20,pendingIntent);
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(System.currentTimeMillis());


    }

}
