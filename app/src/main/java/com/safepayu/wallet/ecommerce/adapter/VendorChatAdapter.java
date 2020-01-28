package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.model.response.VendorChatResponse;

import java.util.List;

public class VendorChatAdapter extends RecyclerView.Adapter<VendorChatAdapter.ChatListViewHolder> {

    private Context context ;
    private List<VendorChatResponse.DataBean> data;
    public static ImageView statusWaitingImageVendor;

    public VendorChatAdapter(Context context, List<VendorChatResponse.DataBean> data1) {
        this.context = context;
        this.data=data1;
    }

    @NonNull
    @Override
    public VendorChatAdapter.ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vendor_chat_adapter,parent,false);
        return new VendorChatAdapter.ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorChatAdapter.ChatListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvChatMessageUser,tvChatMessageAdmin ;
        private LinearLayout userChatLinearLayout;

        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvChatMessageUser=itemView.findViewById(R.id.messageUser_chatAdapter);
            tvChatMessageAdmin=itemView.findViewById(R.id.messageAdmin_chatAdapter);
            statusWaitingImageVendor=itemView.findViewById(R.id.clockChat_chatAdapter);
            userChatLinearLayout=itemView.findViewById(R.id.userChatLinearLayout);
        }

        public void bindData(final int position) {

            try {
                if (data.get(position).getFrom().equals("venue")){
                    tvChatMessageAdmin.setText(data.get(position).getMessage());
                    tvChatMessageAdmin.setVisibility(View.VISIBLE);
                    tvChatMessageUser.setVisibility(View.GONE);
                    statusWaitingImageVendor.setVisibility(View.GONE);
                    userChatLinearLayout.setVisibility(View.GONE);
                }else {
                    userChatLinearLayout.setVisibility(View.VISIBLE);
                    tvChatMessageUser.setText(data.get(position).getMessage());
                    tvChatMessageUser.setVisibility(View.VISIBLE);
                    tvChatMessageAdmin.setVisibility(View.GONE);
                    statusWaitingImageVendor.setVisibility(View.VISIBLE);
                    statusWaitingImageVendor.setImageDrawable(context.getResources().getDrawable(R.drawable.tick_chat_new));

                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}

