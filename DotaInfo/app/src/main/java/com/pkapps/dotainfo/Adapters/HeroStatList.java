package com.pkapps.dotainfo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkapps.dotainfo.CacheDB.AllMatchesTable;
import com.pkapps.dotainfo.CacheDB.HeroStatsTable;
import com.pkapps.dotainfo.Constants.DotaMisc;
import com.pkapps.dotainfo.R;
import com.pkapps.dotainfo.Tools.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PraveenKumar on 4/3/2018.
 */

public class HeroStatList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context ctx;
    List<HeroStatsTable> items = new ArrayList<>();
    public View lyt_parent;

    public HeroStatList(Context ctx, List<HeroStatsTable> items) {
        this.ctx = ctx;
        this.items = items;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_stat_list, parent, false);
        vh = new HeroStatList.OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MainList.OriginalViewHolder) {
            MainList.OriginalViewHolder view = (MainList.OriginalViewHolder) holder;

            HeroStatsTable p = items.get(position);
            view.name.setText(DotaMisc.getHeroName(p.getHeroID()));
            view.image.setImageResource(ctx.getResources().obtainTypedArray(R.array.hero_images).getResourceId(p.getHeroID()-1,114));
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
