package com.thonners.kooku;

import android.content.Context;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class to manage the basket:
 * Saving and restoring baskets if closing the app or switching between chefs
 *
 * @author M Thomas
 * @since 21/05/16.
 */

public class BasketManager {

    private Context context ;

    private Basket basket ;

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
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String timestamp = s.format(new Date());

        // Initialise the string
        String saveFileString = timestamp + "\n" ;

        // Loop through the basket, saving the files


    }

    public Basket getSavedBasket() {
        return basket ;
    }
}
