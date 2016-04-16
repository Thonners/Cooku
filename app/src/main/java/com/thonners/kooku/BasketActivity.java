package com.thonners.kooku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BasketActivity extends AppCompatActivity {

    Basket basket ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
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
