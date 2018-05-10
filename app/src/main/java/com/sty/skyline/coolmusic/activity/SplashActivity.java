package com.sty.skyline.coolmusic.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.sty.skyline.coolmusic.R;
import com.sty.skyline.coolmusic.utils.LogUtils;
import com.sty.skyline.coolmusic.utils.PermissionHelper;

import net.youmi.android.AdManager;
import net.youmi.android.normal.common.ErrorCode;
import net.youmi.android.normal.spot.SplashViewSettings;
import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

/**
 * Created by Shi Tianyi on 2018/5/4/0004.
 */

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    private Context mContext;
    private PermissionHelper mPermissionHelper;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //移除标题栏
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        handler = new Handler();

        //当系统为6.0以上时，需要申请权限
        requestPermission();
    }

    private void requestPermission(){
        mPermissionHelper = new PermissionHelper(this);
        mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
            @Override
            public void onAfterApplyAllPermission() {
                LogUtils.i(TAG, "All requested permissions have been granted, so run app logic.");
                runApp();
            }
        });

        if(Build.VERSION.SDK_INT < 23){
            //如果系统版本低于23， 直接跑应用逻辑
            LogUtils.i(TAG, "The API level of system is lower than 23, so run app logic directly.");
            runApp();
        }else{
            //如果权限全部申请了，则直接跑应用逻辑
            if(mPermissionHelper.isAllRequestedPermissionGranted()){
                LogUtils.i(TAG, "All requested permissions have been granted, so run app logic directly.");
                runApp();
            }else { //如果还有权限未申请，而且系统版本大于23，执行权限申请逻辑
                LogUtils.i(TAG, "Some requested permissions have not been granted, so apply permissions first.");
                mPermissionHelper.applyPermissions();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionHelper.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 跑应用的逻辑
     */
    private void runApp(){
        //初始化SDK
        AdManager.getInstance(mContext).init("9ca9f88752601ff9", "c91f620182e46bad",
                true , true);
        //设置开屏
        //setupSplashAd();

        goToMainActivityDelay( 1500);
    }

    private void goToMainActivityDelay(long delayMillis){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, delayMillis);

    }

    /**
     * 设置开屏广告
     */
    private void setupSplashAd(){
        //创建开屏容器
        RelativeLayout splashLayout = findViewById(R.id.rl_splash);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE, R.id.view_divider);

        //对开屏进行设置
        SplashViewSettings splashViewSettings = new SplashViewSettings();
        //设置是否展示失败后自动跳转，默认自动跳转
        splashViewSettings.setAutoJumpToTargetWhenShowFailed(true);
        splashViewSettings.setTargetClass(MainActivity.class);
        //设置开屏的容器
        splashViewSettings.setSplashViewContainer(splashLayout);

        //展示开屏广告
        SpotManager.getInstance(mContext)
                .showSplash(mContext, splashViewSettings, mStopListener);
    }

    static SpotListener mStopListener = new SpotListener(){

        @Override
        public void onShowSuccess() {
            LogUtils.i(TAG, "开屏展示成功");
        }

        @Override
        public void onShowFailed(int errorCode) {
            LogUtils.e(TAG, "开屏展示失败");
            switch (errorCode){
                case ErrorCode.NON_NETWORK:
                    LogUtils.e(TAG, "网络异常");
                    break;
                case ErrorCode.NON_AD:
                    LogUtils.e(TAG, "暂无开屏广告");
                    break;
                case ErrorCode.RESOURCE_NOT_READY:
                    LogUtils.e(TAG, "开屏资源还未准备好");
                    break;
                case ErrorCode.SHOW_INTERVAL_LIMITED:
                    LogUtils.e(TAG, "开屏展示间隔限制");
                    break;
                case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                    LogUtils.e(TAG, "开屏控件处于不可见状态");
                    break;
                default:
                    LogUtils.e(TAG, "errorCode: %d" + errorCode);
                    break;
            }
        }

        @Override
        public void onSpotClosed() {
            LogUtils.i(TAG, "开屏被关闭");
        }

        @Override
        public void onSpotClicked(boolean isWebPage) {
            LogUtils.i(TAG, "开屏被点击");
            LogUtils.i(TAG, "是否网页广告？%s" + (isWebPage ? "是" : "不是"));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //开屏展示界面的onDestroy()回调方法中调用
        SpotManager.getInstance(mContext).onDestroy();
    }
}
