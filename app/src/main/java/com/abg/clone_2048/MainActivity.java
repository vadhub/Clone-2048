package com.abg.clone_2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.yandex.mobile.ads.banner.BannerAdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;

public class MainActivity extends AppCompatActivity {

    private BannerAdView mBanner;
    private MainView view;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = new MainView(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        mBanner = (BannerAdView) findViewById(R.id.adView);
        // demo-banner-yandex
        mBanner.setAdUnitId("R-M-2873095-1");
        mBanner.setAdSize(getAdSize());
        AdRequest adRequest = new AdRequest.Builder().build();
        mBanner.loadAd(adRequest);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new GameFragment(), "gameFragment").commit();
    }

    private BannerAdSize getAdSize() {
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        // Calculate the width of the ad, taking into account the padding in the ad container.
        int adWidthPixels = mBanner.getWidth();
        if (adWidthPixels == 0) {
            // If the ad hasn't been laid out, default to the full screen width
            adWidthPixels = displayMetrics.widthPixels;
        }
        final int adWidth = Math.round(adWidthPixels / displayMetrics.density);

        return BannerAdSize.stickySize(this, adWidth);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_MENU)
            return true;
        else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
        {
            view.game.move(2);
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
        {
            view.game.move(0);
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
        {
            view.game.move(3);
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
        {
            view.game.move(1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public MainView getMainView() {
        return view;
    }
}