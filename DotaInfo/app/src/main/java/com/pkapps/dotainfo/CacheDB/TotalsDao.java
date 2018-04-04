package com.pkapps.dotainfo.CacheDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by PraveenKumar on 4/3/2018.
 */

@Dao
public interface TotalsDao {


    //Insert data
    @Insert
    void insert(TotalsTable totalsData);
    //delete data
    @Query("delete from TotalsTable")
    void deleteTotals();
    //Get total kills
    @Query("Select totalKills from TotalsTable")
    int getKills();

    //Get a row
    @Query("Select * from TotalsTable")
    TotalsTable getData();
    //Get total deaths
    @Query("Select totalDeaths from TotalsTable")
    int getDeaths();

    //Get total goldpermin
    @Query("Select totalgpm from TotalsTable")
    int getGold();

    //Get total xpm
    @Query("Select totalxpm from TotalsTable")
    int getXpm();

    //Get total assists
    @Query("Select totalAssists from TotalsTable")
    int getAssists();

    //Get kda
    @Query("Select totalKDA from TotalsTable")
    int getKDA();
}
