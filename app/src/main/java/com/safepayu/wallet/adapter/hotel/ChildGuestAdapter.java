package com.safepayu.wallet.adapter.hotel;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

import java.util.ArrayList;

public class ChildGuestAdapter extends RecyclerView.Adapter<ChildGuestAdapter.FlightLocationListViewHolder> {

    private Context context ;
    public static ArrayList<String> ChildAgeList=new ArrayList<>();;
    int childNo=0,ParentPos=0;
    String firstAge="",secondAge="";

    public ChildGuestAdapter(Context context, int childNo1, int ParentPos1) {
        this.context = context;
        this.childNo = childNo1;
        this.ParentPos=ParentPos1;

        for (int i=0;i<childNo1;i++){
            ChildAgeList.add("");
        }
    }

    @NonNull
    @Override
    public ChildGuestAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.guest_child_adapter,parent,false);
        return new ChildGuestAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildGuestAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return childNo;
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private EditText AgeED; ;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);
            AgeED = itemView.findViewById(R.id.age_childAdapter);

            itemView.setOnClickListener(this);
        }

        public void bindData(final int position) {
            String Age="";

            Age=AgeED.getText().toString().trim();

            if (TextUtils.isEmpty(Age)){
                if (position==0){
                    firstAge="-"+"0"+"~";
                }else {
                    secondAge="-"+"0"+"~";
                }

                String AgeBoth=firstAge+secondAge;

                try{
                    ChildAgeList.set(ParentPos,AgeBoth);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                if (position==0){
                    firstAge="-"+Age+"~";
                }else {
                    secondAge="-"+Age+"~";
                }

                String AgeBoth=firstAge+secondAge;

                try{
                    ChildAgeList.set(ParentPos,AgeBoth);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            AgeED.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    // TODO Auto-generated method stub
                    String AgeBoth="";
                    try {
                        if (s.toString().length()>0){
                            if (Integer.parseInt(s.toString().trim())<13){
                                if (position==0){
                                    firstAge=s.toString();
                                }else {
                                    secondAge=s.toString() ;
                                }

                                if (TextUtils.isEmpty(secondAge)){
                                    AgeBoth=firstAge;
                                }else {
                                    AgeBoth=firstAge+"~"+secondAge;
                                }

                                try{
                                    ChildAgeList.set(ParentPos,AgeBoth);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }else {
                                AgeED.setError("Age Limit Is 12 For Child");
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
        public void onClick(View v) {

        }
    }
}


