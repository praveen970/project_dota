package com.pkapps.dotainfo.Adapters;



import android.app.Dialog;
import android.content.Context;
import android.provider.SyncStateContract;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pkapps.dotainfo.CacheDB.AllMatchesTable;
import com.pkapps.dotainfo.Constants.DotaMisc;
import com.pkapps.dotainfo.R;
import com.pkapps.dotainfo.Tools.Util;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AllMatchesTable> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, AllMatchesTable obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public MainList(Context context, List<AllMatchesTable> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView result;
        public LinearLayout listTab;
        public View lyt_parent;
        public TextView lobby;
        public ImageButton infoButton;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            result = (TextView) v.findViewById(R.id.match_result);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
            listTab = (LinearLayout) v.findViewById(R.id.result_colour);
            lobby = (TextView) v.findViewById(R.id.lobby);
            infoButton = (ImageButton) v.findViewById(R.id.infoButton);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_layout, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            AllMatchesTable p = items.get(position);
            view.lobby.setText(DotaMisc.getLobby(p.getLobbyType()));
            view.name.setText(DotaMisc.getHeroName(p.getHeroID()));
            if((p.getPlayerSlot()<128 && p.getResult())||(p.getPlayerSlot()>127 && !p.getResult())){
                view.result.setText("Won");
                view.listTab.setBackgroundResource(R.drawable.layout_cornered_radius_green);
            }else{
                view.result.setText("Lost");
                view.listTab.setBackgroundResource(R.drawable.layout_cornered_radius_red);
            }
            view.infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAdditionalInfo(items.get(position));
                }
            });

            view.image.setImageResource(ctx.getResources().obtainTypedArray(R.array.hero_images).getResourceId(p.getHeroID()-1,114));
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void showAdditionalInfo(AllMatchesTable data){
        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.info_dialog);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.personaname)).setText(DotaMisc.getHeroName(data.getHeroID()));
        ((ImageView) dialog.findViewById(R.id.image)).setImageResource(ctx.getResources().obtainTypedArray(R.array.hero_images).getResourceId(data.getHeroID()-1,114));
        ((TextView) dialog.findViewById(R.id.start_time)).setText(Util.getDateCurrentTimeZone(data.getStartTime()));
        ((TextView) dialog.findViewById(R.id.duration)).setText(Util.getDuration(data.getDuration()));
        ((TextView) dialog.findViewById(R.id.mode)).setText(DotaMisc.getGameMode(data.getGameMode())+" "+DotaMisc.getLobby(data.getLobbyType()));
        ((TextView) dialog.findViewById(R.id.kda)).setText(""+data.getKills()+"/"+data.getDeaths()+"/"+data.getAssists());
        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((AppCompatButton) dialog.findViewById(R.id.bt_follow)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}