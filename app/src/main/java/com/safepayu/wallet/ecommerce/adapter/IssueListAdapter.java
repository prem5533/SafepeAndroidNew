package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.model.response.IssueListResponse;

import java.util.List;

public class IssueListAdapter extends RecyclerView.Adapter<IssueListAdapter.IssueListViewHolder> {

    private Context context ;
    private List<IssueListResponse.DataBean> getData;
    private  IssueDetailListner issueDetailListner;

    public IssueListAdapter(Context context, List<IssueListResponse.DataBean> getData1,IssueDetailListner issueDetailListner) {
        this.context = context;
        this.getData=getData1;
        this.issueDetailListner=issueDetailListner;
    }

    public interface IssueDetailListner{
        void IssueDetail(int position,IssueListResponse.DataBean dataBean);
    }

    @NonNull
    @Override
    public IssueListAdapter.IssueListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.issue_list_adapter,parent,false);
        return new IssueListAdapter.IssueListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueListAdapter.IssueListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return getData.size();
    }

    public class IssueListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvVenueName,tvOrderId,tvMessage ;
        public IssueListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvVenueName=itemView.findViewById(R.id.venueNameIssueAdapter);
            tvOrderId=itemView.findViewById(R.id.OrderIdIssueAdapter);
            tvMessage=itemView.findViewById(R.id.messageIssueAdapter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (issueDetailListner!=null){
                        issueDetailListner.IssueDetail(getLayoutPosition(),getData.get(getLayoutPosition()));
                    }
                }
            });
        }

        public void bindData(int position) {
            tvVenueName.setText(getData.get(position).getVenue_name());
            tvOrderId.setText(getData.get(position).getUnique_code());
            tvMessage.setText(getData.get(position).getMessage());
        }
    }
}