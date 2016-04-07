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
	*    Method to add an item to the basket
	*    @param newItem The new menu item to be added to the basket.
	*    @param quantity The number of orders of the menu item to be added to the basket.
	*/
	public void addItem(ChefMenu.ChefMenuItem newItem, int quantity) {
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
		totalPrice += newItemCost ;
		Log.d(LOG_TAG,"Total cost = " + totalPrice) ;
	}

	/**
	*    Method to calculate the total value of the basket
	*    @return totalPrice The total value of the basket.
	*/
	public double getTotalPrice() {
		return totalPrice ;
	}

}
