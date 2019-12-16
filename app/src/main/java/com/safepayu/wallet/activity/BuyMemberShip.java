package com.safepayu.wallet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.PackageListAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.helper.RecyclerLayoutManager;
import com.safepayu.wallet.models.request.BuyPackage;
import com.safepayu.wallet.models.response.BaseResponse;
import com.safepayu.wallet.models.response.BuyPackageResponse;
import com.safepayu.wallet.models.response.PackageListData;
import com.safepayu.wallet.utils.PasscodeClickListener;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.VISIBLE;
import static com.safepayu.wallet.api.ApiClient.BASE_URL;

public class BuyMemberShip extends BaseActivity implements PackageListAdapter.OnPackageSelectListener, View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, PasscodeClickListener {

    private LoadingDialog loadingDialog;
    private RecyclerView packageListView;
    private RecyclerLayoutManager layoutManager;
    private PackageListAdapter mAdapter;
    private PackageListData packageListData;
    private RadioGroup paymentMode;
    private CardView cardView;
    private double FinalAmount;
    String TransactionType="0",PackageID="",PackageName="",PackageAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingDialog = new LoadingDialog(this);

        setToolbar(false, "Buy Member Ship", false);

        cardView = findViewById(R.id.bankdetails);
        packageListView = findViewById(R.id.list_packageListView);

        layoutManager = new RecyclerLayoutManager(2, RecyclerLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        packageListView.setLayoutManager(layoutManager);
        mAdapter = new PackageListAdapter(this, this);
        packageListView.setAdapter(mAdapter);
        getPackages();

        paymentMode = findViewById(R.id.rg_paymentMode);
        findViewById(R.id.btn_proceed).setOnClickListener(this);

        ((TextView) findViewById(R.id.tv_mobileNumber)).setText(BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().MOBILE));
        ((TextView) findViewById(R.id.tv_packageName)).setText("Package");
        ((TextView) findViewById(R.id.tv_packageAmount)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(0d));
        ((TextView) findViewById(R.id.tv_totalAmountPay)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(0d));

