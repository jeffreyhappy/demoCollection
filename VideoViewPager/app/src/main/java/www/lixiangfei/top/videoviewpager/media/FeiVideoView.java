package www.lixiangfei.top.videoviewpager.media;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.util.Formatter;
import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import www.lixiangfei.top.videoviewpager.R;

/**
 * Created by Li on 2017/11/24.
 */

public class FeiVideoView extends FrameLayout implements View.OnClickListener {
    IjkVideoView ijkVideoView;
    ProgressBar mLoadingView;

    FeiVideoControllerView controllerView;
//    VideoSystemOverlay videoSystemOverlay;

    //    FeiVideoInfoManager videoInfoManager;
//    private VideoProgressOverlay videoProgressOverlay;
    private int initWidth;
    private int initHeight;
    private ImageView ivPreview;
//    private ImageView mIvPlayer;
//    private FeiVideoControllerView.ClickFullListener clickFullListener;


    public FeiVideoView(@NonNull Context context) {
        super(context);
        init();
    }

    public FeiVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FeiVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init() {
        Log.d("fei","FeiVideoView init");
//        videoInfoManager = new FeiVideoInfoManager();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_fei_video_view,this,false);
        addView(view);
        ivPreview = view.findViewById(R.id.iv_preview);

//        mIvPlayer= view.findViewById(R.id.iv_play);
//        videoSystemOverlay = (VideoSystemOverlay) findViewById(R.id.video_system_overlay);
//        videoProgressOverlay = (VideoProgressOverlay) findViewById(R.id.video_progress_overlay);
        controllerView = new FeiVideoControllerView(getContext(),(ViewGroup)view);
//        controllerView.setClickFullListener(new FeiVideoControllerView.ClickFullListener() {
//            @Override
//            public void onClickFull() {
//                if (getVideoInfoManager().getFullScreen()){
//                    showFull(getContext(),false);
//                }else {
//                    showFull(getContext(),true);
//                }
//                if (clickFullListener != null){
//                    clickFullListener.onClickFull();
//                }
//            }
//        });

        ijkVideoView = (IjkVideoView) view.findViewById(R.id.video_view);
        ijkVideoView.setMediaController(controllerView);
        ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                controllerView.show();
                Log.d("fei","onPrepared start");
                initWidth = getWidth();
                initHeight = getHeight();

            }
        });
        ijkVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra) {
                Log.d("fei","onInfo what = " + what);
                if (ijkVideoView.isPlaying() && (what == IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED||what == IMediaPlayer.MEDIA_INFO_BUFFERING_START)) {
                    showLoading();
                } else if (what == IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START||what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    hideLoading();
                }
                return false;
            }
        });
        ijkVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                controllerView.show();
//                Toast.makeText(getContext(),"看完了", Toast.LENGTH_SHORT).show();
            }
        });
        mLoadingView = (ProgressBar) view.findViewById(R.id.loading);

        ijkVideoView.setPlayCallback(new FeiPlayCallback() {
            @Override
            public void onStart() {
                if (FeiVideoInfoManager.getInstance().getPlayView() != null
                        &&FeiVideoInfoManager.getInstance().getPlayView() != FeiVideoView.this){
                    FeiVideoInfoManager.getInstance().getPlayView().pause();
                }
                FeiVideoInfoManager.getInstance().setPlayView(FeiVideoView.this);

            }

            @Override
            public void onPause() {

            }

            @Override
            public void onStop() {
                FeiVideoInfoManager.getInstance().setPlayView(null);
            }
        });

        setOnClickListener(this);
    }

    public void setPreViewImage(final String url){
//        new android.os.Handler().post(new Runnable() {
//            @Override
//            public void run() {
                Glide.with(getContext())
                        .load(url)
                        .into(ivPreview);
                ivPreview.setVisibility(View.VISIBLE);
//            }
//        });

    }

    public void setVideoPath(String path){
        ijkVideoView.setVideoPath(path);
    }

    public void start() {
        ivPreview.setVisibility(View.GONE);
        ijkVideoView.start();

    }

    public void pause(){
        ijkVideoView.pause();
    }



    public void stopPlayback(){
        ijkVideoView.stopPlayback();

    }

    public void release(boolean release){
        ijkVideoView.release(release);
    }

    public void stopBackgroundPlay(){
        ijkVideoView.stopBackgroundPlay();
    }

    private void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        //在全屏的模式下，才随着屏幕的变化而变化
        if (getVideoInfoManager().getFullScreen()){
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                getLayoutParams().width = initWidth;
                getLayoutParams().height = initHeight;
            } else {
                getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                getLayoutParams().height = FrameLayout.LayoutParams.MATCH_PARENT;
            }
        }


    }

//    public void setClickFullListener(FeiVideoControllerView.ClickFullListener clickFullListener) {
//        this.clickFullListener = clickFullListener;
//    }


