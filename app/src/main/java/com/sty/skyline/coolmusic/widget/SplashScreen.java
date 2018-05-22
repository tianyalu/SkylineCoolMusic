package com.sty.skyline.coolmusic.widget;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.sty.skyline.coolmusic.R;

/**
 * 引导页图片，停留若干秒，然后自动消失
 * Created by Shi Tianyi on 2018/5/17/0017.
 */

public class SplashScreen {
    public static final int SLIDE_LEFT = 1;
    public static final int SLIDE_UP = 2;
    public static final int FADE_OUT = 3;

    private Dialog splashDialog;
    private Activity activity;

    public SplashScreen(Activity activity){
        this.activity = activity;
    }

    /**
     * 显示
     * @param imageResource 图片资源
     * @param animation 消失时的动画，取值可以是 SplashScreen.SLIDE_LEFT,SplashScreen.SLIDE_UP,SplashScreen.FADE_OUT
     */
    public void show(final int imageResource, final int animation){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //Get reference to display
                DisplayMetrics metrics = new DisplayMetrics();

                //Create the layout for the dialog
                LinearLayout root = new LinearLayout(activity);
                root.setMinimumWidth(metrics.widthPixels);
                root.setMinimumHeight(metrics.heightPixels);
                root.setOrientation(LinearLayout.VERTICAL);
                root.setBackgroundColor(Color.BLACK);
                root.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, 0.0f));
                root.setBackgroundResource(imageResource);

                //Create and show the dialog
                splashDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
                //Check to see if the splash screen should be full screen
                if ((activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                        == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
                    splashDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
                Window window = splashDialog.getWindow();
                switch (animation){
                    case SLIDE_LEFT:
                        //window.setWindowAnimations(R.style.dialog_anim_slide_left);
                        break;
                    case SLIDE_UP:
                        //window.setWindowAnimations(R.style.dialog_anim_slide_up);
                        break;
                    case FADE_OUT:
                        //window.setWindowAnimations(R.style.dialog_anim_fade_out);
                        break;
                    default:
                        break;
                }

                splashDialog.setContentView(root);
                splashDialog.setCancelable(false);
                splashDialog.show();
            }
        };

        activity.runOnUiThread(runnable);
    }

    public void removeSplashScreen(){
        if(splashDialog != null && splashDialog.isShowing()){
            splashDialog.dismiss();
            splashDialog = null;
        }
    }
}
