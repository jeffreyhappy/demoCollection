package www.lixiangfei.top.videoviewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FragmentPic extends Fragment {


    public static FragmentPic newFragment(String url,int pos){
        FragmentPic fragmentPic = new FragmentPic();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        bundle.putInt("pos",pos);
        fragmentPic.setArguments(bundle);
        return fragmentPic;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pic,container,false);
        ImageView iv = view.findViewById(R.id.iv);
        TextView tv = view.findViewById(R.id.tv);
        Bundle bundle = getArguments();
        String strUrl = bundle.getString("url");
        int pos = bundle.getInt("pos");
        tv.setText(pos+"");
        Glide.with(getActivity())
                .load(strUrl)
                .into(iv);
        return view;
    }
}
