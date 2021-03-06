package com.pkapps.dotainfo.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;


import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pkapps.dotainfo.Adapters.MainList;
import com.pkapps.dotainfo.AsyncCalls.AllMatches;
import com.pkapps.dotainfo.AsyncCalls.HeroStats;
import com.pkapps.dotainfo.AsyncCalls.MatchDetails;
import com.pkapps.dotainfo.AsyncCalls.PlayerProfile;
import com.pkapps.dotainfo.AsyncCalls.Totals;
import com.pkapps.dotainfo.CacheDB.AllMatchesTable;
import com.pkapps.dotainfo.CacheDB.AppDatabase;
import com.pkapps.dotainfo.CacheDB.OverviewTable;
import com.pkapps.dotainfo.R;
import com.pkapps.dotainfo.Tools.Util;

import java.util.List;

public class MainActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener,SwipeRefreshLayout.OnRefreshListener
{

    private View parent_view;
    private Context ctx;
    private RecyclerView recyclerView;
    public SwipeRefreshLayout refresher;
    private MainList mAdapter;
    private ImageView imgVw;
    private TextView personaname;
    public ProgressDialog p;
    private Toolbar toolbar;
    List<AllMatchesTable> items;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean isMatchClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent_view = findViewById(android.R.id.content);
        ctx = this;
        pref = getSharedPreferences("prefs",Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("login","true");
        editor.commit();
        isMatchClicked = false;
        setNavigationHeader();
        initToolbar();
        initComponent();

    }
    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("DotaInfo");
    }
    private void initComponent() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        refresher = (SwipeRefreshLayout) findViewById(R.id.refresherView);
        refresher.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        setMatchesAdapter();

    }
    public void setMatchesAdapter(){


        items = AppDatabase.getAppDatabase(ctx).getAllMatchesDao().getLimitedMatches(50);
        //set data and list adapter
        mAdapter = new MainList(ctx, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new MainList.OnItemClickListener() {
            @Override
            public void onItemClick(View view, AllMatchesTable obj, int position) {
                //Snackbar.make(parent_view, "Item " + obj.getMatchID() + " clicked", Snackbar.LENGTH_SHORT).show();
                if(isMatchClicked!=true) {
                    isMatchClicked = true;
                    editor.putLong("matchId", obj.getMatchID());
                    editor.commit();
                    OverviewTable matchData = AppDatabase.getAppDatabase(ctx).getOverviewDao().getMatchOverviewData(obj.getMatchID());
                    if(matchData==null) {
                        new MatchDetails(ctx, obj.getMatchID()).execute();
                    }else{
                        Intent intent = new Intent(ctx, MatchAnalysisActivity.class);
                        intent.putExtra("matchId",obj.getMatchID());
                        ctx.startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(ctx,ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {

            SharedPreferences pref = ctx.getSharedPreferences("prefs",0);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            AppDatabase.getAppDatabase(ctx).getAllMatchesDao().deleteAllMatches();
            AppDatabase.getAppDatabase(ctx).getTotalsTableDao().deleteTotals();
            AppDatabase.getAppDatabase(ctx).getHeroStatsDao().deleteStats();
            AppDatabase.getAppDatabase(ctx).getOverviewDao().deleteOverviewTable();
            finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationHeader(){
        NavigationView nView = (NavigationView) findViewById(R.id.nav_view);
        View header = nView.getHeaderView(0);
        SharedPreferences pref = getSharedPreferences("prefs",Context.MODE_PRIVATE);
        imgVw = (ImageView)header.findViewById(R.id.imageView);
        personaname = (TextView) header.findViewById(R.id.personaname);
        Bitmap bmp = Util.loadImageFromStorage(ctx);
        if(bmp!=null) {
            imgVw.setImageBitmap(bmp);
        }
        personaname.setText(pref.getString("personaname","Player"));
    }

    @Override
    public void onRefresh() {
        String id32 = pref.getString("steam32",null);
        new PlayerProfile(ctx,id32).execute();
        new AllMatches(this,id32).execute();
        new Totals(this,id32).execute();
        new HeroStats(this,id32).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isMatchClicked = false;
    }
}