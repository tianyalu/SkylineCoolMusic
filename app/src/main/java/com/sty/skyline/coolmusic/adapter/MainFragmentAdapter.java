package com.sty.skyline.coolmusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.magicasakura.widgets.TintImageView;
import com.sty.skyline.coolmusic.R;

/**
 * Created by Shi Tianyi on 2018/5/30/0030.
 */

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ItemHolder> {
    @Override
    public MainFragmentAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MainFragmentAdapter.ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView tvItemTitle, tvTitle, tvCount, tvSongCount, tvSectionItem;
        protected ImageView ivMenu, ivSectionImg, ivSectionMenu;
        protected SimpleDraweeView albumArt;
        protected TintImageView tivImage;

        public ItemHolder(View itemView) {
            super(itemView);
            this.tivImage = itemView.findViewById(R.id.fragment_main_item_img);
            //todo
        }

        @Override
        public void onClick(View v) {

        }
    }
}
