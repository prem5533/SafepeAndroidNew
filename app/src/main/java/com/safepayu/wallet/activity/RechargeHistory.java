package com.safepayu.wallet.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.safepayu.wallet.BaseActivity;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.fragment.history.BusHistory;
import com.safepayu.wallet.fragment.history.FlightHistory;
import com.safepayu.wallet.fragment.history.HotelHistory;
import com.safepayu.wallet.fragment.history.RechargeHistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class RechargeHistory extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapterViewPager;
    private Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(false, null, false);

        backbtn=findViewById(R.id.backbtn_rechargeHistory);
        tabLayout = findViewById(R.id.tabs_history);
        viewPager = findViewById(R.id.viewpager_history);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        adapterViewPager = new ViewPagerAdapter(getSupportFragmentManager());
        adapterViewPager.addFragment(new RechargeHistoryFragment(), "Recharge History");
        adapterViewPager.addFragment(new FlightHistory(), "Flights");
        adapterViewPager.addFragment(new BusHistory(),"Bus");
        adapterViewPager.addFragment(new HotelHistory(),"Hotel");
        viewPager.setAdapter(adapterViewPager);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void removeFragment() {
            mFragmentList.clear();
            mFragmentTitleList.clear();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.recharge_history;
    }

    @Override
    protected void connectivityStatusChanged(Boolean isConnected, String message) {

        if (!isConnected){
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.rechargeHistory1Layout),"No Internet Connection",true);
        }

    }
}
