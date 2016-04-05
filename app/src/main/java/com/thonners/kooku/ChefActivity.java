package com.thonners.kooku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Activity to display a chef's home page.
 *
 * @author M Thomas
 * @since 25/03/16
 */
public class ChefActivity extends AppCompatActivity {

    private ChefManager chefManager;
    private Chef chef ;


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

        ImageView chefImage = (ImageView) findViewById(R.id.chef_image);
        chefImage.setImageDrawable(chef.getChefImage());
    }


}
