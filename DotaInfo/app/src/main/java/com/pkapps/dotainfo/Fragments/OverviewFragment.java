package com.pkapps.dotainfo.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkapps.dotainfo.CacheDB.AppDatabase;
import com.pkapps.dotainfo.CacheDB.OverviewTable;
import com.pkapps.dotainfo.Constants.DotaMisc;
import com.pkapps.dotainfo.DataTypes.OverviewPlayer;
import com.pkapps.dotainfo.R;
import com.pkapps.dotainfo.Tools.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    public OverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_overview, container, false);
        SharedPreferences pref = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        long matchId = pref.getLong("matchId",0);
        OverviewTable data = AppDatabase.getAppDatabase(getContext()).getOverviewDao().getMatchOverviewData(matchId);
        //player1
        OverviewPlayer player0 = data.getData().get(0);
        ((ImageView) root.findViewById(R.id.image0)).setImageResource(getContext().getResources().obtainTypedArray(R.array.hero_images).getResourceId(player0.getHeroID()-1,114));
        ((TextView) root.findViewById(R.id.player0)).setText(player0.getPlayerName());
        ((TextView) root.findViewById(R.id.hero0)).setText(DotaMisc.getHeroName(player0.getHeroID()));
        ((TextView) root.findViewById(R.id.gpm0)).setText("GPM "+player0.getGpm());
        ((TextView) root.findViewById(R.id.k0)).setText(Integer.toString(player0.getKills()));
        ((TextView) root.findViewById(R.id.d0)).setText(Integer.toString(player0.getDeaths()));
        ((TextView) root.findViewById(R.id.a0)).setText(Integer.toString(player0.getAssists()));
        //player2
        player0 = data.getData().get(1);
        ((ImageView) root.findViewById(R.id.image1)).setImageResource(getContext().getResources().obtainTypedArray(R.array.hero_images).getResourceId(player0.getHeroID()-1,114));
        ((TextView) root.findViewById(R.id.player1)).setText(player0.getPlayerName());
        ((TextView) root.findViewById(R.id.hero1)).setText(DotaMisc.getHeroName(player0.getHeroID()));
        ((TextView) root.findViewById(R.id.gpm1)).setText("GPM "+player0.getGpm());
        ((TextView) root.findViewById(R.id.k1)).setText(Integer.toString(player0.getKills()));
        ((TextView) root.findViewById(R.id.d1)).setText(Integer.toString(player0.getDeaths()));
        ((TextView) root.findViewById(R.id.a1)).setText(Integer.toString(player0.getAssists()));
        //player3
        player0 = data.getData().get(2);
        ((ImageView) root.findViewById(R.id.image2)).setImageResource(getContext().getResources().obtainTypedArray(R.array.hero_images).getResourceId(player0.getHeroID()-1,114));
        ((TextView) root.findViewById(R.id.player2)).setText(player0.getPlayerName());
        ((TextView) root.findViewById(R.id.hero2)).setText(DotaMisc.getHeroName(player0.getHeroID()));
        ((TextView) root.findViewById(R.id.gpm2)).setText("GPM "+player0.getGpm());
        ((TextView) root.findViewById(R.id.k2)).setText(Integer.toString(player0.getKills()));
        ((TextView) root.findViewById(R.id.d2)).setText(Integer.toString(player0.getDeaths()));
        ((TextView) root.findViewById(R.id.a2)).setText(Integer.toString(player0.getAssists()));
        //player4
        player0 = data.getData().get(3);
        ((ImageView) root.findViewById(R.id.image3)).setImageResource(getContext().getResources().obtainTypedArray(R.array.hero_images).getResourceId(player0.getHeroID()-1,114));
        ((TextView) root.findViewById(R.id.player3)).setText(player0.getPlayerName());
        ((TextView) root.findViewById(R.id.hero3)).setText(DotaMisc.getHeroName(player0.getHeroID()));
        ((TextView) root.findViewById(R.id.gpm3)).setText("GPM "+player0.getGpm());
        ((TextView) root.findViewById(R.id.k3)).setText(Integer.toString(player0.getKills()));
        ((TextView) root.findViewById(R.id.d3)).setText(Integer.toString(player0.getDeaths()));
        ((TextView) root.findViewById(R.id.a3)).setText(Integer.toString(player0.getAssists()));
        //player5
        player0 = data.getData().get(4);
        ((ImageView) root.findViewById(R.id.image4)).setImageResource(getContext().getResources().obtainTypedArray(R.array.hero_images).getResourceId(player0.getHeroID()-1,114));
        ((TextView) root.findViewById(R.id.player4)).setText(player0.getPlayerName());
        ((TextView) root.findViewById(R.id.hero4)).setText(DotaMisc.getHeroName(player0.getHeroID()));
        ((TextView) root.findViewById(R.id.gpm4)).setText("GPM "+player0.getGpm());
        ((TextView) root.findViewById(R.id.k4)).setText(Integer.toString(player0.getKills()));
        ((TextView) root.findViewById(R.id.d4)).setText(Integer.toString(player0.getDeaths()));
        ((TextView) root.findViewById(R.id.a4)).setText(Integer.toString(player0.getAssists()));
        //player6
        player0 = data.getData().get(5);
        ((ImageView) root.findViewById(R.id.image5)).setImageResource(getContext().getResources().obtainTypedArray(R.array.hero_images).getResourceId(player0.getHeroID()-1,114));
        ((TextView) root.findViewById(R.id.player5)).setText(player0.getPlayerName());
        ((TextView) root.findViewById(R.id.hero5)).setText(DotaMisc.getHeroName(player0.getHeroID()));
        ((TextView) root.findViewById(R.id.gpm5)).setText("GPM "+player0.getGpm());
        ((TextView) root.findViewById(R.id.k5)).setText(Integer.toString(player0.getKills()));
        ((TextView) root.findViewById(R.id.d5)).setText(Integer.toString(player0.getDeaths()));
        ((TextView) root.findViewById(R.id.a5)).setText(Integer.toString(player0.getAssists()));
        //player7
        player0 = data.getData().get(6);
        ((ImageView) root.findViewById(R.id.image6)).setImageResource(getContext().getResources().obtainTypedArray(R.array.hero_images).getResourceId(player0.getHeroID()-1,114));
        ((TextView) root.findViewById(R.id.player6)).setText(player0.getPlayerName());
        ((TextView) root.findViewById(R.id.hero6)).setText(DotaMisc.getHeroName(player0.getHeroID()));
        ((TextView) root.findViewById(R.id.gpm6)).setText("GPM "+player0.getGpm());
        ((TextView) root.findViewById(R.id.k6)).setText(Integer.toString(player0.getKills()));
        ((TextView) root.findViewById(R.id.d6)).setText(Integer.toString(player0.getDeaths()));
        ((TextView) root.findViewById(R.id.a6)).setText(Integer.toString(player0.getAssists()));
        //player8
        player0 = data.getData().get(7);
        ((ImageView) root.findViewById(R.id.image7)).setImageResource(getContext().getResources().obtainTypedArray(R.array.hero_images).getResourceId(player0.getHeroID()-1,114));
        ((TextView) root.findViewById(R.id.player7)).setText(player0.getPlayerName());
        ((TextView) root.findViewById(R.id.hero7)).setText(DotaMisc.getHeroName(player0.getHeroID()));
        ((TextView) root.findViewById(R.id.gpm7)).setText("GPM "+player0.getGpm());
        ((TextView) root.findViewById(R.id.k7)).setText(Integer.toString(player0.getKills()));
        ((TextView) root.findViewById(R.id.d7)).setText(Integer.toString(player0.getDeaths()));
        ((TextView) root.findViewById(R.id.a7)).setText(Integer.toString(player0.getAssists()));
        //player9
        player0 = data.getData().get(8);
        ((ImageView) root.findViewById(R.id.image8)).setImageResource(getContext().getResources().obtainTypedArray(R.array.hero_images).getResourceId(player0.getHeroID()-1,114));
        ((TextView) root.findViewById(R.id.player8)).setText(player0.getPlayerName());
        ((TextView) root.findViewById(R.id.hero8)).setText(DotaMisc.getHeroName(player0.getHeroID()));
        ((TextView) root.findViewById(R.id.gpm8)).setText("GPM "+player0.getGpm());
        ((TextView) root.findViewById(R.id.k8)).setText(Integer.toString(player0.getKills()));
        ((TextView) root.findViewById(R.id.d8)).setText(Integer.toString(player0.getDeaths()));
        ((TextView) root.findViewById(R.id.a8)).setText(Integer.toString(player0.getAssists()));
        //player10
        player0 = data.getData().get(9);
        ((ImageView) root.findViewById(R.id.image9)).setImageResource(getContext().getResources().obtainTypedArray(R.array.hero_images).getResourceId(player0.getHeroID()-1,114));
        ((TextView) root.findViewById(R.id.player9)).setText(player0.getPlayerName());
        ((TextView) root.findViewById(R.id.hero9)).setText(DotaMisc.getHeroName(player0.getHeroID()));
        ((TextView) root.findViewById(R.id.gpm9)).setText("GPM "+player0.getGpm());
        ((TextView) root.findViewById(R.id.k9)).setText(Integer.toString(player0.getKills()));
        ((TextView) root.findViewById(R.id.d9)).setText(Integer.toString(player0.getDeaths()));
        ((TextView) root.findViewById(R.id.a9)).setText(Integer.toString(player0.getAssists()));
        return root;
    }

}
