package com.pkapps.dotainfo.CacheDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.pkapps.dotainfo.DataTypes.OverviewPlayer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PraveenKumar on 4/4/2018.
 */
@Entity
public class OverviewTable {

    @NonNull@PrimaryKey String matchID;
    ArrayList<OverviewPlayer> data;
    int radiant_score;
    int dire_score;

    @NonNull
    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(@NonNull String matchID) {
        this.matchID = matchID;
    }

    public ArrayList<OverviewPlayer> getData() {
        return data;
    }

    public void setData(ArrayList<OverviewPlayer> data) {
        this.data = data;
    }

    public int getRadiant_score() {
        return radiant_score;
    }

    public void setRadiant_score(int radiant_score) {
        this.radiant_score = radiant_score;
    }

    public int getDire_score() {
        return dire_score;
    }

    public void setDire_score(int dire_score) {
        this.dire_score = dire_score;
    }
}
