package com.thonners.kooku;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ReviewActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ReviewActivity" ;
    private String chefName ;
    private String deliveredDate ;
    private int rating ;
    private boolean ratingEntered ;

    private ImageView mainImageView ;
    private TextView tvQuestion, tvDeliveredDate ;
    private EditText etReview ;
    private CardView footerButton ;
    private ImageView[] stars = new ImageView[5] ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the views
        mainImageView = (ImageView) findViewById(R.id.review_main_image);
        tvQuestion = (TextView) findViewById(R.id.review_question) ;
        tvDeliveredDate = (TextView) findViewById(R.id.review_delivered) ;
        etReview = (EditText) findViewById(R.id.review_review_text);
        footerButton = (CardView) findViewById(R.id.review_footer_button);
        // Stars
        stars[0] = (ImageView) findViewById(R.id.review_star_1);
        stars[1] = (ImageView) findViewById(R.id.review_star_2);
        stars[2] = (ImageView) findViewById(R.id.review_star_3);
        stars[3] = (ImageView) findViewById(R.id.review_star_4);
        stars[4] = (ImageView) findViewById(R.id.review_star_5);
        setStarOnClickListeners() ;

        // Get the intent extras
        Intent intent = getIntent() ;
        chefName = intent.getStringExtra(OrderTrackingActivity.CHEF_NAME_EXTRA);
        deliveredDate = intent.getStringExtra(OrderTrackingActivity.DELIVERY_DATE_EXTRA);

        // Format the text
        String reviewQuestion = String.format(getResources().getString(R.string.review_question),chefName) ;
        String deliveredDateString = String.format(getResources().getString(R.string.review_delivered),deliveredDate) ;
        // Set the text in the TextViews
        tvQuestion.setText(reviewQuestion);
        tvDeliveredDate.setText(deliveredDateString);
    }

    private void setStarOnClickListeners(){
        for (int i = 0 ; i < stars.length ; i++) {
            createStarOnClickListener(i, stars[i]);
        }
    }

    private void createStarOnClickListener(final int starIndex, ImageView star) {
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRating(starIndex + 1);
            }
        });
    }

    private void setRating(int newRating) {
        if(!ratingEntered) {
            ratingEntered = true;
            footerButton.setVisibility(View.VISIBLE);
        }
        rating = newRating;
        updateStarColours();
    }

    private void updateStarColours() {
        int targetStarIndex = rating - 1 ;
        for (int i = 0 ; i < stars.length ; i++) {
            if (i <= targetStarIndex) {
                stars[i].setColorFilter(getResources().getColor(R.color.star_gold));
            } else {
                stars[i].setColorFilter(getResources().getColor(R.color.star_grey));
            }
        }
    }

    private boolean reviewLeft() {
        return etReview.getText().toString().length() > 0 ;
    }

    public void submitRatingClicked(View view) {
        // Submit the rating
        Log.d(LOG_TAG, "Rating to be submitted: " + rating) ;
        if (reviewLeft()) {
            // Review has been left, so submit the rating & review
            Log.d(LOG_TAG, "Review to be submitted: " + etReview.getText().toString()) ;
        } else {
            // Prompt for a review
            Log.d(LOG_TAG, "Review not left yet.") ;
        }
    }
}
