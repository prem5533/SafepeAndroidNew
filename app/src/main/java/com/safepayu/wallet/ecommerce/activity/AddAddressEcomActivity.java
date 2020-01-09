package com.safepayu.wallet.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.adapter.ChangeAddressAdapter;
import com.safepayu.wallet.ecommerce.adapter.OfferAdapter;

public class AddAddressEcomActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvAddNewAddress,tvChangeAddress,tvContinue,tvSaveAddress,tvConfirmAddress;
    public   Dialog dialog;
    private Button backBtnDialog,backBtnChangeAddress,backBtnAddress;
    private RecyclerView addressList;
    private ChangeAddressAdapter changeAddressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_ecom);
        findId();
    }

    private void findId() {
        tvAddNewAddress = findViewById(R.id.tvaddnew_addaddress);
        tvChangeAddress = findViewById(R.id.tvchange_addaddress);
        tvContinue = findViewById(R.id.tv_continue_addaddress);
        backBtnAddress = findViewById(R.id.back_btn_address);

        //set listener
        tvAddNewAddress.setOnClickListener(this);
        tvChangeAddress.setOnClickListener(this);
        tvContinue.setOnClickListener(this);
        backBtnAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvaddnew_addaddress:
                showDialogAddress(AddAddressEcomActivity.this);
                break;
            case R.id.tvchange_addaddress:
                showDialogChange(AddAddressEcomActivity.this);
                break;
            case R.id.back_btn_address_dialog:
                dialog.dismiss();
                break;
            case R.id.back_btn_change_address_dialog:
                dialog.dismiss();
                break;
            case R.id.tv_continue_addaddress:
                Toast.makeText(getApplicationContext(),"Payment Coming Soon",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_save_address_addaddress:
                dialog.dismiss();
                break;
            case R.id.tv_confirm_addaddress:
                dialog.dismiss();
                break;
            case R.id.back_btn_address:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;
        }
    }



    private void showDialogAddress(AddAddressEcomActivity addAddressEcomActivity) {
        dialog = new Dialog(addAddressEcomActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.address_ecom_dialog);

        backBtnDialog = dialog.findViewById(R.id.back_btn_address_dialog);
        tvSaveAddress = dialog.findViewById(R.id.tv_save_address_addaddress);
        backBtnDialog.setOnClickListener(this);
        tvSaveAddress.setOnClickListener(this);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.show();
    }

    private void showDialogChange(AddAddressEcomActivity addAddressEcomActivity) {
        dialog = new Dialog(addAddressEcomActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.changeaddress_ecom_dialog);

        backBtnChangeAddress = dialog.findViewById(R.id.back_btn_change_address_dialog);
        tvConfirmAddress = dialog.findViewById(R.id.tv_confirm_addaddress);
        addressList = dialog.findViewById(R.id.address_list);
        backBtnChangeAddress.setOnClickListener(this);
        tvConfirmAddress.setOnClickListener(this);

        addressList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        changeAddressAdapter = new ChangeAddressAdapter(getApplicationContext());
        addressList.setAdapter(changeAddressAdapter);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.show();
    }
}
