package com.thonners.kooku;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

/**
*	Class to store purchase orders in the Basket.
*
*	@author M Thomas
*	@since 06/04/2016
*/


public class Basket implements Parcelable {

    private final String LOG_TAG = "Basket" ;
	public static final String BASKET_CHEF_EXTRA = "com.thonners.kooku.basketChefExtra" ;
	public static final String BASKET_ORDERS_EXTRA = "com.thonners.kooku.basketOrdersExtra" ;

    public static final double MINIMUM_ORDER_VALUE = 15.0 ;
    public static final double SURCHARGE_VALUE = 2.5 ;


    private double subtotalPrice = 0.0 ;
    private double totalPrice = 0.0 ;
    private DeliveryManager.DeliveryMethod deliveryMethod ;
	private HashMap<ChefMenuItem, Integer> orders = new HashMap<>() ;
    private ArrayList<ChefMenuItem> menuItems ;

	/**
	*    Constructor
	*/
	public Basket() {
	}

    /**
     * Method to extract the menu items, so that they can be pulled out by an index number when creating the views.
     */
    private void populateMenuItems() {
        // Clear the list to ensure there are no duplicates
        menuItems = new ArrayList<>() ;
        for (ChefMenuItem item : orders.keySet()) {
            menuItems.add(item) ;
        }
    }
    public ArrayList<ChefMenuItem> getMenuItems() {
        populateMenuItems();
        return menuItems ;
    }

    /**
     * Method to increment item count by 1
     * @param item The item to be added to the basket.
     */
	public void addOneOfItem(ChefMenuItem item) {
        addNOfItem(item, 1);
    }

    /**
     * Method to add 'N' of an item to the basket
     * @param item  The item to add
     * @param noToAdd   The number of servings to add
     */
    public void addNOfItem(ChefMenuItem item, int noToAdd) {
        int quantity = noToAdd ;
        // Add old quantity to number of extras, if it already exists
        if (orders.containsKey(item)) quantity += orders.get(item) ;

        // Amend order
        setItemQuantity(item, quantity);
    }

    /**
     * Method to decrement the number of the item in the basket by one
     * @param item  Item for which to decrement number in the basket
     */
    public void removeOneOfItem(ChefMenuItem item) {
        // Remove 1 of the item
        removeNOfItem(item, 1);
    }

    /**
     * Method to remove 'N' of an item from the basket.
     * @param item  Item to remove from basket
     * @param noToRemove    Number of servings of the item to remove
     */
    public void removeNOfItem(ChefMenuItem item, int noToRemove) {
        // Check that the order exists.
        if (!orders.containsKey(item)) return ;

        // Get the current quantity
        int quantity = orders.get(item) ;
        // Decrement by the 'number to remove'
        quantity -= noToRemove ;
        // Force quantity to be at least 0
        quantity = Math.max(0,quantity) ;
        // Amend order
        if (quantity == 0) {
            // Completely remove from basket
            orders.remove(item);
        } else {
            setItemQuantity(item, quantity);
        }
    }

	/**
	*    Method to set the number of servings for an item in the basket
	*    @param item The new menu item to be added to the basket.
	*    @param quantity The number of servings of the menu item required in the basket.
	*/
	public void setItemQuantity(ChefMenuItem item, int quantity) {
		if (orders.containsKey(item)) {
			// If the item is already there, flag a warning - not sure how this will be implemented in practice.
			Log.d(LOG_TAG, "Amending quantity of " + item.getTitle() + " to: " + quantity) ;
		} else {
			// Make a note of what's being added
			Log.d(LOG_TAG,"Adding " + quantity + " " + item.getTitle()) ;
		}
		// Actually add/change the value in the HashMap
		orders.put(item, quantity) ;

		// Update the price
		double newItemCost = item.getPrice() * quantity ;
 		Log.d(LOG_TAG, "New item's cost = " + newItemCost + ". This is for " + quantity + " dishes.") ;
		updateSubtotalPrice();
		Log.d(LOG_TAG,"Total cost = " + subtotalPrice) ;
	}

	/**
	*    Method to calculate and update the total value of the basket.
	*/
	private void updateSubtotalPrice() {
        double price = 0.0 ;
        for (ChefMenuItem item : orders.keySet()) {
            double itemPrice = item.getPrice() * orders.get(item) ;
            price += itemPrice ;
        }

        subtotalPrice = price ;
    }

    private void updateTotalPrice() {
        // Ensure subtotal price is up to date
        updateSubtotalPrice();
        // Initialise to subtotal price
        totalPrice = subtotalPrice ;
        // Add surcharge if required
        if (isSurchargeRequired()) {
            totalPrice += SURCHARGE_VALUE ;
        }
        // Add delivery
        totalPrice += deliveryMethod.getPrice() ;
    }

