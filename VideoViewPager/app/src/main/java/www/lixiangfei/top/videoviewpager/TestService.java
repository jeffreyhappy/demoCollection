package www.lixiangfei.top.videoviewpager;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class TestService extends IntentService {

    public TestService() {
        super("testService");
        Log.d("feifeifei","TestService create default");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TestService(String name) {
        super(name);
        Log.d("feifeifei","TestService create");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("feifeifei","TestService onHandleIntent start");
        int v = 0;
        for (int i = 0 ; i < 1000 ; i++){
            for (int j = 0 ; j < 1000 ; j++){
                v = i + j;
            }
        }
        TestThread testThread = new TestThread();
        testThread.start();
        Log.d("feifeifei","TestService onHandleIntent end " + v);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d("feifeifei","TestService  onTaskRemoved");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("feifeifei","TestService  onDestroy");
    }

    public static class TestThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                Log.d("feifeifei","TestService TestThread in");
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                Log.d("feifeifei","TestService TestThread error");
                e.printStackTrace();
            }
            Log.d("feifeifei","TestService TestThread end");

        }
    }
}
