package com.thonners.kooku;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to hold a chef's menu.
 *
 * @author M Thomas
 * @since 24/03/16
 */

public class ChefMenu {

    private final HashMap<Integer, ChefMenuItem> menuItems = new HashMap<>();

    private String chefBioShort ;
    private String chefBioLong ;

    /**
     * Constructor.
     */
    public ChefMenu() {
    }

    /**
     * Method to add a new item to the menu.
     * @param title Name of the dish
     * @param description   Description of the dish
     * @param price Price of the dish
     * @param ingredients   List of ingredients in the dish.
     */
    public void addMenuItem(int itemID, String title, String subtitle, String description, double price, ArrayList<String> ingredients) {
        ChefMenuItem newItem = new ChefMenuItem(itemID, title, subtitle, description, price, ingredients);
        menuItems.put(itemID, newItem);
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

    /**
     * Class to hold individual menu items and their relevant details.
     */
    public static class ChefMenuItem implements Parcelable {

        private int itemID;
        private String title;
        private String subtitle;
        private String description;
        private double price;
        private ArrayList<String> ingredients = new ArrayList<>();

        /**
         * Menu item constructor.
         *
         * @param title       Name of the dish
         * @param subtitle    Accompaniments
         * @param description Description of the dish
         * @param price       Price of the dish
         * @param ingredients List of ingredients in the dish.
         */
        public ChefMenuItem(int itemID, String title, String subtitle, String description, double price, ArrayList<String> ingredients) {
            this.itemID = itemID;
            this.title = title;
            this.subtitle = subtitle;
            this.description = description;
            this.price = price;
            this.ingredients = ingredients;
        }

        // Getter methods

        public int getItemID() {
            return itemID;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public String getDescription() {
            return description;
        }

        public double getPrice() {
            return price;
        }

        public ArrayList<String> getIngredients() {
            return ingredients;
        }


        // Parcelable Stuff

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(itemID);
            dest.writeString(title);
            dest.writeString(subtitle);
            dest.writeString(description);
            dest.writeDouble(price);
            dest.writeList(ingredients);
        }

        public static final Parcelable.Creator<ChefMenuItem> CREATOR
                = new Parcelable.Creator<ChefMenuItem>() {
            public ChefMenuItem createFromParcel(Parcel in) {
                return new ChefMenuItem(in);
            }

            public ChefMenuItem[] newArray(int size) {
                return new ChefMenuItem[size];
            }
        };

        private ChefMenuItem(Parcel in) {
            itemID = in.readInt();
            title = in.readString();
            subtitle = in.readString();
            description = in.readString();
            price = in.readDouble() ;
            ingredients = new ArrayList<>() ;
            in.readList(ingredients, String.class.getClassLoader());
        }
    }
}
