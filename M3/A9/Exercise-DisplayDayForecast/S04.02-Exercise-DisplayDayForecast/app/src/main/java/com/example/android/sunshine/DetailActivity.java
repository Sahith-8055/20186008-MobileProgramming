package com.example.android.sunshine;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView mWeatherDisplay;
    private String mForecast;

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // TODO (2) Display the weather forecast that was passed from MainActivity
        mWeatherDisplay = (TextView) findViewById(R.id.tv_display_weather);

        Intent intent1 = getIntent();
        //ActivityInfo activityInfo = intent1.resolveActivityInfo(getPackageManager(), intent1.getFlags());
        if (intent1.getExtras() != null) {
            mForecast = intent1.getStringExtra(Intent.EXTRA_TEXT);
            mWeatherDisplay.setText(mForecast);
        }
    }
}