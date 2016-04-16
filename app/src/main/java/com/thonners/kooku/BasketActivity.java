package com.thonners.kooku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;


public class BasketActivity extends AppCompatActivity {

    private final String LOG_TAG = "BasketActivity" ;

    private Basket basket ;
    private HashMap<ChefMenu.ChefMenuItem, Integer> orders ;
    private ArrayList<ChefMenu.ChefMenuItem> menuItems ;
    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager rcLayoutManager;

    private BasketRVAdapter.OnItemClickListener onItemClickListener = new BasketRVAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            // Get the item ID
            Log.d(LOG_TAG, "Item: " + menuItems.get(position) + " clicked.") ;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout
        setContentView(R.layout.activity_basket);
        // Show the back/up button. Will be intercepted and forced to behave like back button in onMenuItemSelected.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get Intent extras
        basket = getIntent().getExtras().getParcelable(Basket.BASKET_ORDERS_EXTRA) ;
        orders = basket.getOrders() ;
        menuItems = basket.getMenuItems() ;

        // Get the instance of the view
        recyclerView = (RecyclerView) findViewById(R.id.basket_recycler) ;

        // Use this setting to improve performance given that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);

        // Use a StaggeredGridLayoutManager - set span to 1, to make it just a vertical list, but maintains possibility to increase number of rows later (tablet?)
        rcLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(rcLayoutManager);

        // Set the adapter
        BasketRVAdapter adapter = new BasketRVAdapter(this, basket, isSurchargeRequired());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

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

    /**
     * Method to determine whether a surcharge is required or not, based on its value compared to
     * the minimum threshold value (set in the prices.xml values resource).
     * @return Whether a surcharge is required or not
     */
    private boolean isSurchargeRequired() {
        double orderThreshold = Basket.MINIMUM_ORDER_VALUE ;
        Log.d(LOG_TAG, "Testing whether surcharge required: Min order value = " + orderThreshold + " and current basket value = " + basket.getSubtotalPrice()) ;
        return basket.getSubtotalPrice() < orderThreshold ;
    }
}
