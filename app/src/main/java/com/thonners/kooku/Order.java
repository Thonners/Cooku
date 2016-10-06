package com.thonners.kooku;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class to hold the details of a historical order.
 *
 * @author M Thomas
 * @since 04/10/16.
 */

public class Order {

    private Chef chef ;
    private ArrayList<ChefMenuItem> items ;
    private double price;
    private Date date ;
    private AddressManager.Address deliveryAddress ;

    /**
     * Constructor
     * @param chef The Chef in the order.
     * @param items A collection of the items ordered
     * @param price The price of the order.
     * @param deliveryAddress The address to which the order was delivered.
     */
    public Order(Chef chef, ArrayList<ChefMenuItem> items, Date date, double price, AddressManager.Address deliveryAddress) {
        this.chef = chef;
        this.items = items ;
        this.date = date ;
        this.price = price;
        this.deliveryAddress = deliveryAddress;
    }

    public Chef getChef() {
        return chef;
    }

    public ArrayList<ChefMenuItem> getItems() {
        return items;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Return the date of the order in an appropriate locale.
     * @return A String representation of the order date.
     */
    public String getDateString() {
        DateFormat sdf = SimpleDateFormat.getDateInstance();
        String dateString = sdf.format(date);
        return dateString ;
    }
    public double getPrice() {
        return price;
    }

    public AddressManager.Address getDeliveryAddress() {
        return deliveryAddress;
    }
}
