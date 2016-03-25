package com.thonners.kooku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private RecyclerView.Adapter rcAdapter;
    private RecyclerView.LayoutManager rcLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.search_results_recycler_view) ;

        // use this setting to improve performance given that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        rcLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rcLayoutManager);

        // Set the adapter
        SearchResultsRVAdapter adapter = new SearchResultsRVAdapter(getChefs());
        recyclerView.setAdapter(adapter);

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
