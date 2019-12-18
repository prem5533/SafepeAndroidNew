package com.safepayu.wallet.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.ScratchListAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.SaveCoinRequest;
import com.safepayu.wallet.models.response.CoinLogResponse;
import com.safepayu.wallet.models.response.SaveCoinResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ScratchActivity extends AppCompatActivity implements ScratchListAdapter.OnScratchSelectListener, View.OnClickListener {

    private RecyclerView recyclerViewScratch;
    private Button BackBtn;
    private LoadingDialog loadingDialog;
    public  Dialog dialog;
    private TextView tvTotalCoins,tvCoins;
    private TextView tvNumberCoin,tvCreatedTime;
    private ScratchView scratchView;
    private SaveCoinRequest saveCoinRequest;
    ScratchListAdapter scratchListAdapter ;

    private String CoinLogId="";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scratch_activity);

        loadingDialog = new LoadingDialog(this);
        BackBtn = findViewById(R.id.backBtn_scratchLayout);
        recyclerViewScratch = findViewById(R.id.list_scratchLayout);
        tvTotalCoins = findViewById(R.id.tv_total_coins);
        tvCoins = findViewById(R.id.tv_coins);
       /* recyclerViewScratch.setLayoutManager(new GridLayoutManager(this, 2));

        ScratchListAdapter scratchListAdapter=new ScratchListAdapter(this,this);
        recyclerViewScratch.setAdapter(scratchListAdapter);*/
        BackBtn.setOnClickListener(this);


        if (isNetworkAvailable()){
            getCoinLog();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.scratchLayout),"Please Check Your Internet Connection",false);
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backBtn_scratchLayout:
                finish();
                break;

        }
    }

    private void getCoinLog() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getcoinLog()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CoinLogResponse>() {
                    @Override
                    public void onSuccess(CoinLogResponse response) {

                        if (response.isStatus()){
                            loadingDialog.hideDialog();
                            tvTotalCoins.setText(response.getData().getCoinWallet());
                            if (response.getData().getCoinWallet().equals("1")){
                                tvCoins.setText(" Coin"); }
                            else {
                                tvCoins.setText(" Coins"); }

                            recyclerViewScratch.setLayoutManager(new GridLayoutManager(ScratchActivity.this, 2));
                            scratchListAdapter=new ScratchListAdapter(ScratchActivity.this,response.getData().getLog(),ScratchActivity.this);
                            recyclerViewScratch.setAdapter(scratchListAdapter);
                            scratchListAdapter.notifyDataSetChanged();

                          /*  recyclerViewScratch.setLayoutManager(new LinearLayoutManager(getApplicationContext(), GridLayoutManager.VERTICAL, false));
                            scratchListAdapter = new ScratchListAdapter(getApplicationContext(),flightHistoryResponse.getData(),FlightHistoryActivity.this);
                            recyclerViewScratch.setAdapter(scratchListAdapter);*/
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.scratchLayout), false, e.getCause());
                    }
                }));

    }

    @Override
    public void onScratchSelect(int position, CoinLogResponse.DataBean.LogBean mLogCoin) {

        if (mLogCoin.getStatus()==0){
            showDialogCoin(position,mLogCoin,ScratchActivity.this); }


    }

    private void showDialogCoin(int position, final CoinLogResponse.DataBean.LogBean mLogCoin, ScratchActivity scratchActivity) {

        dialog = new Dialog(scratchActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_coin_dialog);

        tvNumberCoin = dialog.findViewById(R.id.tv_number_of_coin_dialog);
        tvCreatedTime = dialog.findViewById(R.id.tv_reward_created_dialog);
        scratchView = dialog.findViewById(R.id.scratch_view);

        CoinLogId = String.valueOf(mLogCoin.getId());
        if (mLogCoin.getStatus()==0){
            if (mLogCoin.getAmount()==1){
                tvNumberCoin.setText(String.valueOf(mLogCoin.getAmount())+" Coin");}
            else {
                tvNumberCoin.setText(String.valueOf(mLogCoin.getAmount())+" Coins"); }
            tvCreatedTime.setText(mLogCoin.getCreated_at());
        }


        scratchView.setRevealListener(new ScratchView.IRevealListener() {
            @Override
            public void onRevealed(ScratchView scratchView) {
                Toast.makeText(getApplicationContext(), "Reveled", Toast.LENGTH_LONG).show();
                if (isNetworkAvailable()){

                    getSaveCoin(mLogCoin);
                }else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.scratchLayout),"Please Check Your Internet Connection",false);
                }

            }

            @Override
            public void onRevealPercentChangedListener(ScratchView scratchView, float percent) {
                if (percent>=0.5) {
                    Log.d("Reveal Percentage", "onRevealPercentChangedListener: " + String.valueOf(percent));
                }
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.show();

       /* AlertDialog.Builder builder = new AlertDialog.Builder(scratchActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_flight_location_list);
        AlertDialog dialog = builder.create();

        // Finally, display the alert dialog
        dialog.show();*/
        // Get screen width and height in pixels
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // The absolute width of the available display size in pixels.
        int displayWidth = displayMetrics.widthPixels;
        // The absolute height of the available display size in pixels.
        int displayHeight = displayMetrics.heightPixels;

        // Initialize a new window manager layout parameters
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        // Copy the alert dialog window attributes to new layout parameter instance
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        // Set the alert dialog window width and height
        // Set alert dialog width equal to screen width 90%
        // int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 90%
        // int dialogWindowHeight = (int) (displayHeight * 0.9f);

        // Set alert dialog width equal to screen width 70%
        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 70%
        int dialogWindowHeight = (int) (displayHeight * 0.7f);

        // Set the width and height for the layout parameters
        // This will bet the width and height of alert dialog
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        // Apply the newly created layout parameters to the alert dialog window
        dialog.getWindow().setAttributes(layoutParams);


    }

    private void getSaveCoin(final CoinLogResponse.DataBean.LogBean mLogCoin) {
        saveCoinRequest = new SaveCoinRequest();
        saveCoinRequest.setCoinlogid(Integer.parseInt(CoinLogId));

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getSaveCoin(saveCoinRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SaveCoinResponse>(){

                    @Override
                    public void onSuccess(SaveCoinResponse saveCoinResponse) {
                        loadingDialog.hideDialog();
                        if (saveCoinResponse.isStatus()) {
                            mLogCoin.setStatus(1);
                            int coin = Integer.parseInt(tvTotalCoins.getText().toString());
                            int currentCoin =mLogCoin.getAmount();
                            int totalCoin = coin+currentCoin;
                            tvTotalCoins.setText(""+(Integer.parseInt(tvTotalCoins.getText().toString())+mLogCoin.getAmount()));
                            scratchListAdapter.notifyDataSetChanged();
                            dialog.dismiss();

                            }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.scratchLayout), false, e.getCause());
                    }
                }));
    }
}