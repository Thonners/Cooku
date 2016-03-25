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
     * Class to hold individual menu items and their relevant details.
     */
    private static class ChefMenuItem {

        String title ;
        String description ;
        double price ;
        ArrayList<String> ingredients = new ArrayList<>() ;

        public ChefMenuItem() {

        }

    }
}
