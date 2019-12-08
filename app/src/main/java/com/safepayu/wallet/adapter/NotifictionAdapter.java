package com.safepayu.wallet.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.NotificationResponse;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class NotifictionAdapter  extends RecyclerView.Adapter<NotifictionAdapter.NotificationViewHolder> {


     private Context context;
     private List<NotificationResponse.DataBean> mData;


    public NotifictionAdapter(Context context, List<NotificationResponse.DataBean> mData) {
        this.context = context;
        this.mData = mData;

        Collections.reverse( this.mData);
        notifyItemChanged(this.mData.size());
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.notification_adapter,parent,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNotificationTitle, tvNotificationDescription, tvNotificationDate;
        private ImageView imageView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNotificationDescription = itemView.findViewById(R.id.tv_notifiction_description);
            tvNotificationTitle = itemView.findViewById(R.id.tv_notifiction_title);
            tvNotificationDate = itemView.findViewById(R.id.tv_notifiction_time);
            imageView = itemView.findViewById(R.id.image_notification);


        }

        public void bindData(int position) {

            tvNotificationTitle.setText(mData.get(position).getTitle());
            tvNotificationDescription.setText(mData.get(position).getDescription());
            tvNotificationDate.setText(mData.get(position).getUpdated_at());
            if (mData.get(position).getImage()==null){
                imageView.setVisibility(View.GONE);
            }
            else {
                Picasso.get().load((Uri)mData.get(position).getImage()).into(imageView);
            }

           /* Calendar c = Calendar.getInstance();
            System.out.println("Current time => "+c.getTime());

            java.text.SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());
            // formattedDate have current date/time
            Toast.makeText(context, formattedDate, Toast.LENGTH_SHORT).show();

            long difference =formattedDate  - date;
            days = (int) (difference / (1000*60*60*24));
            hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
            min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
            hours = (hours < 0 ? -hours : hours);
            //Log.i("======= Hours"," :: "+hours);*/
        }
    }
}
