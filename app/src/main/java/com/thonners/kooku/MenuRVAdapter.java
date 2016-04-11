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

    private final int TYPE_ITEM = Integer.MIN_VALUE ;
    private final int TYPE_HEADER = Integer.MIN_VALUE + 1 ;

    private Context context ;
    private ArrayList<ChefMenu.ChefMenuItem> menuItems = new ArrayList<>();

    private OnItemClickListener onItemClickListener ;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is a chef in our case
        // All the views
        public View cardView;

        public ViewHolder(View view) {
            super(view);
            cardView = view;

            cardView.setOnClickListener(this);
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
    public MenuRVAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView ;
        if (viewType == TYPE_HEADER) {
            itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.card_chef_bio, viewGroup, false);
            return new VHHeader(itemView) ;
        } else if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.card_menu_item, viewGroup, false);
            return new VHItem(itemView) ;
        }

        throw new RuntimeException("Bad viewType used: " + viewType) ;
    }

    // Populate views
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position){
        if (viewHolder instanceof VHHeader) {
            VHHeader vH = (VHHeader) viewHolder ;
            vH.tvChefBio.setText("Dummy Text");
        } else {
            VHItem vH = (VHItem) viewHolder ;
            // Get Menu
            final ChefMenu.ChefMenuItem menuItem = menuItems.get(position-1);
            // Populate details
            vH.tvItemID.setText(menuItem.getItemID() + "");
            vH.tvItemTitle.setText(menuItem.getTitle());
            vH.tvItemSubtitle.setText(menuItem.getSubtitle());
            vH.tvItemPrice.setText(context.getText(R.string.currency_icon) + " " + menuItem.getPrice());
        }
    }

    /**
     * Returns the size of the dataset - i.e. the number of menu items.
     * @return The number of menu items.
     */
    @Override
    public int getItemCount() {
        return menuItems.size() + 1;
    }
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0 ;
    }

    class VHItem extends MenuRVAdapter.ViewHolder {
        public View cardView;
        protected RelativeLayout layout ;
        protected TextView tvItemID;
        protected TextView tvItemTitle;
        protected TextView tvItemSubtitle;
        protected TextView tvItemPrice;

        public VHItem(View view) {
            super(view);
            layout      = (RelativeLayout) view.findViewById(R.id.menu_item_layout) ;
            tvItemID    = (TextView) view.findViewById(R.id.menu_item_id) ;
            tvItemTitle = (TextView) view.findViewById(R.id.menu_item_title) ;
            tvItemSubtitle = (TextView) view.findViewById(R.id.menu_item_subtitle) ;
            tvItemPrice = (TextView) view.findViewById(R.id.menu_item_price) ;
        }
    }

    class VHHeader extends MenuRVAdapter.ViewHolder {

        protected TextView tvChefBio ;
        public VHHeader(View view) {
            super(view);
            tvChefBio   = (TextView) view.findViewById(R.id.chef_bio);
        }
    }

}
