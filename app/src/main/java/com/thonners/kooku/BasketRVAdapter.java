package com.thonners.kooku;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to manage the RecyclerView on the basket page.
 *
 * Will populate the RecyclerView with a CardView for each item at the top of the page,
 * with FrameViews below for the subtotal, delivery charge, and any additional surcharges.
 *
 * OnClickListeners and the appropriate interface are also created for the CardViews here.
 *
 * @author M Thomas
 * @since 16/04/16.
 */

public class BasketRVAdapter extends RecyclerView.Adapter<BasketRVAdapter.ViewHolder> {

    private final String LOG_TAG = "BasketRVAdapter" ;

    private final int TYPE_ITEM = Integer.MIN_VALUE ;
    private final int TYPE_FOOTER = Integer.MIN_VALUE + 1 ;
    // Position index for accounting items, after main items list
    private final int SUBTOTAL_POSITION = 0 ;
    private final int DELIVERY_POSITION = 1 ;
    private final int SURCHARGE_POSITION = 2 ;

    private Context context ;
    private Basket basket ;
    private HashMap<ChefMenu.ChefMenuItem, Integer> orders ;
    private ArrayList<ChefMenu.ChefMenuItem> menuItems ;
    private boolean surchargeRequired = false;

    private OnItemClickListener onItemClickListener ;


    public BasketRVAdapter(Context context, Basket basket, boolean surchargeRequired) {
        this.context = context ;
        this.basket = basket ;
        orders = basket.getOrders() ;
        this.surchargeRequired = surchargeRequired ;
        menuItems = basket.getMenuItems() ;
    }


    /**
     * Wrapper class that provides the OnClickListener interface for the CardViews.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * Constructor.
         * @param view The view being created/inflated/held. (Not actually sure!)
         */
        public ViewHolder(View view) {
            super(view);
            if (view instanceof CardView) {
                View cardView = view;
                cardView.setOnClickListener(this);
            }
        }
        /**
         * Override the onClick method to implement the onClickListener interface. Put the callback in here.
         * @param view  The view which has been clicked.
         */
        @Override
        public void onClick(View view){
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, getLayoutPosition());
            }
        }

    }

    /**
     * Interface for the OnClickListener of the card in the ViewHolder.
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 'Setter' method for the OnItemClickListener
     * @param mItemClickListener The ItemClickListener to be assigned.
     */
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    @Override
    public BasketRVAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView ;
        if (viewType == TYPE_FOOTER) {
            itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.frame_basket_accounting, viewGroup, false);
            return new VHFooter(itemView);
        } else if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.card_basket_item, viewGroup, false);
            return new VHItem(itemView) ;
        }

        throw new RuntimeException("Bad viewType used: " + viewType) ;
    }

    /**
     * Method to add the detail to the views. The (sub)class of the ViewHolder,
     * is used to determine which views are filled with what data.
     * @param viewHolder The ViewHolder to be modified.
     * @param position The position of the view in the RecyclerView list.
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position){
        NumberFormat format = NumberFormat.getCurrencyInstance() ;
        if (viewHolder instanceof VHFooter) {
            VHFooter vH = (VHFooter) viewHolder;
            int startIndex = orders.size() ;
            if(position == (startIndex + SUBTOTAL_POSITION)) {
                vH.tvText.setText(context.getString(R.string.subtotal));
                vH.tvValue.setText(format.format(basket.getSubtotalPrice()));
            } else if (position == (startIndex + DELIVERY_POSITION)) {
                vH.tvText.setText(context.getString(R.string.delivery_charge));
                vH.tvValue.setText(format.format(Basket.DELIVERY_CHARGE));
            } else if (position == (startIndex + SURCHARGE_POSITION)) {
                String surchargeText = String.format(context.getString(R.string.surcharge), format.format(Basket.MINIMUM_ORDER_VALUE)) ;
                vH.tvText.setText(surchargeText);
                vH.tvValue.setText(format.format(Basket.SURCHARGE_VALUE));
            } else {
                Log.e(LOG_TAG, "Error: an instance of a VHFOOTER is in an unexpected position: " + position) ;
            }

        } else if (viewHolder instanceof VHItem) {
            VHItem vH = (VHItem) viewHolder ;
            // Get Menu
            final ChefMenu.ChefMenuItem menuItem = menuItems.get(position);
            // Populate details
            String quantity = String.format(context.getString(R.string.quantity_suffix), orders.get(menuItem));
            vH.tvItemQuantity.setText(quantity);
            vH.tvItemTitle.setText(menuItem.getTitle());
            vH.tvItemDetails.setText(menuItem.getSubtitle());
            vH.tvItemPrice.setText(format.format(menuItem.getPrice()));
        }
    }

    /**
     * Returns the number of items to be displayed in the RecyclerView
     * This is equal to (the number of items in the basket) + 2 (or 3):
     *      One extra for the subtotal row
     *      Another extra one is for the delivery charge row
     *      The final possible extra is for the surcharge for small orders - this will only be shown if required
     * @return The number of items in the recycler view.
     */
    @Override
    public int getItemCount() {
        // Number of extra items in the recyclerview, above the number of items in the basket.
        int extras = 2 ;
        // Add an extra row if a surcharge is required
        if (surchargeRequired) extras++ ;
        // Return sum of no of items in basket, with number of extra rows required.
        return orders.size() + extras;
    }

    /**
     * Method to determine what type of view is to be inflated, given its position in the RecyclerView.
     * @param position The position in the RecyclerView.
     * @return The type, one of TYPE_FOOTER, or TYPE_ITEM.
     */
    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    /**
     * Method to determine whether the position of the item in a RecyclerView is a footer item or
     * a normal item.
     * @param position The position in the RecyclerView
     * @return Whether the position should be a footer or not
     */
    private boolean isPositionFooter(int position) {
        return position >= orders.size() ;
    }
    /**
     * ViewHolder subclass for the main menu items. Instances of the necessary text views to be
     * populated later are found/obtained here.
     */
    class VHItem extends BasketRVAdapter.ViewHolder {
        protected TextView tvItemQuantity;
        protected TextView tvItemTitle;
        protected TextView tvItemDetails;
        protected TextView tvItemPrice;

        /**
         * Constructor
         * @param view The view to be held.
         */
        public VHItem(View view) {
            super(view);
            tvItemQuantity = (TextView) view.findViewById(R.id.basket_item_quantity) ;
            tvItemTitle = (TextView) view.findViewById(R.id.basket_item_title) ;
            tvItemDetails = (TextView) view.findViewById(R.id.basket_item_details) ;
            tvItemPrice = (TextView) view.findViewById(R.id.basket_item_price) ;
        }
    }

    /**
     * ViewHolder subclass for the footer items - i.e. the accounting entries; subtotal, delivery charge, surcharge, etc.
     * Instances of the necessary text views to be populated later are found/obtained here.
     */
    class VHFooter extends BasketRVAdapter.ViewHolder {
        protected TextView tvText;
        protected TextView tvValue;
        /**
         * Constructor
         * @param view The view to be held.
         */
        public VHFooter(View view) {
            super(view);
            tvText = (TextView) view.findViewById(R.id.basket_accounting_text);
            tvValue = (TextView) view.findViewById(R.id.basket_accounting_value);
        }
    }


}
