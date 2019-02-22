package com.sty.skyline.coolmusic.model.info;

/**
 * Created by tian on 2019/2/22.
 */

public class PlayList {
    public final long id;
    public final String name;
    public final int songCount;
    public final String albumArt;
    public final String author;

    public PlayList() {
        this.id = -1;
        this.name = "";
        this.songCount = -1;
        this.albumArt = "";
        this.author = "";
    }

    public PlayList(long _id, String _name, int _songCount, String _albumArt, String _author) {
        this.id = _id;
        this.name = _name;
        this.songCount = _songCount;
        this.albumArt = _albumArt;
        this.author = _author;
    }
}
