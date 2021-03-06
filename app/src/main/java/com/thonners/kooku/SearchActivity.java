package com.thonners.kooku;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    // Filter menu
    private boolean[] filters = new boolean[8] ; // Order: Veggie, Vegan, Nut Free, Gluten Free, Halal, Dairy Free, Egg Free, Low Fat

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

    /**
     * Method to show the PopupMenu filter menu when the filter button is clicked.
     * Method also contains the logic to ensure that the filter selection is persistent, and survives
     * re-opening the menu.
     * @param view The filter button
     */
    public void filterClicked(View view) {
        PopupMenu filterMenu = new PopupMenu(this,view);
        filterMenu.inflate(R.menu.search_filter_menu);
        filterMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // Toggle the status
                item.setChecked(!item.isChecked()) ;

                // For each filter, toggle the boolean[] filters value if the filter is pressed.
                // When the menu closes, updateResults() will be called by the OnDismissListener.
                switch (item.getItemId()) {
                    case R.id.filter_veggie:
                        // Vegetarian filter
                        Log.d(LOG_TAG,"Veggie filter selected: " + R.id.filter_veggie);
                        filters[0] = !filters[0] ;
                        break ;
                    case R.id.filter_vegan:
                        // Vegan filter
                        Log.d(LOG_TAG,"Vegan filter selected");
                        filters[1] = !filters[1] ;
                        break ;
                    case R.id.filter_nut_free:
                        // Nut free filter
                        Log.d(LOG_TAG,"Nut free filter selected: " + R.id.filter_veggie);
                        filters[2] = !filters[2] ;
                        break ;
                    case R.id.filter_gluten_free:
                        // Gluten free filter
                        Log.d(LOG_TAG,"Gluten Free filter selected");
                        filters[3] = !filters[3] ;
                        break ;
                    case R.id.filter_halal:
                        // Halal filter
                        Log.d(LOG_TAG,"Halal filter selected: " + R.id.filter_veggie);
                        filters[4] = !filters[4] ;
                        break ;
                    case R.id.filter_dairy_free:
                        // Dairy free filter
                        Log.d(LOG_TAG,"Dairy free filter selected");
                        filters[5] = !filters[5] ;
                        break ;
                    case R.id.filter_egg_free:
                        // Egg free filter
                        Log.d(LOG_TAG,"Egg free filter selected: " + R.id.filter_veggie);
                        filters[6] = !filters[6] ;
                        break ;
                    case R.id.filter_low_fat:
                        // Low fat filter
                        Log.d(LOG_TAG,"Low fat filter selected");
                        filters[7] = !filters[7] ;
                        break ;
                }
                // The menu interaction was handled.
                return false ;
            }
        });
        filterMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Log.d(LOG_TAG,"onDismiss called");
                // Refresh the results based on the filters
                updateResults();
            }
        });

        // setChecked the already active options
        Menu menu = filterMenu.getMenu() ;
        for (int i = 0 ; i < menu.size() ; i++) {
            menu.getItem(i).setChecked(filters[i]) ;
        }

        // Show the menu
        filterMenu.show();
    }

    /**
     * Method to update the recommended chefs results based on the filters selected by the user.
     */
    private void updateResults() {
        // Update the search/recommended results based on search filters.
    }


}
