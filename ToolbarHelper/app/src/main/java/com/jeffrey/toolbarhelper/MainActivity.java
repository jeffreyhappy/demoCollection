package com.jeffrey.toolbarhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        tvTitle.setText("标题");
        CommonToolbar commonToolbar1 = (CommonToolbar) findViewById(R.id.have_custom_left);
        commonToolbar1.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了左边", Toast.LENGTH_SHORT).show();
            }
        });
        commonToolbar1.setLeftText("左边");


        CommonToolbar commonToolbar = (CommonToolbar) findViewById(R.id.have_menu);
        commonToolbar.setRightMenu(mockData());
        commonToolbar.setOnMenuClickListener(new OnMenuClickListener() {
            @Override
            public void onClick(int id, View view) {
                Toast.makeText(MainActivity.this, "id = " + id, Toast.LENGTH_SHORT).show();
            }
        });

    }

    ArrayList<CommonToolbar.RightMenu> mockData(){
        ArrayList<CommonToolbar.RightMenu> menus = new ArrayList<>();


        CommonToolbar.RightMenu menu1 = new CommonToolbar.RightMenu(1,"菜单1",0);
        CommonToolbar.RightMenu menu2 = new CommonToolbar.RightMenu(2,null,R.drawable.ic_arrow_back_black_24dp);
        menus.add(menu1);
        menus.add(menu2);
        return menus;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }
}
