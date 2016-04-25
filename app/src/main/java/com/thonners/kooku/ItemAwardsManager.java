package com.thonners.kooku;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Locale;

/**
 * Class to manage adding appropriate award icons to a view.
 * Should be supplied with a LinearLayout, the view to be populated, and an item.
 *
 * @author M Thomas
 * @since  25/04/16.
 */
public class ItemAwardsManager {

    private static final String LOG_TAG = "ItemAwardsManager" ;

    /**
     * Constructor
     */
    public ItemAwardsManager() {

    }


    /**
     * Method to add all the appropriate award icons for a ChefMenuItem to a given layout.
     * @param layout The layout to which the award icon will be added
     * @param awards The ItemAwards instance containing the awards info
     */
    public void addAwardIconsToView(LinearLayout layout, ItemAwards awards) {
        boolean awardsArray[] = awards.getAwardsArray() ;
        final Context context = layout.getContext() ;
        // Iterate through the awards
        for (int i = 0 ; i < awards.getNoAwards() ; i++) {
            // Only add the award if it's deserved.
            if (awardsArray[i]) {
                View awardIcon = LayoutInflater.from(context).inflate(R.layout.card_item_award, null);
                CardView cardView = (CardView) awardIcon.findViewById(R.id.item_award_card);
                ImageView imageView = (ImageView) awardIcon.findViewById(R.id.item_award_image_view);
                final String toastText = awards.getItemAwardDescription(context, i);

                switch (i) {
                    case ItemAwards.ITEM_AWARD_INDEX_VEGETARIAN:
                        cardView.setCardBackgroundColor(context.getResources().getColor(R.color.veggie_green));
                        imageView.setImageResource(R.drawable.ic_award_vegetarian);
                        break;
                    case ItemAwards.ITEM_AWARD_INDEX_VEGAN:
                        cardView.setCardBackgroundColor(context.getResources().getColor(R.color.vegan_purple));
                        imageView.setImageResource(R.drawable.ic_award_vegetarian);
                        break;
                    case ItemAwards.ITEM_AWARD_INDEX_ORGANIC:
                        cardView.setCardBackgroundColor(context.getResources().getColor(R.color.organic_green));
                        imageView.setImageResource(R.drawable.ic_award_organic);
                        break;
                    default:
                        Log.e(LOG_TAG, "There are more awards available than have been specified in the addAwardIconsToView() method. Add the colours/image resources here to see the icon. It won't be added to the view until it's defined here.");
                        break;
                }
                // Add an onClickListener, to show more info if someone clicks an award
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the award icon to the given layout
                layout.addView(awardIcon);
            }
        }

    }



    public static class ItemAwards implements Parcelable {

        public static final String ITEM_AWARDS_EXTRA = "com.thonners.kooku.itemAwardsExtra" ;
        private static final String LOG_TAG = "ItemAwards" ;

        // Universal Identifiers. Really should be defined as an array in the strings resource file. TODO: Move this to string resource file.
        private static final String ITEM_AWARDS[] = new String[]{"Vegetarian", "Vegan", "Organic" } ;
        // Index positions
        public static final int ITEM_AWARD_INDEX_VEGETARIAN = 0 ;
        public static final int ITEM_AWARD_INDEX_VEGAN = 1 ;
        public static final int ITEM_AWARD_INDEX_ORGANIC = 2 ;
        // Array in which to store the award statuses
        private boolean[] awards ;
        // Number of awards available. To be increased as more awards are added.
        int noAwards = 3 ;

        /**
         * Constructor.
         * @param isVegetarian Whether the dish is vegetarian.
         * @param isVegan Whether the dish is vegan.
         * @param isOrganic Whether the dish is organic.
         */
        public ItemAwards(boolean isVegetarian, boolean isVegan, boolean isOrganic) {
            Log.d(LOG_TAG, "Initialising ItemAwards") ;
            // Initialise the array
            awards = new boolean[noAwards] ;
            // Set the values
            awards[ITEM_AWARD_INDEX_VEGETARIAN] = isVegetarian ;
            awards[ITEM_AWARD_INDEX_VEGAN] = isVegan ;
            awards[ITEM_AWARD_INDEX_ORGANIC] = isOrganic ;
        }

        /**
         * Method to return the boolean array which stores what awards an item has.
         * @return The boolean array defining the awards
         */
        public boolean[] getAwardsArray() {
            return awards ;
        }

        /**
         * Method to return the total number of awards available, and thus how many to iterate through if required.
         * @return Total number of awards available.
         */
        public int getNoAwards() {
            return noAwards ;
        }

        /**
         * Method to return a formatted string which states what the award icon means.
         * e.g. 'This dish is vegetarian.'
         * @param awardIndex The integer index of the award, specified at the head of the ItemAwards class.
         * @return A formatted string stating the meaning of the award icon.
         */
        public String getItemAwardDescription(Context context, int awardIndex) {
            return String.format(Locale.getDefault(),context.getString(R.string.item_award_info),ITEM_AWARDS[awardIndex]) ;
        }

        //
        // Parcelable Stuff
        //

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(noAwards);
            // Loop through awards and add
            for (int i = 0 ; i < noAwards ; i++) {
                dest.writeByte((byte) (awards[i] ? 1 : 0));     //if awards[i] == true, byte == 1
            }
        }

        public static final Parcelable.Creator<ItemAwards> CREATOR
                = new Parcelable.Creator<ItemAwards>() {
            public ItemAwards createFromParcel(Parcel in) {
                return new ItemAwards(in);
            }

            public ItemAwards[] newArray(int size) {
                return new ItemAwards[size];
            }
        };

        private ItemAwards(Parcel in) {
            noAwards = in.readInt();
            awards = new boolean[noAwards] ;
            // Loop through awards and recover
            for (int i = 0 ; i < noAwards ; i++) {
                awards[i] = in.readByte() != 0;     // awards[i] == true if byte != 0
            }
        }

    }
}
