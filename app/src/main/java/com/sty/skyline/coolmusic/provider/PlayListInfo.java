package com.sty.skyline.coolmusic.provider;

import android.content.Context;

import com.sty.skyline.coolmusic.model.info.PlayList;

import java.util.ArrayList;

/**
 * Created by tian on 2019/2/22.
 */

public class PlayListInfo {

    private static Context mContext;

    private PlayListInfo(final Context context) {

    }

    private static final class LazyHolder {
        private static PlayListInfo INSTANCE = new PlayListInfo(mContext.getApplicationContext());
    }

    public static PlayListInfo getInstance(Context context) {
        mContext = context;
        return LazyHolder.INSTANCE;
    }

    public synchronized ArrayList<PlayList> getPlayList() {
        ArrayList<PlayList> results = new ArrayList<>();

        return results;
    }

    public synchronized ArrayList<PlayList> getNetPlayList() {
        ArrayList<PlayList> results = new ArrayList<>();

        return results;
    }
}
