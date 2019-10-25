package com.safepayu.wallet.activity.booking;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;



public class FlightsActivity extends AppCompatActivity implements View.OnClickListener {
     private TextView tvOneWay, tvTwoWay;
     public static TextView tvFlightTraveller;
    private ImageView imageOneWay, imageTwoWay;
    private Button searchFlightBtn,backBtn;
    private LinearLayout liayoutClassAdult;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);

        findId();
    }

    private void findId() {
        backBtn = findViewById(R.id.recharge_back_btn);
        searchFlightBtn = findViewById( R.id.search_flight_btn);
        tvOneWay = findViewById(R.id.tv_oneway);
        tvTwoWay = findViewById(R.id.tv_twoway);
        tvFlightTraveller = findViewById(R.id.tv_flight_traveller);
        imageOneWay = findViewById(R.id.image_one_way);
        imageTwoWay = findViewById(R.id.image_two_way);
        liayoutClassAdult = findViewById(R.id.layout_class_traveller_tab);

        //set listener
        tvOneWay.setOnClickListener(this);
        tvTwoWay.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        searchFlightBtn.setOnClickListener(this);
        liayoutClassAdult.setOnClickListener(this);

        tvOneWay.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_rounded));
        tvOneWay.setTextColor(getResources().getColor(R.color.white));
        imageOneWay.setVisibility(View.VISIBLE);




        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("adult", "No name defined");//"No name defined" is the default value.
      //  int idName = prefs.getInt("idName", 0); //0 is the default value.

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case
                R.id.tv_oneway:
                tvOneWay.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_rounded));
                tvOneWay.setTextColor(getResources().getColor(R.color.white));
                tvTwoWay.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded));
                tvTwoWay.setTextColor(getResources().getColor(R.color.darker_gray));
                imageOneWay.setVisibility(View.VISIBLE);
                imageTwoWay.setVisibility(View.GONE);
                break;
            case  R.id.tv_twoway:
                tvTwoWay.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_rounded));
                tvTwoWay.setTextColor(getResources().getColor(R.color.white));
                tvOneWay.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded));
                tvOneWay.setTextColor(getResources().getColor(R.color.darker_gray));
                imageOneWay.setVisibility(View.GONE);
                imageTwoWay.setVisibility(View.VISIBLE);
                break;
            case R.id.recharge_back_btn:
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                finish();
                break;

            case R. id.search_flight_btn:

                CheckValidate();
                break;
            case R.id.layout_class_traveller_tab:

            BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            break;
        }
    }

    private void CheckValidate() {
        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_flight),"Coming Soon",false);

    }



    //****************bottom sheet dialoug fragment**********************


    public static class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

        private ImageView imageAdultsMinus,imageAdultsPlus,imageAdultsMinusgray,imageChildrenMinus,imageChildrenMinusgray,imageChildrenPlus,imageInfantMinus,imageInfantMinusgray
                ,imageInfantPlus,imageAdultsPlusgray,imageChildrenPlusgray,imageInfantPlusgray;
        private TextView tvAdultsCount,tvChildrenCount,tvInfantCount,tv_done;
        int minteger = 1;
        int mintegerChild = 1;
        int mintegerInfant = 1;
        int mtotalTravellers;

        public BottomSheetFragment() {
        }

        @Override
        public void onCreate( Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view =  inflater.inflate(R.layout.flight_bottom_popup, container, false);
            setStyle(STYLE_NORMAL, R.style. AppBottomSheetDialogTheme);

            findId(view);
            return view;
        }

        private void findId(View view) {

            tvAdultsCount = view.findViewById(R.id.tvAdultsCount);
            tvChildrenCount = view.findViewById(R.id.tvChildrenCount);
            tvInfantCount = view.findViewById(R.id.tvInfantCount);
            tv_done = view.findViewById(R.id.tv_done);
            imageAdultsMinus = view.findViewById(R.id.imageAdultsMinus);
            imageAdultsMinusgray = view.findViewById(R.id.imageAdultsMinusgray);
            imageAdultsPlus = view.findViewById(R.id.imageAdultsPlus);
            imageChildrenMinus = view.findViewById(R.id.imageChildrenMinus);
            imageChildrenMinusgray = view.findViewById(R.id.imageChildrenMinusgray);
            imageChildrenPlus = view.findViewById(R.id.imageChildrenPlus);
            imageInfantMinus = view.findViewById(R.id.imageInfantMinus);
            imageInfantMinusgray = view.findViewById(R.id.imageInfantMinusgray);
            imageInfantPlus = view.findViewById(R.id.imageInfantPlus);
            imageAdultsPlusgray = view.findViewById(R.id.imageAdultsPlusgray);
            imageChildrenPlusgray = view.findViewById(R.id.imageChildrenPlusgray);
            imageInfantPlusgray = view.findViewById(R.id.imageInfantPlusgray);


            imageAdultsMinus.setOnClickListener(this);
            imageAdultsPlus.setOnClickListener(this);
            imageChildrenMinus.setOnClickListener(this);
            imageChildrenPlus.setOnClickListener(this);
            imageInfantPlus.setOnClickListener(this);
            imageInfantMinus.setOnClickListener(this);
            tv_done.setOnClickListener(this);

            tvInfantCount.setText("" +mintegerInfant);
            tvChildrenCount.setText("" + mintegerChild);



        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.imageAdultsMinus:
                    if (minteger==1)
                    {
                        tvAdultsCount.setText("" + 0);
                        imageAdultsMinusgray.setVisibility(View.VISIBLE);
                        imageAdultsMinus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        minteger = minteger - 1;
                        display(minteger);
                        if(minteger==1){
                            imageAdultsMinusgray.setVisibility(View.VISIBLE);
                            imageAdultsMinus.setVisibility(View.INVISIBLE); }
                        else {
                            imageAdultsMinusgray.setVisibility(View.INVISIBLE);
                            imageAdultsMinus.setVisibility(View.VISIBLE);
                        }
                    }
                    break;

                case R.id.imageAdultsPlus:
                    minteger = minteger + 1;
                    display(minteger);
                    tvAdultsCount.setText("" + minteger);
                    imageAdultsMinusgray.setVisibility(View.INVISIBLE);
                    imageAdultsMinus.setVisibility(View.VISIBLE);
                    break;

                case R.id.imageChildrenMinus:
                    if (mintegerChild==0)
                    {
                        tvChildrenCount.setText("" + 0);
                        imageChildrenMinusgray.setVisibility(View.VISIBLE);
                        imageChildrenMinus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        mintegerChild = mintegerChild - 1;
                        displayChildren(mintegerChild);


                        if(mintegerChild==0){
                            imageChildrenMinusgray.setVisibility(View.VISIBLE);
                            imageChildrenMinus.setVisibility(View.INVISIBLE); }
                        else {
                            imageChildrenMinusgray.setVisibility(View.INVISIBLE);
                            imageChildrenMinus.setVisibility(View.VISIBLE);
                        }
                    }
                    break;

                case R.id.imageChildrenPlus:
                    mintegerChild = mintegerChild + 1;
                    displayChildren(mintegerChild);
                    tvChildrenCount.setText("" + mintegerChild);
                    imageChildrenMinusgray.setVisibility(View.INVISIBLE);
                    imageChildrenMinus.setVisibility(View.VISIBLE);
                    break;
                case R.id.imageInfantMinus:
                    if (mintegerInfant==0)
                    {
                        tvInfantCount.setText("" + 0);
                        imageInfantMinusgray.setVisibility(View.VISIBLE);
                        imageInfantMinus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        mintegerInfant = mintegerInfant - 1;
                        displayInfant(mintegerInfant);
                        if(mintegerInfant==0){
                            imageInfantMinusgray.setVisibility(View.VISIBLE);
                            imageInfantMinus.setVisibility(View.INVISIBLE); }
                        else {
                            imageInfantMinusgray.setVisibility(View.INVISIBLE);
                            imageInfantMinus.setVisibility(View.VISIBLE);
                        }
                    }
                    break;

                case R.id.imageInfantPlus:
                    mintegerInfant = mintegerInfant + 1;
                    displayInfant(mintegerInfant);
                    tvInfantCount.setText("" + mintegerInfant);
                    imageInfantMinusgray.setVisibility(View.INVISIBLE);
                    imageInfantMinus.setVisibility(View.VISIBLE);
                    break;

                case R.id.tv_done:

                    if (tvAdultsCount.getText().toString().equals("1"))
                    tvFlightTraveller.setText(tvAdultsCount.getText().toString()+" "+"Adult");
                    else {
                        tvFlightTraveller.setText(tvAdultsCount.getText().toString()+" "+"Adults");
                    }
                    dismiss();
                    break;
            }
        }




        private void display(int intAdult) {

            mtotalTravellers = minteger+mintegerChild+mintegerInfant;
            if (mtotalTravellers>8)
            {
                Toast.makeText(getActivity(),"Travellers can not more than 9 ",Toast.LENGTH_LONG).show();
                imageInfantPlus.setVisibility(View.INVISIBLE);
                imageChildrenPlus.setVisibility(View.INVISIBLE);
                imageAdultsPlus.setVisibility(View.INVISIBLE);
                imageAdultsPlusgray.setVisibility(View.VISIBLE);
                imageChildrenPlusgray.setVisibility(View.VISIBLE);
                imageInfantPlusgray.setVisibility(View.VISIBLE);
            }
            else if (mtotalTravellers<=9){
                tvAdultsCount.setText("" + intAdult);
                imageInfantPlus.setVisibility(View.VISIBLE);
                imageChildrenPlus.setVisibility(View.VISIBLE);
                imageAdultsPlus.setVisibility(View.VISIBLE);
                imageAdultsPlusgray.setVisibility(View.INVISIBLE);
                imageChildrenPlusgray.setVisibility(View.INVISIBLE);
                imageInfantPlusgray.setVisibility(View.INVISIBLE);


                if (intAdult==1)
                {
                    imageAdultsMinusgray.setVisibility(View.VISIBLE);
                    imageAdultsMinus.setVisibility(View.INVISIBLE);

                }
            }

        }

        private void displayChildren(int intChild) {

            mtotalTravellers = minteger+mintegerChild+mintegerInfant;

            if (mtotalTravellers>8)
            {
                Toast.makeText(getActivity(),"Travellers can not more than 9 ",Toast.LENGTH_SHORT).show();
                imageInfantPlus.setVisibility(View.INVISIBLE);
                imageChildrenPlus.setVisibility(View.INVISIBLE);
                imageAdultsPlus.setVisibility(View.INVISIBLE);
                imageAdultsPlusgray.setVisibility(View.VISIBLE);
                imageChildrenPlusgray.setVisibility(View.VISIBLE);
                imageInfantPlusgray.setVisibility(View.VISIBLE);
            }
            else if (mtotalTravellers<=9){
                tvChildrenCount.setText("" + intChild);
                imageInfantPlus.setVisibility(View.VISIBLE);
                imageChildrenPlus.setVisibility(View.VISIBLE);
                imageAdultsPlus.setVisibility(View.VISIBLE);
                imageAdultsPlusgray.setVisibility(View.INVISIBLE);
                imageChildrenPlusgray.setVisibility(View.INVISIBLE);
                imageInfantPlusgray.setVisibility(View.INVISIBLE);
                if (intChild == 0) {
                    imageChildrenMinusgray.setVisibility(View.VISIBLE);
                    imageChildrenMinus.setVisibility(View.INVISIBLE); }
            }
        }

        private void displayInfant(int intInfant) {

            mtotalTravellers = minteger+mintegerChild+mintegerInfant;
            if (mtotalTravellers>8)
            {
                Toast.makeText(getActivity(),"Travellers can not more than 9 ",Toast.LENGTH_SHORT).show();
                imageInfantPlus.setVisibility(View.INVISIBLE);
                imageChildrenPlus.setVisibility(View.INVISIBLE);
                imageAdultsPlus.setVisibility(View.INVISIBLE);
                imageAdultsPlusgray.setVisibility(View.VISIBLE);
                imageChildrenPlusgray.setVisibility(View.VISIBLE);
                imageInfantPlusgray.setVisibility(View.VISIBLE);
            }
            else if (mtotalTravellers<=9){
                tvInfantCount.setText("" + intInfant);
                imageInfantPlus.setVisibility(View.VISIBLE);
                imageChildrenPlus.setVisibility(View.VISIBLE);
                imageAdultsPlus.setVisibility(View.VISIBLE);
                imageAdultsPlusgray.setVisibility(View.INVISIBLE);
                imageChildrenPlusgray.setVisibility(View.INVISIBLE);
                imageInfantPlusgray.setVisibility(View.INVISIBLE);


                if (intInfant == 0) {
                    imageInfantMinusgray.setVisibility(View.VISIBLE);
                    imageInfantMinus.setVisibility(View.INVISIBLE);

                }
            }
        }

    }

}
