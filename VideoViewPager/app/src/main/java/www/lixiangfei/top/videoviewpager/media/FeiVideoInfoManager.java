package www.lixiangfei.top.videoviewpager.media;

import android.view.ViewGroup;

public class FeiVideoInfoManager {
    static FeiVideoInfoManager manager;

    private ViewGroup mOriginParentView; //全屏前的父view
    private ViewGroup.LayoutParams mOriginLP;//全屏前的布局参数
    private boolean isFullScreen;//当前是否是全屏
    private FeiVideoView mPlayView;//全屏当前的PlayView
    private int listPosition; //给列表用的

    public static FeiVideoInfoManager getInstance(){
        if (manager == null){
            synchronized (FeiVideoInfoManager.class){
                if (manager == null){
                    manager = new FeiVideoInfoManager();
                }
            }
        }
        return manager;
    }

    public ViewGroup getOriginParentView() {
        return mOriginParentView;
    }

    public void setOriginParentView(ViewGroup mOriginParentView) {
        this.mOriginParentView = mOriginParentView;
    }

    public ViewGroup.LayoutParams getOriginLP() {
        return mOriginLP;
    }

    public void setOriginLP(ViewGroup.LayoutParams mOriginLP) {
        this.mOriginLP = mOriginLP;
    }

    public boolean getFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }

    public FeiVideoView getPlayView() {
        return mPlayView;
    }

    public void setPlayView(FeiVideoView playView) {
        this.mPlayView = playView;
    }

    public int getListPosition() {
        return listPosition;
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }
}