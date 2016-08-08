package com.thonners.kooku;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

/**
 * Activity in which a user can add a new card with which to make payments.
 *
 * @author M Thomas
 * @since 08/08/16
 */
public class AddCardActivity extends AppCompatActivity {

    private final static String LOG_TAG = "AddCardActivity";

    private boolean cardSaved = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        // Show the back/up button. Will be intercepted and forced to behave like back button in onMenuItemSelected.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_new_card));

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
        if (cardSaved) {
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
        String card = getResources().getString(R.string.card);
        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this) ;
        // Set the text
        builder.setTitle(String.format(getResources().getString(R.string.dialog_save_title),card));
        builder.setMessage(String.format(getResources().getString(R.string.dialog_save_message),card,card));
        // Create the buttons
        builder.setPositiveButton(R.string.dialog_save_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (saveCard()) {
                    cardSaved = true;
                    onBackPressed();
                }
            }
        })
                .setNegativeButton(R.string.dialog_save_discard, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Set the cardSaved variable to true so that it won't stop the activity exiting
                        cardSaved = true;
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
     * Method to save the card securely.
     *
     * Not currently implemented. Just in to allow the rest of the framework to compile.
     *
     * @return Whether the save action has completed successfully.
     */
    private boolean saveCard() {
        return true ;
    }

    public void autoAddCardClicked(View view) {
        Log.d(LOG_TAG,"Auto-add card from camera clicked.");
    }
}
