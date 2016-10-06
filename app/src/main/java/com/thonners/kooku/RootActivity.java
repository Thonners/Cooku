package com.thonners.kooku;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Main Activity, with navigation bar layout and Fragments.
 *
 * Default Fragment should be search (aka find food).
 *
 * @author M Thomas
 * @since 04/10/16
 */
public class RootActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener, MyOrdersFragment.OnMyOrdersFragmentInteractionListener, MyDetailsFragment.OnMyDetailsFragmentInteractionListener, ManageAddressesFragment.OnManageAddressesFragmentInteractionListener, ManagePaymentFragment.OnManagePaymentsFragmentInteractionListener, CodeShareFragment.OnCodeShareFragmentInteractionListener, AboutUsFragment.OnFragmentInteractionListener {

    private final String LOG_TAG = "RootActivity" ;

    // Buying Food Workflow Stuff
    private Chef chef = null ;
    private Basket basket ;

    // Nav bar stuff
    private String[] navBarList ;
    private DrawerLayout navBarLayout ;
    private ListView navBarListView ;
    private CharSequence mTitle ;

    // Nav bar item positions
    private final int NB_FIND_FOOD = 0 ;
    private final int NB_MY_ORDERS = 1 ;
    private final int NB_MY_DETAILS = 2 ;
    private final int NB_MANAGE_ADDRESSES = 3 ;
    private final int NB_MANAGE_PAYMENT = 4 ;
    private final int NE_CODE_SHARE = 5 ;
    private final int NE_ABOUT = 6 ;
    private final int NB_LOGOUT = 7 ;

    /**
     * Standard override of the onCreate method for an activity.
     * Gets instances of the views.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG,"onCreate called") ;
        // Set view
        setContentView(R.layout.activity_search_nav) ;
        // Get nav bar instances/resources
        navBarList = getResources().getStringArray(R.array.nav_bar_list) ;
        navBarLayout = (DrawerLayout) findViewById(R.id.search_drawer_layout) ;
        navBarListView = (ListView) findViewById(R.id.home_nav_bar_list) ;

        // Set the adapter for the Navigation bar
        navBarListView.setAdapter(new ArrayAdapter<>(this,R.layout.drawer_list_item,navBarList));
        // Set the on click listener
        navBarListView.setOnItemClickListener(new DrawerItemClickListener());

        Log.d(LOG_TAG,"onCreate finished") ;
    }

    /**
     * Listener for clicks to items in the NavBar Drawer ListView
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     * Responds to touches to NavBar items:
     * Selects and launches a Fragment/other where appropriate, depending on what was touched.
     * Called by the onItemClick listener.
     * @param position The position in the NavBar ListView
     */
    private void selectItem(int position){

        Log.d(LOG_TAG,"selectItem called") ;

        Fragment newFrag ;

        switch (position) {
            default:
                // Let the default selection overflow into the default
            case NB_FIND_FOOD :
                newFrag = new SearchFragment() ;
                break ;
            case NB_MY_ORDERS :
                newFrag = new MyOrdersFragment() ;
                break ;
            case NB_MY_DETAILS :
                newFrag = new MyDetailsFragment() ;
                break ;
            case NB_MANAGE_ADDRESSES :
                newFrag = new ManageAddressesFragment() ;
                break ;
            case NB_MANAGE_PAYMENT :
                newFrag = new ManagePaymentFragment() ;
                break ;
            case NE_CODE_SHARE :
                newFrag = new CodeShareFragment() ;
                break ;
            case NE_ABOUT :
                newFrag = new AboutUsFragment() ;
                break ;
            case NB_LOGOUT :
                // TODO: Work out how to logout, etc. For now, it's just relaunching the SearchFragment
                newFrag = new SearchFragment() ;
                break ;
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.home_content_frame, newFrag)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        navBarListView.setItemChecked(position, true);
        setTitle(navBarList[position]);
        navBarLayout.closeDrawer(navBarListView);
    }

    /**
     * Sets the ActionBar title to the given String/CharSequence
     * @param title
     */
    @Override
    public void setTitle(CharSequence title) {
        Log.d(LOG_TAG,"Setting title to: " + title) ;
        mTitle = title;
        getSupportActionBar().setTitle(title);
    }

    /**
     * Interaction listener for the Search Fragment.
     */
    public void onSearchFragmentInteraction() {

    }

    public void startActivity(Intent intent, Bundle bundle){
        Log.d(LOG_TAG,"Starting activity...") ;
        ActivityCompat.startActivity(this,intent,bundle);
    }

    /**
     * SearchFragment interaction - set the active chef here.
     * @param chef The active Chef.
     */
    public void setChef(Chef chef) {
        this.chef = chef ;
    }

    /**
     * Hide the keyboard to prevent it popping up when a view gets focus
     */
    public void hideKeyboard() {
        // Stop the keyboard from popping up
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    /**
     * Interaction listener for Order Fragment
     * @param order The order touched
     */
    public void onMyOrdersFragmentInteraction(Order order) {

    }

    @Override
    public void onAboutUsFragmentInteraction() {

    }

    @Override
    public void onCodeShareFragmentInteraction() {

    }

    @Override
    public void onManageAddressesFragmentInteraction() {

    }

    @Override
    public void onManagePaymentsFragmentInteraction() {

    }

    @Override
    public void onMyDetailsFragmentInteraction() {

    }
}

