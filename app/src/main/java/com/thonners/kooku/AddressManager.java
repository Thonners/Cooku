package com.thonners.kooku;

import android.content.Context;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to manage delivery (& potentially collection) addresses for a user
 *
 * @author M Thomas
 * @since 01/06/16
 */

public class AddressManager {

    private static final String LOG_TAG = "AddressManager" ;
    private Context mContext ;
    private static final String ADDRESS_DIRECTORY = "addresses" ;
    private File addressDirectory ;
    private ArrayList<Address> addresses = new ArrayList<>();

    /**
     * Constructor
     * @param context Application context required for access to files
     */
    public AddressManager(Context context) {
        mContext = context ;
        addressDirectory = new File(mContext.getFilesDir(), ADDRESS_DIRECTORY) ;
        if (!addressDirectory.isDirectory()) addressDirectory.mkdirs() ;
    }

    /**
     * Method to obtain instances of all the addresses saved within the app.
     * @return An ArrayList of saved Address instances
     */
    public ArrayList<Address> getAddresses() {
        // Initialise ArrayList to be returned
        ArrayList<Address> addresses = new ArrayList<>();
        // Get all address files
        // Check that the addresses directory exists first
        if (addressDirectory.canRead()) {
            File[] addressFiles = addressDirectory.listFiles();
            // For each file, turn the contents into an address and add to addresses
            for (int i = 0 ; i < addressFiles.length ; i++){
                // Read the file
                Address address = new Address(addressFiles[i]) ;
                // Add it to the list
                if (address.createdSuccessfully()) addresses.add(address) ;
            }
        }
        Log.d(LOG_TAG,"getAddresses() will return " + addresses.size() + " addresses.") ;
        return addresses ;
    }

    public void addAddress(boolean setAsDefaultAddress, String[] newAddressData) {
        // Find the previously maximum address ID
        int maxAddressID = -1 ; // Initialise to -1, so that if no addresses are found, after incrementing first one will be ID = 0. If address 0 is found, next will be 1, etc.
        for (Address address : getAddresses()) {
            maxAddressID = Math.max(maxAddressID,address.getAddressID()) ;
        }
        // Increment max ID for use in the new address
        maxAddressID++ ;
        Address newAddress = new Address(maxAddressID, setAsDefaultAddress, newAddressData) ;
        saveAddress(newAddress);

    }

    /**
     * Add an address, assuming to make the new address default
     * @param setAsDefaultAddress Boolean to set the address as default or not
     * @param line1 Address line 1
     * @param line2 Address line 2
     * @param line3 City
     * @param line4 County
     * @param postCode Post code
     */
    public void addAddress(boolean setAsDefaultAddress, String line1, String line2, String line3, String line4, String postCode) {
        // Find the previously maximum address ID
        int maxAddressID = -1 ; // Initialise to -1, so that if no addresses are found, after incrementing first one will be ID = 0. If address 0 is found, next will be 1, etc.
        for (Address address : getAddresses()) {
            maxAddressID = Math.max(maxAddressID,address.getAddressID()) ;
        }
        // Increment max ID for use in the new address
        maxAddressID++ ;
        Address newAddress = new Address(maxAddressID, setAsDefaultAddress, line1, line2, line3, line4, postCode) ;
        saveAddress(newAddress);

    }

    /**
     * Add an address, assuming to make the new address default
     * @param line1 Address line 1
     * @param line2 Address line 2
     * @param line3 City
     * @param line4 County
     * @param postCode Post code
     */
    public void addAddress(String line1, String line2, String line3, String line4, String postCode) {
        addAddress(true,line1,line2,line3,line4,postCode);
    }

