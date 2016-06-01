package com.thonners.kooku;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class CheckoutActivity extends AppCompatActivity {

    private static final String LOG_TAG = "CheckoutActivity" ;
    private ViewPager pagerAddress ;
    private ViewPager pagerCard ;

    private CheckoutPagerAdapter.OnAddNewClickedListener onAddNewClickedListener = new CheckoutPagerAdapter.OnAddNewClickedListener() {
        @Override
        public void addNewAddressClicked() {
            Log.d(LOG_TAG, "Add new address clicked");
        }

        @Override
        public void addNewCardClicked() {
            Log.d(LOG_TAG, "Add new card clicked");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        // Show the back/up button. Will be intercepted and forced to behave like back button in onMenuItemSelected.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the view pagers
        pagerAddress = (ViewPager) findViewById(R.id.checkout_address_view_pager) ;
        pagerCard = (ViewPager) findViewById(R.id.checkout_payment_view_pager) ;

        // Set the pager adapters
        CheckoutPagerAdapter addressAdapter = new CheckoutPagerAdapter(this, CheckoutPagerAdapter.ADDRESS_PAGER);
        addressAdapter.setOnAddNewClickedListener(onAddNewClickedListener);
        pagerAddress.setAdapter(addressAdapter);
        CheckoutPagerAdapter cardAdapter = new CheckoutPagerAdapter(this, CheckoutPagerAdapter.CARD_PAGER) ;
        cardAdapter.setOnAddNewClickedListener(onAddNewClickedListener);
        pagerCard.setAdapter(cardAdapter);
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

}
