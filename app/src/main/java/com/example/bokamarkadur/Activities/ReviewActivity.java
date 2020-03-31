package com.example.bokamarkadur.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bokamarkadur.Adapters.ReviewsAdapter;
import com.example.bokamarkadur.POJO.Review;
import com.example.bokamarkadur.POJO.ReviewList;
import com.example.bokamarkadur.POJO.ReviewsResponse;
import com.example.bokamarkadur.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = "ReviewActivity";

    ReviewsAdapter reviewsAdapter;

    APIInterface apiInterface;

    private Button addReviewBtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Log.d(TAG, "onCreate: started.");

        // Hide System UI for best experience
        hideSystemUI();

        apiInterface = APIClient.getClient().create(APIInterface.class);


        final RecyclerView recyclerView = findViewById(R.id.reviews_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ReviewsAdapter(new ArrayList<Review>(), R.layout.list_reviews,
                getApplicationContext()));


        final String username = getIncomingIntent();
        Log.d(TAG, "username: " + username);


        TextView user = findViewById(R.id.review_receiver);
        user.setText("Reviews for " + username);



        final Call<ReviewsResponse> getReviews = apiInterface.viewReviews(username);
        getReviews.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                Log.d(TAG, "RESPONSE BODY: " + response.body().getClass());

                TextView noReviews = findViewById(R.id.no_reviews);


                List<Review> reviews = response.body().viewReviews();

                reviewsAdapter = new ReviewsAdapter(reviews, R.layout.list_reviews,
                        getApplicationContext());

                if (reviewsAdapter.getItemCount() != 0) {
                    recyclerView.setAdapter(reviewsAdapter);
                }
                else {
                    noReviews.setText("No Reviews available for " + username);
                }

            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                call.cancel();
            }

        });

        addReviewBtn = findViewById(R.id.add_review);

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (LoginActivity.token != null) {
                            Intent intent = new Intent(ReviewActivity.this, WriteReviewActivity.class);
                            intent.putExtra("username", username); //þurfti að vera declared final til að vera accessible
                            startActivity(intent);
                        }
                        else {
                         Toast.makeText(getApplicationContext(),
                            "You have to be logged in to write a review",
                            Toast.LENGTH_LONG).show();
                        }

                    }
                });

/*
        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData(username);
            }
        });

 */


    }

    private String getIncomingIntent(){

        String username = getIntent().getStringExtra("username");
        return username;
    }
/*
    private void submitData(String username) {
        EditText reviewBox = findViewById(R.id.edt_add_review);

        String reviewBody = reviewBox.getText().toString();

        progressDialog = new ProgressDialog(ReviewActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("reviewBody", reviewBody);


        Call<Review> newReview = apiInterface.writeReview("Bearer " + LoginActivity.token,
                "application/json", username,jsonObject);
        newReview.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                progressDialog.dismiss();
                Log.d("onResponse: ", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    openReviewActivity();
                    Log.d("Success: ", "Your review has been added.");
                } else {
                    try {
                        Log.d("error", response.errorBody().string());
                    } catch (Exception e) {
                        Log.d("error: ", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("myTag", "HELPHELP");
            }
        });

    }

 */

/*
    public void openReviewActivity() {
        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
    }

 */


    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
