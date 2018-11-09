package www.lixiangfei.top.videoviewpager.media;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
    private Context mAppContext;
    private SharedPreferences mSharedPreferences;

    public static final int PV_PLAYER__Auto = 0;
    public static final int PV_PLAYER__AndroidMediaPlayer = 1;
    public static final int PV_PLAYER__IjkMediaPlayer = 2;
    public static final int PV_PLAYER__IjkExoMediaPlayer = 3;

    public Settings(Context context) {
        mAppContext = context.getApplicationContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mAppContext);
    }

    public boolean getEnableBackgroundPlay() {
//        String key = mAppContext.getString(R.string.pref_key_enable_background_play);
//        return mSharedPreferences.getBoolean(key, false);
        return false;
    }

    public int getPlayer() {
//        String key = mAppContext.getString(R.string.pref_key_player);
//        String value = mSharedPreferences.getString(key, "");
//        try {
//            return Integer.valueOf(value).intValue();
//        } catch (NumberFormatException e) {
//            return 0;
//        }
        return PV_PLAYER__Auto;
    }

    public boolean getUsingMediaCodec() {
//        String key = mAppContext.getString(R.string.pref_key_using_media_codec);
//        return mSharedPreferences.getBoolean(key, false);
        return false;
    }

    public boolean getUsingMediaCodecAutoRotate() {
//        String key = mAppContext.getString(R.string.pref_key_using_media_codec_auto_rotate);
//        return mSharedPreferences.getBoolean(key, false);
        return false;
    }

    public boolean getMediaCodecHandleResolutionChange() {
//        String key = mAppContext.getString(R.string.pref_key_media_codec_handle_resolution_change);
//        return mSharedPreferences.getBoolean(key, false);
        return false;
    }

    public boolean getUsingOpenSLES() {
//        String key = mAppContext.getString(R.string.pref_key_using_opensl_es);
//        return mSharedPreferences.getBoolean(key, false);
        return false;
    }

    public String getPixelFormat() {
//        String key = mAppContext.getString(R.string.pref_key_pixel_format);
//        return mSharedPreferences.getString(key, "");
        return "";
    }

    public boolean getEnableNoView() {
//        String key = mAppContext.getString(R.string.pref_key_enable_no_view);
//        return mSharedPreferences.getBoolean(key, false);
        return false;
    }

    public boolean getEnableSurfaceView() {
//        String key = mAppContext.getString(R.string.pref_key_enable_surface_view);
//        return mSharedPreferences.getBoolean(key, false);
        return false;
    }

    public boolean getEnableTextureView() {
//        String key = mAppContext.getString(R.string.pref_key_enable_texture_view);
//        return mSharedPreferences.getBoolean(key, false);
        return false;
    }

    public boolean getEnableDetachedSurfaceTextureView() {
//        String key = mAppContext.getString(R.string.pref_key_enable_detached_surface_texture);
//        return mSharedPreferences.getBoolean(key, false);
        return false;
    }

    public boolean getUsingMediaDataSource() {
//        String key = mAppContext.getString(R.string.pref_key_using_mediadatasource);
//        return mSharedPreferences.getBoolean(key, false);
        return false;
    }

    public String getLastDirectory() {
//        String key = mAppContext.getString(R.string.pref_key_last_directory);
//        return mSharedPreferences.getString(key, "/");
        return "/";
    }

    public void setLastDirectory(String path) {
//        String key = mAppContext.getString(R.string.pref_key_last_directory);
//        mSharedPreferences.edit().putString(key, path).apply();
//        return false;
    }
}
