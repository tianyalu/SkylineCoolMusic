package com.sty.skyline.coolmusic.activity;

/**
 * Created by Shi Tianyi on 2018/5/7/0007.
 */

public interface MusicStateListener {
    /**
     * 更新歌曲状态信息
     */
    void updateTrackInfo();

    void updateTime();

    void changeTheme();

    void reloadAdapter();
}
