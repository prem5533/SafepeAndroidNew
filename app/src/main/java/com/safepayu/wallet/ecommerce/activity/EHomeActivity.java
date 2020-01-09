package com.safepayu.wallet.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.fragment.CartFragment;
import com.safepayu.wallet.ecommerce.fragment.HomeFragment;
import com.safepayu.wallet.ecommerce.fragment.WishlistFragment;

public class EHomeActivity extends AppCompatActivity  {

    private ImageView CartBtn,NotificationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ehome);
        loadFragment(new HomeFragment());
        findId();

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void findId() {
        CartBtn = findViewById(R.id.cartBtn_main);
        NotificationBtn = findViewById(R.id.notification_main);

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
