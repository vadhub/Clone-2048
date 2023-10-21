package com.abg.clone_2048;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.yandex.mobile.ads.appopenad.AppOpenAdLoader;
import com.yandex.mobile.ads.banner.BannerAdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdError;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestConfiguration;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener;
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader;

public class MainActivity extends AppCompatActivity implements Navigator {

    private MainView view;

    @Nullable
    private InterstitialAd mInterstitialAd = null;
    @Nullable
    private InterstitialAdLoader mInterstitialAdLoader = null;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        mInterstitialAdLoader = new InterstitialAdLoader(this);
        mInterstitialAdLoader.setAdLoadListener(new InterstitialAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull final InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                // The ad was loaded successfully. Now you can show loaded ad.
            }

            @Override
            public void onAdFailedToLoad(@NonNull final AdRequestError adRequestError) {
                // Ad failed to load with AdRequestError.
                // Attempting to load a new ad from the onAdFailedToLoad() method is strongly discouraged.
            }
        });
        loadInterstitialAd();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MenuFragment(), "menuFragment").commit();
    }

    private void loadInterstitialAd() {
        //demo-interstitial-yandex
        if (mInterstitialAdLoader != null ) {
            final AdRequestConfiguration adRequestConfiguration =
                    new AdRequestConfiguration.Builder("R-M-2873095-4").build();
            mInterstitialAdLoader.loadAd(adRequestConfiguration);
        }
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

    @Override
    public void startFragment(Fragment fragment, int rows) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.fragment_container, fragment)
                    .commit();

            if (fragment instanceof GameFragment) {
                showAd();
                view = new MainView(this, rows);
            }
        }
    }

    private void showAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.setAdEventListener(new InterstitialAdEventListener() {
                @Override
                public void onAdShown() {
                    // Called when ad is shown.
                }

                @Override
                public void onAdFailedToShow(@NonNull final AdError adError) {
                    Log.e("err", adError.getDescription());
                }

                @Override
                public void onAdDismissed() {
                    // Called when ad is dismissed.
                    // Clean resources after Ad dismissed
                    if (mInterstitialAd != null) {
                        mInterstitialAd.setAdEventListener(null);
                        mInterstitialAd = null;
                    }

                    // Now you can preload the next interstitial ad.
                    loadInterstitialAd();
                }

                @Override
                public void onAdClicked() {
                    // Called when a click is recorded for an ad.
                }

                @Override
                public void onAdImpression(@Nullable final ImpressionData impressionData) {
                    // Called when an impression is recorded for an ad.
                }
            });
            mInterstitialAd.show(this);
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mInterstitialAdLoader != null) {
//            mInterstitialAdLoader.setAdLoadListener(null);
//            mInterstitialAdLoader = null;
//        }
//        destroyInterstitialAd();
//    }
//
//    private void destroyInterstitialAd() {
//        if (mInterstitialAd != null) {
//            mInterstitialAd.setAdEventListener(null);
//            mInterstitialAd = null;
//        }
//    }
}