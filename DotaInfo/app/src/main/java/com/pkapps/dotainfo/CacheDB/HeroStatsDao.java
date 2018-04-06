package com.pkapps.dotainfo.CacheDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by PraveenKumar on 4/3/2018.
 */
@Dao
public interface HeroStatsDao {
    //Insert data
    @Insert
    void insertStats(HeroStatsTable heroStat);

    //Get all data
    @Query("select * from HeroStatsTable")
    List<HeroStatsTable> getStats();

    //Get LimitedData
    @Query("select * from (select * from HeroStatsTable order by gamesPLayed desc) limit :limit")
    List<HeroStatsTable> getLimitedStats(int limit);

    //Delete
    @Query("delete from HeroStatsTable")
    void deleteStats();
}
