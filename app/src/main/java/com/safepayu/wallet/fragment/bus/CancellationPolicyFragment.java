package com.safepayu.wallet.fragment.bus;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.safepayu.wallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CancellationPolicyFragment extends Fragment {


    public CancellationPolicyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_cancellation_policy, container, false);

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
