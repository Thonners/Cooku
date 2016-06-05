package com.thonners.kooku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Activity to display feedback to the user on the order's status
 *
 * @author M Thomas
 * @since 04/06/16
 */

public class OrderTrackingActivity extends AppCompatActivity {

    public static final String CHEF_NAME_EXTRA = "com.thonners.kooku.OrderTrackingActivity.CHEF_NAME_EXTRA" ;
    public static final String DELIVERY_DATE_EXTRA = "com.thonners.kooku.OrderTrackingActivity.DELIVERY_DATE_EXTRA" ;

    private String chefName = "Granny Lü";
    private String[] statuses ;
    private String deliveredDateString = "";
    private TextView statusTextView ;
    private CardView footerButton ;

    private OrderTrackingCard[] cards = new OrderTrackingCard[4] ;
    private int orderStatus = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        // Get the text view
        statusTextView = (TextView) findViewById(R.id.order_tracking_status) ;
        // Get the possible order statuses
        statuses = getResources().getStringArray(R.array.order_statuses) ;
        // Format the statuses with the appropriate extra details
        statuses[0] = String.format(statuses[0], chefName) ;

        // Get the footer button
        footerButton = (CardView) findViewById(R.id.order_tracking_footer_button) ;

        // Get the card instances
        cards[0] = (OrderTrackingCard) findViewById(R.id.order_card_1);
        cards[1] = (OrderTrackingCard) findViewById(R.id.order_card_2);
        cards[2] = (OrderTrackingCard) findViewById(R.id.order_card_3);
        cards[3] = (OrderTrackingCard) findViewById(R.id.order_card_4);
        // Set card numbers
        cards[0].initialise(this, true, OrderTrackingCard.AWAITING_CHEF);
        cards[1].initialise(this, false, OrderTrackingCard.AWAITING_COLLECTION);
        cards[2].initialise(this, false, OrderTrackingCard.EN_ROUTE);
        cards[3].initialise(this, false, OrderTrackingCard.DELIVERED);
        // Set card 1 (index 0) as focused to start with
        updateCardFocus();

        // Create demo onTouchListeners
        createDemoListeners() ;
    }

    /**
     * Method to put on click listeners on the order status cards, to allow the order status to be
     * advanced by clicking a card. No listener will be applied to the final card, as there is no
     * status after this.
     */
    private void createDemoListeners() {
        // Loop through all but final card and apply an onClickListener to set the focus to the next card when this one is clicked
        for (int i = 0 ; i < (cards.length - 1) ; i++) {
            cards[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderTrackingCard card = (OrderTrackingCard) view ;
                    int newStatus = card.getOrderStatusNumber() + 1 ;
                    setOrderStatus(newStatus);
                }
            });
        }
    }


    private void updateCardFocus() {
        // Loop through all cards, and set the requested card to focused, and the other to not focused
        for (int i = 0 ; i < cards.length ; i++) {
            if (i == orderStatus) {
                // Focus the appropriate card
                cards[i].setCardFocused(true);
                // Set the status text
                statusTextView.setText(statuses[i]);
            } else {
                // Forcibly remove focus from all other cards
                cards[i].setCardFocused(false);
            }
        }
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus ;
        updateCardFocus();
        // Show the rate button when the food has been delivered
        if (orderStatus == OrderTrackingCard.DELIVERED){
            footerButton.setVisibility(View.VISIBLE);
            deliveredDateString = getDateString() ;
        }
    }

    private String getDateString() {
        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance() ;
        Calendar calendar = Calendar.getInstance() ;
        return format.format(calendar.getTime()) ;
    }

    public void rateFoodClicked(View view) {
        // Launch the review page
        Intent reviewActivity = new Intent(this, ReviewActivity.class) ;
        reviewActivity.putExtra(CHEF_NAME_EXTRA,chefName);
        reviewActivity.putExtra(DELIVERY_DATE_EXTRA,deliveredDateString);
        startActivity(reviewActivity);
    }
}
