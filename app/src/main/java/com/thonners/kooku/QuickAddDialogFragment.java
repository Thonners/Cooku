package com.thonners.kooku;

/**
 * Dialog fragment to offer quick-add of items to the basket.
 *
 * @author M Thomas
 * @since 27/04/16
 */


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

public class QuickAddDialogFragment extends DialogFragment {

    private static String DIALOG_ITEM = "com.thonners.kooku.dialogItem" ;
    private static int QUICK_ADD_NUMBER_LIMIT = 10 ;

    private static String LOG_TAG = "QuickAddDialogFragment" ;

    private NumberPicker np ;
    private ChefMenuItem item ;

    /**
     * Method to instantiate the dialog, whilst allowing for a ChefMenuItem to be passed as an argument.
     * @param item The ChefMenuItem for which the dialog box is being shown.
     * @return An instance of a QuickAddDialogFragment, with the ChefMenuItem in a bundle.
     */
    public static QuickAddDialogFragment newInstance(ChefMenuItem item) {
        QuickAddDialogFragment quickAddDialogFragment = new QuickAddDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(DIALOG_ITEM,item);
        quickAddDialogFragment.setArguments(args);
        return quickAddDialogFragment;
    }
    /**
     * This interface will be used by the creating Activity to receive the callbacks when the
     * buttons are clicked.
     */
    public interface QuickAddDialogListener {
        // Positive click is 'Add'
        void onDialogPositiveClick(ChefMenuItem item, int numberToAdd);
        // Neutral click is 'Checkout'
        void onDialogNeutralClick(ChefMenuItem item, int numberToAdd);
        // Netagtive click is 'Cancel'
        void onDialogNegativeClick(QuickAddDialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    QuickAddDialogListener mListener;

    /**
     * Override the onAttach method to instantiate the QuickAddDialogListener
     * @param activity The activity calling/creating the dialog
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (QuickAddDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement QuickAddDialogListener");
        }
    }

    /**
     * Method to create the Dialog
     * @param savedInstanceState Saved instance state
     * @return The inflated QuickAddDialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the item
        this.item = getArguments().getParcelable(DIALOG_ITEM) ;
        // Create the prompt string
        String titleText = String.format(getString(R.string.dialog_title),item.getTitle());

        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Create the view (Pass null as the parent view because its going in the dialog layout)
        View layout = inflater.inflate(R.layout.dialog_quick_add, null) ;

        // Set up the number picker
        np = (NumberPicker) layout.findViewById(R.id.dialog_number_picker) ;
        np.setMinValue(1);
        np.setMaxValue(QUICK_ADD_NUMBER_LIMIT);
        np.setValue(1);
        np.setWrapSelectorWheel(true);

        // Create a text view for the title, so its style can be set. Use a layout to allow setting of a style
        TextView titleTextView = (TextView) inflater.inflate(R.layout.dialog_quick_add_title_text_view, null) ;
        titleTextView.setText(titleText);

        // Set the layout for the dialog
        builder.setView(layout)
                // Set the title
                .setCustomTitle(titleTextView)
           //     .setTitle(titleText)
                // Add action buttons
                .setPositiveButton(R.string.dialog_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Add to the basket
                        positiveClicked();
                    }
                })
                .setNeutralButton(R.string.dialog_checkout, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        // Add to basket and checkout - handled by MenuActivity
                        neutralClicked() ;
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Just cancel the dialog
                        QuickAddDialogFragment.this.getDialog().cancel();
                        // If in future, more is required on a cancel click, can call onDialogNegativeClick(QuickAddDialogFragment.this)
                    }
                });



        Dialog dialog =  builder.create() ;

        return dialog ;
    }

    private void positiveClicked() {
        mListener.onDialogPositiveClick(item, np.getValue());
    }
    private void neutralClicked() {
        mListener.onDialogNeutralClick(item, np.getValue());
    }

}
