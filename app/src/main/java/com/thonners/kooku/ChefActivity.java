package com.thonners.kooku;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Activity to display a chef's home page.
 *
 * @author M Thomas
 * @since 25/03/16
 */
public class ChefActivity extends AppCompatActivity {

    private final String LOG_TAG = "ChefActivity" ;

    private ChefManager chefManager;
    private Chef chef ;
    private ChefMenu menu ;
    private CoordinatorLayout coordinatorLayout ;
    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager rcLayoutManager;
    private Basket basket = new Basket() ;
    private boolean showFullBio = false ;

    private MenuRVAdapter.OnItemClickListener onItemClickListener = new MenuRVAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (position == 0) {
                toggleBio(view);
            } else {
                // Get the item ID
                int itemID = Integer.parseInt(((TextView) view.findViewById(R.id.menu_item_id)).getText().toString());
                // Add the item to the basket
                addItemToBasket(menu.getMenuItem(itemID));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        // Get the coordinator layout
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.chef_coordinator_layout);

        // Set colour of trolley
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_basket) ;
        Drawable fabBasketDrawable = fab.getDrawable() ;
        fabBasketDrawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);

        // Initialise chefManager
        chefManager  = new ChefManager(this);

        // Get the Chef
        int chefID = getIntent().getIntExtra(Chef.CHEF_ID,1);
        chef = chefManager.getChefs(true).get(chefID);

        // Get the menu
        menu = chef.getMenu() ;

        // Get the instance of the view
        recyclerView = (RecyclerView) findViewById(R.id.chef_menu) ;

        // Use this setting to improve performance given that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);

        // Use a StaggeredGridLayoutManager - set span to 1, to make it just a vertical list, but maintains possibility to increase number of rows later (tablet?)
        rcLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(rcLayoutManager);

        // Set the adapter
        MenuRVAdapter adapter = new MenuRVAdapter(this, menu);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        // Set the Chef background image
        ImageView chefImage = (ImageView) findViewById(R.id.chef_image);
        chefImage.setImageDrawable(chef.getChefImage());
    }

    private void addItemToBasket(final ChefMenu.ChefMenuItem item) {
        Log.d(LOG_TAG, "Adding item: " + item.getTitle() + " to basket.");
        // Add item to the basket
        basket.addItem(item);
        // Show snackbar
        String snackbarMessage = String.format(getString(R.string.snackbar_message), item.getTitle()) ;
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, snackbarMessage, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.snackbar_action_message), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Remove the item from the basket.
                        basket.removeItem(item);
                        // Show confirmation that it's been removed.
                        String itemRemovedSnackbarMessage = String.format(getString(R.string.snackbar_item_removed), item.getTitle());
                        Snackbar itemRemovedSnackbar = Snackbar.make(coordinatorLayout,itemRemovedSnackbarMessage,Snackbar.LENGTH_SHORT) ;
                        itemRemovedSnackbar.show();
                    }
                });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        // Show it
        snackbar.show();
    }

    public void fabClicked(View view) {
        Log.d(LOG_TAG, "Floating Action Button Clicked. Checking out...") ;
        // TODO: Add intent to move onto checkout page.
        Snackbar snackbar = Snackbar.make(coordinatorLayout,"Current Basket Total = £ " + basket.getTotalPrice(),Snackbar.LENGTH_LONG) ;
        snackbar.show();
    }

    private void toggleBio(View cardView) {
        // Toggle whether to show full bio
        showFullBio = !showFullBio ;
        // Get text views
        TextView tvBioPrompt = (TextView) cardView.findViewById(R.id.chef_bio_prompt) ;
        TextView tvBioLong = (TextView) cardView.findViewById(R.id.chef_bio_long) ;
        if (showFullBio) {
            Log.d(LOG_TAG, "Expanding bio");
            tvBioPrompt.setText(getString(R.string.chef_bio_prompt_2));
            tvBioLong.setVisibility(View.VISIBLE);
        } else {
            Log.d(LOG_TAG, "Shrinking bio");
            tvBioPrompt.setText(getString(R.string.chef_bio_prompt));
            tvBioLong.setVisibility(View.GONE);
        }
    }

}
