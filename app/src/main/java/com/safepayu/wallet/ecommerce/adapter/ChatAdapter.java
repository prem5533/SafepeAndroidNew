package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.ecommerce.model.response.ChatListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.safepayu.wallet.activity.ChatActivity.bitmapChat;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatListViewHolder> {

    private Context context ;
    private List<ChatListResponse.DataBean> data;
    public static ImageView statusWaitingImage,attachmentImage;

    public ChatAdapter(Context context, List<ChatListResponse.DataBean> data1) {
        this.context = context;
        this.data=data1;
    }

    @NonNull
    @Override
    public ChatAdapter.ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_adapter,parent,false);
        return new ChatAdapter.ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatListViewHolder holder, int position) {
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
        private TextView tvChatMessageUser,tvChatMessageAdmin,tvChatTitle ;
        private LinearLayout userChatLinearLayout;
        
        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvChatMessageUser=itemView.findViewById(R.id.messageUser_chatAdapter);
            tvChatMessageAdmin=itemView.findViewById(R.id.messageAdmin_chatAdapter);
            tvChatTitle=itemView.findViewById(R.id.messageTitle_chatAdapter);
            statusWaitingImage=itemView.findViewById(R.id.clockChat_chatAdapter);
            attachmentImage=itemView.findViewById(R.id.attachmentImageChat_chatAdapter);
            userChatLinearLayout=itemView.findViewById(R.id.userChatLinearLayout);
        }

        public void bindData(final int position) {

            try {
                if (data.get(position).getFrom().equals("1")){
                    userChatLinearLayout.setVisibility(View.VISIBLE);
                    String type=data.get(position).getMessage();

                    if (type.equalsIgnoreCase("Image") || type.contains(".png") || type.contains(".jpg") || type.contains(".jpeg")){
                        if (type.equalsIgnoreCase("Image")){
                            attachmentImage.setImageBitmap(bitmapChat);
                        }else {
                            try {
                                if (TextUtils.isEmpty(type)){
                                    attachmentImage.setImageDrawable(context.getResources().getDrawable(R.drawable.image_not_available));
                                }else {
                                    Picasso.get()
                                            .load(ApiClient.ImagePath + type)
                                            .error(context.getResources().getDrawable(R.drawable.image_not_available))
                                            .into(attachmentImage);
                                }
                            } catch (Exception er) {
                                er.printStackTrace();
                            }
                        }
                        attachmentImage.setVisibility(View.VISIBLE);
                        tvChatMessageUser.setVisibility(View.GONE);
                    }else {
                        tvChatMessageUser.setText(data.get(position).getMessage());
                        tvChatMessageUser.setVisibility(View.VISIBLE);
                        attachmentImage.setVisibility(View.GONE);
                    }

                    tvChatMessageAdmin.setVisibility(View.GONE);
                    statusWaitingImage.setVisibility(View.VISIBLE);
                    statusWaitingImage.setImageDrawable(context.getResources().getDrawable(R.drawable.tick_chat_new));
                }else {
                    tvChatMessageAdmin.setText(data.get(position).getMessage());
                    tvChatMessageAdmin.setVisibility(View.VISIBLE);
                    tvChatMessageUser.setVisibility(View.GONE);
                    statusWaitingImage.setVisibility(View.GONE);
                    userChatLinearLayout.setVisibility(View.GONE);
                    attachmentImage.setVisibility(View.GONE);
                }

                try{
                    if (data.get(position).getTitle().equalsIgnoreCase(data.get(position+1).getTitle())){
                        tvChatTitle.setVisibility(View.GONE);
                    }else {
                        tvChatTitle.setText(data.get(position).getTitle());
                        tvChatTitle.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}

