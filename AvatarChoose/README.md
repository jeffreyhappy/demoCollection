## 头像选择

### 权限注意
在6.0下读写外部存储，摄像头都是危险权限，需要先授权。不然直接挂掉

  
### 使用
1. 继承ChooseActivity</p>
2. 调用toLocalChoose()或者toCameraChoose()</p>
3. 获取返回
    ```
    public void onChooseDone(Uri uri) {
             Glide.with(this).load(uri).into(mIv);
    }
    ```  
        
具体如下  
    
    public class MainActivity extends ChooseActivity implements View.OnClickListener {
        private ImageView  mIv;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            findViewById(R.id.button).setOnClickListener(this);
            findViewById(R.id.button2).setOnClickListener(this);
            mIv = (ImageView) findViewById(R.id.imageView);
        }
    
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button:
                    toLocalChoose();
                    break;
                case R.id.button2:
                    toCameraChoose();
                    break;
            }
        }
    
    
        @Override
        public void onChooseDone(Uri uri) {
            Glide.with(this).load(uri).into(mIv);
        }
        
    }
    