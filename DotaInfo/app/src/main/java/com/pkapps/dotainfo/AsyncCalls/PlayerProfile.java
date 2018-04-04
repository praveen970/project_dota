package com.pkapps.dotainfo.AsyncCalls;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.pkapps.dotainfo.Activities.VanityLogin;
import com.pkapps.dotainfo.JsonParsers.Parser;
import com.pkapps.dotainfo.Tools.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PraveenKumar on 3/29/2018.
 */

public class PlayerProfile extends AsyncTask<Void, Void, JSONObject> {
    String steam32;
    Context ctx;
    Bitmap bitmap;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public PlayerProfile(Context ctx,String steam32) {
        this.ctx =   ctx;
        this.steam32 = steam32;
        prefs = ctx.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        while(true){
            Request request = new Request.Builder().url("https://api.opendota.com/api/players/"+steam32).build();
            Request request2 = new Request.Builder().url("https://api.opendota.com/api/players/"+steam32+"/wl").build();
            Request request3 = new Request.Builder().url("https://api.opendota.com/api/players/"+steam32+"/totals").build();
            try {
                Response response = client.newCall(request).execute();
                Response response2 = client.newCall(request2).execute();
                Response response3 = client.newCall(request3).execute();
                if(response != null && response2 != null){
                    String dataSource = response.body().string();
                    String dataSource2 = response2.body().string();
                    String dataSource3 = response3.body().string();
                    JSONObject j1 = new JSONObject(dataSource);
                    JSONObject j2 = new JSONObject(dataSource2);
                    JSONArray j3 = new JSONArray(dataSource3);
                    try {
                        JSONObject profileObject = j1.getJSONObject("profile");
                        editor.putInt("winrate",Util.getWinRate(j2.getInt("win"),j2.getInt("lose")));
                        JSONObject jb1,jb2;
                        jb1 = j3.getJSONObject(0);
                        jb2 = j3.getJSONObject(1);
                        editor.putFloat("kdr",Util.getKDR(jb1.getInt("sum"),jb2.getInt("sum")));
                        editor.commit();
                        bitmap = BitmapFactory.decodeStream((InputStream)new URL(profileObject.getString("avatarfull")).getContent());
                        //SaveImage save = new SaveImage(ctx,bitmap);
                        //save.saveBitmapToCard();
                        //Util.saveImage(ctx,bitmap);
                        Util.saveBitmapToInternalMemory(ctx,bitmap);
                    } catch (IOException e) {

                    }
                    return j1;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }catch (NullPointerException e){

            }

        }


    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        Parser.setProfile(ctx,jsonObject);
        if(ctx.getClass().getSimpleName().equals("VanityLogin")) {
            VanityLogin a = (VanityLogin) ctx;
            a.process2 = true;
        }
    }
}
