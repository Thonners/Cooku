package com.thonners.kooku;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Class to supply the views to the pager adapters in the checkout view
 *
 * @author M Thomas
 * @since 31/05/16
 *
 */

public class CheckoutPagerAdapter extends PagerAdapter {

    private int pagerType ;
    public final static int ADDRESS_PAGER = Integer.MAX_VALUE ;
    public final static int CARD_PAGER = Integer.MIN_VALUE ;
    private Context mContext;

    // Address stuff
    private AddressManager addressManager ;
    private ArrayList<AddressManager.Address> addresses ;

    // Card stuff
    private CreditCardManager cardManager ;
    private ArrayList<CreditCardManager.Card> cards ;

    // Interfaces
    private OnAddNewClickedListener onAddNewClickedListener ;
    public interface OnAddNewClickedListener {
        void addNewAddressClicked() ;
        void addNewCardClicked() ;
    }
    public void setOnAddNewClickedListener(final OnAddNewClickedListener listener) {
        onAddNewClickedListener = listener ;
    }


    public CheckoutPagerAdapter(Context context, int pagerType) {
        this.mContext = context ;
        this.pagerType = pagerType ;
        if (pagerType == ADDRESS_PAGER) {
            addressManager = new AddressManager(context);
            addresses = addressManager.getAddresses();
        } else if (pagerType == CARD_PAGER) {
            cardManager = new CreditCardManager(context) ;
            cards = cardManager.getCards();
        } else {
            // Something's gone wrong.
        }

    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout ;
        if (pagerType == ADDRESS_PAGER) {
            if (position < addresses.size()) {
                layout = (ViewGroup) inflater.inflate(R.layout.frame_address_viewer, collection, false);
                collection.addView(layout);
                populateAddressView(layout, addresses.get(position));
                return layout;
            } else if (position == addresses.size()) {
                layout = (ViewGroup) inflater.inflate(R.layout.frame_checkout_add_new, collection, false);
                ((TextView) layout.findViewById(R.id.add_new_text_view)).setText(mContext.getResources().getString(R.string.add_new_address));
                collection.addView(layout);
                // Attach onClickListener to the card
                CardView addNewAddressCV = (CardView) layout.findViewById(R.id.checkout_add_new) ;
                addNewAddressCV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onAddNewClickedListener != null) {
                            onAddNewClickedListener.addNewAddressClicked();
                        }
                    }
                });
                return layout;
            }
        } else if (pagerType == CARD_PAGER) {
            // TODO: Learn about storing cards securely
            if (position < cards.size()) {
                layout = (ViewGroup) inflater.inflate(R.layout.frame_card_viewer, collection, false);
                collection.addView(layout);
                return layout;
            } else if (position == cards.size()) {
                layout = (ViewGroup) inflater.inflate(R.layout.frame_checkout_add_new, collection, false);
                ((TextView) layout.findViewById(R.id.add_new_text_view)).setText(mContext.getResources().getString(R.string.add_new_payment_method));
                collection.addView(layout);
                // Attach onClickListener to the card
                CardView addNewCardCV = (CardView) layout.findViewById(R.id.checkout_add_new) ;
                addNewCardCV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onAddNewClickedListener != null) {
                            onAddNewClickedListener.addNewCardClicked();
                        }
                    }
                });
                return layout;
            }
        }

        return null;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        // Add one to the size of the array lists returned to offer the 'add new ...' option
        if (pagerType == ADDRESS_PAGER) {
            return addresses.size() + 1 ;
        } else if (pagerType == CARD_PAGER) {
            return cards.size() + 1 ;
        }
        // Will only get here if something's gone wrong with the pagerType
        return 0 ;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (pagerType == ADDRESS_PAGER) {
            return "Address: " + position ;
        } else if (pagerType == CARD_PAGER) {
            return "Card: " + position ;
        }
        return "Error" ;
    }

    private void populateAddressView(ViewGroup layout, AddressManager.Address address) {
        TextView textView = (TextView) layout.findViewById(R.id.address_line_1) ;
        textView.setText(address.getAddressPresentationString());
    }
}
