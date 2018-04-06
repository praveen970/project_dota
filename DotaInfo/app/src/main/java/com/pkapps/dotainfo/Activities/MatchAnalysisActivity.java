package com.pkapps.dotainfo.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pkapps.dotainfo.CacheDB.AllMatchesTable;
import com.pkapps.dotainfo.CacheDB.AppDatabase;
import com.pkapps.dotainfo.CacheDB.OverviewTable;
import com.pkapps.dotainfo.Constants.DotaMisc;
import com.pkapps.dotainfo.Fragments.OverviewFragment;
import com.pkapps.dotainfo.R;
import com.pkapps.dotainfo.Tools.Util;

import java.util.ArrayList;
import java.util.List;

public class MatchAnalysisActivity extends FragmentActivity {

    private ImageButton backButton;
    private TextView matchNumber,radiantScore,direScore,gameMode,lobby,duration,radiant,dire,startTime;
    private ViewPager view_pager;
    private TabLayout tab_layout;
    Long matchId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_analysis);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        matchId = b.getLong("matchId");
        initComponent();
    }

    private void initComponent() {
        matchNumber = (TextView)findViewById(R.id.matchID);
        backButton = (ImageButton) findViewById(R.id.backButton);
        matchNumber.setText(Long.toString(matchId));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radiantScore = (TextView) findViewById(R.id.radiantScore);
        direScore = (TextView) findViewById(R.id.direScore);
        gameMode = (TextView) findViewById(R.id.gamemode);
        radiant = (TextView)findViewById(R.id.radiant);
        dire = (TextView)findViewById(R.id.dire);
        startTime = (TextView) findViewById(R.id.start_time);
        lobby = (TextView) findViewById(R.id.lobby);
        duration = (TextView) findViewById(R.id.duration);
        OverviewTable data = AppDatabase.getAppDatabase(this).getOverviewDao().getMatchOverviewData(matchId);
        if(data.isRadiantWin()){
            radiant.setText("RADIANT VICTORY");
            dire.setVisibility(View.INVISIBLE);
        }else{
            dire.setText("DIRE VICTORY");
            radiant.setVisibility(View.INVISIBLE);
        }
        startTime.setText(Util.getDateCurrentTimeZone(data.getStartTime()));
        radiantScore.setText(data.getRadiant_score());
        direScore.setText(data.getDire_score());
        AllMatchesTable matchData = AppDatabase.getAppDatabase(this).getAllMatchesDao().getMatch(matchId);
        lobby.setText(DotaMisc.getLobby(matchData.getLobbyType()));
        gameMode.setText(DotaMisc.getGameMode(matchData.getGameMode()));
        duration.setText(Util.getDuration(matchData.getDuration()));
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(view_pager);

        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);
    }
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new OverviewFragment(), "OVERVIEW");
        adapter.addFragment(new OverviewFragment(), "PERFORMANCE");
        adapter.addFragment(new OverviewFragment(), "ITEM BUILDS");
        adapter.addFragment(new OverviewFragment(), "ABILITY BUILDS");
        adapter.addFragment(new OverviewFragment(), "STATS");
        viewPager.setAdapter(adapter);
    }
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
