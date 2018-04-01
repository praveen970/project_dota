package com.pkapps.dotainfo.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pkapps.dotainfo.AsyncCalls.AllMatches;
import com.pkapps.dotainfo.AsyncCalls.PlayerProfile;
import com.pkapps.dotainfo.AsyncCalls.SteamID;
import com.pkapps.dotainfo.CacheDB.AppDatabase;
import com.pkapps.dotainfo.R;

public class VanityLogin extends Activity {
    Button search,clear,steamSearch;
    EditText vanityNameBox;
    String vanityName;
    Button startMain;
    public ProgressDialog pd;
    SharedPreferences pref;
    Context ctx;
    boolean loginFromSteam = false;
    public boolean process1=false,process2=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vanity_login);
        pref = getSharedPreferences("prefs",Context.MODE_PRIVATE);
        pd = new ProgressDialog(this);
        clear = (Button) findViewById(R.id.clear);
        search = (Button)findViewById(R.id.vanitySearch);
        steamSearch = (Button) findViewById(R.id.steamSearch);
        vanityNameBox = (EditText) findViewById(R.id.vanityName);
        ctx = this;
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vanityNameBox.setText("");
                clear.setVisibility(View.INVISIBLE);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setEnabled(false);
                vanityName = vanityNameBox.getText().toString();
                try {
                    SharedPreferences pref = ctx.getSharedPreferences("prefs",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("steam64",vanityName);
                    String id = Long.toString(Long.parseLong(vanityName.substring(3))- 61197960265728L);
                    pd.setMessage("Progessing With ID");
                    pd.show();
                    editor.putString("steam32",id);
                    editor.commit();
                    new PlayerProfile(ctx,id).execute();
                    new AllMatches(ctx,id).execute();
                }catch(NumberFormatException e){
                    pd.setMessage("Progessing with Name");
                    pd.show();
                    new SteamID(ctx,"5A8CEA48F10A17DA97EA4332029B392F",vanityName).execute();
                }

            }
        });
        steamSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                steamSearch.setEnabled(false);
                loginFromSteam = true;
                startActivity(new Intent(ctx,SteamWebLoginActivity.class));
            }
        });
//        startMain = (Button) findViewById(R.id.button);
//        startMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pd.setMessage("Progessing");
//                pd.show();
//                //AppDatabase.getAppDatabase(ctx).getAllMatchesDao().deleteAllMatches();
//                new PlayerProfile(ctx,id32).execute();
//                new AllMatches(ctx,id32).execute();
//            }
//        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        String id32 = pref.getString("steam32",null);
        if(id32!=null){
            if(loginFromSteam == true){
                pd.setMessage("Progessing From Steam");
                pd.show();
                new PlayerProfile(ctx,id32).execute();
                new AllMatches(this,id32).execute();
            }else{
                ctx.startActivity(new Intent(ctx, MainActivity.class));
            }

        }
    }
}
