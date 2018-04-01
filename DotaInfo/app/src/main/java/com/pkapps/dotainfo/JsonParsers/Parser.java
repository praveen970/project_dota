package com.pkapps.dotainfo.JsonParsers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.pkapps.dotainfo.AsyncCalls.AllMatches;
import com.pkapps.dotainfo.CacheDB.AllMatchesTable;
import com.pkapps.dotainfo.CacheDB.AppDatabase;
import com.pkapps.dotainfo.Tools.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
                matchData.setResult(matchObject.getBoolean("radiant_win"));
                matchData.setDuration(matchObject.getInt("duration"));
                matchData.setGameMode(matchObject.getInt("game_mode"));
                matchData.setLobbyType(matchObject.getInt("lobby_type"));
                matchData.setHeroID(matchObject.getInt("hero_id"));
                matchData.setPlayerSlot(matchObject.getInt("player_slot"));
                matchData.setStartTime(matchObject.getInt("start_time"));
                matchData.setKills(matchObject.getInt("kills"));
                matchData.setDeaths(matchObject.getInt("deaths"));
                matchData.setAssists(matchObject.getInt("assists"));
                try {
                    AppDatabase.getAppDatabase(ctx).getAllMatchesDao().insert(matchData);
                }catch (Exception e){
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public static void getMatchDetails(Context ctx, JSONObject matchObject){

    }
    public static void setProfile(Context ctx, JSONObject jsonObject){
        Context context = ctx;
        SharedPreferences prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            JSONObject profileObject = jsonObject.getJSONObject("profile");
            editor.putString("personaname",profileObject.getString("personaname"));
            editor.putString("rank_tier",jsonObject.getString("rank_tier"));
            editor.putString("profileUrl",profileObject.getString("profileurl"));
            JSONObject mmrObj = jsonObject.getJSONObject("mmr_estimate");
            editor.putString("mmr",mmrObj.getString("estimate"));
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){

        }
    }
    public static void getRefreshedMatches(Context ctx, JSONArray allMatchJsonArray){
        JSONObject matchObject;
        AllMatchesTable matchData;
        for(int i=0;i<allMatchJsonArray.length();i++){
            try {
                matchObject = allMatchJsonArray.getJSONObject(i);
                matchData = new AllMatchesTable();
                matchData.setMatchID(matchObject.getString("match_id"));
                matchData.setResult(matchObject.getBoolean("radiant_win"));
                matchData.setDuration(matchObject.getInt("duration"));
                matchData.setGameMode(matchObject.getInt("game_mode"));
                matchData.setLobbyType(matchObject.getInt("lobby_type"));
                matchData.setHeroID(matchObject.getInt("hero_id"));
                matchData.setPlayerSlot(matchObject.getInt("player_slot"));
                matchData.setStartTime(matchObject.getInt("start_time"));
                matchData.setKills(matchObject.getInt("kills"));
                matchData.setDeaths(matchObject.getInt("deaths"));
                matchData.setAssists(matchObject.getInt("assists"));
                try {
                    AppDatabase.getAppDatabase(ctx).getAllMatchesDao().insert(matchData);
                }catch (Exception e){
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
