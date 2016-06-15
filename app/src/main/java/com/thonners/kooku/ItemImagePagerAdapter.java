package com.thonners.kooku;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * PagerAdapter to supply images to the Toolbar for an item
 *
 * @author M Thomas
 * @since 15/06/2016
 */

public class ItemImagePagerAdapter extends PagerAdapter {

    private Context mContext ;
    private ArrayList<Drawable> images = new ArrayList<>() ;


    /**
     * Constructor
     * @param context
     */
    public ItemImagePagerAdapter(Context context) {
        this.mContext = context;
        getImages();
    }

    /**
     * Method to populate the images in the ArrayList.
     * Currently just sets the images to the same 3 for every item. In practice will download the
     * images from the server.
     */
    private void getImages() {
        images.add(mContext.getResources().getDrawable(R.drawable.demo_chef_cake_1));
        images.add(mContext.getResources().getDrawable(R.drawable.demo_chef_cake_2));
        images.add(mContext.getResources().getDrawable(R.drawable.demo_chef_danish));
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        ImageView image = new ImageView(mContext) ;
        image.setImageDrawable(images.get(position));

        return image ;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return images.size() ;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "ItemImage_"+ position ;
    }

}
