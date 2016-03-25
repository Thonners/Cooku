package com.thonners.kooku;

import android.support.v7.widget.RecyclerView;
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

    private ArrayList<Chef> chefs = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is a chef in our case
        // All the views
        public View cardView;
        protected RelativeLayout layout ;
        protected ImageView imageView ;
        protected TextView tvChefName ;
        protected TextView tvChefStyle ;
        protected TextView tvChefPrice ;
        protected TextView tvChefETA ;
        public ViewHolder(View view) {
            super(view);
            cardView = view;
            layout      = (RelativeLayout) view.findViewById(R.id.result_relative_layout) ;
            imageView   = (ImageView) view.findViewById(R.id.result_image_view);
            tvChefName  = (TextView) view.findViewById(R.id.chef_name) ;
            tvChefStyle = (TextView) view.findViewById(R.id.chef_style) ;
            tvChefPrice = (TextView) view.findViewById(R.id.chef_price) ;
            tvChefETA   = (TextView) view.findViewById(R.id.chef_eta) ;

        }
    }

    /**
     * Constructor.
     */
    public SearchResultsRVAdapter(ArrayList<Chef> chefs) {
        this.chefs = chefs ;
    }

    // Create new views (invoked by layout manager)
    @Override
    public SearchResultsRVAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.search_result_card_view, viewGroup, false);

        return new ViewHolder(itemView);
    }

    // Populate views
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        // Get Chef
        Chef chef = chefs.get(position);
        // Populate details
        viewHolder.tvChefName.setText(chef.getChefName());
        viewHolder.tvChefStyle.setText(chef.getChefStyle());
        viewHolder.tvChefPrice.setText(chef.getChefPrice());
        viewHolder.tvChefETA.setText(chef.getChefETA() + "\nmins");
        // Set background
        viewHolder.imageView.setAdjustViewBounds(false);
        viewHolder.imageView.setImageDrawable(chef.getChefImage());
        //viewHolder.layout.setBackground(chef.getChefImage());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return chefs.size();
    }
}
