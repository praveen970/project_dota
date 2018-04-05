package com.pkapps.dotainfo.JsonParsers;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.pkapps.dotainfo.CacheDB.AppDatabase;
import com.pkapps.dotainfo.CacheDB.OverviewTable;
import com.pkapps.dotainfo.DataTypes.OverviewPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PraveenKumar on 4/4/2018.
 */

public class MatchAnalysisParser {

    public static void OverviewParser(Context ctx, JSONObject jsonObject){
        OverviewTable overviewObj = new OverviewTable();
        OverviewPlayer playerObj;
        ArrayList<OverviewPlayer> playerData;
        JSONArray playersJson;
        JSONObject statsJsonObj;
        playerData = new ArrayList<>();
        try {
            String matchId = jsonObject.getString("match_id");
            overviewObj.setMatchID(matchId);
            overviewObj.setRadiant_score(jsonObject.getInt("radiant_score"));
            overviewObj.setDire_score(jsonObject.getInt("dire_score"));
            playersJson = jsonObject.getJSONArray("players");
            for(int i=0;i<playersJson.length();i++){
                statsJsonObj = playersJson.getJSONObject(i);
                playerObj = new OverviewPlayer();
                try {
                    playerObj.setPlayerName(statsJsonObj.getString("personaname"));
                }catch (Exception e){
                    playerObj.setPlayerName("Anonymous");

                }
                playerObj.setHeroID(statsJsonObj.getInt("hero_id"));
                playerObj.setLevel(statsJsonObj.getInt("level"));
                playerObj.setXpm(statsJsonObj.getInt("xp_per_min"));
                playerObj.setGpm(statsJsonObj.getInt("gold_per_min"));
                playerObj.setAssists(statsJsonObj.getInt("assists"));
                playerObj.setKills(statsJsonObj.getInt("kills"));
                playerObj.setDeaths(statsJsonObj.getInt("deaths"));
                playerData.add(playerObj);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        overviewObj.setData(playerData);
        try {
            AppDatabase.getAppDatabase(ctx).getOverviewDao().insert(overviewObj);
        }catch (SQLiteConstraintException exception){

        }


    }
}
