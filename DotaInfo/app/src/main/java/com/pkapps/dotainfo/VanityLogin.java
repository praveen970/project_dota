package com.pkapps.dotainfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pkapps.dotainfo.AsyncCalls.AllMatches;
import com.pkapps.dotainfo.CacheDB.AppDatabase;

public class VanityLogin extends Activity {
    Button startMain;
    public ProgressDialog pd;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vanity_login);
        pd = new ProgressDialog(this);
        ctx = this;
        startMain = (Button) findViewById(R.id.button);
        startMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Progessing");
                pd.show();
                //AppDatabase.getAppDatabase(ctx).getAllMatchesDao().deleteAllMatches();
                new AllMatches(ctx,"109548492").execute();
            }
        });
    }


}
