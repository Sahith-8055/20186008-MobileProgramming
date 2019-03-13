/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.implicitintents;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the Open Website button is clicked. It will open the website
     * specified by the URL represented by the variable urlAsString using implicit Intents.
     *
     * @param v Button that was clicked.
     */
    public void onClickOpenWebpageButton(View v) {
        // TODO (5) Create a String that contains a URL ( make sure it starts with http:// or https:// )
        String url = "https://www.udacity.com";
        // TODO (6) Replace the Toast with a call to openWebPage, passing in the URL String from the previous step
        openWebPage(url);
    }

    /**
     * This method is called when the Open Location in Map button is clicked. It will open the
     * a map to the location represented by the variable addressString using implicit Intents.
     *
     * @param v Button that was clicked.
     */
    public void onClickOpenAddressButton(View v) {
        String location = "https://www.google.co.in/maps/place/17%C2%B028'03.8%22N+78%C2%B028'48.7%22E/@17.4677323,78.4796578,19z/data=!3m1!4b1!4m13!1m6!3m5!1s0x3bcb9a7959873c57:0x4312a67627996ded!2sOm+Residency!8m2!3d17.4675978!4d78.4802834!3m5!1s0x0:0x0!7e2!8m2!3d17.4677314!4d78.4802049";
        openWebPage(location);
    }

    /**
     * This method is called when the Share Text Content button is clicked. It will simply share
     * the text contained within the String textThatYouWantToShare.
     *
     * @param v Button that was clicked.
     */
    public void onClickShareTextButton(View v) {
        Toast.makeText(this, "TODO: Share text when this is clicked", Toast.LENGTH_LONG).show();
    }

    /**
     * This is where you will create and fire off your own implicit Intent. Yours will be very
     * similar to what I've done above. You can view a list of implicit Intents on the Common
     * Intents page from the developer documentation.
     *
     * @see <http://developer.android.com/guide/components/intents-common.html/>
     *
     * @param v Button that was clicked.
     */
    public void createYourOwn(View v) {
        String teaser = "https://www.youtube.com/watch?v=yyYEX_Bk_L4&t=2s";
        openWebPage(teaser);
    }

    // TODO (1) Create a method called openWebPage that accepts a String as a parameter
    // Do steps 2 - 4 within openWebPage
    public void openWebPage(String string) {
        Uri uri = Uri.parse(string);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

        ActivityInfo activityInfo = intent.resolveActivityInfo(getPackageManager(), intent.getFlags());
        if (activityInfo.exported) {
            startActivity(intent);
        }
    }
        // TODO (2) Use Uri.parse to parse the String into a Uri
        // TODO (3) Create an Intent with Intent.ACTION_VIEW and the webpage Uri as parameters
        // TODO (4) Verify that this Intent can be launched and then call startActivity
}