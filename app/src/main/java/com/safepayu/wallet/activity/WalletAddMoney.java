package com.safepayu.wallet.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.PackageAdapterForWalletNew;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.halper.RecyclerLayoutManager;
import com.safepayu.wallet.models.response.PackageListData;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WalletAddMoney extends BaseActivity implements PackageAdapterForWalletNew.OnPackageSelectListener {

    Button AddMoneyBtn,BackBtn;
    String PackageID = "", PackageName = "";
    private LoadingDialog loadingDialog;
    private RecyclerView packageListView;
    private RecyclerLayoutManager layoutManager;
    //private PackageAdapterForWallet mAdapter;
    private PackageListData packageListData;
    private ArrayList<String> PackageNameList,PackageIdList;
    private ArrayList<Double> PackageAmountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        loadingDialog = new LoadingDialog(this);

        AddMoneyBtn=findViewById(R.id.btn_addMoneyType);
        BackBtn=findViewById(R.id.sendmoney_back_btn);

        packageListView = findViewById(R.id.list_packageListView);

        layoutManager = new RecyclerLayoutManager(2, RecyclerLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        packageListView.setLayoutManager(layoutManager);
//        mAdapter = new PackageAdapterForWallet(this, this);
//        packageListView.setAdapter(mAdapter);

        ((TextView) findViewById(R.id.mobileNumberWallet)).setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE));
        ((TextView) findViewById(R.id.tv_packageName)).setText("Package");
        ((TextView) findViewById(R.id.tv_packageAmount)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(0d));
        ((TextView) findViewById(R.id.tv_totalAmountPay)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(0d));

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AddMoneyBtn.setVisibility(View.GONE);
        AddMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(PackageName)) {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.packageItem), "Please Select Any Package", false);
                }

            }
        });

        getPackages();

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.wallet_add_money;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {
        if (isConnected){
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addMoneyToWalletLayout),getResources().getString(R.string.internet_check),false);
        }
    }

    @Override
    public void onPackageSelectNew(int position, ArrayList<String> PackageNameList1, ArrayList<String> PackageIdList1, ArrayList<Double> PackageAmountList1,String TaxPer1) {
        PackageID = PackageIdList1.get(position);
        PackageName = PackageNameList1.get(position);
        ((TextView) findViewById(R.id.tv_packageName)).setText(PackageName);
        ((TextView) findViewById(R.id.tv_packageAmount)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(PackageAmountList1.get(position)));
        ((TextView) findViewById(R.id.tv_tax)).setText(TaxPer1 + "%");
        Double totalPayableAmount = BaseApp.getInstance().commonUtils().getAmountWithTax(PackageAmountList1.get(position), Double.parseDouble(TaxPer1));
        ((TextView) findViewById(R.id.tv_totalAmountPay)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(totalPayableAmount));

        showDialog(WalletAddMoney.this, PackageID, PackageName, String.valueOf(PackageAmountList1.get(position)), String.valueOf(BaseApp.getInstance().commonUtils().decimalFormat(totalPayableAmount)));
    }

    private void getPackages() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        PackageNameList=new ArrayList<>();
        PackageIdList=new ArrayList<>();
        PackageAmountList=new ArrayList<>();

        PackageNameList.clear();
        PackageIdList.clear();
        PackageAmountList.clear();

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getAllPackages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PackageListData>() {
                    @Override
                    public void onSuccess(PackageListData response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {

                            for (int i = 0; i < response.getPackages().size(); i++) {
                                String packageName1 = response.getPackages().get(i).getPackageName();
                                if (packageName1.equalsIgnoreCase("Nano") || packageName1.equalsIgnoreCase("Promotional") || packageName1.equalsIgnoreCase("Pro")) {
                                    String id = response.getPackages().get(i).getId();
                                    Double packageAmt = response.getPackages().get(i).getPackageAmount();
                                    String packName = response.getPackages().get(i).getPackageName();

                                    PackageNameList.add(packName);
                                    PackageIdList.add(id);
                                    PackageAmountList.add(packageAmt);
                                }
                            }

                            PackageNameList.add("More");
                            PackageIdList.add("");
                            PackageAmountList.add(Double.valueOf(0));
                            PackageAdapterForWalletNew adapter=new PackageAdapterForWalletNew(WalletAddMoney.this,PackageNameList,PackageIdList,
                                    PackageAmountList,response.getTax().getTaxValue(),WalletAddMoney.this);
                            packageListView.setAdapter(adapter);
                            ((TextView) findViewById(R.id.tv_taxDetails)).setText("Additional " + response.getTax().getTaxValue() + "% GST will be charged from the total amount");
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addMoneyToWalletLayout),"No Package Found",false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.addMoneyToWalletLayout), true, e);
                    }
                }));
    }

    public void showDialog(Activity activity, final String PackageID, String PackName, String AMount, final String Amount2Pay) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.wallet_dialog);

        TextView PackNameTV = dialog.findViewById(R.id.packageName_WalletDialog);
        TextView AmountTV = dialog.findViewById(R.id.packageAmount_WalletDialog);
        TextView AmountToPayTV = dialog.findViewById(R.id.totalAmountPay_WalletDialog);

        PackNameTV.setText(PackName);
        AmountTV.setText(AMount);
        AmountToPayTV.setText(Amount2Pay);

        Button dialogButton = (Button) dialog.findViewById(R.id.payBtn_WalletDialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().EMAIL_VERIFIED).equalsIgnoreCase("0")){
                    dialog.dismiss();
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.addMoneyToWalletLayout),"Please Goto Your Profile and Verify Your Email First",true);
                }else {
                    Intent intent = new Intent(WalletAddMoney.this, PaymentType.class);
                    overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
                    intent.putExtra("RechargePaymentId", "");
                    intent.putExtra("Amount", Amount2Pay);
                    intent.putExtra("PaymentType", "Add Money");
                    intent.putExtra("PaymentFor", "Wallet");
                    intent.putExtra("RechargeTypeId", "0");
                    intent.putExtra("OperatorCode", PackageID);
                    intent.putExtra("CircleCode", "");
                    intent.putExtra("OperatorId", "");
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }


            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }
}
