package com.safepayu.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.Navigation;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.models.response.OperatorResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServiceHistoryAdapter extends RecyclerView.Adapter<ServiceHistoryAdapter.MyServiceViewHolder> {

    private Context context;
    private List<OperatorResponse.HistoryBean> mData;
    private View selectOrder = null;
    private int selectOrderPosition = -1;
    private OnSelectListener callback;

    public ServiceHistoryAdapter(Context context, List<OperatorResponse.HistoryBean> mData,OnSelectListener callback) {
        this.context = context;
        this.mData = mData;
        this.callback = callback;

    }

    public interface OnSelectListener {
        void onOrderItemSelect(int position, OperatorResponse.HistoryBean selectOrderItem);
    }

    @NonNull
    @Override
    public ServiceHistoryAdapter.MyServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        // For First Item with Big ImageView , Other are small
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_history_adapter, parent, false);

        return new ServiceHistoryAdapter.MyServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHistoryAdapter.MyServiceViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        if (Navigation.sizeMobileRecharge==0){
            if (mData.size()>3){
                return 3;
            }else {
                return mData.size();
            }

        }else {
            return mData.size();
        }

    }

    public class MyServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvOperatorName, tvAmount,tvNumber,tvDate;
        private ImageView image;
        public MyServiceViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOperatorName = itemView.findViewById(R.id.operatorName_serviceAdapter);
            tvAmount = itemView.findViewById(R.id.price_serviceAdapter);
            tvNumber = itemView.findViewById(R.id.number_serviceAdapter);
            tvDate = itemView.findViewById(R.id.date_serviceAdapter);
            image = itemView.findViewById(R.id.image_serviceAdapter);
            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {

            try {
                tvOperatorName.setText(mData.get(position).getOperator_name());
                tvAmount.setText(context.getResources().getString(R.string.rupees)+ " " + mData.get(position).getAmount());
                tvNumber.setText(mData.get(position).getNumber());
                tvDate.setText(mData.get(position).getDate());

                Picasso.get().load(ApiClient.ImagePath+mData.get(position).getImage()).into(image);
            }catch (Exception e){
                e.printStackTrace();
            }


        }

        @Override
        public void onClick(View v) {
            if (callback != null) {
                callback.onOrderItemSelect(getLayoutPosition(), mData.get(getLayoutPosition()));
            }
        }
    }
}
