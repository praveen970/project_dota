package com.pkapps.dotainfo.CacheDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by PraveenKumar on 4/3/2018.
 */
@Entity
public class HeroStatsTable {
    @NonNull@PrimaryKey String matchID;
    int heroID;
    int gamesPLayed;
    int won;
    int withPlayed;
    int withWon;
    int againstPlayed;
    int againstWon;

    @NonNull
    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(@NonNull String matchID) {
        this.matchID = matchID;
    }

    public int getHeroID() {
        return heroID;
    }

    public void setHeroID(int heroID) {
        this.heroID = heroID;
    }

    public int getGamesPLayed() {
        return gamesPLayed;
    }

    public void setGamesPLayed(int gamesPLayed) {
        this.gamesPLayed = gamesPLayed;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getWithPlayed() {
        return withPlayed;
    }

    public void setWithPlayed(int withPlayed) {
        this.withPlayed = withPlayed;
    }

    public int getWithWon() {
        return withWon;
    }

    public void setWithWon(int withWon) {
        this.withWon = withWon;
    }

    public int getAgainstPlayed() {
        return againstPlayed;
    }

    public void setAgainstPlayed(int againstPlayed) {
        this.againstPlayed = againstPlayed;
    }

    public int getAgainstWon() {
        return againstWon;
    }

    public void setAgainstWon(int againstWon) {
        this.againstWon = againstWon;
    }
}
