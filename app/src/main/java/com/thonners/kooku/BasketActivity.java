package com.thonners.kooku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.StringTokenizer;


public class BasketActivity extends AppCompatActivity {

    private final String LOG_TAG = "BasketActivity" ;

    private Basket basket ;
    private HashMap<ChefMenu.ChefMenuItem, Integer> orders ;
    private ArrayList<ChefMenu.ChefMenuItem> menuItems ;
    private DeliveryManager deliveryManager ;

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager rcLayoutManager;

    private CardView totalPriceCard ;
    private TextView tvDeliveryTime ;
    private TextView tvTotalPrice ;

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

        // Get the DeliveryManager
        deliveryManager = new DeliveryManager(this, basket) ;

        // Get the instance of the view
        recyclerView = (RecyclerView) findViewById(R.id.basket_recycler) ;

        // Use this setting to improve performance given that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);

        // Use a StaggeredGridLayoutManager - set span to 1, to make it just a vertical list, and keep consistency with other recyclerviews in the app. Can't see much potential for multiple columns on this occasion.
        rcLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(rcLayoutManager);

        // Set the adapter
        BasketRVAdapter adapter = new BasketRVAdapter(this, basket);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        // Get the 'total price' card views
        totalPriceCard = (CardView) findViewById(R.id.card_total_price) ;
        tvDeliveryTime = (TextView) totalPriceCard.findViewById(R.id.tv_delivery_time) ;
        tvTotalPrice = (TextView) totalPriceCard.findViewById(R.id.tv_total_price_value) ;

        // Set the initial values
        updateValues() ;

    }

    /**
     * Method to update the values of the delivery price, total price, etc. and update the display.
     */
    private void updateValues() {
        // Get the selected delivery method
        DeliveryManager.DeliveryMethod method = deliveryManager.getSelectedMethod() ;
        // Set the lead time
        tvDeliveryTime.setText(method.getLeadTimeRangeTwoLines());
        // Set the total price
        NumberFormat format = NumberFormat.getCurrencyInstance() ;
        String totalPriceString = format.format(basket.getTotalPrice()) ;
        tvTotalPrice.setText(totalPriceString);
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

    public void checkoutClicked(View view) {
        // Launch CheckoutActivity
        Log.d(LOG_TAG, "Checkout clicked, launching CheckoutActivity...");
        Intent checkoutActivity = new Intent(this, CheckoutActivity.class);
        //checkoutActivity.putExtra();
        startActivity(checkoutActivity);
    }

}
