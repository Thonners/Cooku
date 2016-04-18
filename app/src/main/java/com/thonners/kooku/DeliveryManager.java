package com.thonners.kooku;

import android.content.Context;

import java.util.ArrayList;

/**
 * Class to provide delivery options and prices, etc.
 *
 * @author M Thomas
 * @since 18/06/2016
 */
public class DeliveryManager {

    private Context context ;
    private final ArrayList<DeliveryMethod> deliveryMethods = new ArrayList<>();
    private Basket basket ;

    /**
     * Constructor
     */
    public DeliveryManager(Context context, Basket basket) {
        this.context = context ;
        this.basket = basket ;
        populateDeliveryMethods();
    }

    /**
     * Method to populate the available delivery methods.
     * Currently uses demo delivery methods. In practise will need to pull the available delivery
     * methods from the chef data when downloaded.
     */
    private void populateDeliveryMethods() {
        getDemoDeliveryMethods() ;
        setSelectedMethod(0); // Initialise to the first one by default for now
    }

    /**
     * Method to create a bunch of sample delivery methods.
     */
    private void getDemoDeliveryMethods() {
        deliveryMethods.add(new DeliveryMethod(context,"First Class",2.5,1,2,true));
        deliveryMethods.add(new DeliveryMethod(context,"Second Class",1.5,3,5,true));
        deliveryMethods.add(new DeliveryMethod(context,"Next Working Day",4.5,1,1,true));
        deliveryMethods.add(new DeliveryMethod(context,"Next Day Saturday",6.5,1,1,true));
        deliveryMethods.add(new DeliveryMethod(context,"Same Day (orders before midday)",10.0,0,0,true));
    }

    /**
     * Returns a collection of available delivery methods
     * @return A collection of available delivery methods
     */
    public ArrayList<DeliveryMethod> getDeliveryMethods() {
        return deliveryMethods ;
    }

    /**
     * Method to return the selected deliver method
     * @return The selected method
     */
    public DeliveryMethod getSelectedMethod() {
        return basket.getDeliveryMethod();
    }

    /**
     * Sets the selected delivery method to that in the array list at the index supplied
     * @param methodIndex The index of the method in the deliveryMethod collection
     */
    public void setSelectedMethod(int methodIndex) {
        basket.setDeliveryMethod(deliveryMethods.get(methodIndex)) ;
    }


    /**
     * Class to hold the details of a delivery method
     */
    public static class DeliveryMethod {
        private Context context ;
        private String title ;
        private double price ;
        private int leadTimeLowerBound; // Specify minimum lead time in days, if boolean is true. If not, lead time is in minutes.
        private int leadTimeUpperBound; // Specify the size of the range in lead times. E.g. if lead time is 3-5 days, leadTimeLowerBound = 3, uncertainty = 2.
        private boolean leadTimeIsDays = true ; // Initialise to true (therefore lead times in days) until we also do 'asap' delivery.

        public DeliveryMethod(Context context, String title, double price, int leadTimeLowerBound, int leadTimeUpperBound, boolean leadTimeIsDays) {
            this.context = context ;
            this.title = title ;
            this.price = price ;
            this.leadTimeLowerBound = leadTimeLowerBound;
            this.leadTimeUpperBound = leadTimeUpperBound ;
            this.leadTimeIsDays = leadTimeIsDays ;
        }

        /**
         * Returns the name of the delivery method
         * @return Delivery method name
         */
        public String getTitle() {
            return title;
        }

        /**
         * Returns the price of the delivery method
         * @return Price of delivery
         */
        public double getPrice() {
            return price;
        }

        /**
         * Returns the lead time, in either days or minutes, depending on the state of the leadTimeIsDays variable
         * @return The lead time of the delivery method
         */
        public int getLeadTimeLowerBound() {
            return leadTimeLowerBound;
        }

        /**
         * Returns the upper limit of the lead time.
         * @return The upper limit of the lead time
         */
        public int getLeadTimeUpperBound() {
            return leadTimeUpperBound;
        }

        /**
         * Returns a formatted string of the lead time.
         * E.g. 3-5 days, or 35-45 mins.
         * @return String representation of the range of the lead time and the appropriate time units.
         */
        public String getLeadTimeRangeOneLine() {
            return getLeadTimeRange() + " " + getLeadTimeUnits() ;
        }

        /**
         * Returns a string formatted with a new line after the lead time range, with the units
         * on a new line.
         * @return Formatted string with lead time on the first line, and the units on the second
         */
        public String getLeadTimeRangeTwoLines() {
            return getLeadTimeRange() + "\n" + getLeadTimeUnits() ;
        }

        /**
         * Method to return a string representation of the lead time range
         * @return The lead time range as a string
         */
        private String getLeadTimeRange() {
            // Initialise the lead time to the minimum time
            String leadTimeRange = leadTimeLowerBound + "";
            // Add a range if required
            if (leadTimeLowerBound == leadTimeUpperBound) {
                leadTimeRange += "-" + leadTimeUpperBound;
            }
            return leadTimeRange;
        }

        /**
         * Method to return a string representation of the lead time units
         * @return The lead time units as a string
         */
        private String getLeadTimeUnits() {
            // Get the correct units
            String leadTimeUnits ;
            if (leadTimeIsDays) {
                leadTimeUnits = context.getResources().getString(R.string.days);
            } else {
                leadTimeUnits = context.getResources().getString(R.string.mins);
            }
            return leadTimeUnits ;
        }

        /**
         * Returns whether the lead time is in days or minutes. For 'regular' delivery methods,
         * it will be in days. Minutes is for when 'asap' delivery is implemented.
         * @return Whether the lead time value is specified in days (true) or minutes (false).
         */
        public boolean isLeadTimeIsDays() {
            return leadTimeIsDays;
        }
    }
}
