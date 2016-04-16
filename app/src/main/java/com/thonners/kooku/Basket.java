package com.thonners.kooku;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

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
    public static final double DELIVERY_CHARGE = 2.5 ;
    public static final double SURCHARGE_VALUE = 2.5 ;


    private double totalPrice = 0.0 ;
	private HashMap<ChefMenu.ChefMenuItem, Integer> orders = new HashMap<>() ;
    private ArrayList<ChefMenu.ChefMenuItem> menuItems ;

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
        for (ChefMenu.ChefMenuItem item : orders.keySet()) {
            menuItems.add(item) ;
        }
    }
    public ArrayList<ChefMenu.ChefMenuItem> getMenuItems() {
        populateMenuItems();
        return menuItems ;
    }

    /**
     * Method to increment item count by 1
     * @param item The item to be added to the basket.
     */
	public void addItem(ChefMenu.ChefMenuItem item) {
        int quantity = 0 ;
        // Get old quantity if it exists
        if (orders.containsKey(item)) quantity = orders.get(item) ;
        // Increment quantity
        quantity++ ;

        // Amend order
        addItemWithQuantity(item, quantity);
    }

    public void removeItem(ChefMenu.ChefMenuItem item) {
        // Check that the order exists.
        if (!orders.containsKey(item)) return ;

        // Get the current quantity
        int quantity = orders.get(item) ;
        // Decrement by 1
        quantity-- ;
        // Amend order
        addItemWithQuantity(item, quantity);
    }

	/**
	*    Method to add an item to the basket
	*    @param newItem The new menu item to be added to the basket.
	*    @param quantity The number of orders of the menu item to be added to the basket.
	*/
	public void addItemWithQuantity(ChefMenu.ChefMenuItem newItem, int quantity) {
		if (orders.containsKey(newItem)) {
			// If the item is already there, flag a warning - not sure how this will be implemented in practice.
			Log.d(LOG_TAG, "Amending quantity of " + newItem.getTitle() + " to: " + quantity) ;
		} else {
			// Make a note of what's being added
			Log.d(LOG_TAG,"Adding " + quantity + " " + newItem.getTitle()) ;
		}
		// Actually add/change the value in the HashMap
		orders.put(newItem, quantity) ;

		// Update the price
		double newItemCost = newItem.getPrice() * quantity ;
 		Log.d(LOG_TAG, "New item's cost = " + newItemCost + ". This is for " + quantity + " dishes.") ;
		updateTotalPrice();
		Log.d(LOG_TAG,"Total cost = " + totalPrice) ;
	}

	/**
	*    Method to calculate and update the total value of the basket.
	*/
	private void updateTotalPrice() {
        double price = 0.0 ;
        for (ChefMenu.ChefMenuItem item : orders.keySet()) {
            double itemPrice = item.getPrice() * orders.get(item) ;
            price += itemPrice ;
        }

        totalPrice = price ;
    }

	/**
	*    Method to return the total value of the basket
	*    @return totalPrice The total value of the basket.
	*/
	public double getSubtotalPrice() {
        updateTotalPrice();
		return totalPrice ;
	}

    /**
     * Method to return the HashMap of the orders.
     * As ChefMenuItems are parcelable, this can be passed over an intent.
     * @return HashMap of basket's orders, with the quantity (value) of each ChefMenuItem (key).
     */
    public HashMap<ChefMenu.ChefMenuItem, Integer> getOrders() {
        return orders ;
    }


    /**
     * Method to return whether the basket is empty or not.
     * Used, for example, in determining whether to show the basket footer button.
     * @return Whether basket total is zero.
     */
	public boolean isEmpty() {
		return totalPrice == 0.0 ;
	}



        // Parcelable Stuff

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
            orders = in.readHashMap(ChefMenu.ChefMenuItem.class.getClassLoader());
        }
}
