package com.sty.skyline.coolmusic.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;

import com.sty.skyline.coolmusic.R;
import com.sty.skyline.coolmusic.widget.SplashScreen;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private ImageView ivBarNet, ivBarMusic, ivBarFriends, ivBarSearch;
    private ArrayList<ImageView> tabs = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ListView mLvLeftMenu;
    private long time = 0;
    private SplashScreen splashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);

        setToolBar();
    }

    private void setToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle("");
    }
}
