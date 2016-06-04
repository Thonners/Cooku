package com.thonners.kooku;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Class to define and provide methods for the order tracking cards.
 *
 * @author M Thomas
 * @since  04/06/16
 */

public class OrderTrackingCard extends CardView {

    private int cardDiameter, cardRadius, cardRadiusDefault, cardRadiusFocused ;
    private int colourAccent, colourPrimary ;
    private int zDefault, zFocused ;
    private int orderStatusNumber ;
    private boolean isFocused ;
    private TextView tv ;

    public OrderTrackingCard(Context context, boolean isFocused) {
        super(context) ;
        this.isFocused = isFocused ;
        initialise(context);
    }

    public OrderTrackingCard(Context context) {
        super(context);
        initialise(context);
    }

    public OrderTrackingCard(Context context, AttributeSet attrs){
        super(context,attrs);
        initialise(context);
    }

    private void initialise(Context context) {
        // Create the TextView and add it to the card
        tv = new TextView(context) ;
        this.addView(tv);
        // Get the dimensions
        cardRadiusDefault = context.getResources().getDimensionPixelOffset(R.dimen.order_status_card_default_radius) ;
        cardRadiusFocused = context.getResources().getDimensionPixelOffset(R.dimen.order_status_card_focused_radius) ;
        colourAccent = context.getResources().getColor(R.color.colorAccent) ;
        colourPrimary = context.getResources().getColor(R.color.colorPrimary);
        zDefault = context.getResources().getDimensionPixelOffset(R.dimen.fab_default_z);
        zFocused = context.getResources().getDimensionPixelOffset(R.dimen.fab_pressed_z);
        // Set the card size
        refreshCard();
    }

    private void refreshCard() {
        // Determine whether the card should be sized for focused or default status
            cardRadius = cardRadiusDefault ;
        int cardColour = colourPrimary ;
        int textColour = colourAccent ;
        int cardZ = zDefault ;
        if (isFocused) {
            cardRadius = cardRadiusFocused ;
            cardColour = colourAccent ;
            textColour = colourPrimary ;
            cardZ = zFocused ;
        }
        // Set Text & Card colours
        this.setCardBackgroundColor(cardColour);
        tv.setTextColor(textColour);
        // Set card Z elevation
        if( Build.VERSION.SDK_INT >= 21 ) this.setElevation((float) cardZ);
        // Set the diameter at double the radius
        cardDiameter = 2 * cardRadius ;
        // Set the width/height parameters of the card
        CardView.LayoutParams params = (CardView.LayoutParams) this.getLayoutParams() ;
        params.height = cardDiameter ;
        params.width = cardDiameter ;
        this.setRadius((float) cardRadius);

    }

    public void setCardFocused(boolean isFocused){
        this.isFocused = isFocused ;
        refreshCard();
    }

    public void setCardNumber(int number) {
        this.orderStatusNumber = number ;
        tv.setText("" + orderStatusNumber);
    }
}
