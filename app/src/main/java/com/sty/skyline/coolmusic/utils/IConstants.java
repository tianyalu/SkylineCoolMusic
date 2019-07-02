package com.sty.skyline.coolmusic.utils;

/**
 * Created by tian on 2019/2/25.
 */

public interface IConstants {
    String MUSIC_COUNT_CHANGED = "com.sty.skyline.coolmusic.musiccountchanged";
    String PLAYLIST_ITEM_MOVED = "com.sty.skyline.coolmusic.moved";
    String PLAYLIST_COUNT_CHANGED = "com.sty.skyline.coolmusic.playlistcountchanged";
    String CHANGE_THEME = "com.sty.skyline.coolmusic.themechange";
    String EMPTY_LIST = "com.sty.skyline.coolmusic.emptyplaylist";
    String PACKAGE = "com.sty.skyline.coolmusic";
    int MUSIC_OVER_FLOW = 0;
    int ARTIS_OVER_FLOW = 1;
    int ALBUM_OVER_FLOW = 2;
    int FOLDE_OVER_FLOW = 3;

    //歌手和专辑列表点击都会进入MyMusic 此时要传递参数表明是从哪里进入的
    int START_FROM_ARTIST = 1;
    int START_FROM_ALBUM = 1;
    int START_FROM_LOCAL = 2;
    int START_FROM_FAVORITE = 3;

    String NAVIGATE_NOW_PLAYING = "navigate_now_playing";
}
