package com.safepayu.wallet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
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

public class TransferCommissionToWallet extends BaseActivity implements PasscodeClickListener {

    Button SendToWalletBtn,BackBtn;
    private EditText AmountED;
    private int Amount = 0;
    private TextView AmountTotalTV,tvTax;
    private double tax=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        SendToWalletBtn=findViewById(R.id.sendToWalletBtn);
        BackBtn=findViewById(R.id.backBtn_transferCommission);
        AmountED = findViewById(R.id.withdrawAmount_commWallet);
        AmountTotalTV= findViewById(R.id.calculatedamount);
        tvTax=findViewById(R.id.tax_comWallet);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tax=Double.parseDouble(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().COMMISSION_CHARGE));
        tvTax.setText(tax+"% Of Amount Will Be Charged As Admin Charge");

        SendToWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Amount = Integer.parseInt(AmountED.getText().toString().trim());
                } catch (Exception e) {
                    Amount = 0;
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(AmountED.getText().toString().trim())) {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commisionToWallet), "Please Enter Amount", false);
                } else {
                    if (Amount > 0) {
//                        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE) == null || BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE).equals("")) {
//                            startActivity(new Intent(TransferCommissionToWallet.this, CreatePassCodeActivity.class));
//                        } else {
//                            PasscodeDialog passcodeDialog = new PasscodeDialog(TransferCommissionToWallet.this, TransferCommissionToWallet.this, "");
//                            passcodeDialog.show();
//                        }
                        PasscodeDialog passcodeDialog = new PasscodeDialog(TransferCommissionToWallet.this, TransferCommissionToWallet.this, "");
                        passcodeDialog.show();
                    } else {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commisionToWallet), "Please Enter Amount", false);
                    }
                }
            }
        });

        AmountED.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
               try {
                   if (s.length()>1 || s.length()==1){
                       double amt=CalculateAmount(Integer.parseInt(AmountED.getText().toString().trim()),tax);
                       String text = getResources().getString(R.string.rupees)+" "+AmountED.getText().toString().trim()+" - Admin Charge = ";
                       AmountTotalTV.setText(text+getResources().getString(R.string.rupees)+" "+String.format("%.2f", amt));
                   }else {
                       AmountTotalTV.setText(getResources().getString(R.string.rupees)+" "+"0.0");
                   }
               }catch (Exception e){
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

        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().EMAIL_VERIFIED).equalsIgnoreCase("0")){

            SendToWalletBtn.setVisibility(View.GONE);

            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commissionLayout),"Please Goto Your Profile and Verify Your Email First",true);
        }else {

            try {
//                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PACKAGE_PURCHASED).equalsIgnoreCase("0")) {
//                    SendToWalletBtn.setVisibility(View.GONE);
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//                    alertDialogBuilder.setTitle(Html.fromHtml("<font color='#3db7c2'>Commission</font>"));
//                    alertDialogBuilder
//                            .setMessage("Please Buy Membership To Enjoy App's Features")
//                            .setCancelable(false)
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//
//                    alertDialog.show();
//                } else {
//
//                    SendToWalletBtn.setVisibility(View.VISIBLE);
//                }
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
        return R.layout.transfer_commission_to_wallet;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }


    private void transferCommWalletToMainWallet() {
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.transferCommWalletToMainWallet(AmountED.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CommissionWalletTransferResponse>() {
                    @Override
                    public void onSuccess(CommissionWalletTransferResponse response) {
                        Intent intentStatus=new Intent(TransferCommissionToWallet.this,PaidOrderActivity.class);
                        if (response.isStatus()) {
                            //Toast.makeText(TransferCommissionToWallet.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                           // finish();
                            intentStatus.putExtra("status","success");
                        } else {
                            intentStatus.putExtra("status","failed");
                            Toast.makeText(TransferCommissionToWallet.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            //BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commisionToWallet), response.getMessage(), false);
                        }

                        if (response.getMessage().contains("Insufficient Balance.") || response.getMessage().equalsIgnoreCase("Insufficient Balance.")){

                            String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
                            intentStatus.putExtra("date",currentDate);
                            intentStatus.putExtra("txnid","");
                            intentStatus.putExtra("productinfo","Insufficient Balance In Commission Wallet");
                        }else {
                            intentStatus.putExtra("date",response.getDate());
                            intentStatus.putExtra("txnid",response.getUtrId());
                            intentStatus.putExtra("productinfo","Commission Wallet To Main Wallet Transaction");
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
            transferCommWalletToMainWallet();
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.commisionToWallet), "Invalid Passcode", false);
        }

    }

    private double CalculateAmount(int amount,double tax){

        double totalAmount=0.0f,minusAmount=0.0f;
        int checkAmount=0;

        minusAmount=((((double) amount) / 100) * tax);
        totalAmount=(double)amount- minusAmount;
        checkAmount=(int)minusAmount;
        if (checkAmount>9){

        }else {
            totalAmount=(double)amount-(double)10;
        }

        return totalAmount;
    }
}
