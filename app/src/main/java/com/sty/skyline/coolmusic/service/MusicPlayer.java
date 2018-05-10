package com.sty.skyline.coolmusic.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.util.WeakHashMap;

/**
 * Created by Shi Tianyi on 2018/5/7/0007.
 */

public class MusicPlayer {
    private static final WeakHashMap<Context, ServiceBinder> mConnectionMap;
    private static final long[] sEmptyList;
    //public static MediaAidlInterface mService = null;
    private static ContentValues[] mContentValuesCache = null;

    static {
        mConnectionMap = new WeakHashMap<>();
        sEmptyList = new long[0];
    }

    public static final ServiceToken bindToService(final Context context,
                                                   final ServiceConnection callback){
        Activity realActivity = ((Activity) context).getParent();
        if(realActivity == null){
            realActivity = (Activity) context;
        }
        final ContextWrapper contextWrapper = new ContextWrapper(realActivity);
        contextWrapper.startService(new Intent(contextWrapper, MediaPlayer.class));
        final ServiceBinder binder = new ServiceBinder(callback, contextWrapper.getApplicationContext());
        if(contextWrapper.bindService(new Intent().setClass(contextWrapper, MediaPlayer.class), binder, 0)){
            mConnectionMap.put(contextWrapper, binder);
            return new ServiceToken(contextWrapper);
        }
        return null;
    }

    public static final class ServiceBinder implements ServiceConnection{
        private final ServiceConnection mCallback;
        private final Context mContext;

        public ServiceBinder(final ServiceConnection callback, final Context context){
            this.mCallback = callback;
            this.mContext = context;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //todo service
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //todo service
        }
    }

    public static final class ServiceToken{
        public ContextWrapper mContextWrapper;

        public ServiceToken(final ContextWrapper context){
            mContextWrapper = context;
        }
    }
}
