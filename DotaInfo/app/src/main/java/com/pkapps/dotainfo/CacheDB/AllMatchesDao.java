package com.pkapps.dotainfo.CacheDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by PraveenKumar on 3/27/2018.
 */
@Dao
public interface AllMatchesDao {

    //Add match to AllMatchesTable
    @Insert
    void insert(AllMatchesTable match);

    //Get all Matches from AllMatchestable

    @Query("Select * from AllMatchesTable")
    List<AllMatchesTable> getAllMatches();

    //Delete all data from AllMatchesTable

    @Query("delete from AllMatchesTable")
    void deleteAllMatches();

    //Get Limited list of matches

    @Query("select * from AllMatchesTable order by matchID desc limit :limit")
    List<AllMatchesTable> getLimitedMatches(int limit);

    @Query("Select MAX(kills) from ( select * from AllMatchesTable order by matchID desc limit 20) limit 1")
    int getMaxKill();
    @Query("Select MAX(deaths) from ( select * from AllMatchesTable order by matchID desc limit 20) limit 1")
    int getMaxDeath();
    @Query("Select MAX(assists) from ( select * from AllMatchesTable order by matchID desc limit 20) limit 1")
    int getMaxAssist();

}
