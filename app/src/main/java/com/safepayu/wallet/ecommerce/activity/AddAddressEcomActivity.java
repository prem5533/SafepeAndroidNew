package com.safepayu.wallet.ecommerce.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.DatePickerEcom;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.adapter.CartItemQuantityAdapter;
import com.safepayu.wallet.ecommerce.adapter.ChangeAddressAdapter;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.api.ApiServiceEcom;
import com.safepayu.wallet.ecommerce.model.request.OrderSaveRequest;
import com.safepayu.wallet.ecommerce.model.request.SaveEcomAddressRequest;
import com.safepayu.wallet.ecommerce.model.response.AddressUserResponse;
import com.safepayu.wallet.ecommerce.model.response.CartsQuantityResponse;
import com.safepayu.wallet.ecommerce.model.response.RemoveEcomAddressResponse;
import com.safepayu.wallet.ecommerce.model.response.UpdateEcomAddressResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AddAddressEcomActivity extends AppCompatActivity implements View.OnClickListener, ChangeAddressAdapter.onEditAddress, CompoundButton.OnCheckedChangeListener {

    private TextView tvAddNewAddress, tvChangeAddress, tvContinue, tvSaveAddress, tvConfirmAddress,saveBtnAddress,tvUsername,tvType,tvAddress,tvCity,
            tvPincode,tvState,tvMobile,tvOrderRs,tvDeliveryCharge,tvTotalRs,viewDetail,price_detail,tvProductActualprice,tvTaxCharge,tvSelectDate,tvEstimateTime;
    private EditText etUserName,etMobile,etLocation,etCity,etState,etPincode,etLandmark,etCountry;
    public Dialog dialog;
    private Button backBtnDialog, backBtnChangeAddress, backBtnAddress,btnSubmit;
    private RecyclerView addressList;
    private ChangeAddressAdapter changeAddressAdapter;
    private CartItemQuantityAdapter cartItemQuantityAdapter;
    private LoadingDialog loadingDialog;
    private RadioGroup radioType;
    SaveEcomAddressRequest saveEcomAddressRequest;
    private String selectedRadioButtonText ,SaveEdit,selectedRadioDeliveryType,selectedRadioButtonClick,selectedRadioButtonDelivery;
    AddressUserResponse addressUserResponse;
    private RecyclerView recylceItemDetail;
    int  total;
    double discper =0,discamt = 0,sum = 0,deliverySum= 0,deliveryTax=0,onlyDiscount,discountSum=0;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    CartsQuantityResponse cartsQuantityResponse;
    public  static OrderSaveRequest orderSaveRequest;
    private ImageView cross;
    private RadioGroup radioGroup,radioTimeDelivery,radioTimeClick;
    private RadioButton rbClick,rbDelivery;
    private LinearLayout liClick,liDelivery,litime2,litime1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_ecom);
        findId();

        getCarts();

        if (isNetworkAvailable()) {
            getUserAddress();
        } else {
            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.cartEcommLayout), "No Internet Connection!", true);
        }

    }



    private void findId() {
        loadingDialog=new LoadingDialog(AddAddressEcomActivity.this);
        tvAddNewAddress = findViewById(R.id.tvaddnew_addaddress);
        tvChangeAddress = findViewById(R.id.tvchange_addaddress);
        tvContinue = findViewById(R.id.tv_continue_addaddress);
        backBtnAddress = findViewById(R.id.back_btn_address);
        tvUsername = findViewById(R.id.tv_username_addddress);
        tvType = findViewById(R.id.tvtype);
        tvAddress = findViewById(R.id.tvAddress_addaddress);
        tvCity = findViewById(R.id.tvcity_addaddress);
        tvPincode = findViewById(R.id.tvpincode_addaddress);
        tvState = findViewById(R.id.tvstate_addaddress);
        tvMobile = findViewById(R.id.tvmobile_addaddress);
        recylceItemDetail = findViewById(R.id.recylceItemDetail);
        tvOrderRs = findViewById(R.id.tv_order_rs);
        tvDeliveryCharge = findViewById(R.id.tv_delivery_charge);
        tvTotalRs = findViewById(R.id.tv_total_rs);
        viewDetail = findViewById(R.id.view_detail);
        price_detail = findViewById(R.id.price_detail);
        tvProductActualprice = findViewById(R.id.tv_product_detail_actualprice);
        tvTaxCharge = findViewById(R.id.tv_tax_charge);


        //set listener
        tvAddNewAddress.setOnClickListener(this);
        tvChangeAddress.setOnClickListener(this);
        tvContinue.setOnClickListener(this);
        backBtnAddress.setOnClickListener(this);
        viewDetail.setOnClickListener(this);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

                showDeliveryTypeDialog(AddAddressEcomActivity.this);

                break;
            case R.id.tv_add_address_addaddress:
                if (validate()) {
                    //************address type*************************
                    int selectedRadioButtonID = radioType.getCheckedRadioButtonId();
                    // If nothing is selected from Radio Group, then it return -1
                    if (selectedRadioButtonID != -1) {
                        RadioButton selectedRadioButton = radioType.findViewById(selectedRadioButtonID);
                         selectedRadioButtonText = selectedRadioButton.getText().toString();
                        Toast.makeText(getApplicationContext(), selectedRadioButtonText, Toast.LENGTH_SHORT).show();

                        //************save location *******************
                        saveNewAddress();

                    } else{
                        Toast.makeText(getApplicationContext(), "Nothing selected from Radio Group.", Toast.LENGTH_SHORT).show();
                    }
                }

            case R.id.back_btn_address:
                overridePendingTransition(R.anim.right_to_left, R.anim.slide_in);
                finish();
                break;


            case  R.id.view_detail:
                price_detail.startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
                break;
        }
    }

    void getData(){
         orderSaveRequest = new OrderSaveRequest();
        List<OrderSaveRequest.ProductsBean> productsBeanList=new ArrayList<>();
        for (int i=0;cartsQuantityResponse.getCarts().size()>i;i++){

            double netAmount = Double.parseDouble(cartsQuantityResponse.getCarts().get(i).getSelling_price())*cartsQuantityResponse.getCarts().get(i).getQuantities();
            String address = tvAddress.getText().toString()+", "+tvCity.getText().toString()+", "+tvPincode.getText().toString()+", "+tvState.getText().toString();
            OrderSaveRequest.ProductsBean productsBean=new OrderSaveRequest.ProductsBean();
            productsBean.setProduct_id(cartsQuantityResponse.getCarts().get(i).getProduct_id());
            productsBean.setProduct_qty(cartsQuantityResponse.getCarts().get(i).getQuantities());
            productsBean.setModifier_id(String.valueOf(cartsQuantityResponse.getCarts().get(i).getModifier_id()));
            productsBean.setDelivery_type(selectedRadioDeliveryType);
            productsBean.setDelivery_address(address);
            productsBean.setBilling_address(address);
            productsBean.setMerchant_id(cartsQuantityResponse.getCarts().get(i).getMerchant_id());
            productsBean.setVenue_id(cartsQuantityResponse.getCarts().get(i).getVenue_id());
            productsBean.setAttributes("");
            productsBean.setCost(Double.parseDouble(cartsQuantityResponse.getCarts().get(i).getSelling_price()));
            productsBean.setDiscount_applied(0);
            productsBean.setOffer_id(cartsQuantityResponse.getCarts().get(i).getOffer_id());
            productsBean.setTax_applied(Double.parseDouble(cartsQuantityResponse.getCarts().get(i).getTax_amount()));
            productsBean.setTax_id(Integer.parseInt(cartsQuantityResponse.getCarts().get(i).getTax_id()));
            productsBean.setNet_amount(String.valueOf(String.format("%.2f",(netAmount))));
            productsBeanList.add(productsBean);

        }

        orderSaveRequest.setProducts(productsBeanList);
    }


    private boolean validate() {

        if (etUserName.getText().toString().trim().length() == 0) {
            etUserName.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etUserName, "Please enter User Name", true);
            return false;
        }
        else  if (etMobile.getText().toString().trim().length() == 0) {
            etMobile.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etMobile, "Please enter Contact number", true);
            return false;
        } else if (etLocation.getText().toString().trim().length() == 0) {
            etLocation.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etCity, "Please enter Location", true);
            return false;
        }
        else if (etCity.getText().toString().trim().length() == 0) {
            etCity.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etCity, "Please enter City", true);
            return false;
        }
        else if (etState.getText().toString().trim().length() == 0) {
            etState.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etState, "Please enter State", true);
            return false;
        }
        else if (etPincode.getText().toString().trim().length() == 0) {
            etPincode.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etPincode, "Please enter Pincode", true);
            return false;
        }
        else if (etLandmark.getText().toString().trim().length() == 0) {
            etLandmark.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etLandmark, "Please enter Landmark", true);
            return false;
        }
        else if (etCountry.getText().toString().trim().length() == 0) {
            etCountry.requestFocus();
            BaseApp.getInstance().toastHelper().showSnackBar(etCountry, "Please enter Country", true);
            return false;
        }
        return true;
    }

    private void getCarts() {

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiServiceEcom apiService = ApiClientEcom.getClient(AddAddressEcomActivity.this).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiService.getCartsQuantityItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CartsQuantityResponse>() {
                    @Override
                    public void onSuccess(CartsQuantityResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                            cartsQuantityResponse = response;
                            recylceItemDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            cartItemQuantityAdapter = new CartItemQuantityAdapter(getApplicationContext(),response.getCarts());
                            recylceItemDetail.setAdapter(cartItemQuantityAdapter);

                            for (total = 0 ; total<response.getCarts().size();total++){

                                if (response.getCarts().get(total).getOffer_id()!=0)
                                {
                                    if (response.getCarts().get(total).getOffer_type().equals("discper")){


                                        double b = ((Double.parseDouble(response.getCarts().get(total).getSelling_price())-((Double.parseDouble(response.getCarts().get(total).getSelling_price()))*(Double.parseDouble(response.getCarts().get(total).getDisc_per()))/100)));
                                        double discperTotal= b *Double.parseDouble(""+response.getCarts().get(total).getQuantities());
                                        sum=sum+ discperTotal;
                                        tvOrderRs.setText("₹ " +String.format("%.3f", sum));

                                        onlyDiscount = ((Double.parseDouble(response.getCarts().get(total).getSelling_price()))*(Double.parseDouble(response.getCarts().get(total).getDisc_per()))/100);
                                        double discoutperTotal= onlyDiscount *Double.parseDouble(""+response.getCarts().get(total).getQuantities());
                                        discountSum = discountSum + discoutperTotal;


                                    } else if (response.getCarts().get(total).getOffer_type().equals("discamt")){
                                        double c = (Double.parseDouble(response.getCarts().get(total).getSelling_price())- Double.parseDouble(response.getCarts().get(total).getDisc_amt()))*Double.parseDouble(""+response.getCarts().get(total).getQuantities());
                                        sum=sum+ c;
                                        tvOrderRs.setText("₹ "+String.format("%.2f",(sum)));

                                      double  onlyDiscount = (Double.parseDouble(response.getCarts().get(total).getDisc_amt()))*Double.parseDouble(""+response.getCarts().get(total).getQuantities());
                                        discountSum = discountSum + onlyDiscount;
                                    }
                                } else {
                                    double totalAmount = Double.parseDouble(response.getCarts().get(total).getSelling_price())* Double.parseDouble(""+response.getCarts().get(total).getQuantities());
                                    sum=sum+ totalAmount;
                                    tvOrderRs.setText("₹ "+String.format("%.2f",(sum)));
                                    discountSum = 0;

                                }

                                double deliveryTotal = Double.parseDouble(response.getCarts().get(total).getDelivery_charge());
                                deliverySum = deliverySum + deliveryTotal;
                                tvDeliveryCharge.setText("₹ "+String.format("%.2f",(deliverySum)));

                                double taxTotal =Double.parseDouble(response.getCarts().get(total).getTax_amount())* Double.parseDouble(""+response.getCarts().get(total).getQuantities());
                                deliveryTax = deliveryTax + taxTotal;
                                tvTaxCharge.setText("₹ "+String.format("%.2f",(deliveryTax)));

                                tvTotalRs.setText("₹ "+String.format("%.2f",(deliveryTax+deliverySum+sum)));
                                tvProductActualprice.setText("₹ "+String.format("%.2f",(deliveryTax+deliverySum+sum)));

                            }

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_address), response.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.relative_address), true, e);
                    }
                }));
    }

    private void saveNewAddress() {
        saveEcomAddressRequest=new SaveEcomAddressRequest();
        saveEcomAddressRequest.setName(etUserName.getText().toString());
        saveEcomAddressRequest.setMobile(etMobile.getText().toString());
        saveEcomAddressRequest.setArea(etLocation.getText().toString());
        saveEcomAddressRequest.setCity(etCity.getText().toString());
        saveEcomAddressRequest.setState(etState.getText().toString());
        saveEcomAddressRequest.setPost_code(etPincode.getText().toString());
        saveEcomAddressRequest.setLandmark(etLandmark.getText().toString());
        saveEcomAddressRequest.setCountry(etCountry.getText().toString());
        saveEcomAddressRequest.setType(selectedRadioButtonText);

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiServiceEcom apiServiceEcom = ApiClientEcom.getClient(AddAddressEcomActivity.this).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiServiceEcom.saveuserLocaiton(saveEcomAddressRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UpdateEcomAddressResponse>(){
                    @Override
                    public void onSuccess(UpdateEcomAddressResponse response) {
                        loadingDialog.hideDialog();
                        if (response.isStatus()) {
                             dialog.dismiss();
                             Toast.makeText(getApplicationContext(),response.getMessage(),Toast.LENGTH_LONG).show();
                            tvUsername.setText(response.getAddress().getName());
                            tvType.setText(response.getAddress().getType());
                            tvAddress.setText(response.getAddress().getArea());
                            tvCity.setText(response.getAddress().getCity());
                            tvPincode.setText(response.getAddress().getPincode());
                            tvState.setText(response.getAddress().getState());
                            tvMobile.setText(response.getAddress().getMobile());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.relative_address), false, e.getCause());
                    }
                }));
    }

    private void updateAddress(final int position) {
        saveEcomAddressRequest=new SaveEcomAddressRequest();
        saveEcomAddressRequest.setAddress_id(String.valueOf(addressUserResponse.getAddressess().get(position).getId()));
        saveEcomAddressRequest.setName(etUserName.getText().toString());
        saveEcomAddressRequest.setMobile(etMobile.getText().toString());
        saveEcomAddressRequest.setArea(etLocation.getText().toString());
        saveEcomAddressRequest.setCity(etCity.getText().toString());
        saveEcomAddressRequest.setState(etState.getText().toString());
        saveEcomAddressRequest.setPost_code(etPincode.getText().toString());
        saveEcomAddressRequest.setLandmark(etLandmark.getText().toString());
        saveEcomAddressRequest.setCountry(etCountry.getText().toString());
        saveEcomAddressRequest.setType(selectedRadioButtonText);

        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiServiceEcom apiServiceEcom = ApiClientEcom.getClient(AddAddressEcomActivity.this).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiServiceEcom.updateUserLocaiton(saveEcomAddressRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UpdateEcomAddressResponse>(){
                    @Override
                    public void onSuccess(UpdateEcomAddressResponse response) {
                        loadingDialog.hideDialog();

                        if (response.isStatus()) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),response.getMessage(),Toast.LENGTH_LONG).show();

                            addressUserResponse.getAddressess().get(position).setName(response.getAddress().getName());
                            addressUserResponse.getAddressess().get(position).setMobile(response.getAddress().getMobile());
                            addressUserResponse.getAddressess().get(position).setArea(response.getAddress().getArea());
                            addressUserResponse.getAddressess().get(position).setCity(response.getAddress().getCity());
                            addressUserResponse.getAddressess().get(position).setState(response.getAddress().getState());
                            addressUserResponse.getAddressess().get(position).setPincode(response.getAddress().getPincode());
                            addressUserResponse.getAddressess().get(position).setLandmark(response.getAddress().getLandmark());
                            addressUserResponse.getAddressess().get(position).setCountry(response.getAddress().getCountry());
                            addressUserResponse.getAddressess().get(position).setType(response.getAddress().getType());

                            changeAddressAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.relative_address), false, e.getCause());
                    }
                }));
    }

    private void showDialogAddress(AddAddressEcomActivity addAddressEcomActivity) {
        dialog = new Dialog(addAddressEcomActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.address_ecom_dialog);

        backBtnDialog = dialog.findViewById(R.id.back_btn_address_dialog);
        tvSaveAddress = dialog.findViewById(R.id.tv_add_address_addaddress);
        etUserName = dialog.findViewById(R.id.et_username_dialog);
        etMobile = dialog.findViewById(R.id.mobilenumber);
        etLocation = dialog.findViewById(R.id.update_location);
        etCity = dialog.findViewById(R.id.update_city);
        etState = dialog.findViewById(R.id.update_state);
        etPincode = dialog.findViewById(R.id.update_pin);
        etLandmark = dialog.findViewById(R.id.update_landmark);
        etCountry = dialog.findViewById(R.id.dialog_country);
        radioType = dialog.findViewById(R.id.radio_type);
        saveBtnAddress = dialog.findViewById(R.id.tv_save_address_addaddress);

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

        addressList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        changeAddressAdapter = new ChangeAddressAdapter(getApplicationContext(),addressUserResponse.getAddressess(),AddAddressEcomActivity.this);
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

    private void getUserAddress() {
        ApiServiceEcom apiService = ApiClientEcom.getClient(getApplicationContext()).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiService.getUserAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AddressUserResponse>() {
                    @Override
                    public void onSuccess(AddressUserResponse response) {
                        if (response.isStatus()) {
                            addressUserResponse = response;
                            int size = response.getAddressess().size();
                            tvUsername.setText(response.getAddressess().get(size-1).getName());
                            tvAddress.setText(response.getAddressess().get(size-1).getArea());
                            tvCity.setText(response.getAddressess().get(size-1).getCity());
                            tvPincode.setText("-"+response.getAddressess().get(size-1).getPincode());
                            tvState.setText(response.getAddressess().get(size-1).getState());
                            tvMobile.setText(response.getAddressess().get(size-1).getMobile());
                            tvType.setText(response.getAddressess().get(size-1).getType());
                            BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ADDRESS_POS,String.valueOf(size-1));

                        } else {
                            BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.rl_address), response.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.rl_address), true, e);
                    }
                }));
    }


    @Override
    public void onEditAddressItem(final int position, AddressUserResponse.AddressessBean addressessBean) {
        showDialogAddress(AddAddressEcomActivity.this);
        etUserName.setText(addressessBean.getName());
        etMobile.setText(addressessBean.getMobile());
        etLocation.setText(addressessBean.getArea());
        etCity.setText(addressessBean.getCity());
        etState.setText(addressessBean.getState());
        etPincode.setText(addressessBean.getPincode());
        etLandmark.setText(addressessBean.getLandmark());
        etCountry.setText(addressessBean.getCountry());

        saveBtnAddress.setVisibility(View.VISIBLE);
        tvSaveAddress.setVisibility(View.GONE);

        saveBtnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validate()) {
                    //************address type*************************
                    int selectedRadioButtonID = radioType.getCheckedRadioButtonId();
                    // If nothing is selected from Radio Group, then it return -1
                    if (selectedRadioButtonID != -1) {
                        RadioButton selectedRadioButton = radioType.findViewById(selectedRadioButtonID);
                        selectedRadioButtonText = selectedRadioButton.getText().toString();
                        Toast.makeText(getApplicationContext(), selectedRadioButtonText, Toast.LENGTH_SHORT).show();

                        //************update location *******************
                        updateAddress(position);

                    } else{
                        Toast.makeText(getApplicationContext(), "Nothing selected from Radio Group.", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });



    }



    @Override
    public void onRemoveAddressItem(int position, AddressUserResponse.AddressessBean addressessBean) {

        getRemoveAddress(position,addressessBean);
    }

    @Override
    public void onCheckedAddressItem(final int position, final AddressUserResponse.AddressessBean addressessBean) {
       // Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();

        tvConfirmAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"SELECTCONFIRM",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                tvUsername.setText(addressessBean.getName());
                tvType.setText(addressessBean.getType());
                tvAddress.setText(addressessBean.getArea());
                tvCity.setText(addressessBean.getCity());
                tvPincode.setText(addressessBean.getPincode());
                tvState.setText(addressessBean.getState());
                tvMobile.setText(addressessBean.getMobile());
                BaseApp.getInstance().sharedPref().setString(BaseApp.getInstance().sharedPref().ADDRESS_POS,String.valueOf(position));
            }
        });
    }

    private void getRemoveAddress(final int position, final AddressUserResponse.AddressessBean addressessBean) {
        loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);
        ApiServiceEcom apiServiceEcom = ApiClientEcom.getClient(AddAddressEcomActivity.this).create(ApiServiceEcom.class);
        BaseApp.getInstance().getDisposable().add(apiServiceEcom.deleteUserAddress(String.valueOf(addressessBean.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RemoveEcomAddressResponse>(){
                    @Override
                    public void onSuccess(RemoveEcomAddressResponse response) {
                        loadingDialog.hideDialog();

                        if (response.isStatus()) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),response.getMessage(),Toast.LENGTH_LONG).show();

                            addressUserResponse.getAddressess().remove(position);
                            changeAddressAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showApiExpectation(findViewById(R.id.lichangeAddress), false, e.getCause());
                    }
                }));
    }


    private void showDeliveryTypeDialog(AddAddressEcomActivity addAddressEcomActivity) {
        dialog = new Dialog(addAddressEcomActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delivery_type_dialog);


        cross = dialog.findViewById(R.id.cross);
        radioGroup = dialog.findViewById(R.id.rg_delivery);
        radioTimeDelivery= dialog.findViewById(R.id.rg_time_schedule);
        radioTimeClick= dialog.findViewById(R.id.rg_timeSchedule);

        rbDelivery = dialog.findViewById(R.id.radio_delivery);
        rbClick = dialog.findViewById(R.id.radio_click);
        btnSubmit = dialog.findViewById(R.id.btn_submit);
        liClick = dialog.findViewById(R.id.liclick);
        liDelivery = dialog.findViewById(R.id.lidelivery);
        tvSelectDate = dialog.findViewById(R.id.tv_selectDate);
        tvEstimateTime = dialog.findViewById(R.id.tv_estimateTime);
        litime2 = dialog.findViewById(R.id.litime2);
        litime1 = dialog.findViewById(R.id.litime1);

        rbDelivery.setOnCheckedChangeListener(this);
        rbClick.setOnCheckedChangeListener(this);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, +10);
        Date newDate = calendar.getTime();
        String Time[] = String.valueOf(newDate).split(" ");
        String t1 = Time[0];
        String t2 = Time[1];
        String t3 = Time[2];
        String t4 = Time[3];
        String t5 = Time[4];
        String t6 = Time[5];

        tvEstimateTime.setText(t1+", "+ t2+" " +t3+" "+t6);


        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedRadioDelivertTime = radioTimeClick.getCheckedRadioButtonId();

                if (selectedRadioDelivertTime != -1) {
                    RadioButton selectedRadioButton = radioTimeClick.findViewById(selectedRadioDelivertTime);
                     selectedRadioButtonClick = selectedRadioButton.getText().toString();
                    Toast.makeText(getApplicationContext(), selectedRadioButtonClick, Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Nothing selected from Radio Group.", Toast.LENGTH_SHORT).show();

                }
                int selectedRadioClickTime = radioTimeDelivery.getCheckedRadioButtonId();
                if (selectedRadioClickTime != -1) {
                    RadioButton selectedRadioButton = radioTimeDelivery.findViewById(selectedRadioClickTime);
                     selectedRadioButtonDelivery = selectedRadioButton.getText().toString();
                    Toast.makeText(getApplicationContext(), selectedRadioButtonDelivery, Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Nothing selected from Radio Group.", Toast.LENGTH_SHORT).show();

                }
                int selectedRadioButtonID = radioGroup.getCheckedRadioButtonId();
                // If nothing is selected from Radio Group, then it return -1
                if (selectedRadioButtonID != -1) {
                    RadioButton selectedRadioButton = radioGroup.findViewById(selectedRadioButtonID);
                     selectedRadioDeliveryType = selectedRadioButton.getText().toString();

                    getData();
                    Intent intent = new Intent(AddAddressEcomActivity.this, EcomPaymentActivity.class);

                        if (selectedRadioDeliveryType.equals("Click & Collect")) {
                            if (tvSelectDate.getText().toString().trim().length() == 0) {
                                tvSelectDate.requestFocus();
                                BaseApp.getInstance().toastHelper().showSnackBar(tvSelectDate, "Please Enter Date", true);
                            } else {
                                intent.putExtra("paid_amount", tvProductActualprice.getText().toString().trim().substring(1));
                                intent.putExtra("total_items",String.valueOf(cartsQuantityResponse.getCarts().size()));
                                intent.putExtra("total_tax",tvTaxCharge.getText().toString().trim().substring(1));
                                intent.putExtra("total_deliveryCharge",tvDeliveryCharge.getText().toString().trim().substring(1));
                                intent.putExtra("total_discount",String.valueOf(discountSum));
                                intent.putExtra("delivery_type",selectedRadioDeliveryType);
                                intent.putExtra("delivery_time",tvSelectDate.getText().toString()+" "+selectedRadioButtonClick);
                                startActivity(intent);
                                finish();
                            }
                        } else if (selectedRadioDeliveryType.equals("Delivery")){

                                intent.putExtra("paid_amount", tvProductActualprice.getText().toString().trim().substring(1));
                                intent.putExtra("total_items",String.valueOf(cartsQuantityResponse.getCarts().size()));
                                intent.putExtra("total_tax",tvTaxCharge.getText().toString().trim().substring(1));
                                intent.putExtra("total_deliveryCharge",tvDeliveryCharge.getText().toString().trim().substring(1));
                                intent.putExtra("total_discount",String.valueOf(discountSum));
                                intent.putExtra("delivery_type",selectedRadioDeliveryType);
                                intent.putExtra("delivery_time",tvEstimateTime.getText().toString()+" "+selectedRadioButtonDelivery);
                                startActivity(intent);
                                finish();
                        }
                } else{
                    Toast.makeText(getApplicationContext(), "Nothing selected from Radio Group.", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();
            }
        });

        liClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerEcom datePicker = DatePickerEcom.newInstance(null, tvSelectDate);
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.show();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // The absolute width of the available display size in pixels.
        int displayWidth = displayMetrics.widthPixels;
        // The absolute height of the available display size in pixels.
        int displayHeight = displayMetrics.heightPixels;

        // Initialize a new window manager layout parameters
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        // Copy the alert dialog window attributes to new layout parameter instance
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        // Set the alert dialog window width and height
        // Set alert dialog width equal to screen width 90%
        // int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 90%
        // int dialogWindowHeight = (int) (displayHeight * 0.9f);

        // Set alert dialog width equal to screen width 70%
        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        // Set alert dialog height equal to screen height 70%
        int dialogWindowHeight = (int) (displayHeight * 0.9f);

        // Set the width and height for the layout parameters
        // This will bet the width and height of alert dialog
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        // Apply the newly created layout parameters to the alert dialog window
        dialog.getWindow().setAttributes(layoutParams);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            if (buttonView.getId() == R.id.radio_delivery) {
                rbDelivery.setChecked(true);
                rbClick.setChecked(false);
                liDelivery.setVisibility(View.VISIBLE);
                liClick.setVisibility(View.GONE);
                litime2.setVisibility(View.GONE);
                litime1.setVisibility(View.VISIBLE);
            }
            if (buttonView.getId() == R.id.radio_click) {
                rbClick.setChecked(true);
                rbDelivery.setChecked(false);
                liDelivery.setVisibility(View.GONE);
                liClick.setVisibility(View.VISIBLE);
                litime2.setVisibility(View.VISIBLE);
                litime1.setVisibility(View.GONE);
            }
        }
    }
}
