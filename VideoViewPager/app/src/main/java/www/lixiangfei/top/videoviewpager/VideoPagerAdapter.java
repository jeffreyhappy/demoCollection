package www.lixiangfei.top.videoviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VideoPagerAdapter extends FragmentPagerAdapter {

    ArrayList<VpBean> beans;

    public VideoPagerAdapter(FragmentManager fm, ArrayList<VpBean> beans) {
        super(fm);
        this.beans = beans;
    }

    @Override
    public Fragment getItem(int i) {
        VpBean bean = beans.get(i);
        if (bean.getType() == VpBean.TYPE_PIC){
            return FragmentPic.newFragment(bean.getUrl(),i);
        }else {
            return FragmentVideo.newFragment(bean.getVideoUrl(),bean.getUrl());
        }
    }

    @Override
    public int getCount() {
        return beans.size();
    }
}
