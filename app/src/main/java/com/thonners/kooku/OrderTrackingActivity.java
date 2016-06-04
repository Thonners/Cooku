package com.thonners.kooku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Activity to display feedback to the user on the order's status
 *
 * @author M Thomas
 * @since 04/06/16
 */

public class OrderTrackingActivity extends AppCompatActivity {

    private OrderTrackingCard[] cards = new OrderTrackingCard[4] ;
    private int orderStatus = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        // Get the card instances
        cards[0] = (OrderTrackingCard) findViewById(R.id.order_card_1);
        cards[1] = (OrderTrackingCard) findViewById(R.id.order_card_2);
        cards[2] = (OrderTrackingCard) findViewById(R.id.order_card_3);
        cards[3] = (OrderTrackingCard) findViewById(R.id.order_card_4);
        // Set card numbers
        cards[0].setCardNumber(1);
        cards[1].setCardNumber(2);
        cards[2].setCardNumber(3);
        cards[3].setCardNumber(4);
        // Set card 1 (index 0) as focused to start with
        updateCardFocus();
    }

    private void updateCardFocus() {
        // Loop through all cards, and set the requested card to focused, and the other to not focused
        for (int i = 0 ; i < cards.length ; i++) {
            if (i == orderStatus) {
                cards[i].setCardFocused(true);
            } else {
                cards[i].setCardFocused(false);
            }
        }
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus ;
    }
}
