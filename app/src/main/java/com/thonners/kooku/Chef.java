package com.thonners.kooku;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;

/**
 * Class to contain all the relevent information for a Chef.
 *
 * @author M Thomas
 * @since 24/03/16
 */

public class Chef {

    private int chefNo ;
    private String chefName ;
    private String chefStyle ;
    private int chefPriceBracket ;
    private int chefETA ;
    private Location chefLocation ;
    private ChefMenu menu ;
    private Drawable chefImage ;
    private Bitmap chefImageBM;

    /**
     * Constructor.
     */
    public Chef(String name, String style, int price, int eta, Drawable image) {
        this.chefName = name ;
        this.chefStyle = style ;
        this.chefPriceBracket = price ;
        this.chefETA = eta ;
        this.chefImage = image ;
    }



    //---------------------- Public Methods -----------------------------
    public String getChefName() {
        return chefName ;
    }
    public String getChefStyle() {
        return chefStyle ;
    }
    public int getChefPriceBracket() {
        return chefPriceBracket ;
    }
    public String getChefPrice(){
        String priceString = "" ;
        for (int i = 0 ; i < chefPriceBracket ; i++) {
            priceString += "£" ;
        }
        return priceString ;
    }
    public int getChefETA() {
        return chefETA ;
    }
    public Drawable getChefImage() {
        return chefImage ;
    }
    public Bitmap getChefImageBM() {
        return chefImageBM;
    }


}
