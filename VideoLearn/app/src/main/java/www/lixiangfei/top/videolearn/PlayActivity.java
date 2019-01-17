package www.lixiangfei.top.videolearn;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.SurfaceTexture;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class PlayActivity extends AppCompatActivity {

    private final String TAG = PlayActivity.class.getSimpleName();
    boolean isPlay = false;
    boolean VERBOSE = true;
    MediaExtractor extractor;
    TextureView textureView;
    // May be set/read by different threads.
    private volatile boolean mIsStopRequested;
    // Declare this here to reduce allocations.
    private MediaCodec.BufferInfo mBufferInfo = new MediaCodec.BufferInfo();
    private boolean mLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Button btn = findViewById(R.id.btn);
        textureView = findViewById(R.id.tv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay){
                    requestStop();
                }else {
                    requestPlay();
                }
            }
        });
    }

    private void requestPlay(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                play();
            }
        }).start();
    }

    private void play(){
        //1 构建MediaExtractor ,这个是资源获取
        //2 选择track
        //3 使用track
        //4 通过track对应的格式初始化MediaCodec,这个是解码器,并且把解码器与Surface绑定
        //5 启动解码器
        //6 通过MediaExtractor提取流放入解码器 MediaExtractor -> MediaCodec -> Surface
        extractor = new MediaExtractor();
        File audioFile = getMediaFile();
        if (audioFile == null){
            Log.d(TAG,"audioFile == null ");
            return;
        }
        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                extractor.setDataSource(audioFile.toString());
                int trackID = selectTrack(extractor);
                if (trackID < 0 ){
                    Log.d(TAG,"trackId < 0") ;
                }else {
                    extractor.selectTrack(trackID);
                }
                MediaFormat mediaFormat = extractor.getTrackFormat(trackID);
                MediaCodec mediaCodec = MediaCodec.createDecoderByType(mediaFormat.getString(MediaFormat.KEY_MIME));
                SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
                Surface surface = new Surface(surfaceTexture);
                mediaCodec.configure(mediaFormat,surface,null,0);
                mediaCodec.start();

                doExtract(extractor, trackID, mediaCodec, new FrameCallback() {
                    @Override
                    public void preRender(long presentationTimeUsec) {

                    }

                    @Override
                    public void postRender() {

                    }

                    @Override
                    public void loopReset() {

                    }
                });
//            }else {
//                Log.d(TAG,"SDK_INT  <  N") ;
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getMediaFile(){
        String name = "gen-sliders.mp4";
        File file = new File(getFilesDir(),name);
        if (file.exists()){
            return file;
        }
        AssetManager assetManager = getAssets();
        try {
            AssetFileDescriptor assetFileDescriptor =  assetManager.openFd(name);
            FileOutputStream outputStream = new FileOutputStream(file);
            FileInputStream inputStream = assetFileDescriptor.createInputStream();
            byte[] bytes = new byte[1024];
            while (inputStream.read(bytes) != -1){
                outputStream.write(bytes);
            }
            outputStream.close();
            inputStream.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private int selectTrack(MediaExtractor extractor){
        // 找到以video开头的轨道
        int count = extractor.getTrackCount();
        for (int i = 0 ; i < count ; i++){
            MediaFormat mediaFormat = extractor.getTrackFormat(i);
            if (mediaFormat.getString(MediaFormat.KEY_MIME).startsWith("video/")){
                Log.d(TAG,"selectTrack find " + mediaFormat.getString(MediaFormat.KEY_MIME));
                return i;
            }
        }
        return -1;
    }


    public void requestStop() {
        mIsStopRequested = true;
    }



    /**
     * Work loop.  We execute here until we run out of video or are told to stop.
     */
    private void doExtract(MediaExtractor extractor, int trackIndex, MediaCodec decoder,
                           FrameCallback frameCallback) {
//         We need to strike a balance between providing input and reading output that
//         operates efficiently without delays on the output side.
//
//         To avoid delays on the output side, we need to keep the codec's input buffers
//         fed.  There can be significant latency between submitting frame N to the decoder
//         and receiving frame N on the output, so we need to stay ahead of the game.
//
//         Many video decoders seem to want several frames of video before they start
//         producing output -- one implementation wanted four before it appeared to
//         configure itself.  We need to provide a bunch of input frames up front, and try
//         to keep the queue full as we go.
//
//         (Note it's possible for the encoded data to be written to the stream out of order,
//         so we can't generally submit a single frame and wait for it to appear.)
//
//         We can't just fixate on the input side though.  If we spend too much time trying
//         to stuff the input, we might miss a presentation deadline.  At 60Hz we have 16.7ms
//         between frames, so sleeping for 10ms would eat up a significant fraction of the
//         time allowed.  (Most video is at 30Hz or less, so for most content we'll have
//         significantly longer.)  Waiting for output is okay, but sleeping on availability
//         of input buffers is unwise if we need to be providing output on a regular schedule.
//
//
//         In some situations, startup latency may be a concern.  To minimize startup time,
//         we'd want to stuff the input full as quickly as possible.  This turns out to be
//         somewhat complicated, as the codec may still be starting up and will refuse to
//         accept input.  Removing the timeout from dequeueInputBuffer() results in spinning
//         on the CPU.
//
//         If you have tight startup latency requirements, it would probably be best to
//         "prime the pump" with a sequence of frames that aren't actually shown (e.g.
//         grab the first 10 NAL units and shove them through, then rewind to the start of
//         the first key frame).
//
//         The actual latency seems to depend on strongly on the nature of the video (e.g.
//         resolution).
//
//
//         One conceptually nice approach is to loop on the input side to ensure that the codec
//         always has all the input it can handle.  After submitting a buffer, we immediately
//         check to see if it will accept another.  We can use a short timeout so we don't
//         miss a presentation deadline.  On the output side we only check once, with a longer
//         timeout, then return to the outer loop to see if the codec is hungry for more input.
//
//         In practice, every call to check for available buffers involves a lot of message-
//         passing between threads and processes.  Setting a very brief timeout doesn't
//         exactly work because the overhead required to determine that no buffer is available
//         is substantial.  On one device, the "clever" approach caused significantly greater
//         and more highly variable startup latency.
//
//         The code below takes a very simple-minded approach that works, but carries a risk
//         of occasionally running out of output.  A more sophisticated approach might
//         detect an output timeout and use that as a signal to try to enqueue several input
//         buffers on the next iteration.
//
//         If you want to experiment, set the VERBOSE flag to true and watch the behavior
//         in logcat.  Use "logcat -v threadtime" to see sub-second timing.

        final int TIMEOUT_USEC = 10000;
        ByteBuffer[] decoderInputBuffers = decoder.getInputBuffers();
        int inputChunk = 0;
        long firstInputTimeNsec = -1;

        boolean outputDone = false;
        boolean inputDone = false;
        while (!outputDone) {
            if (VERBOSE) Log.d(TAG, "loop");
            if (mIsStopRequested) {
                Log.d(TAG, "Stop requested");
                return;
            }

            // Feed more data to the decoder.
            if (!inputDone) {
                int inputBufIndex = decoder.dequeueInputBuffer(TIMEOUT_USEC);
                if (inputBufIndex >= 0) {
                    if (firstInputTimeNsec == -1) {
                        firstInputTimeNsec = System.nanoTime();
                    }
                    ByteBuffer inputBuf = decoderInputBuffers[inputBufIndex];
                    // Read the sample data into the ByteBuffer.  This neither respects nor
                    // updates inputBuf's position, limit, etc.
                    int chunkSize = extractor.readSampleData(inputBuf, 0);
                    if (chunkSize < 0) {
                        // End of stream -- send empty frame with EOS flag set.
                        decoder.queueInputBuffer(inputBufIndex, 0, 0, 0L,
                                MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                        inputDone = true;
                        if (VERBOSE) Log.d(TAG, "sent input EOS");
                    } else {
                        if (extractor.getSampleTrackIndex() != trackIndex) {
                            Log.w(TAG, "WEIRD: got sample from track " +
                                    extractor.getSampleTrackIndex() + ", expected " + trackIndex);
                        }
                        long presentationTimeUs = extractor.getSampleTime();
                        decoder.queueInputBuffer(inputBufIndex, 0, chunkSize,
                                presentationTimeUs, 0 /*flags*/);
                        if (VERBOSE) {
                            Log.d(TAG, "submitted frame " + inputChunk + " to dec, size=" +
                                    chunkSize);
                        }
                        inputChunk++;
                        extractor.advance();
                    }
                } else {
                    if (VERBOSE) Log.d(TAG, "input buffer not available");
                }
            }

            if (!outputDone) {
                int decoderStatus = decoder.dequeueOutputBuffer(mBufferInfo, TIMEOUT_USEC);
                if (decoderStatus == MediaCodec.INFO_TRY_AGAIN_LATER) {
                    // no output available yet
                    if (VERBOSE) Log.d(TAG, "no output from decoder available");
                } else if (decoderStatus == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                    // not important for us, since we're using Surface
                    if (VERBOSE) Log.d(TAG, "decoder output buffers changed");
                } else if (decoderStatus == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    MediaFormat newFormat = decoder.getOutputFormat();
                    if (VERBOSE) Log.d(TAG, "decoder output format changed: " + newFormat);
                } else if (decoderStatus < 0) {
                    throw new RuntimeException(
                            "unexpected result from decoder.dequeueOutputBuffer: " +
                                    decoderStatus);
                } else { // decoderStatus >= 0
                    if (firstInputTimeNsec != 0) {
                        // Log the delay from the first buffer of input to the first buffer
                        // of output.
                        long nowNsec = System.nanoTime();
                        Log.d(TAG, "startup lag " + ((nowNsec-firstInputTimeNsec) / 1000000.0) + " ms");
                        firstInputTimeNsec = 0;
                    }
                    boolean doLoop = false;
                    if (VERBOSE) Log.d(TAG, "surface decoder given buffer " + decoderStatus +
                            " (size=" + mBufferInfo.size + ")");
                    if ((mBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                        if (VERBOSE) Log.d(TAG, "output EOS");
                        if (mLoop) {
                            doLoop = true;
                        } else {
                            outputDone = true;
                        }
                    }

                    boolean doRender = (mBufferInfo.size != 0);

                    // As soon as we call releaseOutputBuffer, the buffer will be forwarded
                    // to SurfaceTexture to convert to a texture.  We can't control when it
                    // appears on-screen, but we can manage the pace at which we release
                    // the buffers.
                    if (doRender && frameCallback != null) {
                        frameCallback.preRender(mBufferInfo.presentationTimeUs);
                    }
                    decoder.releaseOutputBuffer(decoderStatus, doRender);
                    if (doRender && frameCallback != null) {
                        frameCallback.postRender();
                    }

                    if (doLoop) {
                        Log.d(TAG, "Reached EOS, looping");
                        extractor.seekTo(0, MediaExtractor.SEEK_TO_CLOSEST_SYNC);
                        inputDone = false;
                        decoder.flush();    // reset decoder state
                        frameCallback.loopReset();
                    }
                }
            }
        }
    }


    /**
     * Callback invoked when rendering video frames.  The MoviePlayer client must
     * provide one of these.
     */
    public interface FrameCallback {
        /**
         * Called immediately before the frame is rendered.
         * @param presentationTimeUsec The desired presentation time, in microseconds.
         */
        void preRender(long presentationTimeUsec);

        /**
         * Called immediately after the frame render call returns.  The frame may not have
         * actually been rendered yet.
         * TODO: is this actually useful?
         */
        void postRender();

        /**
         * Called after the last frame of a looped movie has been rendered.  This allows the
         * callback to adjust its expectations of the next presentation time stamp.
         */
        void loopReset();
    }
}