        paymentMode.setOnCheckedChangeListener(this);
        findViewById(R.id.backbtn_from_membership).setOnClickListener(this);


    }

    public void showPackageDetails(PackageListData.Packages selectedPackage) {
        PackageID=selectedPackage.getId();
        PackageName=selectedPackage.getPackageName();
        PackageAmount= String.valueOf(selectedPackage.getPackageAmount());
        ((TextView) findViewById(R.id.tv_packageName)).setText(selectedPackage.getPackageName());
        ((TextView) findViewById(R.id.tv_packageAmount)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(selectedPackage.getPackageAmount()));
        ((TextView) findViewById(R.id.tv_tax)).setText(packageListData.getTax().getTaxValue() + "%");
        Double totalPayableAmount = BaseApp.getInstance().commonUtils().getAmountWithTax(selectedPackage.getPackageAmount(), Double.parseDouble(packageListData.getTax().getTaxValue()));
        ((TextView) findViewById(R.id.tv_totalAmountPay)).setText(getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(totalPayableAmount));

        FinalAmount=CalculateAmount(selectedPackage.getPackageAmount());

        showDialog(BuyMemberShip.this,selectedPackage);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_buy_member_ship;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

    }

    private void getPackages() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getAllPackages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PackageListData>() {
                    @Override
                    public void onSuccess(PackageListData response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()) {

                            try{
                                packageListData = response;
                                mAdapter.addItem(response.getPackages());

                                try{
                                    ((TextView) findViewById(R.id.tv_taxDetails)).setText("Additional " + response.getTax().getTaxValue() + "% GST will be charged from the total amount");
                                }catch (Exception r){
                                    r.printStackTrace();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.buy_packageId),"Something Went Wrong",false);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.buy_packageId), true, e);
                    }
                }));
    }

    private void checkBuyPackage() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getcheckBuyPackage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        loadingDialog.hideDialog();
                        if (response.getStatus()){
                            showDialogPackage(BuyMemberShip.this);
                        }else {
                            if (TransactionType.equalsIgnoreCase("1")){

                                Intent intent=new Intent(BuyMemberShip.this,MemberBankAddPackages.class);
                                intent.putExtra("TransactionType",TransactionType);
                                intent.putExtra("PackageID",PackageID);
                                intent.putExtra("PackageName",PackageName);
                                intent.putExtra("Amount",String.valueOf(FinalAmount));
                                startActivity(intent);
                            }else if (TransactionType.equalsIgnoreCase("2")) {
                                Intent intent=new Intent(BuyMemberShip.this,MemberBankAddPackages.class);
                                intent.putExtra("TransactionType",TransactionType);
                                intent.putExtra("PackageID",PackageID);
                                intent.putExtra("PackageName",PackageName);
                                intent.putExtra("Amount",String.valueOf(FinalAmount));
                                startActivity(intent);
                            }else {
                                BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.buy_packageId), "Please Select Transfer Type", false);
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.buy_packageId), false, e.getCause());
                    }
                }));
    }

    private void BuyPackageMethod(BuyPackage buyPackage){

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.buyPackage(buyPackage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BuyPackageResponse>() {
                    @Override
                    public void onSuccess(BuyPackageResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.buy_packageId),response.getMessage(),false);
                            finish();
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.buy_packageId),response.getMessage(),false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.buy_packageId), true, e);
                    }
                }));

    }

    @Override
    public void onPackageSelect(int position, PackageListData.Packages selectedPackage) {
        showPackageDetails(selectedPackage);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_proceed:
                if (mAdapter.getSelectedData() != null) {

                    checkBuyPackage();

                } else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.buy_packageId), "Please Select Pacakage", false);
                }
                break;

            case R.id.backbtn_from_membership:
                finish();
                break;
        }
    }

    public void showDialogPackage(Activity activity) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(activity);

        dialog.setTitle("SafePe Alert")
                .setCancelable(false)
                .setMessage("\nPackage Already Purchased.\nTo Upgrade Your Package Please Contact SafePe Customer Support\n")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.dismiss();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                .setIcon(getResources().getDrawable(R.drawable.safelogo_transparent))
                .show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_walled:
                cardView.setVisibility(View.GONE);
                TransactionType = "1";
                break;

            case R.id.rb_bank:
                cardView.setVisibility(VISIBLE);
                TransactionType = "2";
                break;
        }
    }

    @Override
    public void onPasscodeMatch(boolean isPasscodeMatched) {

        if (isPasscodeMatched){
            BuyPackage buyPackage=new BuyPackage();
            buyPackage.setTransaction_type(TransactionType);
            buyPackage.setPackage_id(PackageID);
            buyPackage.setBuy_date("");
            buyPackage.setPayment_mode("");
            buyPackage.setRefrence_no("");
            buyPackage.setDocument_attached("");
            buyPackage.setPaid_to_account("");
            buyPackage.setPaid_from_account("");

            BuyPackageMethod(buyPackage);
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.buy_packageId), "Invalid Passcode", false);
        }
    }

    public void showDialog(Activity activity, PackageListData.Packages selectedPackage) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.single_package_detail);

        ImageView imageView = dialog.findViewById(R.id.image_package1);
        TextView PackNameTV = dialog.findViewById(R.id.package_detail_dialog);

        PackNameTV.setText(selectedPackage.getDescription());

        try {
            if (TextUtils.isEmpty(selectedPackage.getImage())){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.image_not_available));
            }else {
                Picasso.get()
                        .load(BASE_URL+ selectedPackage.getImage())
                        .error(getResources().getDrawable(R.drawable.image_not_available))
                        .into(imageView);
            }

        }catch (Exception er){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.image_not_available));
            er.printStackTrace();
        }

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    private double CalculateAmount(Double amount){

        double minusAmount=0.0f;
        minusAmount=((( amount) / 100) * 18.00);

        return minusAmount+amount;
    }
}
