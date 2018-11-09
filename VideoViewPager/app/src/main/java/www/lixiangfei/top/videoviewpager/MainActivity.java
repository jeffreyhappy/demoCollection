package www.lixiangfei.top.videoviewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import www.lixiangfei.top.videoviewpager.media.FeiVideoView;

public class MainActivity extends AppCompatActivity {

    ViewPager mVp;
    FeiVideoView mPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVp = findViewById(R.id.vp);
//        mPlayer = findViewById(R.id.player);

//        mPlayer.setPreViewImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541570942418&di=9d7d637e62bd15d3b02e19c287b63dce&imgtype=0&src=http%3A%2F%2Fpic.nipic.com%2F2008-05-06%2F200856201542395_2.jpg");
//        mPlayer.setVideoPath("http://cloud.video.taobao.com/play/u/1/p/1/e/6/t/1/211726082466.mp4");
//        mPlayer.start();

        VideoPagerAdapter adapter = new VideoPagerAdapter(getSupportFragmentManager(),mock());
        mVp.setAdapter(adapter);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TestService.class);
                startService(intent);
            }
        });

    }

    private ArrayList<VpBean> mock(){
        ArrayList<VpBean> list = new ArrayList<>();
        VpBean videoBean = new VpBean(VpBean.TYPE_VIDEO,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541570942418&di=9d7d637e62bd15d3b02e19c287b63dce&imgtype=0&src=http%3A%2F%2Fpic.nipic.com%2F2008-05-06%2F200856201542395_2.jpg");
        videoBean.setVideoUrl("http://cloud.video.taobao.com/play/u/1/p/1/e/6/t/1/211726082466.mp4");
        list.add(videoBean);
        list.add(new VpBean(VpBean.TYPE_PIC,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541570942418&di=9d7d637e62bd15d3b02e19c287b63dce&imgtype=0&src=http%3A%2F%2Fpic.nipic.com%2F2008-05-06%2F200856201542395_2.jpg"));
        list.add(new VpBean(VpBean.TYPE_PIC,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541570942418&di=9d7d637e62bd15d3b02e19c287b63dce&imgtype=0&src=http%3A%2F%2Fpic.nipic.com%2F2008-05-06%2F200856201542395_2.jpg"));
        list.add(new VpBean(VpBean.TYPE_PIC,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541570942418&di=9d7d637e62bd15d3b02e19c287b63dce&imgtype=0&src=http%3A%2F%2Fpic.nipic.com%2F2008-05-06%2F200856201542395_2.jpg"));
        return list;
    }
}
