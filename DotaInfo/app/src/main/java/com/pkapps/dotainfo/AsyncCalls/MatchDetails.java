package com.pkapps.dotainfo.AsyncCalls;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.pkapps.dotainfo.Activities.MatchAnalysisActivity;
import com.pkapps.dotainfo.JsonParsers.MatchAnalysisParser;

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
    Context ctx;

    public MatchDetails(Context ctx,String matchID) {
        this.ctx = ctx;
        this.matchID = matchID;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request requestForMatches = new Request.Builder().url("https://api.opendota.com/api/matches/" + matchID).build();
        while(true) {
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
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        Log.d("debug",jsonObject.toString());
        MatchAnalysisParser.OverviewParser(ctx,jsonObject);
        Intent intent = new Intent(ctx, MatchAnalysisActivity.class);
        intent.putExtra("matchId",matchID);
        ctx.startActivity(intent);
    }
}
