package com.pkapps.dotainfo.JsonParsers;

import android.content.Context;

import com.pkapps.dotainfo.AsyncCalls.AllMatches;
import com.pkapps.dotainfo.CacheDB.AllMatchesTable;
import com.pkapps.dotainfo.CacheDB.AppDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PraveenKumar on 3/27/2018.
 */

public class Parser {

    public static void getAllMatches(Context ctx, JSONArray allMatchJsonArray){
        JSONObject matchObject;
        AllMatchesTable matchData;
        for(int i=allMatchJsonArray.length()-1;i>=0;i--){
            try {
                matchObject = allMatchJsonArray.getJSONObject(i);
                matchData = new AllMatchesTable();
                matchData.setMatchID(matchObject.getString("match_id"));
                matchData.setResult(matchObject.getString("radiant_win"));
                matchData.setDuration(matchObject.getInt("duration"));
                matchData.setGameMode(matchObject.getInt("game_mode"));
                matchData.setLobbyType(matchObject.getInt("lobby_type"));
                matchData.setHeroID(matchObject.getInt("hero_id"));
                try {
                    AppDatabase.getAppDatabase(ctx).getAllMatchesDao().insert(matchData);
                }catch (Exception e){
                    continue;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
