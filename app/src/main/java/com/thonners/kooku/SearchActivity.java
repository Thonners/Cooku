package com.thonners.kooku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * Activity to get and display the results of the available chefs.
 *
 * @author M Thomas
 * @since 25/03/16
 */
public class SearchActivity extends AppCompatActivity {

    private final String LOG_TAG = "SearchActivity";

    private RecyclerView recyclerView ;
    private RecyclerView.Adapter rcAdapter;
    private RecyclerView.LayoutManager rcLayoutManager;

    /**
     * Standard onCreate override.
     * Sets up the RecyclerView with a layout manager and SearchResultsRVAdapter.
     * Currently populates results with fakes for the purposes of demonstration. TODO: Get proper results and delete this line!
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout
        setContentView(R.layout.activity_search);
        // Stop the keyboard from popping up
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Get the instance of the view
        recyclerView = (RecyclerView) findViewById(R.id.search_results_recycler_view) ;

        // Use this setting to improve performance given that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        rcLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rcLayoutManager);

        // Set the adapter
        SearchResultsRVAdapter adapter = new SearchResultsRVAdapter(getChefs());
        recyclerView.setAdapter(adapter);

    }

    /**
     * Method to search for the chef who could deliver the food in the soonest possible time.
     * Method will be called when the user clicks on the card.
     * @param view The instance of the CardView which is the ASAP card.
     */
    public void asapClicked(View view) {
        Log.d(LOG_TAG, "A.S.A.P. button clicked.");
    }

    /**
     * Method to search for chefs based on the user's current location
     * Method will be called when the user clicks on the card.
     * @param view The instance of the CardView which is the location card.
     */
    public void locationClicked(View view) {
        Log.d(LOG_TAG, "Location button clicked.");
    }
    /**
     * Method to begin the search for results.
     * In practice, will call an AsyncTask to find the results, then populat the view.
     * Initially based on location, then updated if any search terms are added.
     *
     * Currently chefs results are hard-programmed for demonstration purposes.
     */
    private ArrayList<Chef> getChefs() {
        ArrayList<Chef> chefs = new ArrayList<>();
        // Create some demo chefs
        Chef chef1 = new Chef("Pablo", "Danish Pastries", 3, 45, getResources().getDrawable(R.drawable.demo_chef_danish));
        Chef chef2 = new Chef("MC Thomma$", "Welsh Rarebit", 4, 25, getResources().getDrawable(R.drawable.demo_chef_rarebit));
        Chef chef3 = new Chef("Mary Berry", "Cake", 5, 60, getResources().getDrawable(R.drawable.demo_chef_cake_1));
        Chef chef4 = new Chef("Marjory Dawes", "CAKE", 1, 20, getResources().getDrawable(R.drawable.demo_chef_cake_2));
        // Add to array list
        chefs.add(chef1) ;
        chefs.add(chef2);
        chefs.add(chef3);
        chefs.add(chef4);
        // Return list
        return chefs ;
    }
}
