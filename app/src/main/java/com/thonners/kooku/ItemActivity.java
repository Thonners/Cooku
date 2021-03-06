package com.thonners.kooku;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ItemActivity - Shows more details for a particular item, and allows the user to add it to the basket
 *
 * @author M Thomas
 * @since 24/04/16
 */

public class ItemActivity extends AppCompatActivity {

    private final String LOG_TAG = "ItemActivity" ;

    private ChefMenuItem item ;
    private Chef chef ;
    private Basket basket ;
    private CoordinatorLayout coordinatorLayout ;
    // For favourite status. In practice will be handled by FavouritesManager
    private boolean isFav = false ;
    private MenuItem favouriteMenuItem ;
    // Basket footer button values
    private CardView basketFooterButton ;
    private TextView basketFooterButtonTV ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        // Get the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.item_toolbar) ;
        // Set the toolbar as the action bar
        setSupportActionBar(toolbar);
        // Show the back/up button. Will be intercepted and forced to behave like back button in onMenuItemSelected.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the coordinator layout
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.item_coordinator_layout);

        // Get the item
        item = getIntent().getExtras().getParcelable(MenuActivity.ITEM_EXTRA) ;

        //Set the images
        ItemImagePagerAdapter imagePagerAdapter = new ItemImagePagerAdapter(this);
        ViewPager itemImageViewPager = (ViewPager) findViewById(R.id.item_image_view_pager) ;
        itemImageViewPager.setAdapter(imagePagerAdapter);

        // Get the basket
        basket = getIntent().getExtras().getParcelable(MenuActivity.BASKET_EXTRA) ;

        // Set the Chef's name in the title
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.item_collapsing_toolbar) ;
        collapsingToolbarLayout.setTitle(item.getTitle());
        // Populate the awards
        LinearLayout awardsLayout = (LinearLayout) findViewById(R.id.item_awards_layout) ;
        ItemAwardsManager awardsManager = new ItemAwardsManager() ;
        awardsManager.addAwardIconsToView(awardsLayout,item.getAwards());
        // Fill the info
        ((TextView) findViewById(R.id.item_price)).setText(item.getPriceString());
        ((TextView) findViewById(R.id.item_description_tv)).setText(item.getDescription());
        ((TextView) findViewById(R.id.item_ingredients_tv)).setText(item.getIngredientsString());
        TextView tvContains = (TextView) findViewById(R.id.item_contains_tv);
        String containsString = item.getContains(this);
        if (!containsString.matches("")) {
            tvContains.setVisibility(View.VISIBLE);
            tvContains.setText(containsString);
        }

        // Get the reviews' RecyclerView
        RecyclerView reviewsRV = (RecyclerView) findViewById(R.id.item_reviews) ;

        // Use this setting to improve performance given that changes
        // in content do not change the layout size of the RecyclerView
        reviewsRV.setHasFixedSize(true);
        reviewsRV.setNestedScrollingEnabled(true);

        // Use a StaggeredGridLayoutManager - set span to 1, to make it just a vertical list, but maintains possibility to increase number of rows later (tablet?)
        RecyclerView.LayoutManager rcLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        reviewsRV.setLayoutManager(rcLayoutManager);

        // Set the adapter
        ReviewManager reviewManager = new ReviewManager();
        ItemReviewRVAdapter adapter = new ItemReviewRVAdapter(this, reviewManager.getReviews(item.getItemID()));
        reviewsRV.setAdapter(adapter);

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
        basket.updateFooterButton(basketFooterButton, basketFooterButtonTV);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_item_menu, menu);
        return true;
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
            case R.id.item_add:
                Log.d(LOG_TAG,"Add menu button clicked. Adding item to basket.") ;
                addItemToBasket();
                break;
            case R.id.item_favourite:
                Log.d(LOG_TAG,"Favourite menu button clicked. Adding item to favourites (Not actually implemented yet).") ;
                favouriteMenuItem = item ;
                toggleFavourite() ;
                break;
        }
        return(super.onOptionsItemSelected(item));
    }
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MenuActivity.BASKET_EXTRA,basket) ;
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }


    /**
     * Method to respond to the clicked floating action button.
     * @param view The Floating Action Button that has been clicked
     */
    public void fabClicked(View view){
        Log.d(LOG_TAG,"FAB clicked. Adding item to basket.") ;
        addItemToBasket();
    }
    private void addItemToBasket() {
        // Get the appropriate snackbar from the basket class helper method
        Snackbar snackbar = basket.addNOfItem(this, coordinatorLayout, basketFooterButton, basketFooterButtonTV, item, 1);
        // Show it
        snackbar.show();

        // Update footer button
        basket.updateFooterButton(basketFooterButton, basketFooterButtonTV);
        //updateFooterButtonBasket();
    }

    /**
     * Method to toggle whether an item should be 'favourited' or not
     */
    private void toggleFavourite() {
        // Toggle 'favourite' status
        isFav = !isFav ;
        // Switch menu icon as appropriate
        if (isFav){
            favouriteMenuItem.setIcon(getResources().getDrawable(R.drawable.ic_favorite_white_48dp));
        } else {
            favouriteMenuItem.setIcon(getResources().getDrawable(R.drawable.ic_favorite_border_white_48dp));
        }

    }

    private void updateFooterButtonBasket() {
        // Hide the footer button if the basket is empty, otherwise force visible and refresh value
        if (basket.isEmpty()) {
            basketFooterButton.setVisibility(View.GONE);
        } else {
            basketFooterButton.setVisibility(View.VISIBLE);
            basketFooterButtonTV.setText(basket.getSubtotalPriceString());
        }
    }
    /**
     * Method to respond to a user clicking on the basket footer button. Launches BasketActivity.
     */
    private void basketButtonClicked() {
        // Launch new intent
        Log.d(LOG_TAG, "Basket footer button clicked. Launching BasketActivity...");
        Intent basketActivity = new Intent(this, BasketActivity.class) ;
        basketActivity.putExtra(Basket.BASKET_ORDERS_EXTRA, basket) ;
        startActivity(basketActivity);
    }

}
