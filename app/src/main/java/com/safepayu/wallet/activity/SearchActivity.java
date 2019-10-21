package com.safepayu.wallet.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
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
            }
        });

        SearchED.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_SEARCH))) {


                    if (TextUtils.isEmpty(SearchED.getText().toString().trim())){
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.searchLayout),"Please Enter Anything In Search Box",false);
                    }else {
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
                    handled = true;
                }
                return handled;
            }
        });
        
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

            ListPos=1;
            
        }else if (search.equalsIgnoreCase("Package")){
            returnList.add("Buy Package");
            returnList.add("Package Details");

            ListPos=2;
            
        }else if (search.equalsIgnoreCase("Wallet")){
            returnList.add("Commission Wallet");
            returnList.add("Transfer Commission Wallet To Main Wallet");
            returnList.add("Add Money");
            returnList.add("Wallet History");
            returnList.add("Withdraw From Wallet");

            ListPos=3;
            
        }else if (search.equalsIgnoreCase("Recharge")){
            returnList.add("Mobile Recharge");
            returnList.add("DTH Recharge");
            returnList.add("Recharge History");
            returnList.add("Metro Recharge");
            returnList.add("Google Play");

            ListPos=4;
        }
        
        
        return returnList;
    }

    @Override
    public void onListSelect(int position, String SearchItem) {

        Toast.makeText(this, SearchItem, Toast.LENGTH_SHORT).show();

    }
}
