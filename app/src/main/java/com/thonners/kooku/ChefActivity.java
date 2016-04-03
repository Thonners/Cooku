package com.thonners.kooku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Activity to display a chef's home page.
 *
 * @author M Thomas
 * @since 25/03/16
 */
public class ChefActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        setTitle(getIntent().getStringExtra("CHEF_NAME"));
    }
}
