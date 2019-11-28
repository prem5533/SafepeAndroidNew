package com.safepayu.wallet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.OfferPagerAdapter;
import com.safepayu.wallet.adapter.SplashPagerAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.PromotionRequest;
import com.safepayu.wallet.models.response.PromotionResponse;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SplashViewPagerActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager intro_images;
    private LoadingDialog loadingDialog;
    private LinearLayout llPagerDots;
    private TextView[] dots;
    SplashPagerAdapter splashPagerAdapter;
    private TextView tv_skip,tv_done;

    int NUM_PAGES,currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    private int dotscount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_view_pager);
        findId();
        if (isNetworkAvailable()) {
            getPrmotionalOffer();
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.splash_pager), "No Internet Connection", false);
        }

        /*After setting the adapter use the timer */
      /*  final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                intro_images.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
        dots = new TextView[NUM_PAGES];*/



    }

    private void findId() {

        loadingDialog = new LoadingDialog(this);
        intro_images = (ViewPager) findViewById(R.id.pager_introduction);
        llPagerDots = (LinearLayout) findViewById(R.id.ll_dots);
        tv_skip =  findViewById(R.id.tvskip);
        tv_done =  findViewById(R.id.tvdone);

        tv_skip.setOnClickListener(this);
        tv_done.setOnClickListener(this);

        intro_images.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getPrmotionalOffer() {

        // loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);


        final PromotionRequest promotionRequest = new PromotionRequest();
        promotionRequest.setType("2");
        BaseApp.getInstance().getDisposable().add(apiService.getPromotionOffer(promotionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PromotionResponse>() {
                    @Override
                    public void onSuccess(PromotionResponse promotionResponse) {

                        NUM_PAGES= promotionResponse.getData().size();

                        loadingDialog.hideDialog();
                        if (promotionResponse.isStatus()) {

                         splashPagerAdapter = new SplashPagerAdapter(SplashViewPagerActivity.this,promotionResponse.getData());
                            intro_images.setAdapter(splashPagerAdapter);
                            dotscount   =  splashPagerAdapter.getCount();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                    }
                })); }

    private void addBottomDots(int position) {
        dots = new TextView[NUM_PAGES];

        if (NUM_PAGES-1==position){
         //   tv_skip.setText("DONE");]
            tv_done.setVisibility(View.VISIBLE);
            tv_skip.setVisibility(View.GONE);

        }
        else if (position<NUM_PAGES-1){
            tv_done.setVisibility(View.GONE);
            tv_skip.setVisibility(View.VISIBLE);
        }

        llPagerDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#ffffff"));
            llPagerDots.addView(dots[i]);
        }

        if (dots.length > 0){
            dots[position].setTextColor(Color.parseColor("#fdb632"));
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvskip:
                int tab = intro_images.getCurrentItem();
                tab++;
                intro_images.setCurrentItem(tab);
                break;
            case R.id.tvdone:
                startActivity(new Intent(SplashViewPagerActivity.this, Navigation.class));
                finish();
                break;
        }
    }
}
