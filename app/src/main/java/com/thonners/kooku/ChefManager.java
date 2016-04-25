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
            Toast.makeText(context, "An error occurred.", Toast.LENGTH_SHORT).show();
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
        Chef chef1 = new Chef(1, "Pablo", "Danish Pastries", 3, 45, context.getResources().getDrawable(R.drawable.demo_chef_danish), getDemoMenu(1));
        Chef chef2 = new Chef(2, "MC Thomma$", "Welsh Rarebit", 4, 25, context.getResources().getDrawable(R.drawable.demo_chef_rarebit),getDemoMenu(2));
        Chef chef3 = new Chef(3, "Mary Berry", "Cake", 5, 60, context.getResources().getDrawable(R.drawable.demo_chef_cake_1), getDemoMenu(3));
        Chef chef4 = new Chef(4, "Marjory Dawes", "CAKE", 1, 20, context.getResources().getDrawable(R.drawable.demo_chef_cake_2), getDemoMenu(4));
        Chef chef5 = new Chef(5, "Betty", "Cupcakes", 3, 20, context.getResources().getDrawable(R.drawable.demo_cupcakes_1), getDemoMenu(5));
        Chef chef6 = new Chef(6, "Jeff", "Cupcakes", 5, 60, context.getResources().getDrawable(R.drawable.demo_cupcakes_2), getDemoMenu(6));
        Chef chef7 = new Chef(7, "Dodgy McDodgerson", "Cupcakes", 5, 20, context.getResources().getDrawable(R.drawable.demo_cupcakes_3), getDemoMenu(7));
        // Add to array list
        chefs.put(chef1.getChefID(), chef1) ;
        chefs.put(chef2.getChefID(), chef2);
        chefs.put(chef3.getChefID(),chef3);
        chefs.put(chef4.getChefID(), chef4);
        chefs.put(chef5.getChefID(), chef5);
        chefs.put(chef6.getChefID(),chef6);
        chefs.put(chef7.getChefID(), chef7);
    }

    /**
     * Method to populate a demo menu for the demo chefs
     * @param chefNo Chef ID number
     * @return The ChefMenu instance
     */
    private ChefMenu getDemoMenu(int chefNo) {
        ChefMenu menu = new ChefMenu() ;
        String bioShort , bioLong ;
        switch (chefNo) {
            case 1:
                menu.addMenuItem(1, "Pete's Danish Pastries", "Mixed pastries", "Delectable selection of danish pastries. Hand-made with a secret family recipe.", 3.5, new ArrayList<String>() {{add("Pastry"); add("Sugar"); }},true,false,false,false,false,false);
                menu.addMenuItem(2, "Pete's Croissants", "12 Fresh Croissants", "Crispy, flaky and soft inside, these freshly made croissants are the perfect vehicle for Pete's homemade Jam.\nAdd a healthy dollop of butter to really get the best out of them.", 5.0, new ArrayList<String>() {{add("Pastry"); add("Butter"); }},true,false,false,false,false,false);
                bioShort = "Pastries are my passion. I am a professional chef baking my favourite foods in my spare time with sumptuous ingredients." ;
                bioLong = "I started cooking when I was 3 months old and made my first pastry before I said my first word. Baking has been in my family for two centuries, so in many ways, I feel like I belong when I bake.\n\nMy particular twist is a secret - but it lies somewhere in the crispy subtlety of my cinnamon bun.\n\nIngredients are key. I always buy organic, high quality ingredients and you'll taste this right through to the smallest crumbs of my baking.\n\nWhen I'm not rustling up food on Kooku, I am baking for friends and family or [behind my desk at a management consultancy]/[prepping food at a Soho restaurant].";
                menu.addChefBios(bioShort, bioLong);
                break;
            case 2:
                menu.addMenuItem(1, "Welsh Rarebit", "Cheese on toast with Mustard", "", 3.5, new ArrayList<String>() {{add("Pastry"); add("Sugar"); }},true,false,false,false,false,true);
                menu.addMenuItem(2, "Spag Bol", "", "", 5.0, new ArrayList<String>() {{add("Pasta"); add("Beef"); add("Tomato"); add("Oregano"); }},true,false,false,false,true,false);
                menu.addMenuItem(3, "Bangers and mash", "Organic sausages with creamy, cheesy mash", "", 7.5, new ArrayList<String>() {{add("Sausage"); add("Potato"); add("Milk"); }},true,false,false,false,false,true);
                menu.addMenuItem(4, "Brownies", "", "", 6.0, new ArrayList<String>() {{add("Chocolate"); add("Butter"); add("Flour"); }},true,false,false,true,false,false);
                bioShort = "Lovingly prepared in a brand new kitchen.";
                bioLong = "MC Thomma$ first started cooking properly after leaving university. Since then, he's always to be found in the kitchen trying a new combination of chili powder and paprika. You might have thought that there would only be a couple of ways to combine these ingredients, but MC Thomma$ regularly proves this wrong!";
                menu.addChefBios(bioShort, bioLong);
                break;
            case 3:
                menu.addMenuItem(1, "Victoria Sponge Cake", "Springy", "", 7.5, new ArrayList<String>() {{add("Flour"); add("Sugar"); }},true,false,false,false,false,true);
                menu.addMenuItem(2, "Coffee and Walnut Cake", "Perfect for afternoon tea", "", 8.0, new ArrayList<String>() {{add("Flour"); add("Butter"); add("Coffee"); add("Walnut"); }},true,false,false,false,false,false);
                menu.addMenuItem(3, "Lemon Drizzle Cake", "Bake-off Classic", "", 12.0, new ArrayList<String>() {{add("Flour"); add("Butter"); add("Lemon"); add("Drizzle"); }},true,false,false,false,false,false);
                bioShort = "Bake Off Queen";
                bioLong = "Older than the sun, and better at cooking than you, Mary Berry is a national treasure. You can be sure that the base of your cake will not be soggy!";
                menu.addChefBios(bioShort, bioLong);
                break;
            case 4:
                menu.addMenuItem(1, "CAKE", "Full fat", "", 10, new ArrayList<String>() {{add("Flour"); add("Sugar"); }},true,false,false,false,false,false);
                menu.addMenuItem(2, "Fat-fighters cake", "Half the calories (when cut in half)", "", 20.0, new ArrayList<String>() {{add("Flour"); add("Sweetener"); }},true,false,false,false,false,false);
                bioShort = "Fat-fighters inspirational leader";
                bioLong = "When not helping fellow fatties to get thin, Marjory is almost certainly to be found in the cake store, eating. She hasn't much flair in the kitchen, but does love a bit of cake.";
                menu.addChefBios(bioShort, bioLong);
                break;
            case 5:
                menu.addMenuItem(1, "Assorted Cupcakes", "Multicoloured cupcake extravaganza", "Selection of 12 sumptuous cupcakes, using a recipe perfected after catering for 6 children's birthdays across 20 years.", 12.5, new ArrayList<String>() {{add("Flour"); add("Eggs");add("Sugar");add("Butter");add("Milk");add("Food Colouring"); }},true,true,false,false,false,true);
                menu.addMenuItem(2, "One Big Cupcake", "Ideal for birthdays", "One full size vanilla cake, decorated with swirls of icing in your favourite colours and flavours. Topped with sprinkles.", 12.5, new ArrayList<String>() {{add("Flour"); add("Eggs");add("Sugar");add("Butter");add("Milk");add("Food Colouring"); }},true,true,false,false,false,true);
                menu.addMenuItem(3, "Vanilla Cupcakes", "For those who know what they like", "12 perfectly made cupcakes, using a recipe perfected after catering for 6 children's birthdays across 14 years. All decorated in white. Perfect for a wedding.", 12.5, new ArrayList<String>() {{add("Flour"); add("Eggs");add("Sugar");add("Butter");add("Milk");add("Food Colouring"); }},true,true,true,false,false,true);
                menu.addMenuItem(4, "Royal Wedding Cupcakes", "Relive the day", "12 cupcakes, decorated in red, white and blue, with icing mosaic pictures of the happy couple.", 12.5, new ArrayList<String>() {{add("Flour"); add("Eggs");add("Sugar");add("Butter");add("Milk");add("Food Colouring"); }},true,true,false,false,false,true);
                bioShort = "Cupcake Competition Champion '12-'15.";
                bioLong = "I started cooking cupcakes for my eldest's birthday parties, as it was much easier than trying to cut up cake evenly! Using a recipe that I've honed over the last 2 decades, with the fussiest of quality control (my children!), I've been able to nail down exactly what it is that puts the cup in cupcake. Every cupcake is hand-decorated with freshly prepared icing sugar, coloured according to IEEE RGB colour-code standards.";
                menu.addChefBios(bioShort, bioLong);
                break;
            case 6:
                menu.addMenuItem(1, "Carrot Cupcakes", "Orange Greatness", "8 Carrot Cupcakes. The vegetable cupcake maker's staple. Start here if vegetable cupcakes are a new phenomenon for you. The carrot flavours sing through the icing and the fresh, fluffy sponge, to give you a treat that's also healthy!", 10, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Carrot"); add("Butter"); add("Milk");  }},true,true,true,false,false,false);
                menu.addMenuItem(2, "Chickpea and Wasabi Cupcakes", "Fusion Cupcakes", "8 Chickpea and Wasabi Cupcakes. Inspired by tastes from Japan, these eastern flavours are westernised in the most American way possible. Cupcakes. A surprisingly sharp flavour for those not expecting it, but once you Chickpea, then you'll see.", 20.0, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Chickpea"); add("Wasabi"); add("Butter"); add("Milk");  }},true,true,true,false,false,false);
                menu.addMenuItem(3, "Ginger and Beetroot Cupcakes", "Aphrodisiac Cupcakes", "8 Ginger and Beetroot Cupcakes, which legend has it, have aphrodisiac powers. Share a ginger and beetroot cupcake or two with your significant other, and see where the magic takes you.", 10, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Ginger"); add("Beetroot"); add("Butter"); add("Milk");  }},true,true,true,false,false,false);
                menu.addMenuItem(4, "Root Vegetable Cupcakes", "Lovely after a Sunday roast", "8 Root Vegetable Cupcakes, featuring Turnips, Carrots, Parsnip and Sweet Potato, sumptuously blended in an organic treat.", 20.0, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Roots"); add("Butter"); add("Milk"); }},true,true,true,false,false,false);
                menu.addMenuItem(5, "Seasonal Veg Cupcakes", "Perfect all year round", "8 Assorted Vegetable Cupcakes, made using the freshest of seasonal vegetables. Supplied from my garden and the local farmer, Tim.", 20.0, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Various Seasonal Veg"); add("Butter"); add("Milk");}},true,true,true,false,false,false);
                menu.addMenuItem(6, "Home-Grown Cupcakes", "Even the flour is grown at home", "16 Assorted Vegetable Cupcakes, made using whatever is freshly available from my garden at the time of order. Usually some combination of the above, with some extra niche options too. A veritable smorgasbord of cupcake delight awaits...", 20.0, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Home Grown Veg"); add("Butter"); add("Milk from Nessie");}},true,true,true,false,false,false);
                bioShort = "Organic, vegetable-based Cupcakes are my speciality.";
                bioLong = "Using nothing but the finest, organic ingredients, including free-range eggs from my own hens, and milk from Nessie the cow. My cupcakes have evolved over the years to the culinary masterpieces that they are today. The batter which constitutes the sponge is inseparably intertwined with the base vegetable for the cupcake variety. The process takes 12 hours to complete, starting with the shredded vegetable being soaked in the milk and eggs overnight. Once the hens are a-crowing again in the morning, the rest of the baking procedure takes place. It is the time that the vegetable spends becoming acquainted with its milk that lends to the undeniably organic flavour of my cupcakes. The pièce de résistance of my cupcakes has to be the topping. Each naturally coloured and flavoured icing sculpture is unique, and based on the cloud shapes appearing exactly at the time of its conception.";
                menu.addChefBios(bioShort, bioLong);
                break;
            case 7:
                menu.addMenuItem(1, "Cup MDMAkes", "Party Cakes", "12 Cupcakes laced with your favourite amphetamine, this batch is guaranteed to send you, and all your mates, on a trip you won't forget. The blend of hallucinogens with delicate icing is more potent than NASA's most inflammable rocket fuel.", 10, new ArrayList<String>() {{add("Mandy"); add("Flour"); add("Sugar");add("Eggs"); add("Magic");  }},false,false,false,false,true,false);
                bioShort = "One trick pony - doesn't matter when it's the best trick in the world.";
                bioLong = "My cupcakes are infused with a little magic. Illegal in 12 states, and guaranteed to take you higher than the moon, these cup-MDMAkes are the bomb. You will be back for more - to show how confident I am, I'll even send you a free batch if you're not.";
                menu.addChefBios(bioShort, bioLong);
                break;
        }
        return menu ;
    }

}
