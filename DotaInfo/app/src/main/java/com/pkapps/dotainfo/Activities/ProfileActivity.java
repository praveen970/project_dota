package com.pkapps.dotainfo.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.pkapps.dotainfo.R;
import com.pkapps.dotainfo.Tools.Util;

import java.awt.font.TextAttribute;

public class ProfileActivity extends Activity{
    TextView mmr,kdr,winRate,personaname;
    CircularImageView imgVw;
    SharedPreferences prefs;
    ImageButton back;
    ProfileActivity ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ctx = this;
        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        mmr = (TextView) findViewById(R.id.mmr);
        kdr = (TextView) findViewById(R.id.kdr);
        winRate = (TextView) findViewById(R.id.winRate);
        personaname = (TextView) findViewById(R.id.personaname);
        imgVw = (CircularImageView) findViewById(R.id.avatar);
        back = (ImageButton) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(ctx);
            }
        });
        initValues();
    }
    public void initValues(){
        mmr.setText(prefs.getString("mmr","9999"));
        kdr.setText(Float.toString(prefs.getFloat("kdr",10.0f)));
        winRate.setText(Integer.toString(prefs.getInt("winrate",99))+"%");
        personaname.setText(prefs.getString("personaname","Player"));
        Bitmap bmp = Util.getBitmap((ProfileActivity)this);
        if(bmp!=null) {
            imgVw.setImageBitmap(bmp);
        }
    }


}
