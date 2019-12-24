package com.safepayu.wallet.fragment.history;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.hotel.HotelBookHistoryAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.booking.hotel.HotelCancelResponse;
import com.safepayu.wallet.models.response.booking.hotel.HotelHistoryResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class HotelHistory  extends Fragment implements HotelBookHistoryAdapter.HotelBookListListener{

    private LoadingDialog loadingDialog;
    private RecyclerView rvHistoryList;
    private HotelBookHistoryAdapter adapter;
    private HotelHistoryResponse historyResponse;
    private String ReferenceNo="";

    public HotelHistory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.hotel_history_fragment, container, false);

        loadingDialog = new LoadingDialog(getActivity());
        rvHistoryList = rootView.findViewById(R.id.hotelBookList_hotelBookingHistory);
        rvHistoryList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        getHotelHistory();
        return rootView;
    }

    private void getHotelHistory() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getHotelHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<HotelHistoryResponse>() {
                    @Override
                    public void onSuccess(HotelHistoryResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            try {
                                historyResponse=response;
                                adapter=new HotelBookHistoryAdapter(getActivity(),historyResponse.getData(),HotelHistory.this);
                                rvHistoryList.setAdapter(adapter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.hotelBookingHistory), response.getMesage(), false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.hotelBookingHistory), false, e.getCause());
                    }
                }));
    }

    private void getHotelCancel(final int position) {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getHotelCancelRoom(ReferenceNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<HotelCancelResponse>() {
                    @Override
                    public void onSuccess(HotelCancelResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            try {
                                historyResponse.getData().get(position).setStatus(response.getData().getBookingStatus());
                                adapter.notifyDataSetChanged();
                                BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.hotelBookingHistory), response.getData().getMessage(), true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.hotelBookingHistory), response.getMesage(), true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getActivity().findViewById(R.id.hotelBookingHistory), false, e.getCause());
                    }
                }));
    }

    @Override
    public void onLocationClickTo(int position, String BookingRefNo, HotelHistoryResponse.DataBean.HotelPolicyBean HotelPolicy) {
        ReferenceNo=BookingRefNo;

        if (!TextUtils.isEmpty(ReferenceNo)){
            showDialog(position);
        }
    }

    public void showDialog(final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle("SafePe Alert")
                .setCancelable(false)
                .setMessage("\nAre You Sure You Want To Cancel This Trip?\n")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        getHotelCancel(position);
                        dialog.dismiss();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                .setIcon(getResources().getDrawable(R.drawable.safelogo_transparent))
                .show();
    }
}
