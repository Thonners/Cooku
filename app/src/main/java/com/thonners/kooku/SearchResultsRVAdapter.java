package com.thonners.kooku;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Class to manage the RecyclerView. Extends the RecyclerView.Adapter class
 *
 * @author M Thomas
 * @since 24/03/16.
 */

public class SearchResultsRVAdapter extends RecyclerView.Adapter<SearchResultsRVAdapter.ViewHolder> {

    private final String LOG_TAG = "SearchResultsRVAdapter" ;

    private Context context ;
    private ArrayList<Chef> chefs ;

    private OnItemClickListener onItemClickListener ;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is a chef in our case
        // All the views
        public View cardView;
        protected RelativeLayout layout ;
        protected ImageView imageView ;
        protected TextView tvChefID ;
        protected TextView tvChefName ;
        protected TextView tvChefStyle ;
        protected TextView tvChefPrice ;
        protected TextView tvChefETA ;
        public ViewHolder(View view) {
            super(view);
            cardView = view;
            layout      = (RelativeLayout) view.findViewById(R.id.result_relative_layout) ;
            imageView   = (ImageView) view.findViewById(R.id.chef_image);
            tvChefID    = (TextView) view.findViewById(R.id.chef_id) ;
            tvChefName  = (TextView) view.findViewById(R.id.chef_name) ;
            tvChefStyle = (TextView) view.findViewById(R.id.chef_style) ;
            tvChefPrice = (TextView) view.findViewById(R.id.chef_price) ;
            tvChefETA   = (TextView) view.findViewById(R.id.chef_eta) ;

            cardView.setOnClickListener(this);
        }

        /**
         * Override the onClick method to implement the onClickListener interface. Put the callback in here.
         * @param view  The view which has been clicked.
         */
        @Override
        public void onClick(View view){
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(itemView, getPosition());
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
     * @param chefs An ArrayList of the chefs.
     */
    public SearchResultsRVAdapter(Context context, ArrayList<Chef> chefs) {
        this.context = context ;
        this.chefs = chefs ;
    }

    // Create new views (invoked by layout manager)
    @Override
    public SearchResultsRVAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_chef_search_result, viewGroup, false);

        return new ViewHolder(itemView);
    }

    // Populate views
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position){
        // Get Chef
        final Chef chef = chefs.get(position);
        // Populate details
        viewHolder.tvChefID.setText(chef.getChefIDString());
        viewHolder.tvChefName.setText(chef.getChefName());
        viewHolder.tvChefStyle.setText(chef.getChefStyle());
        viewHolder.tvChefPrice.setText(chef.getChefPrice());
        viewHolder.tvChefETA.setText(chef.getChefETA() + "\nmins");
        // Set background
        viewHolder.imageView.setAdjustViewBounds(false);
        viewHolder.imageView.setImageDrawable(chef.getChefImage());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d(LOG_TAG,"Chefs size = " + chefs.size()) ;
        return chefs.size();
    }
}
