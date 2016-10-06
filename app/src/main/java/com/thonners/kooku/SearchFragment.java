package com.thonners.kooku;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private final String LOG_TAG = "SearchFragment" ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // Carryover Stuff from the original search activity
    private Context context ;
    private RecyclerView recyclerView ;
    private RecyclerView.Adapter rcAdapter;
    private RecyclerView.LayoutManager rcLayoutManager;

    // Filter menu
    private boolean[] filters = new boolean[8] ; // Order: Veggie, Vegan, Nut Free, Gluten Free, Halal, Dairy Free, Egg Free, Low Fat

    private ChefManager chefManager;

    private SearchResultsRVAdapter.OnItemClickListener onItemClickListener = new SearchResultsRVAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            launchChefPage(view) ;
        }
    };


    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the layout inflater
        View view = inflater.inflate(R.layout.fragment_search,container, false) ;
        // Hide keyboard
        if (mListener != null) {
            mListener.hideKeyboard();
        }

        // Initialise the chefManager
        chefManager  = new ChefManager(context);

        // Get the instance of the view
        recyclerView = (RecyclerView) view.findViewById(R.id.search_results_recycler_view) ;

        // Use this setting to improve performance given that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Use a StaggeredGridLayoutManager - set span to 1, to make it just a vertical list, but maintains possibility to increase number of rows later (tablet?)
        rcLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(rcLayoutManager);

        // Set the adapter
        SearchResultsRVAdapter adapter = new SearchResultsRVAdapter(context, chefManager.getChefs());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        return view ;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context ;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMyDetailsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Interface to the main RootActivity.
     */
    public interface OnFragmentInteractionListener {
        void onSearchFragmentInteraction();
        void setChef(Chef chef) ;
        void hideKeyboard() ;
        void startActivity(Intent intent, Bundle bundle);
    }



    /**
     * Method to search for the chef who could deliver the food in the soonest possible time.
     * Method will be called when the user clicks on the card.
     * @param view The instance of the CardView which is the ASAP card.
     */
    public void asapClicked(View view) {
        Log.d(LOG_TAG, "A.S.A.P. button clicked.");
    }

    /**
     * Method to search for chefs based on the user's current location
     * Method will be called when the user clicks on the card.
     * @param view The instance of the CardView which is the location card.
     */
    public void locationClicked(View view) {
        Log.d(LOG_TAG, "Location button clicked.");
    }

    /**
     * Method to launch the new intent of the chef's page.
     * @param resultsCardView The card containing the chef's results.
     */
    private void launchChefPage(View resultsCardView) {
        Log.d(LOG_TAG, "Launch chef page of: " + ((TextView) resultsCardView.findViewById(R.id.chef_name)).getText());
        // Get view for the transition
        ImageView chefImage = (ImageView) resultsCardView.findViewById(R.id.chef_image);
        // Define the pairs
        Pair<View, String> imagePair = Pair.create((View) chefImage, "tImage") ;
        // Define the transition options
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imagePair) ;
        // Define the intent of the MenuActivity
        Intent chefPageIntent = new Intent(context, MenuActivity.class);
        // Add chef id
        TextView tvID = (TextView) resultsCardView.findViewById(R.id.chef_id) ;
        chefPageIntent.putExtra(Chef.CHEF_ID,Integer.parseInt(tvID.getText() + ""));
        // Start the activity, with the transition
        ActivityCompat.startActivity(getActivity(), chefPageIntent,options.toBundle());
    }

    /**
     * Method to show the PopupMenu filter menu when the filter button is clicked.
     * Method also contains the logic to ensure that the filter selection is persistent, and survives
     * re-opening the menu.
     * @param view The filter button
     */
    public void filterClicked(View view) {
        PopupMenu filterMenu = new PopupMenu(context,view);
        filterMenu.inflate(R.menu.search_filter_menu);
        filterMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // Toggle the status
                item.setChecked(!item.isChecked()) ;

                // For each filter, toggle the boolean[] filters value if the filter is pressed.
                // When the menu closes, updateResults() will be called by the OnDismissListener.
                switch (item.getItemId()) {
                    case R.id.filter_veggie:
                        // Vegetarian filter
                        Log.d(LOG_TAG,"Veggie filter selected: " + R.id.filter_veggie);
                        filters[0] = !filters[0] ;
                        break ;
                    case R.id.filter_vegan:
                        // Vegan filter
                        Log.d(LOG_TAG,"Vegan filter selected");
                        filters[1] = !filters[1] ;
                        break ;
                    case R.id.filter_nut_free:
                        // Nut free filter
                        Log.d(LOG_TAG,"Nut free filter selected: " + R.id.filter_veggie);
                        filters[2] = !filters[2] ;
                        break ;
                    case R.id.filter_gluten_free:
                        // Gluten free filter
                        Log.d(LOG_TAG,"Gluten Free filter selected");
                        filters[3] = !filters[3] ;
                        break ;
                    case R.id.filter_halal:
                        // Halal filter
                        Log.d(LOG_TAG,"Halal filter selected: " + R.id.filter_veggie);
                        filters[4] = !filters[4] ;
                        break ;
                    case R.id.filter_dairy_free:
                        // Dairy free filter
                        Log.d(LOG_TAG,"Dairy free filter selected");
                        filters[5] = !filters[5] ;
                        break ;
                    case R.id.filter_egg_free:
                        // Egg free filter
                        Log.d(LOG_TAG,"Egg free filter selected: " + R.id.filter_veggie);
                        filters[6] = !filters[6] ;
                        break ;
                    case R.id.filter_low_fat:
                        // Low fat filter
                        Log.d(LOG_TAG,"Low fat filter selected");
                        filters[7] = !filters[7] ;
                        break ;
                }
                // The menu interaction was handled.
                return false ;
            }
        });
        filterMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Log.d(LOG_TAG,"onDismiss called");
                // Refresh the results based on the filters
                updateResults();
            }
        });

        // setChecked the already active options
        Menu menu = filterMenu.getMenu() ;
        for (int i = 0 ; i < menu.size() ; i++) {
            menu.getItem(i).setChecked(filters[i]) ;
        }

        // Show the menu
        filterMenu.show();
    }

    /**
     * Method to update the recommended chefs results based on the filters selected by the user.
     */
    private void updateResults() {
        // Update the search/recommended results based on search filters.
    }



}
