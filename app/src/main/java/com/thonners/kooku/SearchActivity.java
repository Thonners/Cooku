package com.thonners.kooku;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

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

    private ChefManager chefManager;

    private SearchResultsRVAdapter.OnItemClickListener onItemClickListener = new SearchResultsRVAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            launchChefPage(view) ;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout
        setContentView(R.layout.activity_search);
        // Stop the keyboard from popping up
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Initialise the chefManager
        chefManager  = new ChefManager(this);

        // Get the instance of the view
        recyclerView = (RecyclerView) findViewById(R.id.search_results_recycler_view) ;

        // Use this setting to improve performance given that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Use a StaggeredGridLayoutManager - set span to 1, to make it just a vertical list, but maintains possibility to increase number of rows later (tablet?)
        rcLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(rcLayoutManager);

        // Set the adapter
        SearchResultsRVAdapter adapter = new SearchResultsRVAdapter(this, chefManager.getChefs());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);



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
     * Method to launch the new intent of the chef's page.
     * @param resultsCardView The card containing the chef's results.
     */
    private void launchChefPage(View resultsCardView) {
        Log.d(LOG_TAG, "Launch chef page of: " + ((TextView) resultsCardView.findViewById(R.id.chef_name)).getText());
        // Get view for the transition
        ImageView chefImage = (ImageView) resultsCardView.findViewById(R.id.chef_image);
        // Define the pairs
        Pair<View, String> imagePair = Pair.create((View) chefImage, "tImage") ;
        // Define the transition options
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imagePair) ;
        // Define the intent of the MenuActivity
        Intent chefPageIntent = new Intent(this, MenuActivity.class);
        // Add chef id
        TextView tvID = (TextView) resultsCardView.findViewById(R.id.chef_id) ;
        chefPageIntent.putExtra(Chef.CHEF_ID,Integer.parseInt(tvID.getText() + ""));
        // Start the activity, with the transition
        ActivityCompat.startActivity(this, chefPageIntent,options.toBundle());
    }




}
