package www.lixiangfei.top.videoviewpager.media;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;

import www.lixiangfei.top.videoviewpager.R;


/**
 * Created by Li on 2017/11/24.
 */

public class FeiVideoControllerView implements IMediaController,View.OnClickListener{
    View rootView;
//    ViewGroup bottomController;
    MediaController.MediaPlayerControl mPlayer;
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    private static final int UPDATE_PAUSE_START = 3;
//    private SeekBar mProgress;
//    private boolean mDragging;
    private boolean mShowing;
//    private TextView mEndTime, mCurrentTime;
//    private long  mDraggingProgress;
//    private ImageView mFull;

    private static final int sDefaultTimeout = 3000;
//    private ClickFullListener mClickFullListener;
    private ImageView mPlayButtonView;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int pos;
            switch (msg.what) {
                case FADE_OUT:
                    hide();
                    break;
//                case SHOW_PROGRESS:
//                    pos = setProgress();
//                    if (!mDragging && mShowing && mPlayer.isPlaying()) {
//                        msg = obtainMessage(SHOW_PROGRESS);
//                        sendMessageDelayed(msg, 1000 - (pos % 1000));
//                    }
//                    break;
                case UPDATE_PAUSE_START:
                    updatePausePlay();
                    break;
            }
        }
    };

    public FeiVideoControllerView(Context context, ViewGroup parentView){
//        mContext = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_fei_controller_view,parentView,false);
        parentView.addView(rootView);
//        bottomController= (ViewGroup) rootView.findViewById(R.id.bottom_controller);
//        bottomController.setVisibility(View.VISIBLE);
        mPlayButtonView = rootView.findViewById(R.id.iv_play);
//        mPlayButtonView.setOnClickListener(this);
//        mEndTime = (TextView) bottomController.findViewById(R.id.player_duration);
//        mCurrentTime = (TextView) bottomController.findViewById(R.id.player_progress);
//        mFull = (ImageView) bottomController.findViewById(R.id.video_full_screen);
//        mProgress = (SeekBar) bottomController.findViewById(R.id.player_seek_bar);
//        mProgress.setMax(1000);
//        mProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (!fromUser) {
//                    return;
//                }
//                long duration = mPlayer.getDuration();
//                mDraggingProgress = (duration * progress) / 1000L;
//                // 系统原来的实现是在progress改变的时候时刻都在进行videoplayer的seek
//                //这会导致seek m3u8切片文件的时候拖动seek时不准确，所以需要在拖动完成后才进行播放器的seekTo()
////                mPlayer.seekTo( (int) newposition);
//                if (mCurrentTime != null){
//                    mCurrentTime.setText(FeiVideoView.stringForTime((int)mDraggingProgress));
//                }
//                Log.d("fei","onProgressChanged progress new " + progress);
//                Log.d("fei","onProgressChanged progress old " + mPlayer.getCurrentPosition()+ " "  + mPlayer.getDuration());
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                show(3600000);
//                mDragging = true;
//
//                // By removing these pending progress messages we make sure
//                // that a) we won't update the progress while the user adjusts
//                // the seekbar and b) once the user is done dragging the thumb
//                // we will post one of these messages to the queue again and
//                // this ensures that there will be exactly one message queued up.
//
//                mHandler.removeMessages(SHOW_PROGRESS);
//                Log.d("fei","onStartTrackingTouch progress "+ mPlayer.getCurrentPosition()+ " "  + mPlayer.getDuration());
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                mDragging = false;
//                mPlayer.seekTo( (int) mDraggingProgress);
//                setProgress();
//                show(sDefaultTimeout);
//
//                // Ensure that progress is properly updated in the future,
//                // the call to show() does not guarantee this because it is a
//                // no-op if we are already showing.
//                mHandler.sendEmptyMessage(SHOW_PROGRESS);
//                Log.d("fei","onStopTrackingTouch "+ mPlayer.getCurrentPosition()+ " "  + mPlayer.getDuration());
//            }
//        });
//        mFull.setOnClickListener(this);
    }


    @Override
    public void hide() {
        if (mShowing){
            mShowing = false;
            mHandler.removeMessages(SHOW_PROGRESS);
        }
        rootView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean isShowing() {
        return rootView.getVisibility() == View.VISIBLE;
    }

    @Override
    public void setAnchorView(View view) {

    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public void setMediaPlayer(MediaController.MediaPlayerControl player) {
        this.mPlayer = player;
//        updatePausePlay();
//        mHandler.sendEmptyMessage(UPDATE_PAUSE_START);
        mHandler.sendEmptyMessageDelayed(UPDATE_PAUSE_START,100);


    }

    @Override
    public void show(int timeout) {
        if (!mShowing){
//            setProgress();
            mShowing=true;
            rootView.setVisibility(View.VISIBLE);
        }
        mHandler.sendEmptyMessageDelayed(UPDATE_PAUSE_START,100);
//        mHandler.sendEmptyMessage(UPDATE_PAUSE_START);
        mHandler.sendEmptyMessage(SHOW_PROGRESS);
        if (timeout != 0){
            mHandler.removeMessages(FADE_OUT);
            Message msg = mHandler.obtainMessage(FADE_OUT);
            mHandler.sendMessageDelayed(msg,timeout);
        }
    }

    @Override
    public void show() {
        show(0);
    }

    @Override
    public void showOnce(View view) {
        rootView.setVisibility(View.VISIBLE);
    }

//    @Override
//    public void toggleFullScreen() {
//
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_play:
                Log.d("fei","updatePausePlay  start" + mPlayer.isPlaying());

                if(mPlayer.isPlaying()){
                    mPlayer.pause();
//                    updatePausePlay();
                }else {
                    mPlayer.start();
                }
                mHandler.sendEmptyMessageDelayed(UPDATE_PAUSE_START,100);

                Log.d("fei","updatePausePlay  end" + mPlayer.isPlaying());
                break;
//            case R.id.video_full_screen:
//                if (mClickFullListener != null){
//                    mClickFullListener.onClickFull();
//                }
//                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                mPlayer.pause();
//                VideoInfos infos = new VideoInfos();
//                infos.setCurrentPos(mPlayer.getCurrentPosition());
//                TestVideoView.startFullscreen(mContext,TestVideoView.class,"http://cdn9.video.checheng.com/flvs/FF91E122A113F07F/2017-05-24/57AD7568D620A06E-10.flv",infos);
//                break;
        }
    }

