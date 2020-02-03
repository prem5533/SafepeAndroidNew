package com.safepayu.wallet.ecommerce.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.fragment.ThankYouFragment;
import com.safepayu.wallet.ecommerce.model.request.ReviewRequest;
import com.safepayu.wallet.models.response.BaseResponse;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ReviewProduct extends AppCompatActivity {

    private TextView tvSkipBtn,tvSubmitBtn,tvProductName;
    private RatingBar ratingBar;
    private ImageView imageViewProduct;
    private EditText edReview;
    private String Order_details_id="",Order_id="",Rating="",Product_name="",Product_image="",Product_reviewe="";
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_product_fragment);
        findId();
    }

    private void findId() {
        loadingDialog=new LoadingDialog(this);
        tvSkipBtn=findViewById(R.id.skipBtn_reviewProducts);
        tvSubmitBtn=findViewById(R.id.SubmitBtn_Review);
        tvProductName=findViewById(R.id.productName_Review);
        imageViewProduct=findViewById(R.id.productImage_Review);
        ratingBar=findViewById(R.id.productRating_Review);
        edReview=findViewById(R.id.review_Review);

        try {

            Order_details_id=getIntent().getStringExtra("Order_details_id");
            Order_id=getIntent().getStringExtra("Order_id");
            Rating=getIntent().getStringExtra("Rating");
            Product_name=getIntent().getStringExtra("Product_name");
            Product_image=getIntent().getStringExtra("Product_image");
            Product_reviewe=getIntent().getStringExtra("Product_review");

            tvProductName.setText(Product_name);

          /*  if (Product_reviewe.equals("")){
                edReview.setText("");
            } else {*/
              //  edReview.setText(Product_reviewe);
            ratingBar.setRating(Float.parseFloat(Rating));

            try {
                if (TextUtils.isEmpty(Product_image)) {

                } else {
                    Picasso.get()
                            .load(ApiClientEcom.ImagePath + Product_image)
                            .error(getResources().getDrawable(R.drawable.image_not_available))
                            .into(imageViewProduct);
                }
            } catch (Exception er) {
                er.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        tvSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    if (TextUtils.isEmpty(edReview.getText().toString().trim())){
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.reviewLayout),"Please Enter Your Review",true);
                    }else {
                        ReviewRequest reviewRequest=new ReviewRequest();

                        reviewRequest.setOrder_detail_id(Order_details_id);
                        reviewRequest.setOrder_id(Order_id);
                        reviewRequest.setRating(Rating);
                        reviewRequest.setReview(edReview.getText().toString());
                        getSaveReviewRat(reviewRequest);
                    }
                }else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.reviewLayout),"No Internet Connection",true);
                }
            }
        });

        tvSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getSaveReviewRat(ReviewRequest reviewRequest) {

        loadingDialog.showDialog(getString(R.string.loading_message), false);

        ApiServiceEcom apiService = ApiClientEcom.getClient(this).create(ApiServiceEcom.class);

        BaseApp.getInstance().getDisposable().add(apiService.getSaveReviewRat(reviewRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {
                            Toast.makeText(ReviewProduct.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ReviewProduct.this, ThankYouFragment.class));
                            finish();

                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.reviewLayout),response.getMessage(),true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.reviewLayout), true, e);
                    }
                }));
    }
}

