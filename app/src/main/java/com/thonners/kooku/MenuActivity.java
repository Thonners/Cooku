package com.thonners.kooku;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Activity to display a chef's home page.
 *
 * @author M Thomas
 * @since 25/03/16
 */
public class MenuActivity extends AppCompatActivity {

    public static final String ITEM_EXTRA = "com.thonners.kooku.itemExtra";
    public static final String BASKET_EXTRA = "com.thonners.kooku.basketExtra";

    private final String LOG_TAG = "MenuActivity" ;

    private ChefManager chefManager;
    private Chef chef ;
    private ChefMenu menu ;
    private CoordinatorLayout coordinatorLayout ;
    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager rcLayoutManager;
    private Basket basket = new Basket() ;
    private boolean showFullBio = false ;
    // Basket footer button values
    private CardView basketFooterButton ;
    private TextView basketFooterButtonTV ;

    private MenuRVAdapter.OnItemClickListener onItemClickListener = new MenuRVAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (position == 0) {
                toggleBio(view);
            } else {
                // Get the item ID
                int itemID = Integer.parseInt(((TextView) view.findViewById(R.id.menu_item_id)).getText().toString());
                // Launch ItemActivity
                launchItemActivity(menu.getMenuItem(itemID));
                // Add the item to the basket - Removed this functionality, swapped for launching ItemActivity onClick
                //addItemToBasket(menu.getMenuItem(itemID));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        // Show the back/up button. Will be intercepted and forced to behave like back button in onMenuItemSelected.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the coordinator layout
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.chef_coordinator_layout);

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

        // Set the Chef's name in the title
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar) ;
        collapsingToolbarLayout.setTitle(chef.getChefName());

        // Get the basket & its views, and hide it
        basketFooterButton = (CardView) findViewById(R.id.footer_button_basket) ;
        basketFooterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basketButtonClicked() ;
            }
        });
        basketFooterButtonTV = (TextView) basketFooterButton.findViewById(R.id.basket_value) ;

        // Set/hide the basket footer button as appropriate
        updateFooterButtonBasket();
    }

    /**
     * Method to handle item menu clicks.
     * Includes intercepting the up button to force it to behave like the back button
     * @param item The menu item selected
     * @return Whether this method has handled the click
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case android.R.id.home:
            onBackPressed();
            return true;
    }

    return(super.onOptionsItemSelected(item));
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
                        // Update footer button
                        updateFooterButtonBasket();
                    }
                });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        // Show it
        snackbar.show();

        // Update footer button
        updateFooterButtonBasket();
    }

    public void fabClicked(View view) {
        Log.d(LOG_TAG, "Floating Action Button Clicked. Checking out...") ;
        // TODO: Add intent to move onto checkout page.
        Snackbar snackbar = Snackbar.make(coordinatorLayout,"Current Basket Total = £ " + basket.getSubtotalPrice(),Snackbar.LENGTH_LONG) ;
        snackbar.show();
    }

    private void toggleBio(View cardView) {
        // Toggle whether to show full bio
        showFullBio = !showFullBio ;
        // Get text views
        TextView tvBioPrompt = (TextView) cardView.findViewById(R.id.chef_bio_prompt) ;
        TextView tvBioLong = (TextView) cardView.findViewById(R.id.chef_bio_long) ;
        ViewGroup.LayoutParams params = cardView.getLayoutParams() ;
        if (showFullBio) {
            Log.d(LOG_TAG, "Expanding bio");
            tvBioPrompt.setText(getString(R.string.chef_bio_prompt_2));
            tvBioLong.setVisibility(View.VISIBLE);
            /*params.height = ViewGroup.LayoutParams.WRAP_CONTENT ;
            cardView.setLayoutParams(params);*/

        } else {
            Log.d(LOG_TAG, "Shrinking bio");
            tvBioPrompt.setText(getString(R.string.chef_bio_prompt));
            tvBioLong.setVisibility(View.GONE);
            /*params.height = getResources().getDimensionPixelOffset(R.dimen.card_height_big);
            cardView.setLayoutParams(params);*/
        }
    }

    private void updateFooterButtonBasket() {
        // Hide the footer button if the basket is empty, otherwise force visible and refresh value
        if (basket.isEmpty()) {
            basketFooterButton.setVisibility(View.GONE);
        } else {
            basketFooterButton.setVisibility(View.VISIBLE);
            basketFooterButtonTV.setText("£ " + basket.getSubtotalPrice());
        }
    }

    /**
     * Method to respond to a user clicking on the basket footer button. Launches BasketActivity.
     */
    private void basketButtonClicked() {
        // Launch new intent
        Log.d(LOG_TAG, "Basket footer button clicked. Launching BasketActivity...");
        Intent basketActivity = new Intent(this, BasketActivity.class) ;
        basketActivity.putExtra(Basket.BASKET_CHEF_EXTRA, chef.getChefName()) ;
        basketActivity.putExtra(Basket.BASKET_ORDERS_EXTRA, basket) ;
        startActivity(basketActivity);
    }

    /**
     * Method to respond to a user clicking on a ChefMenuItem. Opens an ItemActivity to display
     * more info about the item clicked.
     * @param item The item for which to launch the ItemActivity.
     */
    private void launchItemActivity(ChefMenu.ChefMenuItem item){
        Intent itemActivity = new Intent(this, ItemActivity.class) ;
        itemActivity.putExtra(ITEM_EXTRA, item) ;
        itemActivity.putExtra(BASKET_EXTRA, basket);
        startActivity(itemActivity);
    }
}
