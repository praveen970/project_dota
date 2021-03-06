package com.pkapps.dotainfo.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pkapps.dotainfo.AsyncCalls.AllMatches;
import com.pkapps.dotainfo.AsyncCalls.HeroStats;
import com.pkapps.dotainfo.AsyncCalls.PlayerProfile;
import com.pkapps.dotainfo.AsyncCalls.SteamID;
import com.pkapps.dotainfo.AsyncCalls.Totals;
import com.pkapps.dotainfo.CacheDB.AppDatabase;
import com.pkapps.dotainfo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
                    setCalls(ctx,id);
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
    }

    public void displayDialog(){
        steamSearch.setEnabled(true);
        search.setEnabled(true);
        new AlertDialog.Builder(this)
                .setTitle("Wrong input")
                .setMessage("Input ID or Name may be wrong OR account might be private")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        search.setEnabled(true);
        steamSearch.setEnabled(true);
        String id32 = pref.getString("steam32",null);
        if(id32!=null){
            if(loginFromSteam == true){
                pd.setMessage("Progessing From Steam");
                pd.show();
                setCalls(ctx,id32);
            }else{
                ctx.startActivity(new Intent(ctx, MainActivity.class));
            }

        }
    }
    public void setCalls(Context ctx,String id32){
        new PlayerProfile(ctx,id32).execute();
        new AllMatches(ctx,id32).execute();
        new Totals(ctx,id32).execute();
        new HeroStats(ctx,id32).execute();
    }
}
