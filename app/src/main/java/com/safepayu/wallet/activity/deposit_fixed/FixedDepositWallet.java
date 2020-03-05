package com.safepayu.wallet.activity.deposit_fixed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.ContactUs;
import com.safepayu.wallet.adapter.FixedDepositBeneAdapter;
import com.safepayu.wallet.adapter.InvestmentWalletAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.interfaces.FixedDepositInterface;
import com.safepayu.wallet.models.request.ExceptionLogRequest;
import com.safepayu.wallet.models.response.InvestmentResponse;
import com.safepayu.wallet.models.response.InvestmentWalletLogResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FixedDepositWallet extends AppCompatActivity implements InvestmentWalletAdapter.InvestmentWalletListener, FixedDepositInterface {
    private Button BackBtn;
    private TextView SendWallet, CommBalanceTV, WarningTextTv, tv_fd_amount;
    private LoadingDialog loadingDialog;
    private LinearLayout liSendTowallet, CreditListBtn, DebitListBtn;
    private RecyclerView recyclerViewCredit, recyclerViewDebit;
    private InvestmentWalletLogResponse investmentWalletLogResponse;
    private List<InvestmentWalletLogResponse.DataBean.LogListBean> CreditList, DebitList;
    private Dialog dialogFDeposit;
    private LinearLayout fdEmpty;
    public RecyclerView rv_beneficiary_list;
    public RecyclerView.Adapter mBeneAdapter;
    public FixedDepositInterface fixedDepositInterface;
    public AlertDialog alertDialog;
    private String DeviceName = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().DEVICE_NAME);
    private String UserId = BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().USER_ID);
    ExceptionLogRequest logRequest;
    private List<InvestmentResponse.DataBean.InvestmentBean> investmentBeanList = new ArrayList<>();
    public String interestRateId, interestrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_deposit_wallet);
        fixedDepositInterface = this;
        loadingDialog = new LoadingDialog(this);
        SendWallet = findViewById(R.id.send_to_wallet);
        BackBtn = findViewById(R.id.send_back_btn);
        CommBalanceTV = findViewById(R.id.current_referral_business);
        WarningTextTv = findViewById(R.id.textWarningCommission);
        liSendTowallet = findViewById(R.id.li_send_towallet);
        CreditListBtn = findViewById(R.id.creditLayout_investmentWallet);
        DebitListBtn = findViewById(R.id.debitLayout_investmentWallet);
        fdEmpty = findViewById(R.id.fdEmptyWallet);
        tv_fd_amount = findViewById(R.id.tv_fd_amount);
        recyclerViewCredit = findViewById(R.id.recycleCredit_investmentWallet);
        recyclerViewCredit.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        recyclerViewDebit = findViewById(R.id.recycleDebit_investmentWallet);
        recyclerViewDebit.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        CreditList = new ArrayList<>();
        DebitList = new ArrayList<>();

        BackBtn.setOnClickListener(view -> finish());

        CreditListBtn.setOnClickListener(view -> {
            recyclerViewCredit.setVisibility(View.VISIBLE);
            recyclerViewDebit.setVisibility(View.GONE);
            if (CreditList.size() == 0) {
                fdEmpty.setVisibility(View.VISIBLE);
                recyclerViewCredit.setVisibility(View.GONE);
                recyclerViewDebit.setVisibility(View.GONE);
                Toast.makeText(FixedDepositWallet.this, "No Credit List Found", Toast.LENGTH_SHORT).show();
            } else {
                fdEmpty.setVisibility(View.GONE);
            }
        });

        DebitListBtn.setOnClickListener(view -> {
            recyclerViewCredit.setVisibility(View.GONE);
            recyclerViewDebit.setVisibility(View.VISIBLE);
            if (DebitList.size() == 0) {
                Toast.makeText(FixedDepositWallet.this, "No Debit List Found", Toast.LENGTH_SHORT).show();
                fdEmpty.setVisibility(View.VISIBLE);
                recyclerViewCredit.setVisibility(View.GONE);
                recyclerViewDebit.setVisibility(View.GONE);
            } else {
                fdEmpty.setVisibility(View.GONE);
            }
        });

        liSendTowallet.setOnClickListener(view -> {
            //  startActivity(new Intent(getApplicationContext(), TransferInvestmentToBank.class));
            // overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
        });
        try {
            if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().EMAIL_VERIFIED).equalsIgnoreCase("0")) {
                liSendTowallet.setVisibility(View.GONE);
                WarningTextTv.setVisibility(View.VISIBLE);
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.investmentWallet), "Please Goto Your Profile and Verify Your Email First", true);
            } else {
                getFixedDepositLog();
                try {
                    /*
                    if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")){
                        liSendTowallet.setVisibility(View.GONE);
                        WarningTextTv.setVisibility(View.VISIBLE);
                        //   BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.investmentWallet),"Please Buy Membership To Enjoy App's Features",true);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        //    alertDialogBuilder.setTitle("Commission");
                        alertDialogBuilder.setTitle( Html.fromHtml("<font color='#3db7c2'>Commission</font>"));
                        alertDialogBuilder
                                .setMessage("Please Buy Membership To Enjoy App's Features")
                                .setCancelable(false)
                                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                    }
                                });
                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }else {

                    } */

                    liSendTowallet.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    logRequest = new ExceptionLogRequest(FixedDepositWallet.this, UserId, "InvestmentWallet", e.getMessage(), " 158", "onCreate ", DeviceName);
                    liSendTowallet.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logRequest = new ExceptionLogRequest(FixedDepositWallet.this, UserId, "InvestmentWallet", e.getMessage(), " 164", "onCreate ", DeviceName);
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.investmentWallet), "Please Goto Your Profile and Verify Your Email First", true);
        }
        tv_fd_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFixedDepositBeneList();
            }
        });
        getFixedDepositList();
    }

    private void getFixedDepositLog() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getFixedDepositLog()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<InvestmentWalletLogResponse>() {
                    @Override
                    public void onSuccess(InvestmentWalletLogResponse response) {
                        if (response.isStatus()) {
                            loadingDialog.hideDialog();
                            try {
                                investmentWalletLogResponse = response;
                                CommBalanceTV.setText(getResources().getString(R.string.rupees) + " " + response.getData().getAmount());
                                for (int i = 0; i < investmentWalletLogResponse.getData().getLogList().size(); i++) {
                                    if (investmentWalletLogResponse.getData().getLogList().get(i).getOperation().equalsIgnoreCase("credit")) {
                                        CreditList.add(investmentWalletLogResponse.getData().getLogList().get(i));
                                    } else {
                                        DebitList.add(investmentWalletLogResponse.getData().getLogList().get(i));
                                    }
                                }
                                InvestmentWalletAdapter creditAdapter = new InvestmentWalletAdapter(FixedDepositWallet.this, CreditList, FixedDepositWallet.this);
                                recyclerViewCredit.setAdapter(creditAdapter);

                                InvestmentWalletAdapter debitAdapter = new InvestmentWalletAdapter(FixedDepositWallet.this, DebitList, FixedDepositWallet.this);
                                recyclerViewDebit.setAdapter(debitAdapter);

                                if (CreditList.size() == 0) {
                                    Toast.makeText(FixedDepositWallet.this, "No Credit List Found", Toast.LENGTH_SHORT).show();
                                    fdEmpty.setVisibility(View.VISIBLE);
                                    recyclerViewCredit.setVisibility(View.GONE);
                                    recyclerViewDebit.setVisibility(View.GONE);
                                } else {
                                    fdEmpty.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        logRequest = new ExceptionLogRequest(FixedDepositWallet.this, UserId, "InvestmentWallet", e.getMessage(), " 212", "getInvestmentLog api ", DeviceName);
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.investmentWallet), false, e.getCause());
                    }
                }));
    }

    @Override
    public void onInvestmentWalletClick(int position, InvestmentWalletLogResponse.DataBean.LogListBean logListBeanList) {
        Toast.makeText(this, logListBeanList.getCreated_at(), Toast.LENGTH_SHORT).show();
        dialogDeposit(logListBeanList);
    }

    public TextView tvFDid, tvFDAmount, tvtax, tvstatus, tvPaymentMode, operationText, tv_bonus_amount, tv_balance_amount;
    public TextView tvDescription, tv_contct_support, tvCustomerId, tvHeading;
    public Button FD_back_btn;
    public LinearLayout DescriptionLayout;

    private void dialogDeposit(InvestmentWalletLogResponse.DataBean.LogListBean logListBeanList) {
        dialogFDeposit = new Dialog(this);
        dialogFDeposit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFDeposit.setContentView(R.layout.fixed_deposit_detail);

        tvFDid = dialogFDeposit.findViewById(R.id.tv_fd_id);
        tvFDAmount = dialogFDeposit.findViewById(R.id.tv_fd_amount);
        tvtax = dialogFDeposit.findViewById(R.id.tvtax);
        tvstatus = dialogFDeposit.findViewById(R.id.tvstatus);
        tvPaymentMode = dialogFDeposit.findViewById(R.id.tv_payment_mode);
        tv_contct_support = dialogFDeposit.findViewById(R.id.tv_contct_support);
        DescriptionLayout = dialogFDeposit.findViewById(R.id.description_layout);
        tvDescription = dialogFDeposit.findViewById(R.id.description);
        tvCustomerId = dialogFDeposit.findViewById(R.id.tv_customer_name_number);
        tvHeading = dialogFDeposit.findViewById(R.id.tv_status);

        operationText = dialogFDeposit.findViewById(R.id.operationText);
        tv_bonus_amount = dialogFDeposit.findViewById(R.id.tv_bonus_amount);
        tv_balance_amount = dialogFDeposit.findViewById(R.id.tv_balance_amount);
        FD_back_btn = dialogFDeposit.findViewById(R.id.FD_back_btn);
        DescriptionLayout.setVisibility(View.VISIBLE);
        tvFDAmount.setVisibility(View.VISIBLE);
        tvCustomerId.setVisibility(View.VISIBLE);

        tvHeading.setText("Investment Wallet");

        FD_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFDeposit.dismiss();
            }
        });
        tv_contct_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FixedDepositWallet.this, ContactUs.class));
                dialogFDeposit.dismiss();
                finish();
            }
        });

        tvFDid.setText(logListBeanList.getTransaction_no());
        tvFDAmount.setText("₹ " + logListBeanList.getAmount());
        tvDescription.setText(logListBeanList.getDescription());
        tvCustomerId.setText(logListBeanList.getUserid());
        tvPaymentMode.setText("Payment Mode: " + logListBeanList.getMode_payment());
