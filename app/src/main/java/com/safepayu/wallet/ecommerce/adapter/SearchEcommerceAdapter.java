package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

import java.util.ArrayList;

public class SearchEcommerceAdapter extends RecyclerView.Adapter<SearchEcommerceAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private SearchListener searchListener;
    private ArrayList<String> SearchList;

    public SearchEcommerceAdapter(Context context,ArrayList<String> SearchList1,SearchListener searchListener) {
        this.context = context;
        this.searchListener=searchListener;
        this.SearchList=SearchList1;
    }

    public  interface  SearchListener {
        void onSearchItemClick (int position,String Text);
    }

    @NonNull
    @Override
    public SearchEcommerceAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_ecommerce_adapter,parent,false);
        return new SearchEcommerceAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchEcommerceAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvSearch ;
        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSearch=itemView.findViewById(R.id.searchTv_searchEcommAdapter);
            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {
            tvSearch.setText(SearchList.get(position));
        }

        @Override
        public void onClick(View view) {
            if (searchListener!=null){
                searchListener.onSearchItemClick(getLayoutPosition(),SearchList.get(getLayoutPosition()));
            }
        }
    }
}