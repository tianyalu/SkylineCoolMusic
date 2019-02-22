package com.sty.skyline.coolmusic.ui.fragment;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicasakura.utils.ThemeUtils;
import com.sty.skyline.coolmusic.R;
import com.sty.skyline.coolmusic.model.MainFragmentItem;
import com.sty.skyline.coolmusic.model.info.PlayList;
import com.sty.skyline.coolmusic.provider.PlayListInfo;
import com.sty.skyline.coolmusic.ui.adapter.MainFragmentAdapter;
import com.sty.skyline.coolmusic.widget.recyclerview.RecyclerViewDivider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地界面主界面
 * Created by Shi Tianyi on 2018/5/23/0023.
 */

public class MainFragment extends BaseFragment {
    private MainFragmentAdapter mAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private static List<MainFragmentItem> mList = new ArrayList<>();
    private static PlayListInfo playListInfo; //playlist 管理类
    private SwipeRefreshLayout swipeRefreshLayout; //下拉刷新layout
    private MyAsyncTask reloadAsyncTask;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            reloadAdapter();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playListInfo = PlayListInfo.getInstance(mContext);
        //todo
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        swipeRefreshLayout = view.findViewById(R.id.srl_refresh);
        recyclerView = view.findViewById(R.id.rcv_recycler_view);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout.setColorSchemeColors(ThemeUtils.getColorById(mContext, R.color.theme_color_primary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadAdapter();
            }
        });

        //先给adapter设置空数据,异步加载好后更新数据,防止RecyclerView no attach
        mAdapter = new MainFragmentAdapter(mContext);
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, DividerItemDecoration.VERTICAL,
                2, getResources().getColor(R.color.divider_color)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        //设置没有Item动画
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        reloadAdapter();

        mContext.getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);
        return view;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) { //相当于Fragment的OnResume()
            reloadAdapter();
        }
    }

    //为info设置数据,并放入mListInfo
    private static void setInfo(String title, int count, int id, int i) {
        MainFragmentItem information = new MainFragmentItem();
        information.title = title;
        information.count = count;
        information.avatar = id;
        if (mList.size() < 4) {
            mList.add(new MainFragmentItem());
        }
        mList.set(i, information); //将新的info对象加入到信息列表中
    }

    //设置音乐overflow条目
    private static void setMusicInfo() {
        //todo
        loadCount(true);
    }

    private static void loadCount(boolean has) {
        int localMusicCount = 0;
        int recentMuscCount = 0;
        int downLoadCount = 0;
        int artistsCount = 0;

        if(has) {
            try {
                //todo
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        setInfo("本地音乐", localMusicCount, R.drawable.music_icn_local, 0);
        setInfo("最近播放", recentMuscCount, R.drawable.music_icn_recent, 1);
        setInfo("下载管理", downLoadCount, R.drawable.music_icn_dld, 2);
        setInfo("我的歌手", artistsCount, R.drawable.music_icn_artist, 3);
    }

    //刷新列表
    public void reloadAdapter() {
        reloadAsyncTask = new MyAsyncTask(mContext, mAdapter);
        reloadAsyncTask.execute();
    }

    private static class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        //弱引用是允许被gc回收的
        private final WeakReference<Activity> weakActivity;
        private MainFragmentAdapter mAdapter;

        public MyAsyncTask(Activity weakActivity, MainFragmentAdapter adapter) {
            this.weakActivity = new WeakReference<Activity>(weakActivity);
            this.mAdapter = adapter;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList results = new ArrayList();
            setMusicInfo();
            ArrayList<PlayList> playLists = playListInfo.getPlayList();  //todo
            ArrayList<PlayList> netPlayLists = playListInfo.getNetPlayList(); //todo
            results.addAll(mList);
            results.add("创意歌单");
            results.addAll(playLists);
            if (netPlayLists != null) {
                results.add("收藏的歌单");
                results.addAll(netPlayLists);
            }

            if(mAdapter == null) {
                mAdapter = new MainFragmentAdapter(weakActivity.get());
            }
            mAdapter.updateResults(results, playLists, netPlayLists);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Activity activity = weakActivity.get();
            if(activity == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            mAdapter.notifyDataSetChanged();
            SwipeRefreshLayout refreshLayout = activity.findViewById(R.id.srl_refresh);
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(reloadAsyncTask != null) {
            reloadAsyncTask.cancel(true);
        }
    }
}
