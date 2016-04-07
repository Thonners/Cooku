package com.thonners.kooku;

import java.util.ArrayList;

/**
 * Class to hold a chef's menu.
 *
 * @author M Thomas
 * @since 24/03/16
 */

public class ChefMenu {

    private final ArrayList<ChefMenuItem> menuItems = new ArrayList<>();

    /**
     * Method to add a new item to the menu.
     * @param title Name of the dish
     * @param description   Description of the dish
     * @param price Price of the dish
     * @param ingredients   List of ingredients in the dish.
     */
    public void addMenuItem(String title, String description, double price, ArrayList<String> ingredients) {
        ChefMenuItem newItem = new ChefMenuItem(title, description, price, ingredients);
        menuItems.add(newItem);
    }

    /**
     * Class to hold individual menu items and their relevant details.
     */
    public static class ChefMenuItem {

        String title ;
        String description ;
        double price ;
        ArrayList<String> ingredients = new ArrayList<>() ;

        /**
         * Menu item constructor.
         * @param title Name of the dish
         * @param description   Description of the dish
         * @param price Price of the dish
         * @param ingredients   List of ingredients in the dish.
         */
        public ChefMenuItem(String title, String description, double price, ArrayList<String> ingredients) {
            this.title = title;
            this.description = description;
            this.price = price ;
            this.ingredients = ingredients;
        }

        // Getter methods
        public String getTitle() {
            return title;
        }
        public String getDescription() {
            return description;
        }
        public double getPrice() {
            return price;
        }
        public ArrayList<String> getIngredients() {
            return ingredients ;
        }
    }
}
