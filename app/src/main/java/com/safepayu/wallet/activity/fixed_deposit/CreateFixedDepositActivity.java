package com.safepayu.wallet.activity.fixed_deposit;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.deposit.DepositInterestRateAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.AllListData;
import com.safepayu.wallet.models.response.InvestmentReferResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CreateFixedDepositActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private LoadingDialog loadingDialog;
    public LinearLayout ll_interest_table, ll_back;
    public TextView tv_term_cond, tv_interest_rate, tv_available_balance;
    public Button btn_create_deposit;
    public EditText ed_amount, referralCode;
    public RecyclerView rv_deposit_interest_table_rate;
    public RecyclerView.Adapter mDepositInterestTable;
    public String fdInterest, balanceAmount, term_and_conditions;
    public List<AllListData> dataList = new ArrayList<>();
    private boolean referralCheck = false;
    private Button VerifyReffralBtn, verifyAlready;
    private TextView tvReferUserName, tvForReferralBtn, backToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_fixed_deposit);

        loadingDialog = new LoadingDialog(CreateFixedDepositActivity.this);
        Intent intent = getIntent();
        if (intent != null) {
            fdInterest = intent.getStringExtra("fdInterest");
            balanceAmount = intent.getStringExtra("balanceAmount");
            term_and_conditions = intent.getStringExtra("term_and_conditions");
        }

        referralCode = findViewById(R.id.et_referralCode2);
        VerifyReffralBtn = findViewById(R.id.verify_referral);
        verifyAlready = findViewById(R.id.verify_already);
        tvForReferralBtn = findViewById(R.id.forRefer);
        tvReferUserName = findViewById(R.id.refer_user_name);

        ll_interest_table = findViewById(R.id.ll_interest_table);
        tv_term_cond = findViewById(R.id.tv_term_cond);
        tv_interest_rate = findViewById(R.id.tv_interest_rate);
        tv_available_balance = findViewById(R.id.tv_available_balance);
        btn_create_deposit = findViewById(R.id.btn_create_deposit);
        ed_amount = findViewById(R.id.ed_amount);
        tv_available_balance.setText(balanceAmount);
        ll_back = findViewById(R.id.ll_back);
        tv_interest_rate.setText("Interest @ " + fdInterest + " % for 1 Day");
        tv_term_cond.setOnClickListener(this);
        btn_create_deposit.setOnClickListener(this);
        ll_interest_table.setOnClickListener(this);
        ed_amount.addTextChangedListener(this);
        ll_back.setOnClickListener(this);
        VerifyReffralBtn.setOnClickListener(this);

        referralCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                try {
                    if (referralCheck) {
                        referralCheck = false;
                        tvReferUserName.setText("");
                        VerifyReffralBtn.setVisibility(View.VISIBLE);
                        verifyAlready.setVisibility(View.GONE);
                    } else {
                        if (s.length() == 10) {
                            getReferralDetails();
                        } else {
                            referralCheck = false;
                            VerifyReffralBtn.setVisibility(View.VISIBLE);
                            verifyAlready.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        tvForReferralBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                referralCode.setText("fd8376097766");
                referralCode.setSelection(referralCode.getText().toString().length());
            }
        });
    }

    private void getReferralDetails() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getInvestmentRefer(referralCode.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<InvestmentReferResponse>() {
                    @Override
                    public void onSuccess(InvestmentReferResponse response) {
                        loadingDialog.hideDialog();
                        try {
                            if (response.isStatus()) {
                                referralCheck = true;
                                tvReferUserName.setText(response.getData());
                                referralCode.setSelection(referralCode.getText().toString().length());
                                verifyAlready.setVisibility(View.VISIBLE);
                                VerifyReffralBtn.setVisibility(View.GONE);
                                //
                            } else {
                                referralCheck = false;
                                referralCode.setText("");
                                tvReferUserName.setText("");
                                referralCode.requestFocus();
                                Toast.makeText(CreateFixedDepositActivity.this, "Wrong Referral Code!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        Toast.makeText(CreateFixedDepositActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_term_cond:
                callTermAndCondition();
                break;

            case R.id.ll_interest_table:

                loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
                setInterestData(ed_amount.getText().toString().trim());
                break;

            case R.id.ll_back:
                finish();
                break;

            case R.id.btn_create_deposit:
                int amt = 0;
                try {
                    amt = Integer.parseInt(ed_amount.getText().toString().trim());
                } catch (Exception e) {
                    amt = 0;
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(ed_amount.getText().toString().trim())) {
                    Toast.makeText(this, "Please Enter Amount", Toast.LENGTH_LONG).show();
                } else {

                    if (amt == 0) {
                        Toast.makeText(this, "Please Enter Amount", Toast.LENGTH_LONG).show();
                    } else {
                        if (referralCheck) {
                            Intent intent = new Intent(CreateFixedDepositActivity.this, FDChoosePayment.class);
                            intent.putExtra("AmountDeposit", ed_amount.getText().toString().trim());
                            intent.putExtra("ReferId", referralCode.getText().toString().trim());
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Please Verify Your Referral Id", Toast.LENGTH_LONG).show();
                        }
                    }

                }
                break;

            case R.id.verify_referral:

                if (TextUtils.isEmpty(referralCode.getText().toString().trim())) {
                    Toast.makeText(this, "Please Enter Referral Code!", Toast.LENGTH_SHORT).show();
                } else {
                    getReferralDetails();
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            ll_interest_table.setVisibility(View.VISIBLE);
        } else {
            ll_interest_table.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void callInterestTable() {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_deposit_interest_table_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);

        LinearLayout ll_alertDismiss = dialogView.findViewById(R.id.ll_alertDismiss);
        TextView tv_fixed_deposit_amount = dialogView.findViewById(R.id.tv_fixed_deposit_amount);
        tv_fixed_deposit_amount.setText(ed_amount.getText().toString().trim());

        rv_deposit_interest_table_rate = dialogView.findViewById(R.id.rv_deposit_interest_table_rate);
        rv_deposit_interest_table_rate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        mDepositInterestTable = new DepositInterestRateAdapter(this, dataList);
        rv_deposit_interest_table_rate.setAdapter(mDepositInterestTable);

        ll_alertDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
        loadingDialog.hideDialog();
    }

    private void callTermAndCondition() {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_term_cond_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        LinearLayout ll_alertDismiss = dialogView.findViewById(R.id.ll_alertDismiss);
        TextView tv_term_cond = dialogView.findViewById(R.id.tv_term_cond);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_term_cond.setText(Html.fromHtml(term_and_conditions, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv_term_cond.setText(Html.fromHtml(term_and_conditions));
        }
        ll_alertDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void setInterestData(String ed_amount) {

        // p * r * (t/365) / 100

        DecimalFormat df = new DecimalFormat("######.##");
        for (int i = 1; i <= 400; i++) {
            switch (i) {
                case 1:
                case 7:
                case 25:
                case 50:
                case 100:
                case 125:
                case 150:
                case 175:
                case 200:
                case 225:
                case 250:
                case 275:
                case 300:
                case 325:
                case 350:
                case 375:
                case 400:
                    AllListData allListData = new AllListData();
                    allListData.period = String.valueOf(i);
                    allListData.r_rate = fdInterest;
                    float amount = (Float.valueOf(ed_amount) * Float.valueOf(fdInterest) * (Float.valueOf(df.format(i)) / 365)) / 100;
                    allListData.amount = df.format(amount);

                    dataList.add(allListData);
                    break;
                default:
            }
        }
        callInterestTable();
    }
}