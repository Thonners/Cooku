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
        Chef chef1 = new Chef(1, "Pablo", "Danish Pastries", getDemoBios(1), 3, 45, context.getResources().getDrawable(R.drawable.demo_chef_danish), getDemoMenu(1));
        Chef chef2 = new Chef(2, "MC Thomma$", "Welsh Rarebit", getDemoBios(2), 4, 25, context.getResources().getDrawable(R.drawable.demo_chef_rarebit),getDemoMenu(2));
        Chef chef3 = new Chef(3, "Mary Berry", "Cake", getDemoBios(3), 5, 60, context.getResources().getDrawable(R.drawable.demo_chef_cake_1), getDemoMenu(3));
        Chef chef4 = new Chef(4, "Marjory Dawes", "CAKE", getDemoBios(4), 1, 20, context.getResources().getDrawable(R.drawable.demo_chef_cake_2), getDemoMenu(4));
        // Add to array list
        chefs.put(chef1.getChefID(), chef1) ;
        chefs.put(chef2.getChefID(), chef2);
        chefs.put(chef3.getChefID(),chef3);
        chefs.put(chef4.getChefID(), chef4);
    }

    /**
     * Method to populate a demo menu for the demo chefs
     * @param chefNo Chef ID number
     * @return The ChefMenu instance
     */
    private ChefMenu getDemoMenu(int chefNo) {
        ChefMenu menu = new ChefMenu() ;
        switch (chefNo) {
            case 1:
                menu.addMenuItem(1, "Pete's Danish Pastries", "Mixed pastries", "", 3.5, new ArrayList<String>() {{add("Pastry"); add("Sugar"); }});
                menu.addMenuItem(2, "Pete's Croissants", "12 Fresh Croissants", "", 5.0, new ArrayList<String>() {{add("Pastry"); add("Butter"); }});
                break;
            case 2:
                menu.addMenuItem(1, "Welsh Rarebit", "Cheese on toast with Mustard", "", 3.5, new ArrayList<String>() {{add("Pastry"); add("Sugar"); }});
                menu.addMenuItem(2, "Spag Bol", "", "", 5.0, new ArrayList<String>() {{add("Pasta"); add("Beef"); add("Tomato"); add("Oregano"); }});
                menu.addMenuItem(3, "Bangers and mash", "Organic sausages with creamy, cheesy mash", "", 7.5, new ArrayList<String>() {{add("Sausage"); add("Potato"); add("Milk"); }});
                menu.addMenuItem(4, "Brownies", "", "", 6.0, new ArrayList<String>() {{add("Chocolate"); add("Butter"); add("Flour"); }});
                break;
            case 3:
                menu.addMenuItem(1, "Victoria Sponge Cake", "Springy", "", 7.5, new ArrayList<String>() {{add("Flour"); add("Sugar"); }});
                menu.addMenuItem(2, "Coffee and Walnut Cake", "Perfect for afternoon tea", "", 8.0, new ArrayList<String>() {{add("Flour"); add("Butter"); add("Coffee"); add("Walnut"); }});
                menu.addMenuItem(3, "Lemon Drizzle Cake", "Bake-off Classic", "", 12.0, new ArrayList<String>() {{add("Flour"); add("Butter"); add("Lemon"); add("Drizzle"); }});
                break;
            case 4:
                menu.addMenuItem(1, "CAKE", "Full fat", "", 10, new ArrayList<String>() {{add("Flour"); add("Sugar"); }});
                menu.addMenuItem(2, "Fat-fighters cake", "Half the calories (when cut in half)", "", 20.0, new ArrayList<String>() {{add("Flour"); add("Sweetener"); }});
                break;

        }

        return menu ;
    }

    private String[] getDemoBios(int chefNo) {
        String bioShort = "", bioLong = "";
        switch (chefNo) {
            case 1:
                bioShort = "Traditional Danish recipes passed down from mother to son.";
                bioLong = "Pete's been cooking pasty based treats for as long as he can remember. His friends always enjoy sampling the latest wares from his kitchen, and this part of the sentance is just to make the bio longer.";
                break;
            case 2:
                bioShort = "Lovingly prepared in a brand new kitchen.";
                bioLong = "MC Thomma$ first started cooking properly after leaving university. Since then, he's always to be found in the kitchen trying a new combination of chili powder and paprika. You might have thought that there would only be a couple of ways to combine these ingredients, but MC Thomma$ regularly proves this wrong!";
                break;
            case 3:
                bioShort = "Bake Off Queen";
                bioLong = "Older than the sun, and better at cooking than you, Mary Berry is a national treasure. You can be sure that the base of your cake will not be soggy!";
                break;
            case 4:
                bioShort = "Fat-fighters inspirational leader";
                bioLong = "When not helping fellow fatties to get thin, Marjory is almost certainly to be found in the cake store, eating. She hasn't much flair in the kitchen, but does love a bit of cake.";
                break;
        }

        String[] bios = {bioShort, bioLong} ;
        return bios ;
    }
}
