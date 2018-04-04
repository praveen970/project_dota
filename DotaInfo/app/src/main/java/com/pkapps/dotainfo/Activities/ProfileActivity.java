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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.pkapps.dotainfo.Adapters.HeroStatList;
import com.pkapps.dotainfo.CacheDB.AppDatabase;
import com.pkapps.dotainfo.CacheDB.HeroStatsTable;
import com.pkapps.dotainfo.CacheDB.TotalsTable;
import com.pkapps.dotainfo.Constants.DotaMisc;
import com.pkapps.dotainfo.R;
import com.pkapps.dotainfo.Tools.Util;

import org.w3c.dom.Text;

import java.awt.font.TextAttribute;
import java.util.List;

public class ProfileActivity extends Activity{
    TextView mmr,kdr,winRate,personaname;
    CircularImageView imgVw;
    RecyclerView recyclerView;
    ImageView medal;
    SharedPreferences prefs;
    ImageButton back;
    HeroStatList mAdapter;
    ProfileActivity ctx;
    List<HeroStatsTable> items;
    int totalMatches;
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
        medal = (ImageView) findViewById(R.id.medal);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(ctx);
            }
        });
        initValues();
        initAvgMaxCard();

    }
    public void initValues(){
        mmr.setText(prefs.getString("mmr","Unknown"));
        kdr.setText(Float.toString(prefs.getFloat("kdr",10.0f)));
        winRate.setText(Integer.toString(prefs.getInt("winrate",99))+"%");
        int rank = prefs.getInt("rank_tier",0);
        medal.setImageResource(Util.getMedal(ctx,rank));
        personaname.setText(prefs.getString("personaname","Player"));
        Bitmap bmp = Util.loadImageFromStorage(ctx);
        if(bmp!=null) {
            imgVw.setImageBitmap(bmp);
        }
    }
    public void initAvgMaxCard(){
        TotalsTable tableData = new TotalsTable();
        tableData = AppDatabase.getAppDatabase(this).getTotalsTableDao().getData();

        if((totalMatches = tableData.getTotalMatches())!=0) {
            ((TextView) findViewById(R.id.totalMatches)).setText("(Matches:"+Integer.toString(totalMatches)+")");
            ((TextView) findViewById(R.id.limitMaches)).setText("(Matches:20)");
            ((TextView) findViewById(R.id.gpm)).setText(Integer.toString(tableData.getTotalgpm() / totalMatches));
            ((TextView) findViewById(R.id.xpm)).setText(Integer.toString(tableData.getTotalxpm() / totalMatches));
            ((TextView) findViewById(R.id.lashits)).setText(Integer.toString(tableData.getTotallh() / totalMatches));
            ((TextView) findViewById(R.id.kills)).setText(Integer.toString(tableData.getTotalKills() / totalMatches)+"/"+
            AppDatabase.getAppDatabase(this).getAllMatchesDao().getMaxKill());
            ((TextView) findViewById(R.id.deaths)).setText(Integer.toString(tableData.getTotalDeaths() / totalMatches)+"/"+
                    AppDatabase.getAppDatabase(this).getAllMatchesDao().getMaxDeath());
            ((TextView) findViewById(R.id.assists)).setText(Integer.toString(tableData.getTotalAssists() / totalMatches)+"/"+
                    AppDatabase.getAppDatabase(this).getAllMatchesDao().getMaxAssist());
            initHeroStatsCard();
        }
    }
    public void initHeroStatsCard(){
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        //recyclerView.setHasFixedSize(true);
//        items = AppDatabase.getAppDatabase(ctx).getHeroStatsDao().getLimitedStats(5);
//        mAdapter = new HeroStatList(this,items);
//        recyclerView.setAdapter(mAdapter);
        float percent;
        ((TextView)findViewById(R.id.totalPlays)).setText("(Matches:"+totalMatches+")");
        LinearLayout lg = (LinearLayout) findViewById(R.id.green);
        LinearLayout lr = (LinearLayout) findViewById(R.id.red);
        LinearLayout lg2 = (LinearLayout) findViewById(R.id.green2);
        LinearLayout lr2 = (LinearLayout) findViewById(R.id.red2);
        LinearLayout lg3 = (LinearLayout) findViewById(R.id.green3);
        LinearLayout lr3 = (LinearLayout) findViewById(R.id.red3);
        items = AppDatabase.getAppDatabase(this).getHeroStatsDao().getLimitedStats(3);
        HeroStatsTable p = items.get(0);
        ((TextView)findViewById(R.id.won1)).setText("Won:"+p.getWon());
        ((TextView)findViewById(R.id.lost1)).setText("Lost:"+(p.getGamesPLayed()-p.getWon()));
        percent = p.getWon()/(float)p.getGamesPLayed();
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.MATCH_PARENT, percent);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.MATCH_PARENT, 1f-percent);
        lg.setLayoutParams(layoutParams1);
        lr.setLayoutParams(layoutParams2);
        ((TextView) findViewById(R.id.name)).setText(DotaMisc.getHeroName(p.getHeroID()));
        ((ImageView) findViewById(R.id.image)).setImageResource(ctx.getResources().obtainTypedArray(R.array.hero_images).getResourceId(p.getHeroID()-1,114));
        p = items.get(1);
        ((TextView)findViewById(R.id.won2)).setText("Won:"+p.getWon());
        ((TextView)findViewById(R.id.lost2)).setText("Lost:"+(p.getGamesPLayed()-p.getWon()));
        percent = p.getWon()/(float)p.getGamesPLayed();
        layoutParams1 = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.MATCH_PARENT, percent);
        layoutParams2 = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.MATCH_PARENT, 1f-percent);
        lg2.setLayoutParams(layoutParams1);
        lr2.setLayoutParams(layoutParams2);
        ((TextView) findViewById(R.id.name2)).setText(DotaMisc.getHeroName(p.getHeroID()));
        ((ImageView) findViewById(R.id.image2)).setImageResource(ctx.getResources().obtainTypedArray(R.array.hero_images).getResourceId(p.getHeroID()-1,114));
        p = items.get(2);
        ((TextView)findViewById(R.id.won3)).setText("Won:"+p.getWon());
        ((TextView)findViewById(R.id.lost3)).setText("Lost:"+(p.getGamesPLayed()-p.getWon()));
        percent = p.getWon()/(float)p.getGamesPLayed();
        layoutParams1 = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.MATCH_PARENT, percent);
        layoutParams2 = new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.MATCH_PARENT, 1f-percent);
        lg3.setLayoutParams(layoutParams1);
        lr3.setLayoutParams(layoutParams2);
        ((TextView) findViewById(R.id.name3)).setText(DotaMisc.getHeroName(p.getHeroID()));
        ((ImageView) findViewById(R.id.image3)).setImageResource(ctx.getResources().obtainTypedArray(R.array.hero_images).getResourceId(p.getHeroID()-1,114));
    }

}
