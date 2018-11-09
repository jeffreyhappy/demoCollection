package www.lixiangfei.top.videoviewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.lixiangfei.top.videoviewpager.media.FeiVideoView;

public class FragmentVideo extends Fragment {

    public static FragmentVideo newFragment(String strVideoUrl,String strImgUrl){
        FragmentVideo fragmentPic = new FragmentVideo();
        Bundle bundle = new Bundle();
        bundle.putString("img_url",strImgUrl);
        bundle.putString("video_url",strVideoUrl);
        fragmentPic.setArguments(bundle);
        return fragmentPic;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video,container,false);
        FeiVideoView feiVideoView = view.findViewById(R.id.player);
        Bundle bundle = getArguments();
        String strImgUrl   = bundle.getString("img_url");
        String strVideoUrl = bundle.getString("video_url");
        feiVideoView.setVideoPath(strVideoUrl);
        feiVideoView.setPreViewImage(strImgUrl);
        return view;
    }
}
