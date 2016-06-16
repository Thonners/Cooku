package com.thonners.kooku;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Class to manage the reviews on an item page.
 *
 * Will populate the RecyclerView with CardViews using the pre-defined layout 'card_item_review.xml'
 *
 * OnClickListeners provide the appropriate interface, should clicking on a review wish to be useful
 * for something in the future - e.g. reading a users other reviews
 *
 * @author M Thomas
 * @since 16/06/16
 */

public class ItemReviewRVAdapter  extends RecyclerView.Adapter<ItemReviewRVAdapter.ViewHolder> {

    private final String LOG_TAG = "ItemReviewRVAdapter" ;

    private Context context ;
    private ArrayList<ReviewManager.Review> reviews = new ArrayList<>() ;

    private final int TYPE_DIVIDER = Integer.MIN_VALUE  ;
    private final int TYPE_REVIEW = Integer.MAX_VALUE ;

    private OnItemClickListener onItemClickListener ;
    private OnItemLongClickListener onItemLongClickListener ;

    /**
     * Constructor
     * @param context Activity context
     */
    public ItemReviewRVAdapter(Context context, ArrayList<ReviewManager.Review> reviews) {
        this.context = context ;
        this.reviews = reviews ;
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
     * Returns the number of items to be displayed in the RecyclerView
     * This is equal to (the number of reviews) + 1:
     *      The extra one is for the FrameView holding the 'user reviews' divider
     * @return The number of reviews.
     */
    @Override
    public int getItemCount() {
        return reviews.size() + 1;
    }
    /**
     * Method to determine what type of view is to be inflated, given its position in the RecyclerView.
     * Will return TYPE_DIVIDER for the view in position 0 only.
     * @param position The position in the RecyclerView.
     * @return The type, one of TYPE_DIVIDER, or TYPE_ITEM.
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_DIVIDER ;
        } else {
            return TYPE_REVIEW;
        }
    }

    /**
     * Wrapper class that provides the OnClickListener interface for the CardViews.
     * Currently clicks aren't used, but could be used in future to either expand long views, or
     * offer other options regarding users - e.g. read other user reviews, view user favourites, etc.
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
     * Method to create the view holders. Will return difference classes of view holder depending
     * on the position of the view in the layout.
     * @param viewGroup The ViewGroup to be inflated.
     * @param viewType The type of view to be inflated - defined at the top of this MenuRVAdapter class.
     * @return The appropriate ViewHolder for the position.
     */
    @Override
    public ItemReviewRVAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView ;
        if (viewType == TYPE_DIVIDER) {
            itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.frame_menu_divider, viewGroup, false) ;
            return new VHDivider(itemView) ;
        } else if (viewType == TYPE_REVIEW) {
            itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.card_item_review, viewGroup, false);
            return new VHReview(itemView) ;
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
        if (viewHolder instanceof VHDivider) {
            VHDivider vH = (VHDivider) viewHolder ;
            vH.tvDivider.setText(context.getString(R.string.review_divider));
        }
        if (viewHolder instanceof VHReview) {
            VHReview vH = (VHReview) viewHolder ;
            // Get Reviews
            final ReviewManager.Review review = reviews.get(position-1);
            // Populate details
            vH.tvUserName.setText(review.getUsername());
            vH.tvReview.setText(review.getReview());
            vH.tvReviewTitle.setText(review.getReviewTitle());
            // Loop through stars and colour gold appropriately
            for (int i = 0 ; i < review.getRating() ; i++) {
                vH.stars[i].setColorFilter(context.getResources().getColor(R.color.star_gold));
            }
        }
    }
    /**
     * ViewHolder subclass for the review-divider item.
     */
    class VHDivider extends ItemReviewRVAdapter.ViewHolder {
        protected TextView tvDivider;
        /**
         * Constructor
         * @param view The view to be held.
         */
        public VHDivider(View view) {
            super(view);
            tvDivider = (TextView) view.findViewById(R.id.menu_divider_tv) ;
        }
    }
    /**
     * ViewHolder subclass for the main review cards. Instances of the necessary text views to be
     * populated later are found/obtained here.
     */
    class VHReview extends ItemReviewRVAdapter.ViewHolder {
        protected ImageView userImage ;
        protected TextView tvUserName;
        protected TextView tvReviewTitle;
        protected TextView tvReview;
        protected ImageView[] stars = new ImageView[5];
        /**
         * Constructor
         * @param view The view to be held.
         */
        public VHReview(View view) {
            super(view);
            userImage   = (ImageView) view.findViewById(R.id.review_card_user_image) ;
            tvUserName = (TextView) view.findViewById(R.id.review_card_user_name) ;
            tvReviewTitle = (TextView) view.findViewById(R.id.review_card_item_name) ;
            tvReview = (TextView) view.findViewById(R.id.review_card_text) ;
            // Get the stars
            stars[0]   = (ImageView) view.findViewById(R.id.review_star_1) ;
            stars[1]   = (ImageView) view.findViewById(R.id.review_star_2) ;
            stars[2]   = (ImageView) view.findViewById(R.id.review_star_3) ;
            stars[3]   = (ImageView) view.findViewById(R.id.review_star_4) ;
            stars[4]   = (ImageView) view.findViewById(R.id.review_star_5) ;

        }
    }
}
