package com.sty.skyline.coolmusic.utils;

import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;

/**
 * Created by tian on 2019/2/25.
 */

public class MusicUtils implements IConstants {
    public static final int FILTER_SIZE = 1 * 1024 * 1024; //1MB
    public static final int FILTER_DURATION = 1 * 60 * 1000; //1分钟

    private static String[] proj_music = new String[]{
           Media._ID, Media.TITLE,
            Media.DATA, Media.ALBUM_ID,
            Media.ALBUM, Media.ARTIST,
            Media.ARTIST_ID, Media.DURATION, Media.SIZE
    };
    private static String[] proj_album = new String[] {
            MediaStore.Audio.Albums._ID,
    };
}
