package com.jeffrey.color;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jeffrey.color.divider.DividerBuilder;
import com.jeffrey.color.divider.DividerHelper;
import com.jeffrey.color.divider.SimpleItemDecoration;

public class MainActivity extends AppCompatActivity {

    Drawable headBg ;
    Drawable iv1Drawable ;
    Drawable iv2Drawable  ;

    RecyclerView rv;
    RelativeLayout  rlHead;



    private int scrollY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 初始化工作
         */
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new SimpleAdapter());

        rlHead = (RelativeLayout) findViewById(R.id.rl_head);

        headBg = rlHead.getBackground().mutate();//获取head的背景drawable
        iv1Drawable = ((ImageView)findViewById(R.id.iv1)).getDrawable().mutate();//获取图片的drawable
        iv2Drawable = ((ImageView)findViewById(R.id.iv2)).getDrawable().mutate();//获取图片的drawable


        //给recyclerView一个滚动监听
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                float fraction = calcFraction(dy);

                setUI(fraction);

            }


        });


        DividerBuilder builder = new DividerBuilder()
                .divider(ResourcesCompat.getDrawable(getResources(),R.drawable.shape_divider_normal,null))
                .showBottom(false)
                .showTop(false);
        rv.addItemDecoration(builder.build());
    }


    /**
     *
     * @param dy
     * @return   0~1 ,滑动距离越大，值越大
     */
    private float calcFraction(int dy){
        //这里的300是图片的高度，图片滚完后就是100%
        float imgHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
        float toolbarHeight = rlHead.getHeight();
        float maxHeight = imgHeight - toolbarHeight; //图片从头到尾移动的距离

        scrollY += dy; //dy是这次移动的距离，每次移动的距离加起来就是总移动的距离，dy是有正有负的

        /**
         *
         */
        if (scrollY >= maxHeight) {
            return 1.0f;

        } else if (scrollY <= 0) {
            return 0f;
        } else {
//            float delta = Math.abs(scrollY - maxHeight) ;
            return scrollY/maxHeight;
        }
    }


    private void setUI(float fraction){
        //背景只需要设置透明度，255是全不透明
        headBg.setAlpha((int) (fraction*255));

        //This evaluator can be used to perform type interpolation between integer values that represent ARGB colors.
        //这个求值器用来执行计算用整形表示的颜色的差值
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        int   startColor = ActivityCompat.getColor(MainActivity.this,R.color.colorPrimary);
        int   endColor   = Color.WHITE;

        //根据fraction计算出开始和结束中间的色值
        int calcColor = (int) argbEvaluator.evaluate(fraction, startColor, endColor);

        ColorFilter colorFilter = new PorterDuffColorFilter(calcColor, PorterDuff.Mode.SRC_IN);

        iv1Drawable.setColorFilter(colorFilter);
        iv2Drawable.setColorFilter(colorFilter);
    }




}
