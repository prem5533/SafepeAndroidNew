package com.safepayu.wallet.adapter.bus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

import java.util.ArrayList;

public class FillDetailAdapter extends RecyclerView.Adapter<FillDetailAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private FillDetailAdapter.FillDetailListener fillDetailListener;
    private int size=0;
    private String EmailText="";
    private ArrayList<String> NameList,AgeList,GenderList;

    public  interface  FillDetailListener {
        void onLocationClickTo (int position);
    }

    public FillDetailAdapter(Context context,  int size1, FillDetailAdapter.FillDetailListener boardingListListener) {
        this.context = context;
        this.size=size1;
        this.fillDetailListener=boardingListListener;

        NameList=new ArrayList<>();
        AgeList=new ArrayList<>();
        GenderList=new ArrayList<>();
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
        private RadioGroup radioGroup;
        private EditText edName,edAge,edEmail;
        private RelativeLayout rlEmail;
        public FlightLocationListViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvPassengerNo = itemView.findViewById(R.id.passengerNo_fillDetailAdapter);
            edName = itemView.findViewById(R.id.name_fillDetailAdapter);
            edAge = itemView.findViewById(R.id.age_fillDetailAdapter);
            edEmail = itemView.findViewById(R.id.email_fillDetailAdapter);
            radioGroup = itemView.findViewById(R.id.radioGrp_fillDetailAdapter);
            rlEmail = itemView.findViewById(R.id.emailLayout_fillDetailAdapter);

            itemView.setOnClickListener(this);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    RadioButton rb=itemView.findViewById(checkedId);
                    Toast.makeText(context, rb.getText(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        public void bindData(int position) {
            tvPassengerNo.setText("Passenger "+(position+1));
            if (position==1){
                rlEmail.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {

            if (fillDetailListener != null) {
                fillDetailListener.onLocationClickTo(getLayoutPosition());

            }

        }


    }
}
