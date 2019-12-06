package com.safepayu.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.models.response.OperatorResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflter;
    List<OperatorResponse.OperatorsBean> mOperList = new ArrayList<>();


    public SpinnerAdapter(Context context, List<OperatorResponse.OperatorsBean> mOperList) {
        this.context = context;
        this.mOperList = mOperList;
    }

    @Override
    public int getCount() {
        return mOperList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.spinner_custom_layout, null);
        ImageView icon = (ImageView) convertView.findViewById(R.id.imageView);
        TextView names = (TextView) convertView.findViewById(R.id.textView);
        names.setText(mOperList.get(position).getOperator_name());
        Picasso.get().load(ApiClient.ImagePath+mOperList.get(position).getImage()).into(icon);
        return convertView;
    }
}
