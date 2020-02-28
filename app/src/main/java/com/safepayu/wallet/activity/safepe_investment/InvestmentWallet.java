package com.safepayu.wallet.activity.safepe_investment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
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
import com.safepayu.wallet.adapter.InvestmentWalletAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.InvestmentWalletLogResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class InvestmentWallet extends AppCompatActivity implements InvestmentWalletAdapter.InvestmentWalletListener{

    private Button BackBtn;
    private TextView SendWallet,CommBalanceTV,WarningTextTv;
    private LoadingDialog loadingDialog;
    private LinearLayout liSendTowallet,CreditListBtn,DebitListBtn;
    private RecyclerView recyclerViewCredit,recyclerViewDebit;
    private InvestmentWalletLogResponse investmentWalletLogResponse;
    private List<InvestmentWalletLogResponse.DataBean.LogListBean> CreditList,DebitList;
    private Dialog dialogFDeposit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.investment_wallet);

        loadingDialog = new LoadingDialog(this);
        SendWallet = findViewById(R.id.send_to_wallet);
        BackBtn = findViewById(R.id.send_back_btn);
        CommBalanceTV = findViewById(R.id.current_referral_business);
        WarningTextTv = findViewById(R.id.textWarningCommission);
        liSendTowallet = findViewById(R.id.li_send_towallet);
        CreditListBtn = findViewById(R.id.creditLayout_investmentWallet);
        DebitListBtn = findViewById(R.id.debitLayout_investmentWallet);
        recyclerViewCredit = findViewById(R.id.recycleCredit_investmentWallet);
        recyclerViewCredit.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        recyclerViewDebit = findViewById(R.id.recycleDebit_investmentWallet);
        recyclerViewDebit.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        CreditList=new ArrayList<>();
        DebitList=new ArrayList<>();

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CreditListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewCredit.setVisibility(View.VISIBLE);
                recyclerViewDebit.setVisibility(View.GONE);
                if (CreditList.size()==0){
                    Toast.makeText(InvestmentWallet.this, "No Credit List Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DebitListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewCredit.setVisibility(View.GONE);
                recyclerViewDebit.setVisibility(View.VISIBLE);
                if (DebitList.size()==0){
                    Toast.makeText(InvestmentWallet.this, "No Debit List Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        liSendTowallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TransferInvestmentToWallet.class));
                overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
            }
        });

        try {
            if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().EMAIL_VERIFIED).equalsIgnoreCase("0")){
                liSendTowallet.setVisibility(View.GONE);
                WarningTextTv.setVisibility(View.VISIBLE);
                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.investmentWallet),"Please Goto Your Profile and Verify Your Email First",true);
            }else {
                getInvestment();
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
                }catch (Exception e){
                    e.printStackTrace();
                    liSendTowallet.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.investmentWallet),"Please Goto Your Profile and Verify Your Email First",true);
        }
    }

    private void getInvestment() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getInvestmentLog()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<InvestmentWalletLogResponse>() {
                    @Override
                    public void onSuccess(InvestmentWalletLogResponse response) {
                        if (response.isStatus()){
                            loadingDialog.hideDialog();
                            try {
                                investmentWalletLogResponse=response;
                                CommBalanceTV.setText(getResources().getString(R.string.rupees)+" "+response.getData().getAmount());
                                for (int i=0;i<investmentWalletLogResponse.getData().getLogList().size();i++){
                                    if (investmentWalletLogResponse.getData().getLogList().get(i).getOperation().equalsIgnoreCase("credit")){
                                        CreditList.add(investmentWalletLogResponse.getData().getLogList().get(i));
                                    }else {
                                        DebitList.add(investmentWalletLogResponse.getData().getLogList().get(i));
                                    }
                                }
                                InvestmentWalletAdapter creditAdapter=new InvestmentWalletAdapter(InvestmentWallet.this,CreditList,InvestmentWallet.this);
                                recyclerViewCredit.setAdapter(creditAdapter);

                                InvestmentWalletAdapter debitAdapter=new InvestmentWalletAdapter(InvestmentWallet.this,DebitList,InvestmentWallet.this);
                                recyclerViewDebit.setAdapter(debitAdapter);

                                if (CreditList.size()==0){
                                    Toast.makeText(InvestmentWallet.this, "No Credit List Found", Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.investmentWallet), false, e.getCause());
                    }
                }));
    }


    @Override
    public void onInvestmentWalletClick(int position, InvestmentWalletLogResponse.DataBean.LogListBean logListBeanList) {
        Toast.makeText(this, logListBeanList.getCreated_at(), Toast.LENGTH_SHORT).show();
        dialogDeposit(logListBeanList);
    }

    private TextView tvFDid,tvFDAmount,tvtax,tvstatus,tvPaymentMode,operationText,tv_bonus_amount,tv_balance_amount;
    private TextView tvDescription,tv_contct_support,tvCustomerId,tvHeading;
    private Button FD_back_btn;
    private LinearLayout DescriptionLayout;

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
                startActivity(new Intent(InvestmentWallet.this, ContactUs.class));
                dialogFDeposit.dismiss();
                finish();
            }
        });

        tvFDid.setText(logListBeanList.getTransaction_no());
        tvFDAmount.setText("₹ " +logListBeanList.getAmount());
        tvDescription.setText(logListBeanList.getDescription());
        tvCustomerId.setText(logListBeanList.getUserid());
        tvPaymentMode.setText("Payment Mode: " +logListBeanList.getMode_payment());
//        operationText.setText("Invest Amount: ₹" +investmentBean.getPackage_amount());
        tv_bonus_amount.setText("Operation: " +logListBeanList.getOperation());
//        tv_balance_amount.setText("Invest Balance Amount: ₹" +investmentBean.getBalance_amount());

        tvtax.setText(logListBeanList.getCreated_at());

        if (logListBeanList.getStatus()==0){
            tvstatus.setText("Pending");
            tvstatus.setTextColor(Color.parseColor("#FFBF00"));
        } else if (logListBeanList.getStatus()==1){
            tvstatus.setText("Approved");
            tvstatus.setTextColor(Color.parseColor("#84DE02"));
        } else if (logListBeanList.getStatus()==2){
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
}