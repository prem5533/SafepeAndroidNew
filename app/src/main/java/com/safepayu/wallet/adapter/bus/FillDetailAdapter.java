package com.safepayu.wallet.adapter.bus;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;

import java.util.ArrayList;

public class FillDetailAdapter extends RecyclerView.Adapter<FillDetailAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private FillDetailAdapter.FillDetailListener fillDetailListener;
    private int size=0;
    private String EmailText="";
    private ArrayList<String> NameList,AgeList,GenderList;
    private String CardType="",CardId="";

    public  interface  FillDetailListener {
        void onFillDetailClickTo (int position,ArrayList<String> NameList,ArrayList<String>  AgeList,ArrayList<String>  GenderList,
                                String EmailTexT,String CardType,String CardId);
    }

    public FillDetailAdapter(Context context,  int size1, FillDetailAdapter.FillDetailListener boardingListListener) {
        this.context = context;
        this.size=size1;
        this.fillDetailListener=boardingListListener;

        NameList=new ArrayList<>();
        AgeList=new ArrayList<>();
        GenderList=new ArrayList<>();

        for (int k=0;k<size;k++){
            NameList.add("");
            AgeList.add("");
            GenderList.add("");
        }
    }

    @NonNull
    @Override
    public FillDetailAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_fill_detail_adapter,parent,false);
        return new FillDetailAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FillDetailAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvPassengerNo ;
        private RadioGroup radioGroup,radioGroupCard;
        private EditText edName,edAge,edEmail,edCardNumber;
        private RelativeLayout rlEmail,rlCardType,rlCardNumber;
        private Button ProceedBtn;
        public FlightLocationListViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvPassengerNo = itemView.findViewById(R.id.passengerNo_fillDetailAdapter);
            edName = itemView.findViewById(R.id.name_fillDetailAdapter);
            edAge = itemView.findViewById(R.id.age_fillDetailAdapter);
            edEmail = itemView.findViewById(R.id.email_fillDetailAdapter);
            edCardNumber = itemView.findViewById(R.id.cardId_fillDetailAdapter);
            radioGroup = itemView.findViewById(R.id.radioGrp_fillDetailAdapter);
            radioGroupCard= itemView.findViewById(R.id.radioGrpCard_fillDetailAdapter);
            rlEmail = itemView.findViewById(R.id.emailLayout_fillDetailAdapter);
            rlCardNumber = itemView.findViewById(R.id.cardNumberLayout);
            rlCardType = itemView.findViewById(R.id.cardLayout);
            ProceedBtn = itemView.findViewById(R.id.proceedBtn_fillDetailAdapter);

            itemView.setOnClickListener(this);
            ProceedBtn.setOnClickListener(this);
        }

        public void bindData(final int position) {
            tvPassengerNo.setText("Passenger "+(position+1));
            if (position==0){
                rlEmail.setVisibility(View.VISIBLE);
                rlCardNumber.setVisibility(View.VISIBLE);
                rlCardType.setVisibility(View.VISIBLE);
            }
            if (position==size-1){
                ProceedBtn.setVisibility(View.VISIBLE);
            }

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    RadioButton rb=itemView.findViewById(checkedId);

                    GenderList.set(position,rb.getText().toString());

                }
            });

            radioGroupCard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    RadioButton rb=itemView.findViewById(checkedId);

                    CardType=rb.getText().toString();
                }
            });

            edName.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    // TODO Auto-generated method stub
                    try {
                        if (s.toString().length()>0){
                            NameList.set(position,s.toString());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {

                    // TODO Auto-generated method stub
                }
            });

            edAge.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    // TODO Auto-generated method stub
                    try {
                        if (s.toString().length()>0){
                            AgeList.set(position,s.toString());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {

                    // TODO Auto-generated method stub
                }
            });

            edEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    // TODO Auto-generated method stub
                    try {
                        if (s.toString().length()>0){
                            EmailText=s.toString();
                            if (!BaseApp.getInstance().commonUtils().isValidEmail(EmailText)){
                                Toast.makeText(context, "Please Enter A Valid Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {

                    // TODO Auto-generated method stub
                }
            });

            edCardNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    // TODO Auto-generated method stub
                    try {
                        if (CardType.equalsIgnoreCase("PAN")){
                            if (s.length()<10){

                            }else {
                                CardId=edCardNumber.getText().toString();
                            }
                        }else {
                            if (s.length()<12){

                            }else {
                                CardId=edCardNumber.getText().toString();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {

                    // TODO Auto-generated method stub
                }
            });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.proceedBtn_fillDetailAdapter:
                    if (fillDetailListener != null) {
                        if (TextUtils.isEmpty(CardType)){
                            Toast.makeText(context, "Please Select Card Type", Toast.LENGTH_SHORT).show();
                        }else {
                            if (TextUtils.isEmpty(CardId)){
                                Toast.makeText(context, "Please Enter Card Id", Toast.LENGTH_SHORT).show();
                            }else {
                                fillDetailListener.onFillDetailClickTo(getLayoutPosition(),NameList,AgeList,GenderList,EmailText,
                                        CardType, CardId);
                            }
                        }

                    }
                    break;
            }

        }


    }
}
