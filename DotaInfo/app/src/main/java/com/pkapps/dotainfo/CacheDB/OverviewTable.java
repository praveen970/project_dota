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

    @NonNull@PrimaryKey long matchID;
    ArrayList<OverviewPlayer> data;
    String radiant_score;
    String dire_score;
    boolean isRadiantWin;
    long startTime;

    @NonNull
    public long getMatchID() {
        return matchID;
    }

    public void setMatchID(@NonNull long matchID) {
        this.matchID = matchID;
    }

    public ArrayList<OverviewPlayer> getData() {
        return data;
    }

    public void setData(ArrayList<OverviewPlayer> data) {
        this.data = data;
    }

    public String getRadiant_score() {
        return radiant_score;
    }

    public void setRadiant_score(String radiant_score) {
        this.radiant_score = radiant_score;
    }

    public String getDire_score() {
        return dire_score;
    }

    public void setDire_score(String dire_score) {
        this.dire_score = dire_score;
    }

    public boolean isRadiantWin() {
        return isRadiantWin;
    }

    public void setRadiantWin(boolean radiantWin) {
        isRadiantWin = radiantWin;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