//        operationText.setText("Invest Amount: ₹" +investmentBean.getPackage_amount());
        tv_bonus_amount.setText("Operation: " + logListBeanList.getOperation());
//        tv_balance_amount.setText("Invest Balance Amount: ₹" +investmentBean.getBalance_amount());

        tvtax.setText(logListBeanList.getCreated_at());

        if (logListBeanList.getStatus() == 1) {
            tvstatus.setText("Approved");
            tvstatus.setTextColor(Color.parseColor("#84DE02"));
        } else if (logListBeanList.getStatus() == 2) {
            tvstatus.setText("Pending");
            tvstatus.setTextColor(Color.parseColor("#FFBF00"));
        } else {
            tvstatus.setText("Failed");
            tvstatus.setTextColor(Color.parseColor("#E32636"));
        }


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogFDeposit.getWindow();
        dialogFDeposit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialogFDeposit.show();
    }

    private void getFixedDepositList() {
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getFixedDepositList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<InvestmentResponse>() {
                    @Override
                    public void onSuccess(InvestmentResponse response) {
                        if (response.isStatus()) {
                            for (int i = 0; i < response.getData().getInvestment().size(); i++) {
                                investmentBeanList.add(response.getData().getInvestment().get(i));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();  logRequest = new ExceptionLogRequest(FixedDepositWallet.this, UserId, "InvestmentWallet",
                                e.getMessage(), "314", "getFixedDeposit api ", DeviceName);

                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.ll_parant), false, e.getCause());
                    }
                }));
    }

    private void callFixedDepositBeneList() {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_fixed_deposit_bene_list_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.setCancelable(true);
        LinearLayout ll_alertDismiss = dialogView.findViewById(R.id.ll_alertDismiss);
        rv_beneficiary_list = dialogView.findViewById(R.id.rv_beneficiary_list);
        rv_beneficiary_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        mBeneAdapter = new FixedDepositBeneAdapter(this, investmentBeanList, fixedDepositInterface);
        rv_beneficiary_list.setAdapter(mBeneAdapter);

        ll_alertDismiss.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

    @Override
    public void onClickInterestRate(String interestRateId, String interestrate) {
        tv_fd_amount.setText("Rs. " + interestrate);
        this.interestRateId = interestRateId;
        this.interestrate = interestrate;
        alertDialog.dismiss();
        showCheckDone("Are You sure\nYou want to break your.\nFixed Deposit ???\n\n\n");
    }

    public void showCheckDone(String Message) {
        new AlertDialog.Builder(FixedDepositWallet.this)
                .setTitle("SafePe - Fixed Deposit")
                .setMessage(Message)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), TransferFdToBank.class).
                            putExtra("FD_ID", String.valueOf(interestRateId)).
                            putExtra("FD_AMOUNT", String.valueOf(interestrate)));
                    overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setIcon(getResources().getDrawable(R.drawable.appicon_new))
                .show();
    }
}