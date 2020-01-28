package com.safepayu.wallet.ecommerce.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.adapter.IssueListAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.response.IssueListResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ChatIssueList extends AppCompatActivity implements IssueListAdapter.IssueDetailListner{
    private Button BackBtn;
    private RecyclerView chatRecyclerView;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_issuelist_vendor_activity);
        findId();
    }

    private void findId() {
        loadingDialog = new LoadingDialog(this);

        BackBtn=findViewById(R.id.backBtn_chatIssueListLayout);
        chatRecyclerView = findViewById(R.id.recycleIssueList_chatIssueListLayout);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //linearLayoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(linearLayoutManager);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (isNetworkAvailable()){
            getMessageEcomm();
        }else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatIssueListLayout), "No Internet Connection!", true);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getMessageEcomm() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiServiceEcom apiService = ApiClientEcom.getClient(this).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiService.getMessageEcomm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<IssueListResponse>() {
                    @Override
                    public void onSuccess(IssueListResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            IssueListAdapter issueListAdapter=new IssueListAdapter(ChatIssueList.this,response.getData(),ChatIssueList.this);
                            chatRecyclerView.setAdapter(issueListAdapter);
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.chatIssueListLayout), response.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.chatIssueListLayout), true, e);
                    }
                }));
    }

    @Override
    public void IssueDetail(int position, IssueListResponse.DataBean dataBean) {
        Intent intent=new Intent(ChatIssueList.this, VendorChatActivity.class);
        intent.putExtra("venueId",dataBean.getVenue_id());
        intent.putExtra("orderId",String.valueOf(dataBean.getOrder_id()));
        intent.putExtra("venueName",String.valueOf(dataBean.getVenue_name()));
        intent.putExtra("contactNumber",String.valueOf(dataBean.getPhone_number()));
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
    }
}
