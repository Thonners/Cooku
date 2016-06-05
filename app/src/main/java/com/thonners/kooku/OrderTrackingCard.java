package com.thonners.kooku;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Class to define and provide methods for the order tracking cards.
 *
 * @author M Thomas
 * @since  04/06/16
 */

public class OrderTrackingCard extends CardView {

    public static final int AWAITING_CHEF = 0 ;
    public static final int AWAITING_COLLECTION = 1 ;
    public static final int EN_ROUTE = 2 ;
    public static final int DELIVERED = 3 ;

    private int cardDiameter, cardRadius, cardRadiusDefault, cardRadiusFocused ;
    private int colourAccent, colourPrimary ;
    private int textSizeDefault, textSizeFocused ;
    private int zDefault, zFocused ;
    private int orderStatusNumber ;
    private boolean isFocused ;
    private TextView tv ;

    public OrderTrackingCard(Context context) {
        super(context);
    }

    public OrderTrackingCard(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public void initialise(Context context, boolean isFocused, int orderStatus) {
        this.isFocused = isFocused ;
        // Create the TextView and add it to the card
        tv = new TextView(context) ;
        tv.setGravity(Gravity.CENTER);
        this.addView(tv);
        // Get the dimensions
        cardRadiusDefault = context.getResources().getDimensionPixelOffset(R.dimen.order_status_card_default_radius) ;
        cardRadiusFocused = context.getResources().getDimensionPixelOffset(R.dimen.order_status_card_focused_radius) ;
        textSizeDefault = context.getResources().getDimensionPixelOffset(R.dimen.order_status_card_default_text_size) ;
        textSizeFocused = context.getResources().getDimensionPixelOffset(R.dimen.order_status_card_focused_text_size) ;
        colourAccent = context.getResources().getColor(R.color.colorAccent) ;
        colourPrimary = context.getResources().getColor(R.color.colorPrimary);
        zDefault = context.getResources().getDimensionPixelOffset(R.dimen.fab_default_z);
        zFocused = context.getResources().getDimensionPixelOffset(R.dimen.fab_pressed_z);
        // Set the card size
        refreshCard();
        // Set the orderStatus
        this.orderStatusNumber = orderStatus ;
        tv.setText("" + (orderStatusNumber + 1));
    }

    private void refreshCard() {
        // Determine whether the card should be sized for focused or default status
            cardRadius = cardRadiusDefault ;
        int cardColour = colourPrimary ;
        int textColour = colourAccent ;
        int textSize = textSizeDefault ;
        int cardZ = zDefault ;
        if (isFocused) {
            cardRadius = cardRadiusFocused ;
            cardColour = colourAccent ;
            textColour = colourPrimary ;
            textSize = textSizeFocused ;
            cardZ = zFocused ;
        }
        // Set Text & Card colours
        this.setCardBackgroundColor(cardColour);
        tv.setTextColor(textColour);
        // Set card Z elevation
        if( Build.VERSION.SDK_INT >= 21 ) this.setElevation((float) cardZ);
        // Set text size
        tv.setTextSize((float) textSize);
        // Set the diameter at double the radius
        cardDiameter = 2 * cardRadius ;
        // Set the width/height parameters of the card
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.getLayoutParams() ;
        params.height = cardDiameter ;
        params.width = cardDiameter ;
        this.setRadius((float) cardRadius);

    }

    public void setCardFocused(boolean isFocused){
        this.isFocused = isFocused ;
        refreshCard();
    }

    public int getOrderStatusNumber() {
        return orderStatusNumber ;
    }

}
