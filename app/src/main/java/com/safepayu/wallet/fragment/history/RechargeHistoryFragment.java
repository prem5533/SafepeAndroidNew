package com.safepayu.wallet.fragment.history;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.ContactUs;
import com.safepayu.wallet.adapter.RechargeHistoryAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.helper.RecyclerLayoutManager;
import com.safepayu.wallet.models.response.RechargeHistoryResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RechargeHistoryFragment extends Fragment implements RechargeHistoryAdapter.OnPackageSelectListener{

    private RecyclerView RechargeHistoryListView;
    private RecyclerLayoutManager layoutManager;
    private LoadingDialog loadingDialog;
    private RechargeHistoryAdapter mAdapter;
    private RechargeHistoryResponse historyResponse;

    private Dialog dialogStatus;
    private RelativeLayout StatusColorBackground;
    private TextView StatusTV,TransactionIdTV,CustomerNumberIdTV,AmountTV,RechargeTypeTV,ContactSupportTV,DateTV;
    private Button GoToWalletBtn;

    public RechargeHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.recharge_history_fragment, container, false);

        loadingDialog = new LoadingDialog(getActivity());

        RechargeHistoryListView = rootView.findViewById(R.id.list_rechargHistory);

        layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(true);
        RechargeHistoryListView.setLayoutManager(layoutManager);
        mAdapter = new RechargeHistoryAdapter(getActivity(), this);
        RechargeHistoryListView.setAdapter(mAdapter);

        dialogStatus = new Dialog(getActivity(), android.R.style.Theme_Light);
        dialogStatus.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogStatus.setContentView(R.layout.recharge_histroy_detail);
        dialogStatus.setCancelable(true);

        StatusColorBackground=dialogStatus.findViewById(R.id.headerbar);
        StatusTV=dialogStatus.findViewById(R.id.tv_status);
        TransactionIdTV=dialogStatus.findViewById(R.id.tv_recharge_transction_id);
        CustomerNumberIdTV=dialogStatus.findViewById(R.id.tv_customer_id_number);
        AmountTV=dialogStatus.findViewById(R.id.tv_wallet_amount);
        RechargeTypeTV=dialogStatus.findViewById(R.id.tv_recharge_type);
        GoToWalletBtn=dialogStatus.findViewById(R.id.recharge_back_btn);
        ContactSupportTV=dialogStatus.findViewById(R.id.tv_contct_support);
        DateTV=dialogStatus.findViewById(R.id.tv_detil_time_date);

        getRechargeHistory();

        GoToWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogStatus.dismiss();
            }
        });

        ContactSupportTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ContactUs.class));
                dialogStatus.dismiss();
            }
        });

        return rootView;
    }

    private void SelectedHistory(RechargeHistoryResponse.DataBean selectedPackage) {

        TransactionIdTV.setText(selectedPackage.getUtransactionID());
        AmountTV.setText(getResources().getString(R.string.rupees)+" "+selectedPackage.getAmount());
        CustomerNumberIdTV.setText(selectedPackage.getNumber());
        DateTV.setText(selectedPackage.getUpdated_at());

        int rechargeTypeNo=0,statusNo=0;
        String RechargeType="";

        try{
            rechargeTypeNo=selectedPackage.getRech_type();
            statusNo=selectedPackage.getStatus();
        }catch (Exception e){
            e.printStackTrace();
        }

        if (statusNo==0){
            StatusTV.setText("Pending");
           // StatusColorBackground.setBackgroundColor(getResources().getColor(R.color.clay_yellow));
        }else  if (statusNo==1){
            StatusTV.setText("Successful");
            //StatusColorBackground.setBackgroundColor(getResources().getColor(R.color.green_500));
        }else {
            StatusTV.setText("Failed");
           // StatusColorBackground.setBackgroundColor(getResources().getColor(R.color.red_500));
        }

        if (rechargeTypeNo==1){
            RechargeType="Mobile Recharge";
        }else if (rechargeTypeNo==2){
            RechargeType="DTH Recharge";
        }else if (rechargeTypeNo==3){
            RechargeType="Electricity Bill Payment";
        }else if (rechargeTypeNo==4){
            RechargeType="Water Bill Payment";
        }else if (rechargeTypeNo==5){
            RechargeType="Gas Bill Payment";
        }else if (rechargeTypeNo==6){
            RechargeType="Postpaid Bill Payment";
        }else if (rechargeTypeNo==7){
            RechargeType="Landline Bill Payment";
        }else if (rechargeTypeNo==8){
            RechargeType="Metro Recharge";
        }
        RechargeTypeTV.setText(RechargeType);

        dialogStatus.show();
    }

    @Override
    public void onPackageSelect(int position, RechargeHistoryResponse.DataBean selectedPackage) {
        SelectedHistory(selectedPackage);
    }

    private void getRechargeHistory() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getRechargeHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RechargeHistoryResponse>() {
                    @Override
                    public void onSuccess(RechargeHistoryResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            try {
                                historyResponse = response;
                                mAdapter.addItem(response.getData());
                            } catch (Exception e) {
                                e.printStackTrace();
                                BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.rechargeHistoryLayout), "Something Went Wrong", false);
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.rechargeHistoryLayout), response.getMessage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        //Log.e(BaseApp.getInstance().toastHelper().getTag(RechargeHistory.class), "onError: " + e.getMessage());
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.rechargeHistoryLayout), false, e.getCause());
                    }
                }));
    }

}
