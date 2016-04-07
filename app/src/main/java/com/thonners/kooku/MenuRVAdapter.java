package com.thonners.kooku;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Class to manage the RecyclerView on the chef's menu page.
 *
 * @author M Thomas
 * @since 07/04/16.
 */
public class MenuRVAdapter extends RecyclerView.Adapter<MenuRVAdapter.ViewHolder> {

    private final String LOG_TAG = "MenuRVAdapter" ;

    private Context context ;
    private ArrayList<ChefMenu.ChefMenuItem> menuItems = new ArrayList<>();

    private OnItemClickListener onItemClickListener ;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is a chef in our case
        // All the views
        public View cardView;
        protected RelativeLayout layout ;
        protected TextView tvItemTitle;
        protected TextView tvItemSubtitle;
        protected TextView tvItemPrice;
        // The item instance
        protected ChefMenu.ChefMenuItem item ;
        public ViewHolder(View view) {
            super(view);
            cardView = view;
            layout      = (RelativeLayout) view.findViewById(R.id.menu_item_layout) ;
            tvItemTitle = (TextView) view.findViewById(R.id.menu_item_title) ;
            tvItemSubtitle = (TextView) view.findViewById(R.id.menu_item_subtitle) ;
            tvItemPrice = (TextView) view.findViewById(R.id.menu_item_price) ;

            cardView.setOnClickListener(this);
        }

        /**
         * Override the onClick method to implement the onClickListener interface. Put the callback in here.
         * @param view  The view which has been clicked.
         */
        @Override
        public void onClick(View view){
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(item);
            }
        }

        /**
         * Setter method for the item instance
         */
        public void setItem(ChefMenu.ChefMenuItem item) {
            this.item = item ;
        }
        /**
         * Getter method for the item instance
         */
        public ChefMenu.ChefMenuItem getItem() {
            return item ;
        }
    }

    /**
     * Interface for the OnClickListener of the card in the ViewHolder.
     */
    public interface OnItemClickListener {
        void onItemClick(ChefMenu.ChefMenuItem item);
    }

    /**
     * 'Setter' method for the OnItemClickListener
     * @param mItemClickListener The ItemClickListener to be assigned.
     */
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    /**
     * Constructor.
     * @param context The application context. No longer required. Remove this.
     * @param menuItems An ArrayList of the chefs.
     */
    public MenuRVAdapter(Context context, ArrayList<ChefMenu.ChefMenuItem> menuItems) {
        this.context = context ;
        this.menuItems = menuItems ;
    }

    // Create new views (invoked by layout manager)
    @Override
    public MenuRVAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_menu_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    // Populate views
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position){
        // Get Chef
        final ChefMenu.ChefMenuItem menuItem = menuItems.get(position) ;
        // Populate details
        viewHolder.tvItemTitle.setText(menuItem.getTitle());
        viewHolder.tvItemSubtitle.setText(menuItem.getSubtitle());
        viewHolder.tvItemPrice.setText(context.getText(R.string.currency_icon) + " " + menuItem.getPrice());
    }

    /**
     * Returns the size of the dataset - i.e. the number of menu items.
     * @return The number of menu items.
     */
    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}
