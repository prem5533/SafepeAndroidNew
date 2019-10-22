package com.safepayu.wallet.activity.booking;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

public class TrainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvBookTickets, tvCheckPnr,tvTrainStatus;
    private LinearLayout linearLayoutBookTickets, linearLayoutCheckPNR,linearLayoutTrainTickets;
    private Button searchTrainBtn,backBtn;


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        findId();
    }

    private void findId() {
        tvBookTickets = findViewById(R.id.tv_book_tickets);
        tvCheckPnr = findViewById(R.id.tv_check_pnr);
        tvTrainStatus = findViewById(R.id.tv_train_status);
        linearLayoutBookTickets = findViewById(R.id.layout_book_tickets_tab);
        linearLayoutCheckPNR = findViewById(R.id.layout_check_pnr_tab);
        linearLayoutTrainTickets = findViewById(R.id.layout_train_status_tab);
        backBtn = findViewById(R.id.recharge_back_btn);
        searchTrainBtn = findViewById( R.id.search_train_btn);

        //set listener

        tvTrainStatus.setOnClickListener(this);
        tvCheckPnr.setOnClickListener(this);
        tvBookTickets.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        searchTrainBtn.setOnClickListener(this);

        tvBookTickets.setBackgroundDrawable(getDrawable(R.drawable.green_rounded));
        tvBookTickets.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recharge_back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
            case R. id.search_train_btn:

                CheckValidate();
                break;

            case R.id.tv_book_tickets:
                tvBookTickets.setBackgroundDrawable(getDrawable(R.drawable.green_rounded));
                tvBookTickets.setTextColor(getResources().getColor(R.color.white));
                tvCheckPnr.setBackgroundDrawable(getDrawable(R.drawable.edittext_rounded));
                tvCheckPnr.setTextColor(getResources().getColor(R.color.darker_gray));
                tvTrainStatus.setBackgroundDrawable(getDrawable(R.drawable.edittext_rounded));
                tvTrainStatus.setTextColor(getResources().getColor(R.color.darker_gray));

                linearLayoutBookTickets.setVisibility(View.VISIBLE);
                linearLayoutCheckPNR.setVisibility(View.GONE);
                linearLayoutTrainTickets.setVisibility(View.GONE);

                break;
            case R.id.tv_check_pnr:
                tvCheckPnr.setBackgroundDrawable(getDrawable(R.drawable.green_rounded));
                tvCheckPnr.setTextColor(getResources().getColor(R.color.white));
                tvTrainStatus.setBackgroundDrawable(getDrawable(R.drawable.edittext_rounded));
                tvTrainStatus.setTextColor(getResources().getColor(R.color.darker_gray));
                tvBookTickets.setBackgroundDrawable(getDrawable(R.drawable.edittext_rounded));
                tvBookTickets.setTextColor(getResources().getColor(R.color.darker_gray));

                linearLayoutBookTickets.setVisibility(View.GONE);
                linearLayoutCheckPNR.setVisibility(View.VISIBLE);
                linearLayoutTrainTickets.setVisibility(View.GONE);

                break;
            case R.id.tv_train_status:
                tvTrainStatus.setBackgroundDrawable(getDrawable(R.drawable.green_rounded));
                tvTrainStatus.setTextColor(getResources().getColor(R.color.white));
                tvBookTickets.setBackgroundDrawable(getDrawable(R.drawable.edittext_rounded));
                tvBookTickets.setTextColor(getResources().getColor(R.color.darker_gray));
                tvCheckPnr.setBackgroundDrawable(getDrawable(R.drawable.edittext_rounded));
                tvCheckPnr.setTextColor(getResources().getColor(R.color.darker_gray));

                linearLayoutBookTickets.setVisibility(View.GONE);
                linearLayoutCheckPNR.setVisibility(View.GONE);
                linearLayoutTrainTickets.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void CheckValidate() {

        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_train),"Coming Soon",false);
    }
}
