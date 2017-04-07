有时候标题栏会浮在内容之上，而内容会有颜色的变化，这时候就要求标题栏能够变化透明度，标题栏中的图片能够变化颜色。看下效果，如下图：

![video.mp4_1488200822.gif](https://github.com/jeffreyhappy/demoCollection/blob/master/Color/video.mp4_1488200822.gif)


### 描述
当界面往下划的时候，我们需要做的是
* 将标题栏是从全透明往全不透明转变
* 将标题栏中的图片从白色往主题色转变

当界面往上时，就反过来转变界面。


### 编写步骤
我们的demo的布局是标题栏在recyclerView的上方，布局如下


我们只需要3个步骤：
1. 监听recyclerView的滑动
2. 滑动时计算出位移的百分比
3. 根据百分比来操作标题栏背景和图片



#### 准备工作
在Activity的onCreate里初始化下视图，获取view对象，获取标题栏的背景图，图片的drawble对象等
```
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
    }
}
```
重点说明下mutate这个方法。一开始我修改标题栏的透明度时没有调用mutate方法，导致所有界面的标题栏都透明度都改变了。

>Drawable mutate ()

>Make this drawable mutable. This operation cannot be reversed. A mutable drawable is guaranteed to not share its state with any other drawable. This is especially useful when you need to modify properties of drawables loaded from resources. By default, all drawables instances loaded from the same resource share a common state; if you modify the state of one instance, all the other instances will receive the same modification. Calling this method on a mutable Drawable will have no effect.

大意就是使当前这个drawable变为易变的，一个易变的drawable被授权不去与其他的drawable共享状态。从资源载入的所有drawble都是共享一个状态实例，只要有一个修改了，其他的使用到该drawable的实例也会被修改。调用了这个方法后我们对drawable的修改就不会影响到其他实例了。


#### 监听recyclerView的滑动
然后对RecyclerView设置滑动监听
```
       //给recyclerView一个滚动监听
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
              //我们这里是竖向滑动，只需要关注dy即可
              //dy是每次滑动事件触发后，跟上次y位置的差值
            }
        });
```        

#### 计算位移百分比
```
    /**
     * @param dy
     * @return   0~1 ,滑动距离越大，值越大
     */
    private float calcFraction(int dy){
        //这里的300是图片的高度，图片滚完后就是100%
        float imgHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
        float toolbarHeight = rlHead.getHeight();
        float maxHeight = imgHeight - toolbarHeight; //图片从头到尾移动的距离

        scrollY += dy; //dy是这次移动的距离，每次移动的距离加起来就是总移动的距离，dy是有正有负的.scrollY是Activiy的成员对象

        if (scrollY >= maxHeight) {
            return 1.0f;

        } else if (scrollY <= 0) {
            return 0f;
        } else {
            return scrollY/maxHeight;
        }
    }
```

#### 根据百分比来操作标题栏背景和图片
```
    private void setUI(float fraction){
        //背景只需要设置透明度，255是全不透明
        headBg.setAlpha((int) (fraction*255)); //这个headBg是标题栏

        //This evaluator can be used to perform type interpolation between integer values that represent ARGB colors.
        //这个求值器用来执行计算用整形表示的颜色的差值
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        int   startColor = ActivityCompat.getColor(MainActivity.this,R.color.colorPrimary);
        int   endColor   = Color.WHITE;

        //根据fraction计算出开始和结束中间的色值
        int calcColor = (int) argbEvaluator.evaluate(fraction, startColor, endColor);

        ColorFilter colorFilter = new PorterDuffColorFilter(calcColor, PorterDuff.Mode.SRC_IN);


        //应用颜色过滤器
        iv1Drawable.setColorFilter(colorFilter);
        iv2Drawable.setColorFilter(colorFilter);
    }
```
argbEvaluator是用来计算开始颜色和结束颜色的中间颜色的，[说明连接](https://developer.android.google.cn/reference/android/animation/ArgbEvaluator.html)

ColorFilter是对Drawable设置一个色彩过滤器。这是一个抽象类不能直接使用，他有三个子类：ColorMatrixColorFilter, LightingColorFilter, PorterDuffColorFilter 。我们先看下描述

* ColorMatrixColorFilter   一个通过4*5的颜色矩阵来改变颜色的颜色过滤器
>  A color filter that transforms colors through a 4x5 color matrix. This filter can be used to change the saturation of pixels, convert from YUV to RGB, etc.

* LightingColorFilter  一个可以模拟简单的灯光效果的颜色过滤器
>A color filter that can be used to simulate simple lighting effects. A LightingColorFilter is defined by two parameters, one used to multiply the source color (called colorMultiply) and one used to add to the source color (called colorAdd). The alpha channel is left untouched by this color filter. Given a source color RGB, the resulting R'G'B' color is computed thusly:

* PorterDuffColorFilter  一个可以使用单个颜色和指定Porter-Duff模式来对源像素进行染色颜色过滤器
>A color filter that can be used to tint the source pixels using a single color and a specific Porter-Duff composite mode.

我们就是需要根据位移百分比来对drawable进行颜色变化，就选用PorterDuffColorFilter（为啥说的这么简单？因为ColorMatrixColorFilter，LightingColorFilter我看的有点懵逼）

#####Porter-Duff模式

[说明文档](https://developer.android.google.cn/reference/android/graphics/PorterDuff.Mode.html),这个没啥好的中文翻译，我看到一句：Porter-Duff 操作是 1 组 12 项用于描述数字图像合成的基本手法。用人话讲就是：2张图要放在一张画板上，这两张图需要融合在一起，融合的方法有12个，他们统称Porter-Duff。具体的融合方法请仔细看下图

![20150416143342583.jpg](http://upload-images.jianshu.io/upload_images/2120696-5de778d56a6602c2.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

我们需要保持原来图片的形状，而切换图片的颜色，选择PorterDuff.Mode.SRC_IN就可以了，如果理解不了，自己按照图片多跑几个模式就知道了。

最后将colorFilter应用到对应的drawable上.图片颜色的变换就完成啦
