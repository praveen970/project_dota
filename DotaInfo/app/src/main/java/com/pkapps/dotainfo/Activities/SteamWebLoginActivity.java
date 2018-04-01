package com.pkapps.dotainfo.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pkapps.dotainfo.R;
import com.pkapps.dotainfo.Tools.Util;

public class SteamWebLoginActivity extends Activity {

    WebView browser;
    StringBuilder currentUrl;
    String id64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steam_web_login);
        browser = (WebView) findViewById(R.id.browser);
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.setWebViewClient(new MyBrowser());
        browser.loadUrl("https://firstsuite.000webhostapp.com/login.php");
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String request){
            if((id64 = Util.checkIDInString(request))!=null){
                String steam32 = Long.toString(Long.parseLong(id64.substring(3))- 61197960265728L);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("steam32",steam32);
                edit.commit();
                finish();
            }else{
                view.loadUrl(request);
            }

            return true;
        }
    }
}
