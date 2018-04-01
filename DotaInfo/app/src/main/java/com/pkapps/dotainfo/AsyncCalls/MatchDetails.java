package com.pkapps.dotainfo.AsyncCalls;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PraveenKumar on 3/29/2018.
 */

public class MatchDetails extends AsyncTask<Void, Void, JSONObject> {
    String matchID;

    public MatchDetails(String matchID) {
        this.matchID = matchID;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request requestForMatches = new Request.Builder().url("https://api.opendota.com/api/matches/" + matchID).build();
        try {
            Response response = client.newCall(requestForMatches).execute();
            String matchDataSource = response.body().string();
            return new JSONObject(matchDataSource);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}
