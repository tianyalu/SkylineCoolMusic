package com.sty.skyline.coolmusic.model;

/**
 * Created by tian on 2019/2/22.
 */

public class MainFragmentItem {
    public String title; //信息标题
    public int count;
    public int avatar; //图片id
    public boolean countChanged = true;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public boolean isCountChanged() {
        return countChanged;
    }

    public void setCountChanged(boolean countChanged) {
        this.countChanged = countChanged;
    }
}
