package com.safepayu.wallet.fragment.bus;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.booking.bus.BusListActivity;

import java.util.ArrayList;

import static com.safepayu.wallet.activity.booking.bus.BusListActivity.response1;

/**
 * A simple {@link Fragment} subclass.
 */
public class CancellationPolicyFragment extends Fragment {

    private ArrayList<String> CancelList;
    TextView tvCancelPolicy50,tvCancelPolicy100,tvCancelPolicyAmount50;

    public CancellationPolicyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_cancellation_policy, container, false);

        CancelList=new ArrayList<>();
        try {
            String cancel=response1.getData().getAvailableTrips().get(0).getCancellationPolicy();

            for (int i=0;i<cancel.length();i++){
                char c=cancel.charAt(i);
                if (c==';'){
                    CancelList.add(cancel.substring(0,i));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        tvCancelPolicy50=rootView.findViewById(R.id.cancelPolicy50);
        tvCancelPolicy100=rootView.findViewById(R.id.cancelPolicy100);
        tvCancelPolicyAmount50=rootView.findViewById(R.id.cancelPolicyAmount50);

        int position=BusListActivity.SelectedBusList;
        String price="";

        try {
            price=response1.getData().getAvailableTrips().get(position).getFares();
            if (price.contains("/")){
                for (int i=0;i<price.length();i++){
                    char c=price.charAt(i);
                    if (c=='/'){
                        price=price.substring(0,i);
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        double refundAmt=(double)((int)Double.parseDouble(price)/2);

        tvCancelPolicyAmount50.setText(getActivity().getResources().getString(R.string.rupees)+" "+refundAmt);

        TextView ProceedBtn=rootView.findViewById(R.id.proceedBtncancelPolicy);
        ProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }

}
