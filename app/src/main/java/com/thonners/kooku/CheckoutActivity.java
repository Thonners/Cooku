package com.thonners.kooku;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class CheckoutActivity extends AppCompatActivity {

    private static final String LOG_TAG = "CheckoutActivity" ;
    private ViewPager pagerAddress ;
    private ViewPager pagerCard ;
    private final static int NEW_ADDRESS_REQUEST = 1;
    private final static int NEW_CARD_REQUEST = 2;

    private CheckoutPagerAdapter.OnAddNewClickedListener onAddNewClickedListener = new CheckoutPagerAdapter.OnAddNewClickedListener() {
        @Override
        public void addNewAddressClicked() {
            Log.d(LOG_TAG, "Add new address clicked");
            launchNewAddressActivity();
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
        initialiseAddressViewPager();
        initialiseCardViewPager();
    }

    private void initialiseAddressViewPager() {
        CheckoutPagerAdapter addressAdapter = new CheckoutPagerAdapter(this, CheckoutPagerAdapter.ADDRESS_PAGER);
        addressAdapter.setOnAddNewClickedListener(onAddNewClickedListener);
        pagerAddress.setAdapter(addressAdapter);
    }
    private void initialiseCardViewPager() {
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

    /**
     * Launch a new AddAddressActivity.
     * Start the activity for result so that the address view pager can be refreshed when it returns.
     */
    private void launchNewAddressActivity() {
        Intent newAddressIntent = new Intent(this, AddAddressActivity.class);
        startActivityForResult(newAddressIntent,NEW_ADDRESS_REQUEST);
    }

    /**
     * Launch a new AddCardActivity.
     * Start the activity for result so that the address view pager can be refreshed when it returns.
     */
    private void launchNewCardActivity() {
        //Intent newAddressIntent = new Intent(this, AddCardActivity.class);
        //startActivityForResult(newAddressIntent,NEW_CARD_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case NEW_ADDRESS_REQUEST:
                if (resultCode == RESULT_OK) {
                    Log.d(LOG_TAG,"New Address result returned OK.");
                    initialiseAddressViewPager();
                }
                break;
            case NEW_CARD_REQUEST:
                if (resultCode == RESULT_OK) {
                    Log.d(LOG_TAG,"New Card result returned OK.");
                    // Refresh the card view pager
                    initialiseCardViewPager();
                }
                break;
            default:
                Log.d(LOG_TAG, "An unexpected activity returned here. Request code: " + requestCode) ;
        }
    }
}
