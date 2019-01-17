package www.lixiangfei.top.audiorecordtest;

import android.app.Activity;
import android.content.Context;
import android.media.AudioRecord;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import www.lixiangfei.top.audiorecordtest.history.RecordHistory;
import www.lixiangfei.top.audiorecordtest.history.RecordHistoryInstance;

public class JavaRecordActivity extends Activity implements View.OnClickListener {

    AudioRecord mAudioRecord;
    int mBufferSizeInBytes;
    TextView mTvInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_record);
        findViewById(R.id.btn_start_record).setOnClickListener(this);
        findViewById(R.id.btn_stop_record).setOnClickListener(this);
        findViewById(R.id.btn_create_record).setOnClickListener(this);
        mTvInfo = findViewById(R.id.tv_info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create_record:
                initAudioRecord();
                break;
            case R.id.btn_start_record:
                startRecord();
                break;
            case R.id.btn_stop_record:
                stopRecord();
                break;
        }
    }

    private void initAudioRecord(){
        mBufferSizeInBytes = AudioParams.bufferSizeInBytes;
        if (mBufferSizeInBytes == AudioRecord.ERROR_BAD_VALUE || mBufferSizeInBytes == AudioRecord.ERROR){
            Log.d("fei","bufferSizeInBytes get error");
            mTvInfo.setText("bufferSizeInBytes get error");
            return;
        }
        mAudioRecord = new AudioRecord(AudioParams.audioSource,AudioParams.sampleRateInHz,AudioParams.channelConfig,AudioParams.audioFormat,mBufferSizeInBytes);
        if (mAudioRecord.getState() == AudioRecord.STATE_INITIALIZED){
            mTvInfo.setText("create success");
        }else {
            mTvInfo.setText("create error " + mAudioRecord.getState());
        }
    }

    private void startRecord(){
        mTvInfo.setText("startRecord");
        int state = mAudioRecord.getState();
        if (state == AudioRecord.STATE_UNINITIALIZED){
            Log.d("fei","startRecord state = STATE_UNINITIALIZED" );
            mTvInfo.setText("startRecord state = STATE_UNINITIALIZED");
            return;
        }
        mAudioRecord.startRecording();
        ReadRunnable readRunnable = new ReadRunnable(this,mAudioRecord,mBufferSizeInBytes);
        new Thread(readRunnable).start();
    }


    private void stopRecord(){
        mTvInfo.setText("stopRecord");
        if (mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING){
            mAudioRecord.stop();
        }
        if (mAudioRecord.getState() == AudioRecord.STATE_INITIALIZED){
            mAudioRecord.release();
        }
    }

    private static class ReadRunnable implements Runnable{
        private AudioRecord audioRecord;
        private int bufferSizeInBytes;
        private File audioFile;
        private Context context;
        public ReadRunnable(Context context, AudioRecord audioRecord, int bufferSizeInBytes){
            this.audioRecord = audioRecord;
            this.bufferSizeInBytes = bufferSizeInBytes;

            audioFile = new File(context.getExternalFilesDir(""),"audio"+getTime()+".pcm");
            this.context = context;
        }


        @Override
        public void run() {
            if (!audioFile.exists()){
                try {
                    audioFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d("fei","record file path " + audioFile.getPath() );
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(audioFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (fileOutputStream == null){
                Log.d("fei","fileOutputStream create error" );
                return;
            }
            Log.d("fei","start record" );

            saveHistory();

            byte[] buffer = new byte[bufferSizeInBytes];
            while (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING){
                int readSize =  audioRecord.read(buffer,0,bufferSizeInBytes);
                Log.d("fei","read buff size " + readSize);
                if (readSize !=  AudioRecord.ERROR_INVALID_OPERATION && readSize != AudioRecord.ERROR_BAD_VALUE){
                    try {
                        fileOutputStream.write(buffer,0,readSize);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.d("fei","record thread finish close stream" );
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void saveHistory(){
            RecordHistory recordHistory = new RecordHistory();
            recordHistory.name = audioFile.getName();
            recordHistory.path = audioFile.getPath();
            RecordHistoryInstance.get(context.getApplicationContext()).recordHistoryDao().insertUser(recordHistory);
        }
    }


    private static String getTime(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyymmddHHmm");
        long now=System.currentTimeMillis();
        return sdf.format(now);
    }
}
