package com.pkapps.dotainfo.CacheDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Created by PraveenKumar on 3/27/2018.
 */

@Database(entities = {AllMatchesTable.class,TotalsTable.class,HeroStatsTable.class,OverviewTable.class},version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AllMatchesDao getAllMatchesDao();
    public abstract TotalsDao getTotalsTableDao();
    public abstract HeroStatsDao getHeroStatsDao();
    public abstract OverviewDao getOverviewDao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "dotainfo-database").allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
