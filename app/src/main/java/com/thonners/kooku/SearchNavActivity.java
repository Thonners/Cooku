package com.thonners.kooku;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Main Search Activity, with navigation bar layout
 */
public class SearchNavActivity extends AppCompatActivity {

    // Nav bar stuff
    private String[] navBarList ;
    private DrawerLayout navBarLayout ;
    private ListView navBarListView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set view
        setContentView(R.layout.activity_search_nav) ;
        // Get nav bar instances/resources
        navBarList = getResources().getStringArray(R.array.nav_bar_list) ;
        navBarLayout = (DrawerLayout) findViewById(R.id.search_drawer_layout) ;
        navBarListView = (ListView) findViewById(R.id.search_nav_bar_list) ;

        // Set the adapter for the Navigation bar
        navBarListView.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,navBarList);
        // Set the on click listener
        navBarListView.setOnClickListener(new DrawerItemClickListener()));
    }
}
