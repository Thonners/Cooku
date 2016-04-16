package com.thonners.kooku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class BasketActivity extends AppCompatActivity {

    private final String LOG_TAG = "BasketActivity" ;

    Basket basket ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout
        setContentView(R.layout.activity_basket);

        // Get Intent extras
        basket = getIntent().getExtras().getParcelable(Basket.BASKET_ORDERS_EXTRA) ;

    }

    /**
     * Method to determine whether a surcharge is required or not, based on its value compared to
     * the minimum threshold value (set in the prices.xml values resource).
     * @return Whether a surcharge is required or not
     */
    private boolean isSurchargeRequired() {
        float orderThreshold = getResources().getDimension(R.dimen.minimum_order_value) ;
        return basket.getTotalPrice() < orderThreshold ;
    }
}