    private void saveAddress(Address address) {
        Log.d(LOG_TAG, "Saving new address. Address ID: " + address.getAddressID());
        String saveFileName = "" + address.getAddressID() ;
        File saveFile = new File(addressDirectory, saveFileName);
        BufferedWriter bw = null ;
        try {
            bw = new BufferedWriter(new FileWriter(saveFile));
            bw.write(address.getAddressSaveString());
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error writing new address file: " + saveFile.getAbsolutePath()) ;
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) bw.close();
            } catch (Exception e) {
            Log.e(LOG_TAG, "Error closing BufferedWriter for file: " + saveFile.getAbsolutePath()) ;
                e.printStackTrace();
            }
        }
    }




    /**
     * Class to hold all details of an address in an instance.
     */
    public static class Address {

        // Indices for the various fields in arrays. Potentially move the whole thing to enum later?
        public static final int INDEX_LINE_1 = 0 ;
        public static final int INDEX_LINE_2 = 1 ;
        public static final int INDEX_CITY = 2 ;
        public static final int INDEX_COUNTY = 3 ;
        public static final int INDEX_POSTCODE = 4 ;
        public static final int INDEX_SIZE = 5; // Always one larger than the largest index - the size of the array required

        private static final String LOG_TAG = "Address" ;

        private int addressID ;
        private boolean isDefaultAddress ;
        private String[] address = new String[INDEX_SIZE] ;

        public Address(int addressID, boolean setAsDefaultAddress, String line1, String line2, String line3, String line4, String postCode) {
            this.addressID = addressID;
            this.isDefaultAddress = setAsDefaultAddress;
            this.address[INDEX_LINE_1] = line1;
            this.address[INDEX_LINE_2] = line2;
            this.address[INDEX_CITY] = line3;
            this.address[INDEX_COUNTY] = line4;
            this.address[INDEX_POSTCODE] = postCode;
        }

        /**
         * Constructor for String[] of address details
         * @param addressID Unique address ID number
         * @param setAsDefaultAddress Use this address as default
         * @param address String array containing the address data
         */
        public Address(int addressID, boolean setAsDefaultAddress, String[] address) {
            this.addressID = addressID;
            this.isDefaultAddress = setAsDefaultAddress;
            this.address = address ;
        }

        public Address(File addressFile){
            BufferedReader br = null ;
            // Wrap in try block to catch any problems reading the file
            try {
                // Get address ID number from file name
                addressID = Integer.parseInt(addressFile.getName());
                // Parse the file for the address info
                br = new BufferedReader(new FileReader(addressFile));
                String readLine ;
                int i = -1 ;
                while ((readLine = br.readLine()) != null) {
                    if (i < 0) {
                        isDefaultAddress = Boolean.parseBoolean(readLine) ;
                    } else if (i < address.length) {
                        address[i] = readLine ;
                    } else {
                        break;
                    }
                    i++ ;
                }

            } catch (NumberFormatException e) {
                // File name not a number - assume it's not an address file...
                Log.e(LOG_TAG,"Error occurred whilst reading address from file: " + addressFile.getAbsolutePath()) ;
                e.printStackTrace();
                // Set ID to int min value to signal that there was an error creating the address
                addressID = Integer.MIN_VALUE ;
            } catch (FileNotFoundException e) {
                // File not found. Shouldn't really see this as addressFile comes from a list of files in the addresses directory...
                Log.e(LOG_TAG,"Error occurred whilst reading address from file: " + addressFile.getAbsolutePath()) ;
                e.printStackTrace();
                // Set ID to int min value to signal that there was an error creating the address
                addressID = Integer.MIN_VALUE ;
            } catch (IOException e) {
                // Problem reading the file...
                Log.e(LOG_TAG,"Error occurred whilst reading address from file: " + addressFile.getAbsolutePath()) ;
                e.printStackTrace();
                // Set ID to int min value to signal that there was an error creating the address
                addressID = Integer.MIN_VALUE ;
            } finally {
                try {
                    if (br != null) br.close();
                } catch (IOException ex) {
                Log.e(LOG_TAG,"Error occurred whilst closing the buffered reader for file: " + addressFile.getAbsolutePath()) ;
                    ex.printStackTrace();
                }
            }
        }

        public int getAddressID() {
            return addressID ;
        }
        public void setAsDefaultAddress(boolean setAsDefaultAddress){
            this.isDefaultAddress = setAsDefaultAddress;
        }

        public boolean isDefaultAddress(){
            return isDefaultAddress;
        }

        public String getAddressPresentationString() {
            String presentationString = "" ;
            // Loop through all strings in address array to make it more robust/easier to add/remove layers in future
            for (int i = 0 ; i < address.length ; i++) {
                presentationString += "\n" + address[i] ;
            }
            return presentationString ;
        }

        public String getAddressSaveString() {
            String saveString = isDefaultAddress + "";
            saveString += "\n" + getAddressPresentationString() ;

            return saveString ;
        }

        public boolean createdSuccessfully() {
            return addressID != Integer.MIN_VALUE ;
        }


    }
}
