package com.sty.skyline.coolmusic.ui.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.magicasakura.utils.ThemeUtils;
import com.sty.skyline.coolmusic.R;
import com.sty.skyline.coolmusic.ui.adapter.MenuItemAdapter;
import com.sty.skyline.coolmusic.ui.dialog.CardPickerDialog;
import com.sty.skyline.coolmusic.ui.fragment.MainFragment;
import com.sty.skyline.coolmusic.ui.fragment.TabNetPagerFragment;
import com.sty.skyline.coolmusic.utils.LogUtils;
import com.sty.skyline.coolmusic.utils.ThemeHelper;
import com.sty.skyline.coolmusic.widget.CustomViewPager;
import com.sty.skyline.coolmusic.widget.LvMenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements CardPickerDialog.ClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActionBar actionBar;
    private ImageView ivBarNet, ivBarMusic, ivBarFriends, ivBarSearch;
    private ArrayList<ImageView> tabs = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ListView mLvLeftMenu;
    private long time = 0;
    //private SplashScreen splashScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);

        initViews();

        setToolBar();
        setViewPager();
        setUpDrawer();

        //todo splash screen
    }

    private void initViews() {
        ivBarNet = findViewById(R.id.iv_bar_net);
        ivBarMusic = findViewById(R.id.iv_bar_music);
        ivBarFriends = findViewById(R.id.iv_bar_friends);
        ivBarSearch = findViewById(R.id.iv_bar_search);
        drawerLayout = findViewById(R.id.dl_drawer_layout);
        mLvLeftMenu = findViewById(R.id.lv_left_menu);
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle("");
    }

    private void setViewPager() {
        tabs.add(ivBarNet);
        tabs.add(ivBarMusic);
        final CustomViewPager customViewPager = (CustomViewPager) findViewById(R.id.cvp_main_viewpager);
        final MainFragment mainFragment = new MainFragment();
        final TabNetPagerFragment tabNetFragment = new TabNetPagerFragment();
        CustomViewPagerAdapter customViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        customViewPagerAdapter.addFragment(mainFragment);
        customViewPagerAdapter.addFragment(tabNetFragment);
        customViewPager.setAdapter(customViewPagerAdapter);
        customViewPager.setCurrentItem(1);
        ivBarMusic.setSelected(true);
        customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ivBarNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewPager.setCurrentItem(0);
            }
        });
        ivBarMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewPager.setCurrentItem(1);
            }
        });

        ivBarSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, NetSearchWordsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    private void switchTabs(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            if (position == i) {
                tabs.get(i).setSelected(true);
            }else {
                tabs.get(i).setSelected(false);
            }
        }
    }

    private void setUpDrawer() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mLvLeftMenu.addHeaderView(inflater.inflate(R.layout.nav_header_main, mLvLeftMenu, false));
        mLvLeftMenu.setAdapter(new MenuItemAdapter(this));
        mLvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //ListView的header
                        LogUtils.i(TAG, "Item 0 is clicked.");
                        break;
                    case 1: //夜间模式
                        //todo by myself
                        LogUtils.i(TAG, "Item 1 is clicked.");
                        //drawerLayout.closeDrawers();
                        break;
                    case 2: //主题换肤
                        CardPickerDialog dialog = new CardPickerDialog();
                        dialog.setClickListener(MainActivity.this);
                        dialog.show(getSupportFragmentManager(), "theme");
                        drawerLayout.closeDrawers();
                        LogUtils.i(TAG, "Item 2 is clicked.");
                        break;
                    case 3: //定时关闭音乐
                        //todo
                        LogUtils.i(TAG, "Item 3 is clicked.");
                        //drawerLayout.closeDrawers();
                        break;
                    case 4: //下载歌曲品质
                        //todo
                        LogUtils.i(TAG, "Item 4 is clicked.");
                        //drawerLayout.closeDrawers();
                        break;
                    case 5: //退出
                        //todo
                        LogUtils.i(TAG, "Item 5 is clicked.");
                        //drawerLayout.closeDrawers();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onConfirm(int currentTheme) {
        if (ThemeHelper.getTheme(MainActivity.this) != currentTheme) {
            ThemeHelper.setTheme(MainActivity.this, currentTheme);
            ThemeUtils.refreshUI(MainActivity.this, new ThemeUtils.ExtraRefreshable() {

                @Override
                public void refreshGlobal(Activity activity) {
                    //for global setting, just do once
                    if (Build.VERSION.SDK_INT >= 21) {
                        final MainActivity context = MainActivity.this;
                        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(
                                null, null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
                        setTaskDescription(taskDescription);
                        getWindow().setStatusBarColor(ThemeUtils.getColorById(context, R.color.theme_color_primary));
                    }
                }

                @Override
                public void refreshSpecificView(View view) {

                }
                //changeTheme(); //todo
            });
        }
    }

    static class CustomViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();

        public CustomViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments == null ? 0 : mFragments.size();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case android.R.id.home: //menu icon
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //todo
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 1000)) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if( fragments != null) {
            for (Fragment fragment : fragments) {
                if( fragment != null) {
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
