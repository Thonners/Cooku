package com.thonners.kooku;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Class to manage the basket:
 * Saving and restoring baskets if closing the app or switching between chefs
 *
 * TODO: Actually implement saving/restoring baskets.
 * ** Not currently in use **
 *
 * @author M Thomas
 * @since 21/05/16.
 */

public class BasketManager {

    private static final String LOG_TAG = "BasketManager" ;

    private Context context ;


    private File basketFile ;
    private String basketFileName = "basket" ;

    /**
     * Constructor.
     */
    public BasketManager(Context context) {
        this.context = context ;
        // Initialise the save file
        basketFile = new File(context.getFilesDir(), basketFileName) ;
    }

    /**
     * Method to create a save file for a basket.
     * Format of the save file will be:
     *  Timestamp
     *  "Item:",ItemID,Quantity
     *  "Item:",ItemID,Quantity
     *  ...
     *  "Item:",ItemID,Quantity
     *  "Item:",ItemID,Quantity
     *
     *  The save file will be located in the internal directory of Kooku, with the name specified
     *  at the head of this class.
     *
     * @param basket The basket to be saved
     */
    public void saveBasket(Basket basket) {
        // Get the date
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMddhhmmss");
        String timestamp = s.format(new Date());

        // Initialise the string
        String saveFileString = timestamp ;

        // Loop through the basket, saving the files
        HashMap<ChefMenuItem, Integer> orders = basket.getOrders() ;
        for (ChefMenuItem item : orders.keySet()) {
            saveFileString += "\n" + item.getItemID() + "," + orders.get(item) + "," + item.getTitle() ;
        }

        Log.d(LOG_TAG, "saveFileString: " + saveFileString) ;

        // Write the file
        try(  PrintWriter out = new PrintWriter(basketFile)  ){
            // Create the file if it doesn't exist already
            if (!basketFile.exists()) {
                basketFile.createNewFile() ;
            }
            // Write the string
            out.println(saveFileString);
        } catch (Exception e) {
            Log.e(LOG_TAG,"Error writing file: " + e.getLocalizedMessage()) ;
            e.printStackTrace();
        }

    }

    /**
     * Method to turn the saved file into an instance of a basket
     * @return A basket instance from the saved file.
     */
    public Basket getSavedBasket() {
        Basket basket = new Basket();
        String date ;
        ChefMenuItemManager manager = new ChefMenuItemManager() ;
        // Get the file, if it exists
        if (basketFile.exists()) {
            Log.d(LOG_TAG,"Retrieving saved basket...");
            try(BufferedReader br = new BufferedReader(new FileReader(basketFile))) {
                for(String line; (line = br.readLine()) != null; ) {
                    String[] entries = line.split(",") ;
                    if (entries.length == 1) {
                        date = entries[0] ;
                    } else if (entries.length == 3) {
                        int itemID = Integer.parseInt(entries[0]) ;
                        int quantity = Integer.parseInt(entries[1]) ;
                        ChefMenuItem item = manager.getChefMenuItem(itemID) ;
                        basket.addNOfItem(item, quantity);
                    }
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error reading basketFile: " + e.getLocalizedMessage()) ;
            }
        }
        return basket ;
    }
}
