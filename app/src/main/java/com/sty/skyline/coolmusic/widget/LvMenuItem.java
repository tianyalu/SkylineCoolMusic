package com.sty.skyline.coolmusic.widget;

import android.text.TextUtils;

/**
 * Created by Steven.S on 2018/6/6/0006.
 */
public class LvMenuItem {
    public String name;
    public int type;
    public int icon;

    private static final int NO_ICON = 0;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_NO_ICON = 1;
    public static final int TYPE_SEPARATOR = 2;

    public LvMenuItem(){
        this(null);
    }

    public LvMenuItem(String name){
        this(NO_ICON, name);
    }

    public LvMenuItem(int icon, String name){
        this.icon = icon;
        this.name = name;

        if(icon == NO_ICON && TextUtils.isEmpty(name)){
            type = TYPE_SEPARATOR;
        }else if(icon == NO_ICON){
            type = TYPE_NO_ICON;
        }else{
            type = TYPE_NORMAL;
        }

        if(type != TYPE_SEPARATOR && TextUtils.isEmpty(name)){
            throw new IllegalArgumentException("You need set a nae for a non-SEPARATOR item");
        }
    }
}
