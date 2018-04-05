package com.pkapps.dotainfo.CacheDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by PraveenKumar on 4/4/2018.
 */
@Dao
public interface OverviewDao {
    @Insert
    void insert(OverviewTable overviewData);

    //Get matchdata based on matchID
    @Query("select * from OverviewTable where matchID=:matchID")
    OverviewTable getMatchOverviewData(String matchID);

    //Delete all data
    @Query("delete from OverviewTable")
    void deleteOverviewTable();
}
