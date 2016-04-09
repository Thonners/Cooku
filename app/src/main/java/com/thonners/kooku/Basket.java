package com.thonners.kooku;

import android.util.Log;

import java.util.HashMap;

/**
*	Class to store purchase orders in the Basket.
*
*	@author M Thomas
*	@since 06/04/2016
*/


public class Basket {

    private final String LOG_TAG = "Basket" ;

	private double totalPrice = 0.0 ;
	private HashMap<ChefMenu.ChefMenuItem, Integer> orders = new HashMap<>() ;

	/**
	*    Constructor
	*/
	public Basket() {
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

    private void updateTotalPrice() {
        double price = 0.0 ;
        for (ChefMenu.ChefMenuItem item : orders.keySet()) {
            double itemPrice = item.getPrice() * orders.get(item) ;
            price += itemPrice ;
        }

        totalPrice = price ;
    }

	/**
	*    Method to calculate the total value of the basket
	*    @return totalPrice The total value of the basket.
	*/
	public double getTotalPrice() {
		return totalPrice ;
	}

}
