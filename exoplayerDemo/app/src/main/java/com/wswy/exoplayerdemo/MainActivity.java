package com.wswy.exoplayerdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {

    private final String VIDEO_URL = "http://7d9qw5.com1.z0.glb.clouddn.com/wyrz_bd.mp4";
    private final String VIDEO_URL_DEFAULT = "http://7d9qw5.com1.z0.glb.clouddn.com/hzw_bd.mp4";
    SimpleExoPlayerView simpleExoPlayer;
    SimpleExoPlayer player;
    private Timeline.Window window;
    long playerPosition;



    SimpleExoPlayer player3;
    long playerPosition3;
    Handler mainHandler;


    BroadcastReceiver  timeReceiver;
    //debug
//    TextView debugTextView;
//    private DebugTextViewHelper debugViewHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainHandler = new Handler();
        window = new Timeline.Window();
        timeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("fei" ,"onReceive");
                TimeUtils.startNextTimeTicket(MainActivity.this);
                String videoUrl = intent.getStringExtra("video_url");
                if (player3 == null){
                    player3 = initPlayer();
                }
                playSurfaceView(player3,videoUrl);

                if (player==null){
                    player = initPlayer();
                    simpleExoPlayer.setPlayer(player);
                }
                playMP4(player,videoUrl);

            }
        };
        registerReceiver(timeReceiver,new IntentFilter("com.test.time"));

        TimeUtils.startNextTimeTicket(this);



    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer1();
        releasePlayer3();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //视频先放着
        //第一个
        simpleExoPlayer = (SimpleExoPlayerView) findViewById(R.id.player1);
        if (player==null){
            player = initPlayer();
            simpleExoPlayer.setPlayer(player);
        }
        playMP4(player,null);





        if (player3 == null){
            player3 = initPlayer();
        }

        playSurfaceView(player3,VIDEO_URL_DEFAULT);

    }

    private SimpleExoPlayer initPlayer() {
        // 1. Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

        // 3. Create the player
        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);

        return player;

    }

    private void playMP4(final SimpleExoPlayer player,String url) {


        // Prepare the player with the source.
        player.setPlayWhenReady(true);
        if (TextUtils.isEmpty(url)){
            LoopingMediaSource loopingSource = loadDatas();
            player.prepare(loopingSource);
        }else {
            LoopingMediaSource loopingSource = loadData(url);
            player.prepare(loopingSource);
        }

    }

    private void playSurfaceView(SimpleExoPlayer player,String url) {
        SurfaceView sv = (SurfaceView) findViewById(R.id.sv);
        player.setVideoSurfaceView(sv);

        player.setPlayWhenReady(true);

        LoopingMediaSource loopingSource = loadData(url);
        player.prepare(loopingSource);
    }

    private LoopingMediaSource  loadData(String url){
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, bandwidthMeter
                , new DefaultHttpDataSourceFactory(Util.getUserAgent(this, "yourApplicationName"), bandwidthMeter));
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(url),
                dataSourceFactory, extractorsFactory, mainHandler, null);

        //不停循环
        LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
        return loopingSource;
    }

    private LoopingMediaSource  loadDatas(){
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, bandwidthMeter
                , new DefaultHttpDataSourceFactory(Util.getUserAgent(this, "yourApplicationName"), bandwidthMeter));
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(VIDEO_URL),
                dataSourceFactory, extractorsFactory, mainHandler, null);
        MediaSource videoSource2 = new ExtractorMediaSource(Uri.parse(VIDEO_URL_DEFAULT),
                dataSourceFactory, extractorsFactory, mainHandler, null);
        //组合几个视频
        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource(videoSource,videoSource2);
        //不停循环
        LoopingMediaSource loopingSource = new LoopingMediaSource(concatenatingMediaSource);
        return loopingSource;
    }
    private void releasePlayer1() {
        if (player != null) {

            int playerWindow = player.getCurrentWindowIndex();
            playerPosition = C.TIME_UNSET;
            Timeline timeline = player.getCurrentTimeline();
            if (!timeline.isEmpty() && timeline.getWindow(playerWindow, window).isSeekable) {
                playerPosition = player.getCurrentPosition();
            }
            player.release();
            player = null;
        }
    }

    private void releasePlayer3() {
        if (player3 != null) {

            int playerWindow = player3.getCurrentWindowIndex();
            playerPosition3 = C.TIME_UNSET;
            Timeline timeline = player3.getCurrentTimeline();
            if (!timeline.isEmpty() && timeline.getWindow(playerWindow, window).isSeekable) {
                playerPosition = player3.getCurrentPosition();
            }
            player3.release();
            player3 = null;
        }
    }
}
