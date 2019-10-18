package com.safepayu.wallet.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.safepayu.wallet.halper.RecyclerLayoutManager;
import com.safepayu.wallet.models.request.BuyPackage;
import com.safepayu.wallet.models.response.BuyPackageResponse;
import com.safepayu.wallet.models.response.PackageListData;
import com.safepayu.wallet.utils.PasscodeClickListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.VISIBLE;

public class BuyMemberShip extends BaseActivity implements PackageListAdapter.OnPackageSelectListener, View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, PasscodeClickListener {

    private LoadingDialog loadingDialog;
    private RecyclerView packageListView;
    private RecyclerLayoutManager layoutManager;
    private PackageListAdapter mAdapter;
    private PackageListData packageListData;
    private RadioGroup paymentMode;
    private CardView cardView;
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

        showDialog(BuyMemberShip.this, PackageID, PackageName, String.valueOf(selectedPackage.getPackageAmount()), String.valueOf(BaseApp.getInstance().commonUtils().decimalFormat(totalPayableAmount)));
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
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.buy_packageId), true, e);
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
                        Log.e(BaseApp.getInstance().toastHelper().getTag(LoginActivity.class), "onError: " + e.getMessage());
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

                    if (TransactionType.equalsIgnoreCase("1")){

//                        if (BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE) == null || BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().PASSCODE).equals("")) {
//                            startActivity(new Intent(BuyMemberShip.this,CreatePassCodeActivity.class));
//                        } else {
//                            PasscodeDialog passcodeDialog = new PasscodeDialog(BuyMemberShip.this, BuyMemberShip.this, "");
//                            passcodeDialog.show();
//                        }

                        Intent intent=new Intent(BuyMemberShip.this,MemberBankAddPackages.class);
                        intent.putExtra("TransactionType",TransactionType);
                        intent.putExtra("PackageID",PackageID);
                        intent.putExtra("Amount",PackageAmount);
                        startActivity(intent);
                    }else if (TransactionType.equalsIgnoreCase("2")) {
                        Intent intent=new Intent(BuyMemberShip.this,MemberBankAddPackages.class);
                        intent.putExtra("TransactionType",TransactionType);
                        intent.putExtra("PackageID",PackageID);
                        intent.putExtra("Amount",PackageAmount);
                        startActivity(intent);
                    }else {
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.buy_packageId), "Please Select Transfer Type", false);
                    }

                } else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.buy_packageId), "Please Select Pacakage", false);
                }
                break;

            case R.id.backbtn_from_membership:
                finish();
                break;
        }
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

    public void showDialog(Activity activity, final String PackageID, String PackName, String AMount, final String Amount2Pay) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.single_package_detail);

//        TextView PackNameTV = dialog.findViewById(R.id.packageName_WalletDialog);
//        TextView AmountTV = dialog.findViewById(R.id.packageAmount_WalletDialog);
//        TextView AmountToPayTV = dialog.findViewById(R.id.totalAmountPay_WalletDialog);
//        TextView bonusAmount = dialog.findViewById(R.id.bonusAmount_WalletDialog);
//        TextView bonuscREDIT = dialog.findViewById(R.id.bonusCredit_WalletDialog);
        ImageView imageView = dialog.findViewById(R.id.image_package1);
        TextView PackNameTV = dialog.findViewById(R.id.package_detail_dialog);

        String PromotionalText="Wallet cash back Rs 10,400.\n" +
                "Cash back can be redeemed Rs 52 on working days.\n" +
                "Validity 200 Days.\n" +
                "Additional 18% GST Will be charged from the account.";

        String NanoText="Wallet cash back Rs.20,800.\n" +
                "Cash back can be redeemed\n" +
                "Rs. 104 on working days.\n" +
                "Validity 200 Days.\n" +
                "Additional 18% GST Will be charged from the account.";

        String ProText="Wallet cash back Rs.52,000.\n" +
                "Cash back can be redeemed Rs.260 on working days.\n" +
                "Validity 200 Days.\n" +
                "Additional 18% GST Will be charged from the account.";

        String SilverText="Wallet cash back Rs 1,04,000.\n" +
                "Cash back can be redeemed Rs.520 on working days.\n" +
                "Validity 200 Days.\n" +
                "Additional 18% GST Will be charged from the account.";

        String SilverPlusText="Wallet cash back Rs 1,56,000.\n" +
                "Cash back can be redeemed\n" +
                "Rs.780 on working days.\n" +
                "Validity 200 Days.\n" +
                "Additional 18% GST Will be charged from the account.";

        String GoldText="Wallet cash back Rs 2,08,000.\n" +
                "Cash back can be redeemed\n" +
                "Rs.1040 on working days.\n" +
                "Validity 200 Days.\n" +
                "Additional 18% GST Will be charged from the account.";

        String TitaniumText="Wallet cash back Rs 3,12,000 .\n" +
                "Cash back can be redeemed\n" +
                "Rs. 1560 on working days.\n" +
                "Validity 200 Days.\n" +
                "Additional 18% GST Will be charged from the account.";

        String DiamondText="Wallet cash back Rs 4,16,000 .\n" +
                "Cash back can be redeemed\n" +
                "Rs.2080 on working days.\n" +
                "Validity 200 Days.\n" +
                "Additional 18% GST Will be charged from the account.";

        if (PackName.equalsIgnoreCase("Promotional")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.promotional_package));
            PackNameTV.setText(PromotionalText);
        }else if (PackName.equalsIgnoreCase("Nano")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.nano_package));
            PackNameTV.setText(NanoText);
        }else if (PackName.equalsIgnoreCase("Pro")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.pro_package));
            PackNameTV.setText(ProText);
        }else if (PackName.equalsIgnoreCase("Silver")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.silver_package));
            PackNameTV.setText(SilverText);
        }else if (PackName.equalsIgnoreCase("Silver Plus")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.silver_plus_package));
            PackNameTV.setText(SilverPlusText);
        }else if (PackName.equalsIgnoreCase("Gold")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.gold_package));
            PackNameTV.setText(GoldText);
        }else if (PackName.equalsIgnoreCase("Titanium")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.titanium_package));
            PackNameTV.setText(TitaniumText);
        }else if (PackName.equalsIgnoreCase("Diamond")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.diamond_package));
            PackNameTV.setText(DiamondText);
        }


//        try{
//            double bonusAmout=CalculateAmount((int)Float.parseFloat(PackageAmount));
//            bonuscREDIT.setText(getResources().getString(R.string.rupees)+" "+(int)bonusAmout);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try{
//            bonusAmount.setText(getResources().getString(R.string.rupees)+" "+2*(int)Float.parseFloat(PackageAmount));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        PackNameTV.setText(PackName);
//        AmountTV.setText(getResources().getString(R.string.rupees)+" "+AMount);
//        AmountToPayTV.setText(getResources().getString(R.string.rupees)+" "+Amount2Pay);
//
//
//        Button dialogButton = (Button) dialog.findViewById(R.id.payBtn_WalletDialog);
//        dialogButton.setVisibility(View.GONE);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    private double CalculateAmount(int amount){

        double minusAmount=0.0f;
        minusAmount=((((double) amount) / 100) * 1.04);

        return minusAmount;
    }
}
