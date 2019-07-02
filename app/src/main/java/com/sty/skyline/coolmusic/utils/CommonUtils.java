package com.sty.skyline.coolmusic.utils;

import android.os.Build;

/**
 * Created by tian on 2019/2/25.
 */

public class CommonUtils {

    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
