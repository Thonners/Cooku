package com.thonners.kooku;

import android.content.Context;
import android.support.v7.widget.CardView;
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
 * Will populate the RecyclerView with a CardView for the chef's bio at the top (i.e. position 0),
 * a FrameView for the menu divider between bio and items (u.e. position 1)
 * and all the menu items, in their individual CardViews below that.
 *
 * OnClickListeners and the appropriate interface are also created for the CardViews here.
 *
 * @author M Thomas
 * @since 07/04/16.
 */
public class MenuRVAdapter extends RecyclerView.Adapter<MenuRVAdapter.ViewHolder> {

    private final String LOG_TAG = "MenuRVAdapter" ;

    private final int TYPE_ITEM = Integer.MIN_VALUE ;
    private final int TYPE_HEADER = Integer.MIN_VALUE + 1 ;
    private final int TYPE_DIVIDER = Integer.MIN_VALUE + 2 ;

    private Context context ;
    private ChefMenu menu ;
    private ArrayList<ChefMenuItem> menuItems = new ArrayList<>();

    private OnItemClickListener onItemClickListener ;
    private OnItemLongClickListener onItemLongClickListener ;

    /**
     * Wrapper class that provides the OnClickListener interface for the CardViews.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        /**
         * Constructor.
         * @param view The view being created/inflated/held. (Not actually sure!)
         */
        public ViewHolder(View view) {
            super(view);
            if (view instanceof CardView) {
                view.setOnClickListener(this);
                view.setOnLongClickListener(this);

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

        /**
         * Override the onLongClick method to implement the onLongClickListener interface. Put the callback in here.
         * @param view  The view which has been clicked.
         */
        @Override
        public boolean onLongClick(View view) {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(view, getLayoutPosition());
                return true;
            }
            return false ;
        }

    }

    /**
     * Interface for the OnClickListener of the card in the ViewHolder.
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * Interface for the OnLongClickListener of the card in the ViewHolder.
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    /**
     * 'Setter' method for the OnItemClickListener
     * @param mItemClickListener The ItemClickListener to be assigned.
     */
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }
    /**
     * 'Setter' method for the OnItemLongClickListener
     * @param mItemLongClickListener The ItemLongClickListener to be assigned.
     */
    public void setOnItemLongClickListener(final OnItemLongClickListener mItemLongClickListener) {
        this.onItemLongClickListener = mItemLongClickListener;
    }


    /**
     * Constructor.
     * @param context The application context. No longer required. Remove this.
     * @param menu The menu of the chef
     */
    public MenuRVAdapter(Context context, ChefMenu menu) {
        this.context = context ;
        this.menu = menu ;
        this.menuItems = menu.getMenuItems() ;
    }

    /**
     * Method to create the view holders. Will return difference classes of view holder depending
     * on the position of the view in the layout.
     * @param viewGroup The ViewGroup to be inflated.
     * @param viewType The type of view to be inflated - defined at the top of this MenuRVAdapter class.
     * @return The appropriate ViewHolder for the position.
     */
    @Override
    public MenuRVAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView ;
        if (viewType == TYPE_HEADER) {
            itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.card_chef_bio, viewGroup, false);
            return new VHHeader(itemView);
        } else if (viewType == TYPE_DIVIDER) {
            itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.frame_menu_divider, viewGroup, false) ;
            return new VHDivider(itemView) ;
        } else if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.card_menu_item, viewGroup, false);
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
        if (viewHolder instanceof VHHeader) {
            VHHeader vH = (VHHeader) viewHolder;
            vH.tvChefBioShort.setText(menu.getChefBioShort());
            vH.tvChefBioLong.setText(menu.getChefBioLong());
        } else if (viewHolder instanceof VHItem) {
            VHItem vH = (VHItem) viewHolder ;
            // Get Menu
            final ChefMenuItem menuItem = menuItems.get(position-2);
            // Populate details
            vH.tvItemID.setText(menuItem.getItemID() + "");
            vH.tvItemTitle.setText(menuItem.getTitle());
            vH.tvItemSubtitle.setText(menuItem.getSubtitle());
            vH.tvItemPrice.setText(menuItem.getPriceString());
        }
    }

    /**
     * Returns the number of items to be displayed in the RecyclerView
     * This is equal to (the number of items in the chef's menu) + 2:
     *      One extra for the CardView holding the chef's bio.
     *      Another extra one is for the menu divider.
     * @return The number of menu items.
     */
    @Override
    public int getItemCount() {
        return menuItems.size() + 2;
    }

    /**
     * Method to determine what type of view is to be inflated, given its position in the RecyclerView.
     * @param position The position in the RecyclerView.
     * @return The type, one of TYPE_HEADER, TYPE_DIVIDER, or TYPE_ITEM.
     */
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        } else if (isPositionDivider(position)) {
            return TYPE_DIVIDER ;
        } else {
            return TYPE_ITEM;
        }
    }

    /**
     * Method to determine if a view type should be the header, given its position.
     * Essentially it returns whether the position is equal to zero.
     * @param position The position in the RecyclerView.
     * @return boolean of whether the view at the given position should be of TYPE_HEADER.
     */
    private boolean isPositionHeader(int position) {
        return position == 0 ;
    }
    /**
     * Method to determine if a view type should be the menu divider, given its position.
     * Essentially it returns whether the position is equal to one.
     * @param position The position in the RecyclerView.
     * @return boolean of whether the view at the given position should be of TYPE_DIVIDER.
     */
    private boolean isPositionDivider(int position) {
        return position == 1 ;
    }

    /**
     * ViewHolder subclass for the main menu items. Instances of the necessary text views to be
     * populated later are found/obtained here.
     */
    class VHItem extends MenuRVAdapter.ViewHolder {
        protected RelativeLayout layout ;
        protected TextView tvItemID;
        protected TextView tvItemTitle;
        protected TextView tvItemSubtitle;
        protected TextView tvItemPrice;

        /**
         * Constructor
         * @param view The view to be held.
         */
        public VHItem(View view) {
            super(view);
            layout      = (RelativeLayout) view.findViewById(R.id.menu_item_layout) ;
            tvItemID    = (TextView) view.findViewById(R.id.menu_item_id) ;
            tvItemTitle = (TextView) view.findViewById(R.id.menu_item_title) ;
            tvItemSubtitle = (TextView) view.findViewById(R.id.menu_item_subtitle) ;
            tvItemPrice = (TextView) view.findViewById(R.id.menu_item_price) ;
        }
    }

    /**
     * ViewHolder subclass for the header item - i.e. the chef's bio.
     * Instances of the necessary text views to be populated later are found/obtained here.
     */
    class VHHeader extends MenuRVAdapter.ViewHolder {
        protected TextView tvChefBioShort;
        protected TextView tvChefBioLong;
        /**
         * Constructor
         * @param view The view to be held.
         */
        public VHHeader(View view) {
            super(view);
            tvChefBioShort = (TextView) view.findViewById(R.id.chef_bio_short);
            tvChefBioLong = (TextView) view.findViewById(R.id.chef_bio_long);
        }
    }

    /**
     * ViewHolder subclass for the menu divider item.
     * Instances of the necessary text views to be populated later are found/obtained here.
     */
    class VHDivider extends MenuRVAdapter.ViewHolder {
        /**
         * Constructor
         * @param view The view to be held.
         */
        public VHDivider(View view) {
            super(view);
        }
    }

}
