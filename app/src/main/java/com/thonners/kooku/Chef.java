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

    private int chefID ;
    private String chefName ;
    private String chefStyle ;
    private String chefBioShort ;
    private String chefBioLong ;
    private int chefPriceBracket ;
    private int chefETA ;
    private Location chefLocation ;
    private ChefMenu menu ;
    private Drawable chefImage ;
    private Bitmap chefImageBM;

    public static final String CHEF_ID = "CHEF_ID" ;

    /**
     * Constructor.
     */
    public Chef(int chefID, String name, String style, String[] bios, int price, int eta, Drawable image, ChefMenu menu) {
        this.chefID = chefID ;
        this.chefName = name ;
        this.chefStyle = style ;
        this.chefBioShort = bios[0] ;
        this.chefBioLong = bios[1] ;
        this.chefPriceBracket = price ;
        this.chefETA = eta ;
        this.chefImage = image ;
        this.menu = menu ;
    }

    //---------------------- Public Methods -----------------------------
    public int getChefID() {
        return chefID ;
    }
    public String getChefIDString() {
        return ("" + chefID);
    }
    public String getChefName() {
        return chefName ;
    }
    public String getChefStyle() {
        return chefStyle ;
    }
    public String getChefBioShort() {
        return chefBioShort;
    }
    public String getChefBioLong() {
        return chefBioLong;
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
    public ChefMenu getMenu() {
        return menu ;
    }

}
