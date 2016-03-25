package com.thonners.kooku;

/**
 * Home activity - to be opened when the user opens the app. Shows the welcome screen and a 'get started' button
 *
 * @author M Thomas
 * @since 24/03/16
 *
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    private final static String LOG_TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void getStartedClicked(View view) {
        // Start the intent of the next activity
        Log.d(LOG_TAG, getString(R.string.get_started) + " clicked!");
        Intent startSearch = new Intent(this, SearchActivity.class) ;
        startActivity(startSearch);
    }

}
