package com.safepayu.wallet.ecommerce.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.safepayu.wallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EcomHomeFragment extends Fragment {

    private ImageView CartBtn,NotificationBtn;

    public EcomHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ecom_home, container, false);
        findId(view);
        return view;
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    private void findId(View view) {
        CartBtn = view.findViewById(R.id.cartBtn_main);
        NotificationBtn = view.findViewById(R.id.favBtn_main);

        CartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new CartFragment());
            }
        });

        NotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new WishlistFragment());
            }
        });
    }

}
