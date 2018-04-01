package com.pkapps.dotainfo.AsyncCalls;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PraveenKumar on 3/30/2018.
 */

public class SteamID extends AsyncTask<Void,Void,Void> {
    String key;
    String vanityName;
    Context ctx;
    String id64,id32;

    public SteamID(Context ctx,String key, String vanityName) {
        this.ctx = ctx;
        this.key = key;
        this.vanityName = vanityName;
    }

    @Override
    protected Void doInBackground(Void...voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.steampowered.com/ISteamUser/ResolveVanityURL/v0001/?key="+key+"&vanityurl="+vanityName).build();
        try {
            Response response = client.newCall(request).execute();
            String dataSource = response.body().string();
            JSONObject j = new JSONObject(dataSource);
            JSONObject req = j.getJSONObject("response");
            SharedPreferences pref = ctx.getSharedPreferences("prefs",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            id64 = req.getString("steamid");
            id32 = Long.toString(Long.parseLong(id64.substring(3))- 61197960265728L);
            editor.putString("steam64",id64);
            editor.putString("steam32",id32);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        new PlayerProfile(ctx,id32).execute();
        new AllMatches(ctx,id32).execute();
    }
}
