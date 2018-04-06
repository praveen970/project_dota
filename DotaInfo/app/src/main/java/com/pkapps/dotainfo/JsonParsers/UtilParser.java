package com.pkapps.dotainfo.JsonParsers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.pkapps.dotainfo.AsyncCalls.AllMatches;
import com.pkapps.dotainfo.CacheDB.AllMatchesTable;
import com.pkapps.dotainfo.CacheDB.AppDatabase;
import com.pkapps.dotainfo.CacheDB.HeroStatsTable;
import com.pkapps.dotainfo.CacheDB.TotalsTable;
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

public class UtilParser {

    public static boolean getAllMatches(Context ctx, JSONArray allMatchJsonArray){
        JSONObject matchObject;
        AllMatchesTable matchData;
        if(allMatchJsonArray != null && allMatchJsonArray.length()!=0) {
            for (int i = allMatchJsonArray.length() - 1; i >= 0; i--) {
                try {
                    matchObject = allMatchJsonArray.getJSONObject(i);
                    matchData = new AllMatchesTable();
                    matchData.setMatchID(matchObject.getLong("match_id"));
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
                    } catch (Exception e) {
                        break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
    }
    public static void getMatchDetails(Context ctx, JSONObject matchObject){

    }
    public static void setProfile(Context ctx, JSONObject jsonObject){
        Context context = ctx;
        SharedPreferences prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if(jsonObject != null) {
            try {
                JSONObject profileObject = jsonObject.getJSONObject("profile");
                try {
                    editor.putString("profileUrl", profileObject.getString("profileurl"));
                } catch (Exception e) {
                    e.printStackTrace();
                    editor.commit();
                }
                try {
                    editor.putString("personaname", profileObject.getString("personaname"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    editor.commit();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                editor.commit();
            }

            try {
                editor.putInt("rank_tier", jsonObject.getInt("rank_tier"));
            } catch (JSONException e) {
                e.printStackTrace();
                editor.commit();
            }
            try {
                JSONObject mmrObj = jsonObject.getJSONObject("mmr_estimate");
                try {
                    editor.putString("mmr", mmrObj.getString("estimate"));
                    editor.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    editor.commit();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                editor.commit();
            }
        }
    }
    public static void getRefreshedMatches(Context ctx, JSONArray allMatchJsonArray){
        JSONObject matchObject;
        AllMatchesTable matchData;
        for(int i=0;i<allMatchJsonArray.length();i++){
            try {
                matchObject = allMatchJsonArray.getJSONObject(i);
                matchData = new AllMatchesTable();
                matchData.setMatchID(matchObject.getLong("match_id"));
                matchData.setResult(matchObject.getBoolean("radiant_win"));
                matchData.setDuration(matchObject.getInt("duration"));
                matchData.setGameMode(matchObject.getInt("game_mode"));
                matchData.setLobbyType(matchObject.getInt("lobby_type"));
                matchData.setHeroID(matchObject.getInt("hero_id"));
                matchData.setPlayerSlot(matchObject.getInt("player_slot"));
                matchData.setStartTime(matchObject.getLong("start_time"));
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
    public static void getTotalsToDB(Context ctx,JSONArray jsonArray){
        JSONObject field;
        String playerID = ctx.getSharedPreferences("prefs",Context.MODE_PRIVATE).getString("steam32","NA");
        TotalsTable totalData = new TotalsTable();
        totalData.setPlayerID(playerID);
        try {
            field = jsonArray.getJSONObject(0);
            totalData.setTotalKills(field.getInt("sum"));
            totalData.setTotalMatches(field.getInt("n"));
            field = jsonArray.getJSONObject(1);
            totalData.setTotalDeaths(field.getInt("sum"));
            field = jsonArray.getJSONObject(2);
            totalData.setTotalAssists(field.getInt("sum"));
            field = jsonArray.getJSONObject(3);
            totalData.setTotalKDA(field.getInt("sum"));
            field = jsonArray.getJSONObject(4);
            totalData.setTotalgpm(field.getInt("sum"));
            field = jsonArray.getJSONObject(5);
            totalData.setTotalxpm(field.getInt("sum"));
            field = jsonArray.getJSONObject(6);
            totalData.setTotallh(field.getInt("sum"));
            field = jsonArray.getJSONObject(7);
            totalData.setTotaldenies(field.getInt("sum"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            AppDatabase.getAppDatabase(ctx).getTotalsTableDao().insert(totalData);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void getHeroStatsToDB(Context ctx, JSONArray jsonArray){
        if(jsonArray.length()!=0){
            HeroStatsTable heroStat;
            int lastPlayed;
            JSONObject statObject;
            for(int i =0;i<jsonArray.length();i++){
                try {
                    statObject = jsonArray.getJSONObject(i);
                    heroStat = new HeroStatsTable();
                    heroStat.setHeroID(statObject.getInt("hero_id"));
                    lastPlayed = statObject.getInt("last_played");
                    if(lastPlayed == 0){
                        break;
                    }
                    heroStat.setMatchID(lastPlayed);
                    heroStat.setGamesPLayed(statObject.getInt("games"));
                    heroStat.setWon(statObject.getInt("win"));
                    heroStat.setWithPlayed(statObject.getInt("with_games"));
                    heroStat.setWithWon(statObject.getInt("with_win"));
                    heroStat.setAgainstPlayed(statObject.getInt("against_games"));
                    heroStat.setAgainstWon(statObject.getInt("against_win"));
                    try {
                        AppDatabase.getAppDatabase(ctx).getHeroStatsDao().insertStats(heroStat);
                    }catch (Exception e){
                        break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
