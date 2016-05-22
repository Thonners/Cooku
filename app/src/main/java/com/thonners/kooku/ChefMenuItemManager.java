package com.thonners.kooku;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to manage and obtain ChefMenuItems.
 *
 * Ultimately this class will manage the communications with Kooku servers
 *
 * @author M Thomas
 * @since 22/05/16
 */

public class ChefMenuItemManager {

    private HashMap<Integer, ChefMenuItem> items = new HashMap<>();

    public ChefMenuItemManager() {
        generateDemoMenuItems();
    }

    private void generateDemoMenuItems() {
        items.put(101,new ChefMenuItem(101,"Pete's Danish Pastries", "Mixed pastries", "Delectable selection of danish pastries. Hand-made with a secret family recipe.", 3.5, new ArrayList<String>() {{add("Pastry"); add("Sugar"); }},true,false,false,false,false,false));
        items.put(102,new ChefMenuItem(102,"Pete's Croissants", "12 Fresh Croissants", "Crispy, flaky and soft inside, these freshly made croissants are the perfect vehicle for Pete's homemade Jam.\nAdd a healthy dollop of butter to really get the best out of them.", 5.0, new ArrayList<String>() {{add("Pastry"); add("Butter"); }},true,false,false,false,false,false));
        items.put(201,new ChefMenuItem(201,"Welsh Rarebit", "Cheese on toast with Mustard", "", 3.5, new ArrayList<String>() {{add("Pastry"); add("Sugar"); }},true,false,false,false,false,true));
        items.put(202,new ChefMenuItem(202,"Spag Bol", "", "", 5.0, new ArrayList<String>() {{add("Pasta"); add("Beef"); add("Tomato"); add("Oregano"); }},true,false,false,false,true,false));
        items.put(203,new ChefMenuItem(203,"Bangers and mash", "Organic sausages with creamy, cheesy mash", "", 7.5, new ArrayList<String>() {{add("Sausage"); add("Potato"); add("Milk"); }},true,false,false,false,false,true));
        items.put(204,new ChefMenuItem(204,"Brownies", "", "", 6.0, new ArrayList<String>() {{add("Chocolate"); add("Butter"); add("Flour"); }},true,false,false,true,false,false));
        items.put(301,new ChefMenuItem(301,"Victoria Sponge Cake", "Springy", "", 7.5, new ArrayList<String>() {{add("Flour"); add("Sugar"); }},true,false,false,false,false,true));
        items.put(302,new ChefMenuItem(302,"Coffee and Walnut Cake", "Perfect for afternoon tea", "", 8.0, new ArrayList<String>() {{add("Flour"); add("Butter"); add("Coffee"); add("Walnut"); }},true,false,false,false,false,false));
        items.put(303,new ChefMenuItem(303,"Lemon Drizzle Cake", "Bake-off Classic", "", 12.0, new ArrayList<String>() {{add("Flour"); add("Butter"); add("Lemon"); add("Drizzle"); }},true,false,false,false,false,false));
        items.put(401,new ChefMenuItem(401,"CAKE", "Full fat", "", 10, new ArrayList<String>() {{add("Flour"); add("Sugar"); }},true,false,false,false,false,false));
        items.put(402,new ChefMenuItem(402,"Fat-fighters cake", "Half the calories (when cut in half)", "", 20.0, new ArrayList<String>() {{add("Flour"); add("Sweetener"); }},true,false,false,false,false,false));
        items.put(501,new ChefMenuItem(501,"Assorted Cupcakes", "Multicoloured cupcake extravaganza", "Selection of 12 sumptuous cupcakes, using a recipe perfected after catering for 6 children's birthdays across 20 years.", 12.5, new ArrayList<String>() {{add("Flour"); add("Eggs");add("Sugar");add("Butter");add("Milk");add("Food Colouring"); }},true,true,false,false,false,true));
        items.put(502,new ChefMenuItem(502,"One Big Cupcake", "Ideal for birthdays", "One full size vanilla cake, decorated with swirls of icing in your favourite colours and flavours. Topped with sprinkles.", 12.5, new ArrayList<String>() {{add("Flour"); add("Eggs");add("Sugar");add("Butter");add("Milk");add("Food Colouring"); }},true,true,false,false,false,true));
        items.put(503,new ChefMenuItem(503,"Vanilla Cupcakes", "For those who know what they like", "12 perfectly made cupcakes, using a recipe perfected after catering for 6 children's birthdays across 14 years. All decorated in white. Perfect for a wedding.", 12.5, new ArrayList<String>() {{add("Flour"); add("Eggs");add("Sugar");add("Butter");add("Milk");add("Food Colouring"); }},true,true,true,false,false,true));
        items.put(504,new ChefMenuItem(504,"Royal Wedding Cupcakes", "Relive the day", "12 cupcakes, decorated in red, white and blue, with icing mosaic pictures of the happy couple.", 12.5, new ArrayList<String>() {{add("Flour"); add("Eggs");add("Sugar");add("Butter");add("Milk");add("Food Colouring"); }},true,true,false,false,false,true));
        items.put(601,new ChefMenuItem(601,"Carrot Cupcakes", "Orange Greatness", "8 Carrot Cupcakes. The vegetable cupcake maker's staple. Start here if vegetable cupcakes are a new phenomenon for you. The carrot flavours sing through the icing and the fresh, fluffy sponge, to give you a treat that's also healthy!", 10, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Carrot"); add("Butter"); add("Milk");  }},true,true,true,false,false,false));
        items.put(602,new ChefMenuItem(602,"Chickpea and Wasabi Cupcakes", "Fusion Cupcakes", "8 Chickpea and Wasabi Cupcakes. Inspired by tastes from Japan, these eastern flavours are westernised in the most American way possible. Cupcakes. A surprisingly sharp flavour for those not expecting it, but once you Chickpea, then you'll see.", 20.0, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Chickpea"); add("Wasabi"); add("Butter"); add("Milk");  }},true,true,true,false,false,false));
        items.put(603,new ChefMenuItem(603,"Ginger and Beetroot Cupcakes", "Aphrodisiac Cupcakes", "8 Ginger and Beetroot Cupcakes, which legend has it, have aphrodisiac powers. Share a ginger and beetroot cupcake or two with your significant other, and see where the magic takes you.", 10, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Ginger"); add("Beetroot"); add("Butter"); add("Milk");  }},true,true,true,false,false,false));
        items.put(604,new ChefMenuItem(604,"Root Vegetable Cupcakes", "Lovely after a Sunday roast", "8 Root Vegetable Cupcakes, featuring Turnips, Carrots, Parsnip and Sweet Potato, sumptuously blended in an organic treat.", 20.0, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Roots"); add("Butter"); add("Milk"); }},true,true,true,false,false,false));
        items.put(605,new ChefMenuItem(605,"Seasonal Veg Cupcakes", "Perfect all year round", "8 Assorted Vegetable Cupcakes, made using the freshest of seasonal vegetables. Supplied from my garden and the local farmer, Tim.", 20.0, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Various Seasonal Veg"); add("Butter"); add("Milk");}},true,true,true,false,false,false));
        items.put(606,new ChefMenuItem(606,"Home-Grown Cupcakes", "Even the flour is grown at home", "16 Assorted Vegetable Cupcakes, made using whatever is freshly available from my garden at the time of order. Usually some combination of the above, with some extra niche options too. A veritable smorgasbord of cupcake delight awaits...", 20.0, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Home Grown Veg"); add("Butter"); add("Milk from Nessie");}},true,true,true,false,false,false));
        items.put(701,new ChefMenuItem(701,"Cup MDMAkes", "Party Cakes", "12 Cupcakes laced with your favourite amphetamine, this batch is guaranteed to send you, and all your mates, on a trip you won't forget. The blend of hallucinogens with delicate icing is more potent than NASA's most inflammable rocket fuel.", 10, new ArrayList<String>() {{add("Mandy"); add("Flour"); add("Sugar");add("Eggs"); add("Magic");  }},false,false,false,false,true,false));
        items.put(801,new ChefMenuItem(801,"Carrot cake (with pecan)", "My famous carrot cake.", "The exact recipe is a family secret. If you don't like pecans, I can leave them out.", 15, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Carrot"); add("Pecan");}},true,true,true,false,false,false));
        items.put(802,new ChefMenuItem(802,"Brownie", "Salted Caramel", "I take an indulgent, fudgy chocolate brownie to the next level with a layer of salted caramel running through each bite.", 4.5, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Chocolate"); add("Caramel"); add("Salt");  }},true,true,true,false,false,false));
        items.put(803,new ChefMenuItem(803,"Chocolate tart", "Hazelnut & salted caramel", "A buttery pastry with a subtle layer of caramel and just a hint of salt and studded with nuts. Enjoy the fudge filling!", 11, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Chocolate"); add("Butter");}},true,true,true,false,false,false));
        items.put(901,new ChefMenuItem(901,"Chocolate soufflé", "Hint of peanut", "My mother's favourite recipe - this light treat will round off a meal perfectly.", 7.6, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Chocolate"); add("Peanut"); add("Butter");  }},true,true,true,false,false,false));
        items.put(902,new ChefMenuItem(902,"Brownie", "Carrot and almond milk", "I know the shredded carrot in this combo may baffle at first glance but it's a tried and tested winner! Hope you think so too.", 4, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Carrot"); add("Almond Milk");}},true,true,true,false,false,false));
        items.put(1001,new ChefMenuItem(1001,"Skinny brownie with cumin", "Healthy Treat", "The subtle flavour of cumin takes the edge of the richness of the Peruvian cacao I use for this majestic treat.", 6, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Chocolate"); add("Cumin"); add("Eggs");  }},true,true,true,false,false,false));
        items.put(1002,new ChefMenuItem(1002,"Chilli cheesecake", "Ricotta", "I stumbled upon this creation in the early hours of a morning and never thought it would work. But it does. Fantastic flavours. Ideal with a lime sorbet.", 9.8, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Chilli"); add("Ricotta"); add("Cream");  }},true,true,true,false,false,false));
        items.put(1003,new ChefMenuItem(1003,"Coconut shortbread", "Long in flavour", "Made with organic butter and fresh coconut, this shortbread is decadence and simplicity combined.", 5, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Coconut"); add("Bread");  }},true,true,true,false,false,false));
        items.put(1101,new ChefMenuItem(1101,"Birthday Bash Cake", "Birthday Sponge cake", "A delicious, light vanilla sponge birthday cake recipe. Quick and simple to make and perfect for decorating to make a birthday really special.", 12, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Margarine"); add("Eggs"); add("Vanilla");  }},true,true,true,false,false,false));
        items.put(1201,new ChefMenuItem(1201,"Confetti Birthday Cake", "Modular Cakes", "Cake that it can be baked, cooled, crumbled and mixed with frosting to make cake pops for kids' parties - which are definitely easier to distribute to smaller children!", 10, new ArrayList<String>() {{add("Flour"); add("Sugar");add(""); add(""); add("");  }},true,true,true,false,false,false));
        items.put(1301,new ChefMenuItem(1301,"Carrot Cake", "Simple carrot cake. The old-fashioned way.", "", 10, new ArrayList<String>() {{add("Flour"); add("Sugar");add("Ginger"); add("Nutmeg"); add("Cinnamon");  }},true,true,true,false,false,false));
    }


    public ChefMenuItem getChefMenuItem(int itemID) {
        return items.get(itemID) ;
    }


}
