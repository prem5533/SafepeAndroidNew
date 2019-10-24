package com.safepayu.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.MyOrderResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrderViewHolder> {

    private Context context;
    private final LayoutInflater inflater;
    private List<MyOrderResponse.BankToWalletBean> mData;
    private View selectOrder = null;
    private int selectOrderPosition = -1;
    private MyOrdersAdapter.OnPackageSelectListener callback;

    public MyOrdersAdapter(Context context, List<MyOrderResponse.BankToWalletBean> mData,MyOrdersAdapter.OnPackageSelectListener callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mData = mData;
        this.callback = callback;

    }

    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        // For First Item with Big ImageView , Other are small
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_history_adapter, parent, false);

        return new MyOrdersAdapter.MyOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnPackageSelectListener {
        void onOrderItemSelect(int position, MyOrderResponse.BankToWalletBean selectOrderItem);
    }

    public class MyOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private  TextView tv_walletDescription, AmountTV,tv_opertionType,TimeTV,StatusTV;
        private  ImageView imageViewStatus, image;
        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_walletDescription = itemView.findViewById(R.id.tv_wallet_description);
            AmountTV = itemView.findViewById(R.id.tv_wallet_amount);
            tv_opertionType = itemView.findViewById(R.id.tv_opertion_type);
            TimeTV = itemView.findViewById(R.id.tv_wallet_time_date);
            StatusTV = itemView.findViewById(R.id.tv_wallet_status);
            imageViewStatus = itemView.findViewById(R.id.image_status);
            image = itemView.findViewById(R.id.image_restaurant);
            image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_my_order_money));
            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {

            tv_walletDescription.setText(mData.get(position).getDescription());
            AmountTV.setText(context.getResources().getString(R.string.rupees)+ " " + mData.get(position).getAmount());
            tv_opertionType.setText(mData.get(position).getOpration());
            TimeTV.setText(mData.get(position).getCreated_at());



          if (String.valueOf(mData.get(position).getStatus()).equals("1")){
              StatusTV.setText("Status");
              imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_success));
          }
           else if (String.valueOf(mData.get(position).getStatus()).equals("0")){
                StatusTV.setText("Fail");
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fail));
            }
          else  if (String.valueOf(mData.get(position).getStatus()).equals("2")){
                StatusTV.setText("Pending");
                imageViewStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pending));
            }
           // Picasso.get().load((Uri)mData.get(position).getImage()).into(imageView);


        }

        @Override
        public void onClick(View v) {
            if (selectOrder == null) {
                selectOrderPosition = getLayoutPosition();
                selectOrder = itemView;
            } else {
                selectOrder.setBackground(context.getResources().getDrawable(R.drawable.package_normal));
                selectOrderPosition = getLayoutPosition();
                selectOrder = itemView;

            }
            if (callback != null) {
                callback.onOrderItemSelect(getLayoutPosition(), mData.get(getLayoutPosition()));
            }
        }
    }
}
