package com.thonners.kooku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Activity to display a chef's home page.
 *
 * @author M Thomas
 * @since 25/03/16
 */
public class ChefActivity extends AppCompatActivity {

    private final String LOG_TAG = "ChefActivity" ;

    private ChefManager chefManager;
    private Chef chef ;
    private ChefMenu menu ;
    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager rcLayoutManager;

    private MenuRVAdapter.OnItemClickListener onItemClickListener = new MenuRVAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(ChefMenu.ChefMenuItem item) {
            addItemToBasket(item);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        setTitle(getIntent().getStringExtra("CHEF_NAME")); // This shouldn't work

        // Initialise chefManager
        chefManager  = new ChefManager(this);

        // Get the Chef
        int chefID = getIntent().getIntExtra(Chef.CHEF_ID,1);
        chef = chefManager.getChefs(true).get(chefID);

        // Get the menu
        menu = chef.getMenu() ;

        // Get the instance of the view
        recyclerView = (RecyclerView) findViewById(R.id.chef_menu) ;

        // Use this setting to improve performance given that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Use a StaggeredGridLayoutManager - set span to 1, to make it just a vertical list, but maintains possibility to increase number of rows later (tablet?)
        rcLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(rcLayoutManager);

        // Set the adapter
        MenuRVAdapter adapter = new MenuRVAdapter(this, menu.getMenuItems());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        // Set the Chef background image
        ImageView chefImage = (ImageView) findViewById(R.id.chef_image);
        chefImage.setImageDrawable(chef.getChefImage());
    }

    private void addItemToBasket(ChefMenu.ChefMenuItem item) {
        Log.d(LOG_TAG, "Adding item: " + item.getTitle() + " to basket.");
    }

}
