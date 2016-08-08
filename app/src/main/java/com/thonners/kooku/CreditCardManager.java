package com.thonners.kooku;

import android.content.Context;

import java.util.ArrayList;

/**
 * TEMP - need to research storing card details securely
 * Class to manage payment methods for a user
 *
 * @author M Thomas
 * @since 06/08/16
 */
public class CreditCardManager {

    private final Context context ;
    private final ArrayList<Card> cards = new ArrayList<>();

    /**
     * Constructor
     */
    public CreditCardManager(Context context) {
        this.context = context ;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * TEMP - need to research storing card details securely
     * Class to hold/access card details.
     */
    public class Card {

    }

}
