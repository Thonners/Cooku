package com.thonners.kooku;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity in which to allow the user to add a new address.
 *
 * @author M Thomas
 * @since 02/06/16
 */

public class AddAddressActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AddAddressActivity" ;

    private AddressManager addressManager ;
    private CardView currentLocationCard, fullAddressCard, postcodeSearchCard ;
    private EditText[] addressLines = new EditText[AddressManager.Address.INDEX_SIZE] ;
    private EditText etPostcodeSearch ;
    private TextView manualAddressEntryPrompt ;
    private FloatingActionButton saveFAB ;
    private boolean addressSaved ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        // Show the back/up button. Will be intercepted and forced to behave like back button in onMenuItemSelected.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_new_address));

        // Initialise AddressManager
        addressManager = new AddressManager(this);

        // Get view instances
        currentLocationCard = (CardView) findViewById(R.id.card_current_location) ;
        fullAddressCard = (CardView) findViewById(R.id.card_address_full) ;
        postcodeSearchCard = (CardView) findViewById(R.id.card_postcode) ;
        addressLines[AddressManager.Address.INDEX_LINE_1] = (EditText) findViewById(R.id.et_line_one);
        addressLines[AddressManager.Address.INDEX_LINE_2] = (EditText) findViewById(R.id.et_line_two);
        addressLines[AddressManager.Address.INDEX_CITY]= (EditText) findViewById(R.id.et_line_three);
        addressLines[AddressManager.Address.INDEX_COUNTY] = (EditText) findViewById(R.id.et_line_four);
        addressLines[AddressManager.Address.INDEX_POSTCODE]= (EditText) findViewById(R.id.et_postcode_full);
        etPostcodeSearch = (EditText) findViewById(R.id.et_postcode);
        manualAddressEntryPrompt = (TextView) findViewById(R.id.manual_address_prompt);
        saveFAB = (FloatingActionButton) findViewById(R.id.address_save_fab);

    }

    /**
     * Method to handle item menu clicks.
     * Includes intercepting the up button to force it to behave like the back button
     * @param item The menu item selected
     * @return Whether this method has handled the click
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    /**
     * Method to catch when the back button is pressed, to ensure that the address is saved before returning.
     * If it's not saved, a dialog will be shown to prompt the user to save, or continue editing.
     */
    @Override
    public void onBackPressed() {
        if (addressSaved) {
            // Return the intent to let the previous activity know this one has returned -
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        } else {
            showSaveDialog();
        }
    }

    /**
     * Dialog to warn the user that going back without saving will cause loss of address data.
     * Options are to save, discard, or continue editing
     */
    private void showSaveDialog() {
        String address= getResources().getString(R.string.address) ;
        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this) ;
        // Set the text
        builder.setTitle(String.format(getResources().getString(R.string.dialog_save_title),address));
        builder.setMessage(String.format(getResources().getString(R.string.dialog_save_message),address,address));
        // Create the buttons
        builder.setPositiveButton(R.string.dialog_save_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (saveAddress()) {
                    addressSaved = true;
                    onBackPressed();
                }
            }
        })
                .setNegativeButton(R.string.dialog_save_discard, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Set the addressSaved variable to true so that it won't stop the activity exiting
                        addressSaved = true;
                        onBackPressed();
                    }
                })
                .setNeutralButton(R.string.dialog_save_continue_editing, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        // Create the dialog
        AlertDialog dialog = builder.create();
        // Show the dialog
        dialog.show();
    }

    /**
     * Method to save the entered address. Before attempting to save, checks will be done that
     * sufficient information has been entered before attempting to save. If insufficient information
     * has been provided, the method should return false, and prompt the user for more info.
     * Sufficient information is determined as Address line 1, city and post-code.
     * @return boolean of whether the save was successful.
     */
    private boolean saveAddress() {
            Log.d(LOG_TAG, "SaveAddress() has been called.");
        // Get the entered data
        String[] address = new String[addressLines.length] ;
        boolean[] addressDataPopulated = new boolean[addressLines.length] ;
        for (int i = 0 ; i < addressLines.length ; i++) {
            address[i] = addressLines[i].getText().toString() ;
            if (address[i].length() > 0) addressDataPopulated[i] = true ;
        }
        // Check that enough of the fields are populated
        if (addressDataPopulated[AddressManager.Address.INDEX_LINE_1] && addressDataPopulated[AddressManager.Address.INDEX_CITY] && addressDataPopulated[AddressManager.Address.INDEX_POSTCODE]) {
            // Save the address
            AddressManager manager = new AddressManager(this) ;
            manager.addAddress(true, address);

            Log.d(LOG_TAG, "SaveAddress() has completed successfully.");
            addressSaved = true;

            return true;
        } else {
            // Not enough data entered.
            Toast.makeText(AddAddressActivity.this, getString(R.string.error_not_enough_info), Toast.LENGTH_SHORT).show();
            return false ;
        }
    }

    public void getAddressFromLocationClicked(View view) {
        Toast.makeText(AddAddressActivity.this, "Feature coming soon", Toast.LENGTH_SHORT).show();
    }
    public void getAddressFromPostcodeClicked(View view) {
        Toast.makeText(AddAddressActivity.this, "Postcode address lookup coming soon", Toast.LENGTH_SHORT).show();
        // Get the entered postcode
        String enteredPostcode = etPostcodeSearch.getText().toString() ;
        Log.d(LOG_TAG, "Searching for address for postcode: " + enteredPostcode + "... or would be if that functionality was implemented...");
        // Set the postcode in the full address card, as this will be shown/used when the full address is entered and saved
        addressLines[AddressManager.Address.INDEX_POSTCODE].setText(enteredPostcode);
        toggleAddressVisibility();
    }
    public void manualAddressEntryClicked(View view) {
        toggleAddressVisibility();
    }
    private void toggleAddressVisibility() {
        // Hide lookup card & manual entry prompt
        postcodeSearchCard.setVisibility(View.GONE);
        manualAddressEntryPrompt.setVisibility(View.GONE);
        // Show manual address entry
        fullAddressCard.setVisibility(View.VISIBLE);
        saveFAB.setVisibility(View.VISIBLE);
    }
    public void saveClicked(View view) {
        saveAddress();
    }

    private AddressManager.Address getAddressFromPostcode(String postcode){
        // Launch A-sync task to look up address from getAddresses.io
        return null ;
    }

}
