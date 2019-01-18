package www.lixiangfei.top.audiorecordtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.java_play).setOnClickListener(this);
        findViewById(R.id.java_record).setOnClickListener(this);
        findViewById(R.id.native_play).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.java_play:
                startActivity(new Intent(MainActivity.this,JavaPlayActivity.class));
                break;
            case R.id.java_record:
                startActivity(new Intent(MainActivity.this,JavaRecordActivity.class));
                break;
            case R.id.native_play:
                startActivity(new Intent(MainActivity.this,NativeAudioPlayActivity.class));
                break;
        }
    }
}
