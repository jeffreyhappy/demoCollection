package www.lixiangfei.top.audiorecordtest;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import www.lixiangfei.top.audiorecordtest.history.RecordHistory;
import www.lixiangfei.top.audiorecordtest.history.RecordHistoryInstance;

public class JavaPlayActivity extends Activity implements View.OnClickListener{
    RecyclerView mRv;
    RecordHistoryAdapter mAdapter;
    TextView mTvSelected;
    RecordHistory mSelected;
    AudioTrack mAudioTrack;

    long currentPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_play);
        findViewById(R.id.btn_create_track).setOnClickListener(this);
        findViewById(R.id.btn_start_play).setOnClickListener(this);
        findViewById(R.id.btn_stop_play).setOnClickListener(this);
        findViewById(R.id.btn_pause_play).setOnClickListener(this);
        findViewById(R.id.btn_resume_play).setOnClickListener(this);
        mTvSelected = findViewById(R.id.tv_selected);
        mRv = findViewById(R.id.rv);
        mAdapter = new RecordHistoryAdapter(null);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
        mRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mSelected = (RecordHistory) adapter.getData().get(position);
                mTvSelected.setText(mSelected.name);
            }
        });
        RecordHistoryInstance.get(getApplicationContext())
                .recordHistoryDao()
                .load()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<RecordHistory>>() {
                    @Override
                    public void accept(List<RecordHistory> recordHistories) throws Exception {
                        if (recordHistories != null && recordHistories.size() > 0){
                            Log.d("JavaPlayActivity","path = " + recordHistories.get(0).path);
                        }
                        mAdapter.setNewData(recordHistories);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create_track:
                create();
                break;
            case R.id.btn_start_play:
                startPlay();
                break;
            case R.id.btn_stop_play:
                stopPlay();
                break;
            case R.id.btn_pause_play:
                pausePlay();
                break;
            case R.id.btn_resume_play:
                resumePlay();
                break;
        }
    }

    private void create(){
        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,AudioParams.sampleRateInHz,AudioFormat.CHANNEL_OUT_MONO,AudioParams.audioFormat,AudioParams.bufferSizeTrack,AudioTrack.MODE_STREAM);
        if (mAudioTrack.getState() != AudioTrack.STATE_INITIALIZED){
            Log.d("JavaPlayActivity","track create error " + mAudioTrack.getState());
        }
    }


    private void startPlay(){
        if (mSelected == null){
            Log.d("JavaPlayActivity","start play mSeleted = null");
            return;
        }
        if (mAudioTrack.getState() != AudioTrack.STATE_INITIALIZED){
            Log.d("JavaPlayActivity","startPlay status error " + mAudioTrack.getState());
            return;
        }

        File file = new File(mSelected.path);
        if(!file.exists()){
            Log.d("JavaPlayActivity","start play File not exist");
            return;
        }

        mAudioTrack.play();
        startWriteData(0);
    }

    private void startWriteData(final long skipSize){
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(new File(mSelected.path));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (fis == null){
                    Log.d("JavaPlayActivity","start play fis create error ");
                    return;
                }

                byte[] buffer = new byte[AudioParams.bufferSizeTrack];
                try {
                    int readSize;
                    fis.skip(skipSize);
                    currentPos = skipSize;
                    while (mAudioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING && (readSize =fis.read(buffer)) != -1){
                        Log.d("JavaPlayActivity","read buff size " + readSize);
                        mAudioTrack.write(buffer,0,readSize);
                        currentPos +=readSize;
                    }
                    fis.close();
                    //只有自己播放结束才调用停止
                    if (mAudioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING){
                        stopPlay();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void stopPlay(){
        if (mAudioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING){
            mAudioTrack.stop();
        }
        if (mAudioTrack.getState() == AudioTrack.STATE_INITIALIZED){
            mAudioTrack.release();
        }
    }


    private void pausePlay(){
        if (mAudioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING){
            mAudioTrack.pause();
        }
    }

    private void resumePlay(){
        if (mAudioTrack.getPlayState() == AudioTrack.PLAYSTATE_PAUSED){
            mAudioTrack.play();
            startWriteData(currentPos);
        }
    }
}
