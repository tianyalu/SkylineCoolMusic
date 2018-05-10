package com.sty.skyline.coolmusic.utils;

import android.text.TextUtils;
import android.util.Log;

import com.sty.skyline.coolmusic.BuildConfig;

/**
 * Created by Shi Tianyi on 2018/5/4/0004.
 */

public class LogUtils {
    private static final boolean DEBUG_ENABLE = BuildConfig.LOG_DEBUG;
    private static final String DEF_TAG = "SkylineCoolMusic";

    private LogUtils(){}

    //黑色
    public static void v(String msg){
        v(DEF_TAG, msg);
    }
    public static void v(String tag, String msg){
        if(DEBUG_ENABLE){
            String newTag = tag;
            if(TextUtils.isEmpty(newTag)){
                newTag = DEF_TAG;
            }
            Log.v(newTag, msg);
        }
    }

    //蓝色
    public static void d(String msg){
        d(DEF_TAG, msg);
    }
    public static void d(String tag, String msg){
        if(DEBUG_ENABLE){
            String newTag = tag;
            if(TextUtils.isEmpty(newTag)){
                newTag = DEF_TAG;
            }
            Log.d(newTag, msg);
        }
    }

    //绿色
    public static void i(String msg){
        i(DEF_TAG, msg);
    }
    public static void i(String tag, String msg){
        if(DEBUG_ENABLE){
            String newTag = tag;
            if(TextUtils.isEmpty(newTag)){
                newTag = DEF_TAG;
            }
            Log.i(newTag, msg);
        }
    }

    //橙色
    public static void w(String msg){
        w(DEF_TAG, msg);
    }
    public static void w(String tag, String msg){
        if(DEBUG_ENABLE){
            String newTag = tag;
            if(TextUtils.isEmpty(newTag)){
                newTag = DEF_TAG;
            }
            Log.w(newTag, msg);
        }
    }
    public static void w(String tag, String msg, Throwable tr){
        if(DEBUG_ENABLE){
            String newTag = tag;
            if(TextUtils.isEmpty(newTag)){
               newTag = DEF_TAG;
            }
            Log.w(newTag, msg, tr);
        }
    }
    public static void w(String tag, Throwable tr){
        if(DEBUG_ENABLE){
            String newTag = tag;
            if(TextUtils.isEmpty(newTag)){
                newTag = DEF_TAG;
            }
            Log.w(newTag, tr);
        }
    }

    //红色
    public static void e(String msg){
        e(DEF_TAG, msg);
    }
    public static void e(String tag, String msg){
        if(DEBUG_ENABLE){
            String newTag = tag;
            if(TextUtils.isEmpty(newTag)){
                newTag = DEF_TAG;
            }
            Log.e(newTag, msg);
        }
    }
    public static void e(String tag, Throwable tr){
        e(tag, "", tr);
    }
    public static void e(String tag, String msg, Throwable tr){
        if(DEBUG_ENABLE){
            String newTag = tag;
            if(TextUtils.isEmpty(newTag)){
                newTag = DEF_TAG;
            }
            Log.e(newTag, msg, tr);
        }
    }

}
