package com.example.slider;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotsLayout;
    private TextView[] mDots;
    private SliderAdapter sliderAdapter;
    private Button mNxtButton;
    private Button mPrvButton;
    private int mCurrentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotsLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        mNxtButton = (Button) findViewById(R.id.next);
        mPrvButton = (Button) findViewById(R.id.prev);
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNxtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNxtButton.getText().equals("Finish")) {
                    Intent login = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(login);
                    finish();
                }
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);

            }
        });

        mPrvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });
    }


    public void addDotsIndicator(int position) {
        mDots = new TextView[3];
        mDotsLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            mDotsLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;
            if (i == 0) {
                mNxtButton.setEnabled(true);
                mPrvButton.setEnabled(false);
                mPrvButton.setVisibility(View.INVISIBLE);
                mNxtButton.setText("Next");
                mPrvButton.setText("");
            } else if (i == mDots.length - 1) {
                mNxtButton.setEnabled(true);
                mPrvButton.setEnabled(true);
                mPrvButton.setVisibility(View.VISIBLE);
                mNxtButton.setText("Finish");
                mPrvButton.setText("Back");
            } else {
                mNxtButton.setEnabled(true);
                mPrvButton.setEnabled(true);
                mPrvButton.setVisibility(View.VISIBLE);
                mNxtButton.setText("Next");
                mPrvButton.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
