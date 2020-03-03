package com.safepayu.wallet.activity.deposit_fixed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.safepe_investment.AvailableBalanceActivity;
import com.safepayu.wallet.activity.safepe_investment.InvestmentDepositActivity;
import com.safepayu.wallet.activity.safepe_investment.InvestmentProfileActivity;
import com.safepayu.wallet.activity.safepe_investment.InvestmentWallet;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.ExceptionLogRequest;
import com.safepayu.wallet.models.response.ResponseModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FixedDepositActivity extends AppCompatActivity implements View.OnClickListener {
    public LinearLayout liTotalAmountFD, liFixedDeposit, ll_profile_visibility, ll_fixed_deposit_wallet;
    private LoadingDialog loadingDialog;
    public Button send_back_btn_fd;
    public TextView tv_toolbar_name;
    public TextView tv_total_deposit_amount, tv_fixed_deposit_amount, tv_wallet_amount, tv_fixed_deposit_interest_amount,
            tv_fixed_deposit_wallet_amount;
    public String depositAmount, fdInterest, balanceAmount;
    private String DeviceName = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().DEVICE_NAME);
    private String UserId = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
    ExceptionLogRequest logRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_deposit2);

        loadingDialog = new LoadingDialog(FixedDepositActivity.this);

        findId();
    }

    private void findId() {
        liTotalAmountFD = findViewById(R.id.ll_total_amount_FD);
        liFixedDeposit = findViewById(R.id.ll_fixed_deposit);
        ll_profile_visibility = findViewById(R.id.ll_profile_visibility);
        send_back_btn_fd = findViewById(R.id.send_back_btn_fd);
        tv_toolbar_name = findViewById(R.id.tv_toolbar_name);
        ll_fixed_deposit_wallet = findViewById(R.id.ll_fixed_deposit_wallet);
        tv_fixed_deposit_wallet_amount = findViewById(R.id.tv_fixed_deposit_wallet_amount);

        tv_total_deposit_amount = findViewById(R.id.tv_total_deposit_amount);
        tv_fixed_deposit_amount = findViewById(R.id.tv_fixed_deposit_amount);
     //   tv_wallet_amount = findViewById(R.id.tv_wallet_amount);
        tv_fixed_deposit_interest_amount = findViewById(R.id.tv_fixed_deposit_interest_amount);

        liTotalAmountFD.setOnClickListener(this);
        liFixedDeposit.setOnClickListener(this);
        ll_profile_visibility.setOnClickListener(this);
        ll_fixed_deposit_wallet.setOnClickListener(this);
        send_back_btn_fd.setOnClickListener(this);

        safepeFixedDepostAccount();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_total_amount_FD:
                startActivity(new Intent(FixedDepositActivity.this, FixedDepositListActivity.class));
                break;
            case R.id.ll_fixed_deposit:
                startActivity(new Intent(FixedDepositActivity.this, AvailableBalanceFDActivity.class).
                        putExtra("depositAmount", depositAmount).
                        putExtra("fdInterest", fdInterest).
                        putExtra("balanceAmount", balanceAmount)
                );
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;

            case R.id.ll_profile_visibility:
                startActivity(new Intent(FixedDepositActivity.this, InvestmentProfileActivity.class).putExtra("TYPE", "FD"));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);

                break;
            case R.id.send_back_btn_fd:
                finish();
                break;
            case R.id.ll_fixed_deposit_wallet:
                startActivity(new Intent(FixedDepositActivity.this, FixedDepositWallet.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                break;
        }
    }

    private void safepeFixedDepostAccount() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(FixedDepositActivity.this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.safepeFixedDepostAccount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResponseModel>() {
                    @Override
                    public void onSuccess(ResponseModel response) {
                        loadingDialog.hideDialog();
                        if (response.status) {
                            try {
                                if (response.data.showprofile == 0) {
                                    ll_profile_visibility.setVisibility(View.GONE);
                                } else {
                                    ll_profile_visibility.setVisibility(View.VISIBLE);
                                }
                                depositAmount = response.data.fdbal_amount;
                                fdInterest = response.data.fb_interest;
                                balanceAmount = response.data.balance_amount;
                                tv_total_deposit_amount.setText(response.data.balance_amount);
                                tv_fixed_deposit_amount.setText(response.data.fdbal_amount);
                              //  tv_wallet_amount.setText(response.data.wallet_amount);
                                tv_fixed_deposit_wallet_amount.setText("" + response.data.investment_total);
                                tv_fixed_deposit_interest_amount.setText("Earn upto " + response.data.fb_interest + "% interest per day on your SafePe Investment");

                                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().INVESTMENT_WALLET_BALANCE,
                                        String.valueOf(response.data.investment_total));

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
                        logRequest = new ExceptionLogRequest(FixedDepositActivity.this, UserId, "FixedDepositActivity", e.getMessage(),
                                " 138", "safepeFixedDepostAccount api ", DeviceName);
                        BaseApp.getInstance().toastHelper().showApiExpectation(FixedDepositActivity.this.findViewById(R.id.ll_parant),
                                false, e.getCause());
                    }
                }));
    }
}