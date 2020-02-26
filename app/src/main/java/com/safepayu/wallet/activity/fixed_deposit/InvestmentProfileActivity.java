package com.safepayu.wallet.activity.fixed_deposit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.ResponseModel;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.safepayu.wallet.api.ApiClient.BASE_URL;

public class InvestmentProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public LoadingDialog loadingDialog;
    public Button send_back_btn_fd;
    public TextView tv_toolbar_name;
    public CircleImageView img_user;
    public TextView tv_fd_refer_id, tv_amount, tv_bonus_amount, tv_name, tv_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_profile);

        loadingDialog = new LoadingDialog(this);

        send_back_btn_fd = findViewById(R.id.send_back_btn_fd);
        tv_toolbar_name = findViewById(R.id.tv_toolbar_name);
        tv_toolbar_name.setText("Investment Profile");
        img_user = findViewById(R.id.profile_pic);
        tv_fd_refer_id = findViewById(R.id.tv_fd_refer_id);
        tv_amount = findViewById(R.id.tv_amount);
        tv_bonus_amount = findViewById(R.id.tv_bonus_amount);
        tv_name = findViewById(R.id.tv_name);
        tv_mobile = findViewById(R.id.tv_mobile);

        send_back_btn_fd.setOnClickListener(this);
        getInvestment();
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void getInvestment() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiService apiService = ApiClient.getClient(InvestmentProfileActivity.this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getInvestmentProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResponseModel>() {
                    @Override
                    public void onSuccess(ResponseModel response) {
                        loadingDialog.hideDialog();
                        if (response.status) {
                            try {
                                if (response.data.image == null) {
                                    img_user.setImageResource(R.drawable.user);
                                } else {
                                    Glide.with(InvestmentProfileActivity.this)
                                            .load(BASE_URL + response.data.image.trim())
                                            .into(img_user);
                                }
                                tv_fd_refer_id.setText(response.data.fdReferId);
                                tv_amount.setText(response.data.amount);
                                tv_bonus_amount.setText(response.data.bonus_amount);
                                tv_name.setText(response.data.name);
                                tv_mobile.setText(response.data.mobile);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(InvestmentProfileActivity.this.findViewById(R.id.ll_parant), response.message, false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(InvestmentProfileActivity.this.findViewById(R.id.ll_parant), false, e.getCause());
                    }
                }));
    }
}
