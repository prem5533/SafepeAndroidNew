package com.safepayu.wallet.activity.booking.bus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.safepayu.wallet.R;
import com.safepayu.wallet.fragment.bus.CancellationPolicyFragment;
import com.safepayu.wallet.fragment.bus.SelectSeatFragment;

import java.util.ArrayList;
import java.util.List;

public class BusSeat_DetailActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapterViewPager;
    private TextView TripDetailTv;
    private Button BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_seat__detail);

        findId();
    }

    private void findId() {
        BackBtn=findViewById(R.id.bus_seat_back_btn);
        TripDetailTv = findViewById(R.id.tripDetailBus);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        TripDetailTv.setText(getIntent().getStringExtra("tripDetail"));

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        adapterViewPager = new ViewPagerAdapter(getSupportFragmentManager());
        adapterViewPager.addFragment(new SelectSeatFragment(), "Select Seat");
        //adapterViewPager.addFragment(new ReviewsFragment(), "Reviews");
        //adapterViewPager.addFragment(new BoardingDropFragment(),"Bording & Drop Point");
        adapterViewPager.addFragment(new CancellationPolicyFragment(),"Cancellation Policy");
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
}