//    public void setClickFullListener(ClickFullListener mClickFullListener) {
//        this.mClickFullListener = mClickFullListener;
//    }

    private void updatePausePlay(){
        Log.d("fei","updatePausePlay " + mPlayer.isPlaying());
//        if (mPlayer.isPlaying()){
//            mPlayButtonView.setImageDrawable(ActivityCompat.getDrawable(mPlayButtonView.getContext(),R.drawable.ic_video_pause));
//            mPlayButtonView.setImageResource(R.drawable.ic_video_pause);
//        }else {
//            mPlayButtonView.setImageResource(R.drawable.ic_video_play);
//            mPlayButtonView.setImageDrawable(ActivityCompat.getDrawable(mPlayButtonView.getContext(),R.drawable.ic_video_play));

//        }
    }

//    private int setProgress() {
//        if (mPlayer == null || mDragging) {
//            return 0;
//        }
//        int position = mPlayer.getCurrentPosition();
//        int duration = mPlayer.getDuration();
//        if (mProgress != null) {
//            if (duration > 0) {
//                // use long to avoid overflow
//                long pos = 1000L * position / duration;
//                mProgress.setProgress( (int) pos);
//            }
//            int percent = mPlayer.getBufferPercentage();
//            mProgress.setSecondaryProgress(percent * 10);
//        }
//
//        if (mEndTime != null){
//            mEndTime.setText(FeiVideoView.stringForTime(duration));
//        }
//        if (mCurrentTime != null){
//            mCurrentTime.setText(FeiVideoView.stringForTime(position));
//        }
//
//        return position;
//    }






//    public interface ClickFullListener{
//        void onClickFull();
//    }
}