    /**
     * Method to determine whether a surcharge is required or not, based on its value compared to
     * the minimum threshold value (set at the head of this class).
     * @return Whether a surcharge is required or not
     */
    public boolean isSurchargeRequired() {
        return getSubtotalPrice() < MINIMUM_ORDER_VALUE  ;
    }
	/**
	*    Method to return the total value of the items in a basket
	*    @return subtotalPrice The total value of the basket.
	*/
	public double getSubtotalPrice() {
        updateSubtotalPrice();
		return subtotalPrice;
	}

    /**
     * Method to return the subtotal price in a formatted String with the appropriate currency icon.
     * @return The subtotal price as a String
     */
    public String getSubtotalPriceString(){
        Currency currency = Currency.getInstance(Locale.getDefault());
        String currencySymbol = currency.getSymbol() ;
        return String.format(currencySymbol + " %1$.1f",getSubtotalPrice()) ;
    }

    /**
     * Method to return the whole amount payable, including delivery and any surcharges.
     * @return totalPrice The total amount payable for the order
     */
    public double getTotalPrice() {
        updateTotalPrice() ;
        return totalPrice ;
    }
    /**
     * Method to return the total price in a formatted String with the appropriate currency icon.
     * @return The total price as a string.
     */
    public String getTotalPriceString(){
        Currency currency = Currency.getInstance(Locale.getDefault());
        String currencySymbol = currency.getSymbol() ;
        return String.format(currencySymbol + " %1$.1f",getTotalPrice()) ;
    }
    /**
     * Method to return the HashMap of the orders.
     * As ChefMenuItems are parcelable, this can be passed over an intent.
     * @return HashMap of basket's orders, with the quantity (value) of each ChefMenuItem (key).
     */
    public HashMap<ChefMenuItem, Integer> getOrders() {
        return orders ;
    }

    /**
     * Method to set the delivery method.
     * @param selectedMethod The chosen delivery method
     */
    public void setDeliveryMethod(DeliveryManager.DeliveryMethod selectedMethod) {
        this.deliveryMethod = selectedMethod ;
    }

    /**
     * Method to return the delivery method currently selected
     * @return Instance of the selected delivery method
     */
    public DeliveryManager.DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    /**
     * Method to return whether the basket is empty or not.
     * Used, for example, in determining whether to show the basket footer button.
     * @return Whether basket total is zero.
     */
	public boolean isEmpty() {
		return subtotalPrice == 0.0 ;
	}

    /**
     * Method to add the desired items to the basket, and return a snackbar, which should then be
     * displayed by the calling activity, to inform the user that the item has been added.
     * @param context   Calling activity context
     * @param item      ChefMenuItem to be added to the basked
     * @param quantity  The quantity to be added
     * @return  The Snackbar, with appropriate message
     */
    public Snackbar addNOfItem(final Context context, final CoordinatorLayout coordinatorLayout, final CardView footerButton, final TextView footerButtonTV, final ChefMenuItem item, final int quantity){
        Log.d(LOG_TAG, "Adding item: " + item.getTitle() + " to basket. Quantity = " + quantity);
        // Add item to the basket
        addNOfItem(item, quantity);
        // Create snackbar
        String snackbarMessage = String.format(context.getResources().getQuantityString(R.plurals.snackbar_message,quantity), item.getTitle(), quantity);
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, snackbarMessage, Snackbar.LENGTH_LONG)
                .setAction(context.getString(R.string.snackbar_action_message), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Remove the item from the basket.
                        removeNOfItem(item, quantity);
                        // Show confirmation that it's been removed.
                        String itemRemovedSnackbarMessage = String.format(context.getString(R.string.snackbar_item_removed), item.getTitle());
                        Snackbar itemRemovedSnackbar = Snackbar.make(coordinatorLayout,itemRemovedSnackbarMessage,Snackbar.LENGTH_SHORT) ;
                        itemRemovedSnackbar.show();
                        // Update footer button
                        updateFooterButton(footerButton, footerButtonTV);
                    }
                });
        snackbar.setActionTextColor(context.getResources().getColor(R.color.colorAccent));

        return snackbar ;
    }

    public void updateFooterButton(CardView basketFooterButton, TextView textView) {
        Log.d(LOG_TAG,"updateFooterButton called from Basket class");
        // Hide the footer button if the basket is empty, otherwise force visible and refresh value
        if (this.isEmpty()) {
            basketFooterButton.setVisibility(View.GONE);
        } else {
            basketFooterButton.setVisibility(View.VISIBLE);
            textView.setText("£ " + this.getSubtotalPrice());
        }
    }

    // --------------------------------- Parcelable Stuff ------------------------------------------

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(orders);
    }

    public static final Parcelable.Creator<Basket> CREATOR
            = new Parcelable.Creator<Basket>() {
        public Basket createFromParcel(Parcel in) {
            return new Basket(in);
        }

        public Basket[] newArray(int size) {
            return new Basket[size];
        }
    };

    private Basket(Parcel in) {
        orders = in.readHashMap(ChefMenuItem.class.getClassLoader());
    }
}
