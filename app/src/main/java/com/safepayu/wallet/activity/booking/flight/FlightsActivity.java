package com.safepayu.wallet.activity.booking.flight;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClientBooking;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.models.response.booking.FlightSourceResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class FlightsActivity extends AppCompatActivity implements View.OnClickListener {
     private TextView tvOneWay, tvTwoWay,SourceDescTV,DestinationDescTV;
     public static TextView tvFlightTraveller;
    private ImageView imageOneWay, imageTwoWay;
    private Button searchFlightBtn,backBtn;
    private LinearLayout liayoutClassAdult;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private LoadingDialog loadingDialog;
    private ArrayList<String> AirportCodeList,CityList,AirportDescList;
    AutoCompleteTextView SourceACTV,DestinationACTV;
    private CheckBox checkboxFlight;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);

        loadingDialog = new LoadingDialog(this);

        findId();

        SourceACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String city=SourceACTV.getText().toString();
                int index=CityList.indexOf(city);
                SourceACTV.setText(city+" -("+AirportCodeList.get(index)+")");
                SourceACTV.setSelection(SourceACTV.getText().toString().length());
                SourceDescTV.setText(AirportDescList.get(index));
            }
        });

        DestinationACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String city=DestinationACTV.getText().toString();
                int index=CityList.indexOf(city);
                DestinationACTV.setText(city+" -("+AirportCodeList.get(index)+")");
                DestinationACTV.setSelection(DestinationACTV.getText().toString().length());
                DestinationDescTV.setText(AirportDescList.get(index));

            }
        });
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
        SourceACTV= findViewById(R.id.sourceFlight);
        DestinationACTV= findViewById(R.id.destinationFlight);
        SourceDescTV= findViewById(R.id.sourceFlightDesc);
        DestinationDescTV= findViewById(R.id.destinationFlightDesc);
        checkboxFlight= findViewById(R.id.checkbox_flight);

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

        AirportCodeList=new ArrayList<>();
        CityList=new ArrayList<>();
        AirportDescList=new ArrayList<>();

        AirportCodeList.clear();
        CityList.clear();
        AirportDescList.clear();

        //getFlightSources("1");
        new GetSoureces().execute();

        checkboxFlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            /*  Intent intent = new Intent(FlightsActivity.this,FlightListActivity.class);
              intent.putExtra("oneway","1");
              startActivity(intent);*/
            }
        });
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

                Intent intent = new Intent(FlightsActivity.this,FlightListActivity.class);
                startActivity(intent);
             //   CheckValidate();
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


    private void getFlightSources(String flightType) {
          loadingDialog.showDialog(getResources().getString(R.string.loading_message), false);

        ApiService apiService = ApiClientBooking.getClient(this).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.getFlightSources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FlightSourceResponse>() {
                    @Override
                    public void onSuccess(FlightSourceResponse response) {
                          loadingDialog.hideDialog();


//                          if (response.getBankDetails().size()>0){
//                              for (int i=0;i<response.getBankDetails().size();i++){
//                                  AirportCodeList.add(response.getBankDetails().get(i).getAirportCode());
//                                  CityList.add(response.getBankDetails().get(i).getCity());
//                                  AirportDescList.add(response.getBankDetails().get(i).getAirportDesc());
//                              }
//                              ArrayAdapter<String> adapter = new ArrayAdapter<String>(FlightsActivity.this, android.R.layout.simple_dropdown_item_1line, CityList);
//                              SourceACTV.setAdapter(adapter);
//                              SourceACTV.setThreshold(3);
//                          }else {
//                              BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_flight),"No Data Found",true);
//                          }

                        AirportCodeList.add(response.getAirportCode());
                        CityList.add(response.getCity());
                        AirportDescList.add(response.getAirportDesc());
                    }


                    @Override
                    public void onError(Throwable e) {
                          loadingDialog.hideDialog();
                        BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_flight),e.getMessage(),true);
                    }
                }));
    }

    public class GetSoureces extends AsyncTask<Object, Void, Object> {
        public ProgressDialog dialog;
        int status_code = 0;
        private String response = "", actualString = "";
        private  String ConsumerKey="CF1AA97C156A138796A39CA8E7CFDEB3D17C8D60";
        private String ConsumerSecret="7FAEC265BC38BA83F21D23C88A1C416F2820D0B1";

        @Override
        protected Object doInBackground(Object... arg0) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                String URL = "http://webapi.i2space.co.in/Flights/Airports?flightType=1";
                //HttpPost httpost = new HttpPost(URL);
                HttpGet httpGet = new HttpGet(URL);

                httpGet.addHeader("ConsumerKey",ConsumerKey);
                httpGet.addHeader("ConsumerSecret",ConsumerSecret);

                HttpParams httpParameters = new BasicHttpParams();
                int timeoutConnection = 3000;
                HttpConnectionParams.setConnectionTimeout(httpParameters,
                        timeoutConnection);
                int timeoutSocket = 3000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
                HttpContext localContext = new BasicHttpContext();
                HttpResponse httpresponse = httpClient.execute(httpGet, localContext);
                status_code = httpresponse.getStatusLine().getStatusCode();
                HttpEntity entity = httpresponse.getEntity();
                InputStream stream = entity.getContent();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(
                        stream));

                String s = "";

                while ((s = buffer.readLine()) != null) {
                    response += s;
                }


                actualString = response;

                if (response.startsWith("null")) {
                    actualString = response;
                }


            } catch (Exception ex) {
                String msg = ex.getMessage();
            }
            return actualString;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(FlightsActivity.this, "Getting Sources...",
                    "Please wait...", true);
        }

        @Override
        protected void onPostExecute(Object result) {

            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            dialog.dismiss();
            if (status_code == 200) {
                try {
                    jsonArray = new JSONArray(response);
                    Log.v("response",response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (jsonArray.length()>0){
                              for (int i=0;i<jsonArray.length();i++){
                                  try {
                                      jsonObject=jsonArray.getJSONObject(i);
                                  } catch (JSONException e) {
                                      e.printStackTrace();
                                  }
                                  try {
                                      AirportCodeList.add(jsonObject.getString("AirportCode"));
                                      CityList.add(jsonObject.getString("City"));
                                      AirportDescList.add(jsonObject.getString("AirportDesc"));
                                  } catch (JSONException e) {
                                      e.printStackTrace();
                                  }

                              }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(FlightsActivity.this, android.R.layout.simple_dropdown_item_1line, CityList);
                    SourceACTV.setAdapter(adapter);
                    SourceACTV.setThreshold(3);
                    DestinationACTV.setAdapter(adapter);
                    DestinationACTV.setThreshold(3);

                }else {
                    BaseApp.getInstance().toastHelper().showSnackBar(findViewById(R.id.relative_flight),"No Data Found",true);
                }

            }

        }
    }
}
