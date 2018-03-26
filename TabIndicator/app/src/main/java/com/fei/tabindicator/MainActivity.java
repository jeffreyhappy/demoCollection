package com.fei.tabindicator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 创建日期：2018/3/26 on 10:31
 * 描述:
 * 作者:Li
 */

public class MainActivity extends AppCompatActivity {

    ViewPager mVp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mVp = findViewById(R.id.vp);
        mVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return TextFragment.getInstance(position);
            }

            @Override
            public int getCount() {
                return 10;
            }
        });

        TabIndicator indicator = findViewById(R.id.indicator);
        TabIndicator indicator2 = findViewById(R.id.indicator2);
        TabIndicator indicator3 = findViewById(R.id.indicator3);
        indicator.setAdapter(new TestAdapter(mVp));
        indicator2.setAdapter(new TestAdapter(mVp));
        indicator3.setAdapter(new TestAdapter(mVp));


        indicator.bindViewPager(mVp);
        indicator2.bindViewPager(mVp);
        indicator3.bindViewPager(mVp);
    }

    private static class TestAdapter implements TabAdapter{
            ViewPager vp;
            public TestAdapter(ViewPager vp) {
                this.vp = vp;
            }
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public View getView(ViewGroup parentView, final int position) {
                TextView tv = new TextView(parentView.getContext());
                tv.setText(String.valueOf(position));
                tv.setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT));
                tv.setTextColor(ActivityCompat.getColorStateList(vp.getContext(),R.color.selector_tab));
                tv.setGravity(Gravity.CENTER);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vp.setCurrentItem(position);
                    }
                });
                return tv;
            }
        }


    private static class TextFragment extends Fragment{

        int position ;
        public static TextFragment getInstance(int pos){
            TextFragment textFragment = new TextFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("pos",pos);
            textFragment.setArguments(bundle);
            return textFragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            position  = getArguments().getInt("pos");
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            TextView textView = new TextView(container.getContext());
            textView.setText(String.valueOf(position));
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            return textView;
        }
    }
}
