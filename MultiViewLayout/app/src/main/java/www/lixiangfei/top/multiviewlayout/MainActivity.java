package www.lixiangfei.top.multiviewlayout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MultiViewLayout mvl;
    MultiViewVerticalLayout mvvl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mvl = findViewById(R.id.mvl);
        mvvl = findViewById(R.id.mvvl);

        findViewById(R.id.btn_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int childCount = mvl.getChildCount();
                if (childCount > 0){
                    mvl.removeViewAt(childCount-1);
                    mvvl.removeViewAt(childCount-1);
                }
            }
        });
        findViewById(R.id.btn_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOne();
            }
        });
    }

    private void addOne(){
        int childCount = mvl.getChildCount();
        mvl.addView(generateOneView(childCount));
        mvvl.addView(generateOneView(childCount));

    }
    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0 ; i < 10; i++){
            mvl.addView(generateOneView(i));
            mvvl.addView(generateOneView(i));
        }
    }

    private TextView generateOneView(int i ){
        TextView tv = new TextView(this);
        tv.setText(i+"");
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(40);
        int color ;
        if (i % 3 == 0){
            color = Color.BLACK;
        }else if (i%2 == 1){
            color = Color.RED;
        }else {
            color = Color.BLUE;
        }
        tv.setBackgroundColor(color);
        return tv;
    }
}
