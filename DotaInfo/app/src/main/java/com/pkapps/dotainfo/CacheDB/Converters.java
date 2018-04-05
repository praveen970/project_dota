package com.pkapps.dotainfo.CacheDB;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pkapps.dotainfo.DataTypes.OverviewPlayer;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by PraveenKumar on 4/4/2018.
 */

public class Converters  {

    @TypeConverter
    public static String fromArrayList(ArrayList<OverviewPlayer> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static ArrayList<OverviewPlayer> fromString(String value){
        Type listType = new TypeToken<ArrayList<OverviewPlayer>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
}
