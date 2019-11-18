package com.safepayu.wallet.activity.booking.bus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.bus.BusListAdapter;

public class BusListActivity extends AppCompatActivity implements View.OnClickListener, BusListAdapter.OnBusItemClickListener {

    private BusListAdapter busListAdapter;
    private RecyclerView recyclerViewBus;
    private Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);
        findId();
    }

    private void findId() {
        recyclerViewBus = findViewById(R.id.list_bus);
        backBtn = findViewById(R.id.backbtn_bus_list);

        backBtn.setOnClickListener(this);

        recyclerViewBus.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        busListAdapter = new BusListAdapter(getApplicationContext(),this);
        recyclerViewBus.setAdapter(busListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backbtn_bus_list:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
        }
    }

    @Override
    public void onBusItemSelect() {
        overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
        startActivity(new Intent(BusListActivity.this, BusSeat_DetailActivity.class));

    }
}
