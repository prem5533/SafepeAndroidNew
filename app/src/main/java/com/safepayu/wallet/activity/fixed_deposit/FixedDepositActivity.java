package com.safepayu.wallet.activity.fixed_deposit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.WalletActivity;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.ResponseModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FixedDepositActivity extends AppCompatActivity implements View.OnClickListener {

    public LinearLayout ll_back, ll_fixed_deposit, ll_paytm_wallet,ll_total_amount;
    private LoadingDialog loadingDialog;
    public TextView tv_total_deposit_amount, tv_fixed_deposit_amount, tv_wallet_amount, tv_fixed_deposit_interest_amount, tv_safepe_wallet_interest_amount;
    public String depositAmount, fdInterest, balanceAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_deposit);

        loadingDialog = new LoadingDialog(FixedDepositActivity.this);

        ll_fixed_deposit = findViewById(R.id.ll_fixed_deposit);
        ll_paytm_wallet = findViewById(R.id.ll_paytm_wallet);
        ll_total_amount = findViewById(R.id.ll_total_amount);
        ll_back = findViewById(R.id.ll_back);
        tv_total_deposit_amount = findViewById(R.id.tv_total_deposit_amount);
        tv_fixed_deposit_amount = findViewById(R.id.tv_fixed_deposit_amount);
        tv_wallet_amount = findViewById(R.id.tv_wallet_amount);
        tv_fixed_deposit_interest_amount = findViewById(R.id.tv_fixed_deposit_interest_amount);
        tv_safepe_wallet_interest_amount = findViewById(R.id.tv_safepe_wallet_interest_amount);

        ll_fixed_deposit.setOnClickListener(this);
        ll_paytm_wallet.setOnClickListener(this);
        ll_total_amount.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        safepeInvestmentAccount();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_fixed_deposit:
                startActivity(new Intent(FixedDepositActivity.this, AvailableBalanceActivity.class).
                        putExtra("depositAmount", depositAmount).
                        putExtra("fdInterest", fdInterest).putExtra("balanceAmount", balanceAmount));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;

            case R.id.ll_paytm_wallet:
                startActivity(new Intent(FixedDepositActivity.this, WalletActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;

            case R.id.ll_total_amount:
                startActivity(new Intent(FixedDepositActivity.this, FixedDepositListActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;

            case R.id.ll_back:
                finish();
                break;
        }
    }

    private void safepeInvestmentAccount() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(FixedDepositActivity.this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.safepeInvestmentAccount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResponseModel>() {
                    @Override
                    public void onSuccess(ResponseModel response) {
                        loadingDialog.hideDialog();
                        if (response.status) {
                            try {
                                depositAmount = response.data.fdbal_amount;
                                fdInterest = response.data.fb_interest;
                                balanceAmount = response.data.balance_amount;
                                tv_total_deposit_amount.setText(response.data.balance_amount);
                                tv_fixed_deposit_amount.setText(response.data.fdbal_amount);
                                tv_wallet_amount.setText(response.data.wallet_amount);
                                tv_fixed_deposit_interest_amount.setText("Earn upto " + response.data.fb_interest + "% interest per annum on your fixed deposits");
                                //  tv_safepe_wallet_interest_amount.setText(response.data.investment_interest);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(FixedDepositActivity.this.findViewById(R.id.ll_parant), response.message, false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(FixedDepositActivity.this.findViewById(R.id.ll_parant), false, e.getCause());
                    }
                }));
    }
}
