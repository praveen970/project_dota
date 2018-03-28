package com.pkapps.dotainfo.AsyncCalls;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.pkapps.dotainfo.JsonParsers.Parser;
import com.pkapps.dotainfo.MainActivity;
import com.pkapps.dotainfo.VanityLogin;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PraveenKumar on 3/27/2018.
 */

public class AllMatches extends AsyncTask<Void, Void, JSONArray> {

    String steam32;
    VanityLogin ctx;
    public AllMatches(Context ctx, String steam32) {
        this.steam32 = steam32;
        this.ctx = (VanityLogin) ctx;
    }

    @Override
    protected JSONArray doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request requestForMatches = new Request.Builder().url("https://api.opendota.com/api/players/"+steam32+"/matches").build();
        try {
            Response response = client.newCall(requestForMatches).execute();
            String matchDataSource = response.body().string();
            return new JSONArray(matchDataSource);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        Parser.getAllMatches(ctx,jsonArray);
        ctx.pd.dismiss();
        ctx.startActivity(new Intent(ctx, MainActivity.class));
    }
}
