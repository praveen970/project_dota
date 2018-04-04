package com.pkapps.dotainfo.CacheDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by PraveenKumar on 4/3/2018.
 */

@Entity
public class TotalsTable {
    @NonNull@PrimaryKey
            String playerID;
    int totalKills;
    int totalDeaths;
    int totalAssists;
    int totalKDA;
    int totalgpm;
    int totalxpm;
    int totallh;
    int totaldenies;
    int totalMatches;

    @NonNull
    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(@NonNull String playerID) {
        this.playerID = playerID;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public int getTotalAssists() {
        return totalAssists;
    }

    public void setTotalAssists(int totalAssists) {
        this.totalAssists = totalAssists;
    }

    public int getTotalKDA() {
        return totalKDA;
    }

    public void setTotalKDA(int totalKDA) {
        this.totalKDA = totalKDA;
    }

    public int getTotalgpm() {
        return totalgpm;
    }

    public void setTotalgpm(int totalgpm) {
        this.totalgpm = totalgpm;
    }

    public int getTotalxpm() {
        return totalxpm;
    }

    public void setTotalxpm(int totalxpm) {
        this.totalxpm = totalxpm;
    }

    public int getTotallh() {
        return totallh;
    }

    public void setTotallh(int totallh) {
        this.totallh = totallh;
    }

    public int getTotaldenies() {
        return totaldenies;
    }

    public void setTotaldenies(int totaldenies) {
        this.totaldenies = totaldenies;
    }

    public int getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    }
}
