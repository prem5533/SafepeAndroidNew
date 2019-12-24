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
import com.safepayu.wallet.adapter.bus.BusHistoryListAdapter;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.request.booking.flight.FlightBookingDetailRequest;
import com.safepayu.wallet.models.response.booking.bus.BusCancelResponse;
import com.safepayu.wallet.models.response.booking.bus.BusHistoryResponse;
import com.safepayu.wallet.models.response.booking.flight.FlighPdfResponse;
import com.safepayu.wallet.utils.pdf.DownloadTask;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BusHistory  extends Fragment implements BusHistoryListAdapter.BusBookListListener,BusHistoryListAdapter.DownloadListListener{

    private LoadingDialog loadingDialog;
    private RecyclerView rvHistoryList;
    private BusHistoryListAdapter adapter;
    private BusHistoryResponse historyResponse;
    private  String ReferenceNo="";

    public BusHistory() {
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

        getBusHistory();
        return rootView;
    }

    private void getBusHistory() {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getBusHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BusHistoryResponse>() {
                    @Override
                    public void onSuccess(BusHistoryResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            try {
                                historyResponse=response;
                                adapter=new BusHistoryListAdapter(getActivity(),historyResponse.getData(),BusHistory.this,BusHistory.this);
                                rvHistoryList.setAdapter(adapter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.hotelBookingHistory), response.getMessage(), false);
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

        BaseApp.getInstance().getDisposable().add(apiService.getBusCancel(ReferenceNo,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BusCancelResponse>() {
                    @Override
                    public void onSuccess(BusCancelResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            try {
                                historyResponse.getData().get(position).setBookingStatus("Cancelled");
                                adapter.notifyDataSetChanged();
                                BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.hotelBookingHistory), response.getData().getMessage(), true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.hotelBookingHistory), response.getMessage(), true);
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
    public void onLocationClickTo(int position, String BookingRefNo, String SeatNos) {
        ReferenceNo=BookingRefNo;

        if (!TextUtils.isEmpty(ReferenceNo)){

            try {
                String seatNo=SeatNos;
                seatNo=seatNo+"~";
                ArrayList<String> seat=new ArrayList<>();
                int start=0;
                for (int j=0;j<seatNo.length();j++){
                    char c=seatNo.charAt(j);
                    if (c=='~'){
                        seat.add(seatNo.substring(start,j));
                        start=j+1;
                    }
                }
            }catch (Exception e){

                e.printStackTrace();
            }
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

    //**********************************for pdf download************************
    private void getPdf(String REFno) {

        FlightBookingDetailRequest flightBookingDetailRequest = new FlightBookingDetailRequest();
        //  flightBookingDetailRequest.setReferenceNo("300905016582");
        // flightBookingDetailRequest.setReferenceNo("300270016738");
        flightBookingDetailRequest.setReferenceNo(REFno);
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(getActivity()).create(ApiService.class);
        BaseApp.getInstance().getDisposable().add(apiService.getFlightPdf(flightBookingDetailRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FlighPdfResponse>(){
                    @Override
                    public void onSuccess(FlighPdfResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {

                            new DownloadTask(getActivity(), response.getData());
                        }else {
                            BaseApp.getInstance().toastHelper().showSnackBar(getActivity().findViewById(R.id.hotelBookingHistory),response.getMessage(),getAllowEnterTransitionOverlap());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(getView().findViewById(R.id.hotelBookingHistory), false, e.getCause());
                    }
                }));

    }

    @Override
    public void onLocationClickTo(String BookingRefNo) {
        getPdf( BookingRefNo);
    }
}
