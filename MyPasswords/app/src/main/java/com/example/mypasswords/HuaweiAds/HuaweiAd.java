package com.example.mypasswords.HuaweiAds;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.InterstitialAd;

public class HuaweiAd {
    private String TAG= HuaweiAd.class.getSimpleName();
    private InterstitialAd interstitialAd ;
    private Context context;


    public HuaweiAd(Context context){
        this.context=context;
    }
    /**
     * Ads Methods
     */



    private AdListener adListener = new AdListener() {
        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
//            Toast.makeText(context, "Ad loaded", Toast.LENGTH_SHORT).show();
            // Display an interstitial ad.
            showInterstitial();
        }

        @Override
        public void onAdFailed(int errorCode) {
            Toast.makeText(context, "Ad load failed with error code: " + errorCode,
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Ad load failed with error code: " + errorCode);
        }

        @Override
        public void onAdClosed() {
            super.onAdClosed();
            Toast.makeText(context, "Ad closed", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onAdClosed");
        }

        @Override
        public void onAdClicked() {
            Log.d(TAG, "onAdClicked");
            super.onAdClicked();
        }

        @Override
        public void onAdOpened() {
            Log.d(TAG, "onAdOpened");
            super.onAdOpened();
        }
    };

    public void loadInterstitialAd(String typeOfAd) {
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdId(getAdId(typeOfAd));
        interstitialAd.setAdListener(adListener);

        AdParam adParam = new AdParam.Builder().build();
        interstitialAd.loadAd(adParam);
    }

    private String getAdId(String typeOfAd) {
        if (typeOfAd.equalsIgnoreCase("image")) {
            return "teste9ih9j0rc3"; // Image
        } else {
            return "testb4znbuh3n2"; // Video
        }
    }

    private void showInterstitial() {
        // Display an interstitial ad.
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Toast.makeText(context, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }



}
