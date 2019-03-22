package com.example.android.sunshine.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.android.sunshine.data.WeatherContract;

public class SunshineSyncUtils {
    //  done (1) Declare a private static boolean field called sInitialized
    private static boolean sInitialized;
    //  done (2) Create a synchronized public static void method called initialize
    synchronized public static void initialize(@NonNull final Context context) {
//      done (3) Only execute this method body if sInitialized is false
        if (sInitialized) return;
//      done (4) If the method body is executed, set sInitialized to true
        sInitialized = true;
//      done (5) Check to see if our weather ContentProvider is empty
        new AsyncTask<Void, Void, Void>() {
            @Override
            public Void doInBackground( Void... voids ) {
                Uri forecastQueryUri = WeatherContract.WeatherEntry.CONTENT_URI;
                String[] projectionColumns = {WeatherContract.WeatherEntry._ID};
                String selectionStatement = WeatherContract.WeatherEntry
                        .getSqlSelectForTodayOnwards();
                Cursor cursor = context.getContentResolver().query(
                        forecastQueryUri,
                        projectionColumns,
                        selectionStatement,
                        null,
                        null);
                //  done (6) If it is empty or we have a null Cursor, sync the weather now!
                if (null == cursor || cursor.getCount() == 0) {
                    startImmediateSync(context);
                }
                cursor.close();
                return null;
            }
        }.execute();
    }

    /**
     * Helper method to perform a sync immediately using an IntentService for asynchronous
     * execution.
     *
     * @param context The Context used to start the IntentService for the sync.
     */
    public static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, SunshineSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }
}