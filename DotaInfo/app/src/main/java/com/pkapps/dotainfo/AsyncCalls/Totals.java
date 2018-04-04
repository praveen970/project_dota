package com.pkapps.dotainfo.AsyncCalls;

import android.content.Context;
import android.os.AsyncTask;

import com.pkapps.dotainfo.JsonParsers.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PraveenKumar on 4/3/2018.
 */

public class Totals extends AsyncTask<Void,Void,JSONArray> {
    String steam32;
    Context ctx;

    public Totals(Context ctx,String steam32) {
        this.ctx = ctx;
        this.steam32 = steam32;
    }

    @Override
    protected JSONArray doInBackground(Void...voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.opendota.com/api/players/"+steam32+"/totals").build();
        try {
            Response response = client.newCall(request).execute();
            String dataSource = response.body().string();
            return new JSONArray(dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray o) {
        super.onPostExecute(o);
        Parser.getTotalsToDB(ctx,o);

    }
}
