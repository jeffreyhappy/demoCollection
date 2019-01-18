package www.lixiangfei.top.audiorecordtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class NativeAudioPlayActivity extends AppCompatActivity implements View.OnClickListener {

    static {
        System.loadLibrary("native-play");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_play);
        findViewById(R.id.native_play).setOnClickListener(this);
        findViewById(R.id.native_test_open_file).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.native_play:
                initEngine();
                startPlay();
                break;
            case R.id.native_test_open_file:
                testOpenFile();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        destory();
    }

    native void initEngine();
    native void startPlay();
    native void testOpenFile();
    native void destory();
}
