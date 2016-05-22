package com.thonners.kooku;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to hold a chef's menu.
 *
 * @author M Thomas
 * @since 24/03/16
 */

public class ChefMenu {

    private int chefID ;

    private final HashMap<Integer, ChefMenuItem> menuItems = new HashMap<>();

    private String chefBioShort ;
    private String chefBioLong ;

    /**
     * Constructor.
     */
    public ChefMenu(int chefID) {
        this.chefID = chefID ;
    }

    /**
     * Method to add a new item to the menu.
     * @param title Name of the dish
     * @param description   Description of the dish
     * @param price Price of the dish
     * @param ingredients   List of ingredients in the dish.
     */
    public void addMenuItem(int itemID, String title, String subtitle, String description, double price, ArrayList<String> ingredients, boolean isVegetarian, boolean isVegan, boolean isOrganic, boolean containsNuts, boolean containsAlcohol, boolean containsLactose) {
        ChefMenuItem newItem = new ChefMenuItem(itemID, title, subtitle, description, price, ingredients, isVegetarian, isVegan, isOrganic, containsNuts, containsAlcohol, containsLactose);
        menuItems.put(itemID, newItem);
    }

    public void addMenuItem(int itemID, ChefMenuItem item) {
        menuItems.put(itemID, item);
    }

    public void addChefBios(String shortBio, String longBio) {
        this.chefBioShort = shortBio ;
        this.chefBioLong = longBio ;
    }

    /**
     * Returns an array list of the menu items.
     * @return ArrayList of menu items
     */
    public ArrayList<ChefMenuItem> getMenuItems() {
        return new ArrayList<>(menuItems.values()) ;
    }

    /**
     * Method to return an item instance corresponding to the item's ID number
     * @param itemID Item ID number
     * @return The ChefMenuItem instance.
     */
    public ChefMenuItem getMenuItem(int itemID) {
        if (menuItems.containsKey(itemID)){
            return menuItems.get(itemID);
        } else {
            return null;
        }
    }

    /**
     * Method to return the chef's short (single line) bio
     * @return Chef's short bio
     */
    public String getChefBioShort() {
        return chefBioShort ;
    }
    /**
     * Method to return the chef's long (multi-line) bio
     * @return Chef's extended bio
     */
    public String getChefBioLong() {
        return chefBioLong ;
    }

}
