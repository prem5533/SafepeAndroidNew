package com.safepayu.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.booking.MetroActivity;
import com.safepayu.wallet.activity.booking.TrainActivity;
import com.safepayu.wallet.activity.booking.bus.BusActivity;
import com.safepayu.wallet.activity.booking.flight.FlightsActivity;
import com.safepayu.wallet.activity.booking.hotel.HotelActivity;
import com.safepayu.wallet.activity.recharge.DthRecharge;
import com.safepayu.wallet.activity.recharge.ElectricityPay;
import com.safepayu.wallet.activity.recharge.GasPay;
import com.safepayu.wallet.activity.recharge.MobileRecharge;
import com.safepayu.wallet.activity.recharge.PostpaidBillpay;
import com.safepayu.wallet.activity.recharge.WaterBillPay;
import com.safepayu.wallet.adapter.SearchAdapter;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity implements SearchAdapter.OnListSelectListener {
    
    private Button BackBtn,SearchBtn;
    private EditText SearchED;
    private ArrayList<String> MainList;
    private RecyclerView recylerSearchList;
    int ListPos=0;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        BackBtn=findViewById(R.id.search_back_btn);
        SearchBtn=findViewById(R.id.btnSearch);
        SearchED=findViewById(R.id.search_input);
        recylerSearchList = findViewById(R.id.recycler_search);

        MainList=new ArrayList<>();
        


        recylerSearchList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        searchAdapter = new SearchAdapter(getApplicationContext(), MainList,SearchActivity.this);
        recylerSearchList.setAdapter(searchAdapter);

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(SearchED.getText().toString().trim())){
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.searchLayout),"Please Enter Anything In Search Box",false);
                }else {
                    performSearch();
                }
            }
        });

        SearchED.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    performSearch();
                    return true;
                }
                return false;
            }
        });
        
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void performSearch() {
        BaseApp.getInstance().commonUtils().hideKeyboard(SearchActivity.this);
        MainList.clear();
        MainList=getData(SearchED.getText().toString().trim());

        if (MainList.size()>0){
            searchAdapter = new SearchAdapter(getApplicationContext(), MainList,SearchActivity.this);
            recylerSearchList.setAdapter(searchAdapter);
            searchAdapter.notifyDataSetChanged();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.searchLayout),"Sorry! Not Found Anything",false);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.search_activity;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {
        if (!isConnected){
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.searchLayout),getResources().getString(R.string.internet_check),false);
        }

    }
    
    private ArrayList<String> getData(String search){
        ArrayList<String> returnList=new ArrayList<>();
        returnList.clear();
        
        if (search.equalsIgnoreCase("Profile")){
            returnList.add("Profile");
            returnList.add("Change Address");
            returnList.add("Update Address");
            returnList.add("Change Password");
            returnList.add("QR Code");
            returnList.add("Address");
            
        }else if (search.equalsIgnoreCase("Address")){
            returnList.add("Profile");
            returnList.add("Change Address");
            returnList.add("Update Address");
            returnList.add("Change Password");
            returnList.add("QR Code");
            returnList.add("Address");

        }else if (search.equalsIgnoreCase("Package")){
            returnList.add("Buy Package");
            returnList.add("Package Details");
            
        }else if (search.equalsIgnoreCase("Wallet") || search.equalsIgnoreCase("Commission") ||
                search.equalsIgnoreCase("Money") || search.equalsIgnoreCase("Withdraw") ||
                search.equalsIgnoreCase("Pay") || search.equalsIgnoreCase("Transfer") ||
                search.equalsIgnoreCase("Transaction")){
            returnList.add("Commission Wallet");
            returnList.add("Transfer Commission Wallet To Main Wallet");
            returnList.add("Add Money");
            returnList.add("Wallet History");
            returnList.add("Withdraw From Wallet");
            returnList.add("Wallet ");
            returnList.add("Pay ");
            
        }else if (search.equalsIgnoreCase("Recharge")){
            returnList.add("Mobile Recharge");
            returnList.add("DTH Recharge");
            returnList.add("Recharge History");
            returnList.add("Metro Recharge");
            returnList.add("Google Play");

        }else if (search.equalsIgnoreCase("History")){
            returnList.add("Wallet History");
            returnList.add("Recharge History");
            returnList.add("My Orders");

        }else if (search.equalsIgnoreCase("Shopping")){
            returnList.add("Shopping");
            returnList.add("My Orders");

        }else if (search.equalsIgnoreCase("Passcode")){
            returnList.add("Change Passcode");

        }else if (search.equalsIgnoreCase("Order") || search.equalsIgnoreCase("Orders")){
            returnList.add("Shopping");
            returnList.add("My Orders");

        }else if (search.equalsIgnoreCase("genealogy")){
            returnList.add("Genealogy");

        }else if (search.equalsIgnoreCase("Refer") || search.equalsIgnoreCase("Refer & Earn")){
            returnList.add("Refer & Earn");

        }else if (search.equalsIgnoreCase("Earn")){
            returnList.add("Refer & Earn");

        }else if (search.equalsIgnoreCase("KYC")){
            returnList.add("KYC");

        }else if (search.equalsIgnoreCase("Contact") || search.equalsIgnoreCase("Contact Us")){
            returnList.add("Contact Us");

        }else if (search.equalsIgnoreCase("Bill Payment") || search.equalsIgnoreCase("Bill")){
            returnList.add("Water Bill Payment");
            returnList.add("Electricity Bill Payment");
            returnList.add("Landine Bill Payment");
            returnList.add("Postpaid Bill Payment");
            returnList.add("Gas Bill Payment");
            returnList.add("Credit Card Bill Payment");


        }else if (search.equalsIgnoreCase("Booking")){
            returnList.add("Movie Booking");
            returnList.add("Flight Booking");
            returnList.add("Bus Booking");
            returnList.add("Train Booking");
            returnList.add("Hotel Booking");


        }else if (search.equalsIgnoreCase("offers") || search.equalsIgnoreCase("vouchers")){
            returnList.add("KFC");
            returnList.add("Brand Factory");
            returnList.add("Dominos");
            returnList.add("Big Bazar");

        }else if (search.equalsIgnoreCase("KFC") || search.equalsIgnoreCase("Dominos")){
            returnList.add("KFC");
            returnList.add("Dominos");

        }else if (search.equalsIgnoreCase("Brand Factory") || search.equalsIgnoreCase("Big Bazar")){
            returnList.add("Brand Factory");
            returnList.add("Big Bazar");

        }else if (search.equalsIgnoreCase("UPI")){
            returnList.add("UPI");

        }

        return returnList;
    }

    @Override
    public void onListSelect(int position, String SearchItem) {
        SendActivity(SearchItem);
    }

    private void SendActivity(String SearchItem){

        if (SearchItem.equalsIgnoreCase("Profile") || SearchItem.equalsIgnoreCase("Change Address") ||
                SearchItem.equalsIgnoreCase("Update Address") || SearchItem.equalsIgnoreCase("Change Password") ||
                SearchItem.equalsIgnoreCase("QR Code") || SearchItem.equalsIgnoreCase("Address")){

            startActivity(new Intent(getApplicationContext(), Profile.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if ( SearchItem.equalsIgnoreCase("Package Details")){

            startActivity(new Intent(getApplicationContext(), PackageDetails.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Buy Package")){

            startActivity(new Intent(getApplicationContext(), BuyMemberShip.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Wallet")){

            startActivity(new Intent(getApplicationContext(), WalletActivity.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Transfer Commission Wallet To Main Wallet")){

            startActivity(new Intent(getApplicationContext(), TransferCommissionToWallet.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Commission Wallet")){

            startActivity(new Intent(getApplicationContext(), Commission.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Add Money")){

            startActivity(new Intent(getApplicationContext(), WalletAddMoney.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Wallet History")){

            startActivity(new Intent(getApplicationContext(), WalletHistory.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Withdraw From Wallet")){

            startActivity(new Intent(getApplicationContext(), SendMoney.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Wallet")){

            startActivity(new Intent(getApplicationContext(), WalletActivity.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Recharge History")){

            startActivity(new Intent(getApplicationContext(), RechargeHistory.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Mobile Recharge")){

            startActivity(new Intent(getApplicationContext(), MobileRecharge.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("DTH Recharge")){

            startActivity(new Intent(getApplicationContext(), DthRecharge.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Metro Recharge")){

            startActivity(new Intent(getApplicationContext(), MetroActivity.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Google Play")){

            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();

        }else if (SearchItem.equalsIgnoreCase("Shopping")){

            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();

        }else if (SearchItem.equalsIgnoreCase("My Orders")){

            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();

        }else if (SearchItem.equalsIgnoreCase("Change Passcode")){

            startActivity(new Intent(getApplicationContext(), ForgotPasscode.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Genealogy")){

            startActivity(new Intent(getApplicationContext(), Geneology.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Refer & Earn")){

            startActivity(new Intent(getApplicationContext(), ReferAndEarn.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("KYC")){

            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();

        }else if (SearchItem.equalsIgnoreCase("Contact Us")){

            startActivity(new Intent(getApplicationContext(), ContactUs.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Credit Card Bill Payment")){

            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();

        }else if (SearchItem.equalsIgnoreCase("Gas Bill Payment")){

            startActivity(new Intent(getApplicationContext(), GasPay.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Postpaid Bill Payment")){

            startActivity(new Intent(getApplicationContext(), PostpaidBillpay.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Landine Bill Payment")){

            startActivity(new Intent(getApplicationContext(), PostpaidBillpay.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Electricity Bill Payment")){

            startActivity(new Intent(getApplicationContext(), ElectricityPay.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Water Bill Payment")){

            startActivity(new Intent(getApplicationContext(), WaterBillPay.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Movie Booking")){

            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();

        }else if (SearchItem.equalsIgnoreCase("Flight Booking")){

            startActivity(new Intent(getApplicationContext(), FlightsActivity.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Bus Booking")){

            startActivity(new Intent(getApplicationContext(), BusActivity.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Train Booking")){

            startActivity(new Intent(getApplicationContext(), TrainActivity.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("Hotel Booking")){

            startActivity(new Intent(getApplicationContext(), HotelActivity.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }else if (SearchItem.equalsIgnoreCase("KFC")){

            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();

        }else if (SearchItem.equalsIgnoreCase("Dominos")){

            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();

        }else if (SearchItem.equalsIgnoreCase("Brand Factory")){

            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();

        }else if (SearchItem.equalsIgnoreCase("Big Bazar")){

            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();

        }else if (SearchItem.equalsIgnoreCase("UPI")){

            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();

        }else if (SearchItem.equalsIgnoreCase("Pay")){

            startActivity(new Intent(getApplicationContext(), SendMoneyToWallet.class));
            overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);

        }
    }
}
