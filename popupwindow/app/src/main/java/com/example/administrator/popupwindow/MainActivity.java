package com.example.administrator.popupwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showDialogNoCancel();
            }
        });
    }


    private void showDialog(){
        ShareDialog shareDialog = new ShareDialog();
        shareDialog.show(getSupportFragmentManager(),"popupDialog");
        shareDialog.setCancelable(true);
    }


    private void showDialogNoCancel(){
        ShareDialog shareDialog = new ShareDialog();
        shareDialog.show(getSupportFragmentManager(),"popupDialog");
        shareDialog.setCancelable(false);
    }
}
