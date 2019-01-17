package www.lixiangfei.top.videolearn;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity {

    private final String TAG = MediaPlayerActivity.class.getSimpleName();

    TextureView textureView;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        textureView = findViewById(R.id.texture);
        mediaPlayer = new MediaPlayer();
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.prepareAsync();
            }
        });
        findViewById(R.id.btn_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG,"setOnPreparedListener " );

                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d(TAG,"setOnErrorListener what " + what + " extra " + extra);
                return false;
            }
        });

        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Log.d(TAG,"setOnInfoListener what " + what + " extra " + extra);

                return false;
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG,"setOnCompletionListener ");

            }
        });
//            mediaPlayer.setDataSource(this,Uri.parse(getMediaFile().getPath()));

            textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                    Log.d(TAG,"onSurfaceTextureAvailable ");
                    Surface surface = new Surface(surfaceTexture);
                    mediaPlayer.setSurface(surface);
                    try {
                        mediaPlayer.setDataSource("http://v11-tt.ixigua.com/c3f9790368180e40ded99609b2ec0189/5bf3eb13/video/m/2202ccebb6602424779b48145d1f7685ae8115fb7f900006e74353d946a/?rc=amVqNjY0Z3c5aTMzaDczM0ApQHRAbzc6NjQ4MzUzMzgzNDU0NDVvQGgzdSlAZjN1KWRzcmd5a3VyZ3lybHh3ZjM0QGdwZi0wcGlubV8tLWAtL3NzLW8jbyM1MzAtMC0tLi0zNS0uNS06I28jOmEtcSM6YHZpXGJmK2BeYmYrXnFsOiM2Ll4%3D");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                    Log.d(TAG,"onSurfaceTextureSizeChanged ");

                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    Log.d(TAG,"onSurfaceTextureDestroyed ");
                    return false;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                    Log.d(TAG,"onSurfaceTextureUpdated ");

                }
            });

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
}
