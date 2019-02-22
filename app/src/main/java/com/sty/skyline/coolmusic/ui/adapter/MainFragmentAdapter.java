package com.sty.skyline.coolmusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.magicasakura.widgets.TintImageView;
import com.sty.skyline.coolmusic.R;
import com.sty.skyline.coolmusic.model.MainFragmentItem;
import com.sty.skyline.coolmusic.model.info.PlayList;

import java.util.ArrayList;

/**
 * Created by Shi Tianyi on 2018/5/30/0030.
 */

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ItemHolder> {
    private ArrayList<PlayList> playLists, netPlayLists = new ArrayList<>();
    private Context mContext;
    private boolean createdExpanded = true;
    private boolean collectedExpanded = true;
    private ArrayList itemResults = new ArrayList();
    private boolean isLoveList = true;


    public MainFragmentAdapter(Context context) {
        this.mContext = context;
    }

    public void updateResults(ArrayList itemResults, ArrayList<PlayList> playLists, ArrayList<PlayList> netPlayLists) {
        this.isLoveList = true;
        this.itemResults = itemResults;
        this.playLists = playLists;
        this.netPlayLists = netPlayLists;
    }

    public void updatePlayLists(ArrayList<PlayList> playLists) {
        this.playLists = playLists;
    }

    @Override
    public MainFragmentAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_item, parent, false);
                break;
            case 1:
                if(isLoveList) {
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_playlist_first_item, parent, false);
                }else {
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_playlist_item, parent, false);
                }
                break;
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_item, parent, false);
                break;
            case 3:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_item, parent, false);
                break;
            default:
                break;
        }
        if(v != null) {
            return new ItemHolder(v);
        }else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(MainFragmentAdapter.ItemHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                MainFragmentItem item = (MainFragmentItem) itemResults.get(position);
                holder.tvItemTitle.setText(item.title);
                holder.tvCount.setText("(" + item.count + ")");
                holder.tivImage.setImageResource(item.avatar);
//                holder.tivImage.setImageTintList(R.color.theme_color_primary);
                setOnListener(holder, position); //todo
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:
                holder.tvSectionItem.setText("收藏的歌单" + "(" + netPlayLists.size() + ")");
                holder.ivSectionImg.setImageResource(R.drawable.list_icn_arr_right);
                setSectionListener(holder, position);

                break;
            default:
                break;
        }
    }

    @Override
    public void onViewRecycled(ItemHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        if(itemResults == null) {
            return 0;
        }
        if(!createdExpanded && playLists != null){
            itemResults.removeAll(playLists);
        }
        if(!collectedExpanded) {
            itemResults.removeAll(netPlayLists);
        }
        return itemResults.size();
    }


    private void setOnListener(ItemHolder itemHolder, final int position) {
        //todo
    }

    private void setOnPlayListListener(ItemHolder itemHolder, final int position, final long playListId,
                                       final String albumArt, final String playListName) {
        //todo
    }

    private void setSectionListener(final ItemHolder itemHolder, int position) {
        //todo
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == 0) {
            return -1;
        }
        if (itemResults.get(position) instanceof MainFragmentItem) {
            return 0;
        }
        if(itemResults.get(position) instanceof PlayList) {
            return 1;
        }
        if(itemResults.get(position) instanceof String) {
            if(itemResults.get(position).equals("收藏的歌单")) {
                return 3;
            }
        }
        return 2;
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView tvItemTitle, tvTitle, tvCount, tvSongCount, tvSectionItem;
        protected ImageView ivMenu, ivSectionImg, ivSectionMenu;
        protected SimpleDraweeView albumArt;
        protected TintImageView tivImage;

        public ItemHolder(View itemView) {
            super(itemView);
            this.tivImage = itemView.findViewById(R.id.fragment_main_item_img);
            this.tvItemTitle = itemView.findViewById(R.id.fragment_main_item_title);
            this.tvCount = itemView.findViewById(R.id.fragment_main_item_count);

            this.tvTitle = itemView.findViewById(R.id.fragment_main_playlist_item_title);
            this.tvSongCount = itemView.findViewById(R.id.fragment_main_playlist_item_count);
            this.albumArt = itemView.findViewById(R.id.fragment_main_playlist_item_img);
            this.ivMenu = itemView.findViewById(R.id.fragment_main_playlist_item_menu);

            this.tvSectionItem = itemView.findViewById(R.id.expand_title);
            this.ivSectionImg = itemView.findViewById(R.id.expand_img);
            this.ivSectionMenu = itemView.findViewById(R.id.expand_menu);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //todo
        }
    }
}
