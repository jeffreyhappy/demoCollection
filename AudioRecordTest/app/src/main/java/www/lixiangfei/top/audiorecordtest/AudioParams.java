package www.lixiangfei.top.audiorecordtest;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

public class AudioParams {
    public static final int audioSource = MediaRecorder.AudioSource.MIC;
    public static final int sampleRateInHz = 44100;
    public static final int channelConfig = AudioFormat.CHANNEL_IN_MONO;//单声道
    public static final int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    public static final int bufferSizeInBytes = AudioRecord.getMinBufferSize(AudioParams.sampleRateInHz, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
    public static final int bufferSizeTrack = AudioTrack.getMinBufferSize(AudioParams.sampleRateInHz, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

}
