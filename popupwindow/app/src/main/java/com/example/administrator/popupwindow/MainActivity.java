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
    }


    private void showDialog(){
        PopupDialog popupDialog = new PopupDialog();
        popupDialog.show(getSupportFragmentManager(),"popupDialog");
        popupDialog.setCancelable(true);
    }
}
