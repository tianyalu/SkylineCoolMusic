package com.sty.skyline.coolmusic.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.sty.skyline.coolmusic.service.MusicPlayer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Shi Tianyi on 2018/5/6/0006.
 */

public class BaseActivity extends AppCompatActivity implements ServiceConnection {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private MusicPlayer.ServiceToken mToken;
    private PlaybackStatus mPlaybackStatus; //receiver接受播放状态变化等 todo
    //private QuickControlsFragment fragment; //底部播放控制栏 //todo
    private ArrayList<MusicStateListener> mMusicListener = new ArrayList<>();

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    private static final class PlaybackStatus extends BroadcastReceiver {
        private final WeakReference<BaseActivity> mReference;

        public PlaybackStatus(final BaseActivity activity){
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            BaseActivity baseActivity = mReference.get();
            if(baseActivity != null){
                //if(action.equals(MediaService.ME)) //todo
            }
        }
    }

    public void setMusicStateListenerListener(final  MusicStateListener status){
        if(status == this){
            throw new UnsupportedOperationException("Override the method, don't add a listener");
        }

        if(status != null){
            mMusicListener.add(status);
        }
    }

    public void removeMusicStateListenerListener(final MusicStateListener status){
        if(status != null){
            mMusicListener.remove(status);
        }
    }
}
