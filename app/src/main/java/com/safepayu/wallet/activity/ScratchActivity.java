package com.safepayu.wallet.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.adapter.ScratchListAdapter;

public class ScratchActivity extends AppCompatActivity implements ScratchListAdapter.OnScratchSelectListener{

    private RecyclerView recyclerViewScratch;
    private Button BackBtn;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scratch_activity);

        BackBtn = findViewById(R.id.backBtn_scratchLayout);

        recyclerViewScratch = findViewById(R.id.list_scratchLayout);
        recyclerViewScratch.setLayoutManager(new GridLayoutManager(this, 2));

        ScratchListAdapter scratchListAdapter=new ScratchListAdapter(this,this);
        recyclerViewScratch.setAdapter(scratchListAdapter);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onScratchSelect(int position) {

    }
}