package com.thonners.kooku;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to manage creation of new chefs.
 * Probably not actually needed in the app as this will be done by the central database.
 * Used for testing purposes now.
 *
 * @author M Thomas
 * @since 24/03/16.
 */
public class ChefManager {

    private final String LOG_TAG = "ChefManager" ;

    private Context context ;
    private HashMap<Integer, Chef> chefs = new HashMap<>(); // Integer = chefID

    /**
     * Constructor
     * @param context Application context initialising the ChefManager.
     */
    public ChefManager(Context context) {
        this.context = context ;
        populateDemoChefs() ;
    }

    /**
     * Method for adding a new chef to the hashmap of chefs.
     * @param chefID    The singular and unique chefID for the chef. This is unique in the Kooku database.
     * @param chef      The Chef instance of the chef.
     */
    public void addChef(int chefID, Chef chef) {
        if (chefs.containsKey(chefID)) {
            Toast.makeText(context, "An error occurred.", Toast.LENGTH_SHORT);
            Log.e(LOG_TAG, "Error occurred adding chefID: " + chefID + " for chef named: " + chef.getChefName() + ". ChefID already found in chefs HashMap for chef: " + chefs.get(chefID).getChefName());
        } else {
            Log.d(LOG_TAG, "Adding chef with id: " + chefID + ", name: " + chef.getChefName());
            chefs.put(chefID, chef);
        }
    }

    /**
     * Method to return an ArrayList of the Chefs.
     * TODO: Amend the rest of the code in other activities so that this can just return the HashMap of the chefs.
     * @return An ArrayList of the Chef values in the chefs HashMap.
     */
    public ArrayList<Chef> getChefs() {
        return new ArrayList<>(chefs.values()) ;
    }

    /**
     * Method to the return the HashMap of chefs
     * @param hashMap Boolean to indicate whether to return the hashmap. Any boolean, true or false, will cause it to return the hashmap
     * @return  The HashMap of chefs.
     */
    public HashMap<Integer, Chef> getChefs(boolean hashMap) {
        return chefs ;
    }

    /**
     * Method to generate a group of example chefs. In reality, chef instances should be created from the return of an async task which has received the appropriate data from a Kooku server.
     */
    private void populateDemoChefs() {
        // Create some demo chefs
        Chef chef1 = new Chef(1, "Pablo", "Danish Pastries", 3, 45, context.getResources().getDrawable(R.drawable.demo_chef_danish));
        Chef chef2 = new Chef(2, "MC Thomma$", "Welsh Rarebit", 4, 25, context.getResources().getDrawable(R.drawable.demo_chef_rarebit));
        Chef chef3 = new Chef(3, "Mary Berry", "Cake", 5, 60, context.getResources().getDrawable(R.drawable.demo_chef_cake_1));
        Chef chef4 = new Chef(4, "Marjory Dawes", "CAKE", 1, 20, context.getResources().getDrawable(R.drawable.demo_chef_cake_2));
        // Add to array list
        chefs.put(chef1.getChefID(), chef1) ;
        chefs.put(chef2.getChefID(), chef2);
        chefs.put(chef3.getChefID(),chef3);
        chefs.put(chef4.getChefID(), chef4);
    }
}
