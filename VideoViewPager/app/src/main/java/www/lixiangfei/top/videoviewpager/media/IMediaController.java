package www.lixiangfei.top.videoviewpager.media;

import android.view.View;
import android.widget.MediaController;

public interface IMediaController {
    void hide();

    boolean isShowing();

    void setAnchorView(View view);

    void setEnabled(boolean enabled);

    void setMediaPlayer(MediaController.MediaPlayerControl player);

    void show(int timeout);

    void show();

    //----------
    // Extends
    //----------
    void showOnce(View view);
}