//    @Override
//    protected void endGesture(int behaviorType) {
//        switch (behaviorType) {
//            case VideoBehaviorView.FINGER_BEHAVIOR_BRIGHTNESS:
//            case VideoBehaviorView.FINGER_BEHAVIOR_VOLUME:
//                Log.i("DDD", "endGesture: left right");
////                videoSystemOverlay.hide();
//                break;
//            case VideoBehaviorView.FINGER_BEHAVIOR_PROGRESS:
//                Log.i("DDD", "endGesture: bottom");
//                ijkVideoView.seekTo(videoProgressOverlay.getTargetProgress());
////                videoProgressOverlay.hide();
//                break;
//        }
//    }
//
//    @Override
//    protected void updateSeekUI(int delProgress) {
//        videoProgressOverlay.show(delProgress, ijkVideoView.getCurrentPosition(), ijkVideoView.getDuration());
//    }
//
//    @Override
//    protected void updateVolumeUI(int max, int progress) {
//        videoSystemOverlay.show(VideoSystemOverlay.SystemType.VOLUME, max, progress);
//    }
//
//    @Override
//    protected void updateLightUI(int max, int progress) {
//        videoSystemOverlay.show(VideoSystemOverlay.SystemType.BRIGHTNESS, max, progress);
//    }


    //    public static void startFullscreen(Context context, Class _class, String url,VideoInfos infos) {
////        JZUtils.setRequestedOrientation(JZUtils.scanForActivity(context), FULLSCREEN_ORIENTATION);
//        ViewGroup vp = (ViewGroup) (JZUtils.scanForActivity(context))//.getWindow().getDecorView();
//                .findViewById(Window.ID_ANDROID_CONTENT);
//        View old = vp.findViewById(R.id.jz_fullscreen_id);
//        if (old != null) {
//            vp.removeView(old);
//        }
//        try {
//            Constructor<TestVideoView> constructor = _class.getConstructor(Context.class,VideoInfos.class);
//            final TestVideoView jzVideoPlayer = constructor.newInstance(context,infos);
//            jzVideoPlayer.setId(R.id.jz_fullscreen_id);
//            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            vp.addView(jzVideoPlayer, lp);
//            jzVideoPlayer.setVideoPath(url);
//            jzVideoPlayer.start();
////            final Animation ra = AnimationUtils.loadAnimation(context, R.anim.start_fullscreen);
////            jzVideoPlayer.setAnimation(ra);
////            jzVideoPlayer.setUp(dataSourceObjects, defaultUrlMapIndex, JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, objects);
////            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
////            jzVideoPlayer.startButton.performClick();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public FeiVideoInfoManager getVideoInfoManager() {
        return FeiVideoInfoManager.getInstance();
    }


//    private void showFull(Context context,Boolean showFull){
////        if (videoView == null){
////            return;
////        }
//        ViewGroup fullScreen = findFullView(context);
//        if (showFull){
//            ViewGroup viewGroup = (ViewGroup) this.getParent();
//            if (viewGroup == null){
//                return;
//            }
//            getVideoInfoManager().setOriginParentView(viewGroup);
//            getVideoInfoManager().setFullScreen(true);
//            getVideoInfoManager().setOriginLP(getLayoutParams());
//            getVideoInfoManager().setPlayView(this);
////            viewGroup.addView(new FrameLayout(getContext()),getLayoutParams());
//            viewGroup.removeView(this);
//            //放一个view来站着位置，要不然listview会跑偏
//            fullScreen.addView(this);
//            fullScreen.setVisibility(View.VISIBLE);
//            this.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            scanForActivity(context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            int mHideFlags =
//                    View.SYSTEM_UI_FLAG_LOW_PROFILE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
////            fullScreen.setSystemUiVisibility(mHideFlags);
//        }else {
//            fullScreen.setVisibility(View.GONE);
//            fullScreen.removeAllViews();
//            ViewGroup originGroupView = getVideoInfoManager().getOriginParentView();
//            ViewGroup.LayoutParams originLP= getVideoInfoManager().getOriginLP();
////            originGroupView.removeAllViews();
//            originGroupView.addView(this,originLP);
//            getVideoInfoManager().setFullScreen(false);
//            getVideoInfoManager().setOriginLP(null);
//            getVideoInfoManager().setOriginParentView(null);
//            getVideoInfoManager().setPlayView(null);
//            scanForActivity(context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//        }
//    }

//    ViewGroup findFullView(Context context){
//        ViewGroup vp = (ViewGroup) (scanForActivity(context))//.getWindow().getDecorView();
//                .findViewById(Window.ID_ANDROID_CONTENT);
//        FrameLayout old = (FrameLayout) vp.findViewById(R.id.jz_fullscreen_id);
//        if (old != null) {
//            vp.removeView(old);
//            return old;
//        }
//        FrameLayout frameLayout = new FrameLayout(context);
//        frameLayout.setBackgroundColor(Color.BLACK);
//        frameLayout.setId(R.id.jz_fullscreen_id);
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        vp.addView(frameLayout, lp);
//        return frameLayout;
//    }

    public static Activity scanForActivity(Context context) {
        if (context == null) {
            return null;
        }

        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }

        return null;
    }


//    public boolean handleBack(){
//        if (getVideoInfoManager().getFullScreen()){
//            showFull(getContext(),false);
//            return  true;
//        }else {
//            return false;
//        }
//    }


    public static String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;
        StringBuilder mFormatBuilder = new StringBuilder();
        mFormatBuilder.setLength(0);
        Formatter formatter = new Formatter(mFormatBuilder, Locale.getDefault());
        if (hours > 0) {
            return formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return formatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    @Override
    public void onClick(View v) {
        if (ijkVideoView.isPlaying()){
            pause();
            controllerView.show();
        }else {
            start();
            controllerView.hide();
        }
//        controllerView.show(3000);
    }
}
