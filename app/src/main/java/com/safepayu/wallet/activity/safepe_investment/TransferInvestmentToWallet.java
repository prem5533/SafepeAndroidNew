package com.safepayu.wallet.activity.safepe_investment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.PaidOrderActivity;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.models.response.CommissionWalletTransferResponse;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.safepayu.wallet.utils.PasscodeDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TransferInvestmentToWallet extends BaseActivity implements PasscodeClickListener {

    Button SendToWalletBtn,BackBtn;
    private EditText AmountED;
    private int Amount = 0;
    private TextView limitText,tvWalletBal;
    private LinearLayout relativeLayoutBal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        SendToWalletBtn=findViewById(R.id.sendToWalletBtn);
        BackBtn=findViewById(R.id.backBtn_transferCommission);
        AmountED = findViewById(R.id.withdrawAmount_commWallet);
        limitText = findViewById(R.id.limitInvestmentTransfer);
        tvWalletBal = findViewById(R.id.wallet_amount_investment);
        relativeLayoutBal = findViewById(R.id.linear_wallet_amount_investment);

        try {
            tvWalletBal.setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().INVESTMENT_WALLET_BALANCE));
            relativeLayoutBal.setVisibility(View.VISIBLE);
        }catch (Exception e){
            relativeLayoutBal.setVisibility(View.GONE);
            e.printStackTrace();
        }

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //final int minLimit= (int)Double.parseDouble(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().LIMIT_MIN));
        //final int maxLimit= (int)Double.parseDouble(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().LIMIT));

        //limitText.setText("Please Enter Amount Between Rs "+minLimit+" and Rs "+maxLimit);
        limitText.setVisibility(View.GONE);

        SendToWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
//                    Amount = Integer.parseInt(AmountED.getText().toString().trim());
//                } catch (Exception e) {
//                    Amount = 0;
//                    e.printStackTrace();
//                }
//
//                if (TextUtils.isEmpty(AmountED.getText().toString().trim())) {
//                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.investmentToWallet), "Please Enter Amount", false);
//                } else {
//                    if (Amount > 0) {
//                        if (Amount>minLimit && Amount<maxLimit+1) {
//                            PasscodeDialog passcodeDialog = new PasscodeDialog(TransferInvestmentToWallet.this, TransferInvestmentToWallet.this, "");
//                            passcodeDialog.show();
//                        }else {
//
//                            showMessage("Please Enter Amount Between Rs "+minLimit+" and Rs "+maxLimit);
//                           // BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.investmentToWallet), "Please Enter Amount Between Rs "+minLimit+" and Rs "+maxLimit, true);
//                        }
//                    } else {
//                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.investmentToWallet), "Please Enter Amount", true);
//                    }
//                }

                PasscodeDialog passcodeDialog = new PasscodeDialog(TransferInvestmentToWallet.this, TransferInvestmentToWallet.this, "");
                passcodeDialog.show();
            }
        });

        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().EMAIL_VERIFIED).equalsIgnoreCase("0")){

            SendToWalletBtn.setVisibility(View.GONE);

            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commissionLayout),"Please Goto Your Profile and Verify Your Email First",true);
        }else {

            try {
                /* if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")) {
                    SendToWalletBtn.setVisibility(View.GONE);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle(Html.fromHtml("<font color='#3db7c2'>Commission</font>"));
                    alertDialogBuilder
                            .setMessage("Please Buy Membership To Enjoy App's Features")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();
                } else {

                    SendToWalletBtn.setVisibility(View.VISIBLE);
                }*/
                SendToWalletBtn.setVisibility(View.VISIBLE);
            }catch (Exception e){
                SendToWalletBtn.setVisibility(View.GONE);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(Html.fromHtml("<font color='#3db7c2'>Commission</font>"));
                alertDialogBuilder
                        .setMessage("Please Buy Membership To Enjoy App's Features")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
                e.printStackTrace();
            }
        }

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.transfer_investment_to_wallet;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }


    private void transferInvestmentToWallet() {
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.transferInvestmentToWallet(AmountED.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommissionWalletTransferResponse>() {
                    @Override
                    public void onSuccess(CommissionWalletTransferResponse response) {
                        Intent intentStatus=new Intent(TransferInvestmentToWallet.this, PaidOrderActivity.class);
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

                        if (response.isStatus()) {

                            intentStatus.putExtra("status","success");
                            intentStatus.putExtra("date",response.getDate());
                            intentStatus.putExtra("txnid",response.getUtrId());
                            intentStatus.putExtra("productinfo","Investment Wallet To Main Wallet Transaction");
                        } else {

                            Toast.makeText(TransferInvestmentToWallet.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            intentStatus.putExtra("status","failed");
                            intentStatus.putExtra("date",currentDate);
                            intentStatus.putExtra("txnid","");
                            intentStatus.putExtra("productinfo",response.getMessage());
                        }

                        intentStatus.putExtra("Amount",AmountED.getText().toString().trim());
                        startActivity(intentStatus);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.mobileRechargeLayout), true, e);
                    }
                }));

    }

    @Override
    public void onPasscodeMatch(boolean isPasscodeMatched) {
        if (isPasscodeMatched) {
            transferInvestmentToWallet();
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.investmentToWallet), "Invalid Passcode", false);
        }

    }


    public void showMessage(String Message) {
        new AlertDialog.Builder(TransferInvestmentToWallet.this)
                .setTitle("SafePe ")
                .setMessage(Message)
                .setCancelable(false)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                //.setPositiveButton(android.R.string.yes, null)

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.dismiss();


                    }
                })
                .setIcon(getResources().getDrawable(R.drawable.appicon_new))
                .show();
    }

}
