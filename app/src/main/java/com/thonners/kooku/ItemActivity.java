package com.thonners.kooku;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
