package com.safepayu.wallet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.activity.BuyMemberShip;

import java.util.ArrayList;

public class PackageAdapterForWalletNew extends RecyclerView.Adapter<PackageAdapterForWalletNew.MyViewHolder> {

    Context mContext;
    private OnPackageSelectListener callback;
    private ArrayList<String> PackageNameList,PackageIdList;
    private ArrayList<Double> PackageAmountList;
    String TaxPer;
    private int selectedPackagePosition = -1;
    private View selectedPackageView = null;

    public interface OnPackageSelectListener {
        void onPackageSelectNew(int position, ArrayList<String> PackageNameList1,ArrayList<String> PackageIdList1,
                             ArrayList<Double> PackageAmountList1,String TaxPer1);
    }
    public PackageAdapterForWalletNew(Context context,ArrayList<String> PackageNameList1,ArrayList<String> PackageIdList1,
                                        ArrayList<Double> PackageAmountList1,String TaxPer1,OnPackageSelectListener callback1) {

        this.mContext = context;
        this.callback =callback1;
        this.PackageNameList =PackageNameList1;
        this.PackageIdList =PackageIdList1;
        this.PackageAmountList =PackageAmountList1;
        this.TaxPer =TaxPer1;
    }

    @Override
    public PackageAdapterForWalletNew.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.packages_item_view, parent, false);

        return new PackageAdapterForWalletNew.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PackageAdapterForWalletNew.MyViewHolder holder, final int position) {



        if (PackageNameList.get(position).equalsIgnoreCase("more")){
            holder.packageName.setText("More...");
            holder.packageName.setTextColor(mContext.getResources().getColor(R.color.bue_A800));
            holder.packageName.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            holder.packageName.setGravity(Gravity.CENTER);


        }else {
            holder.packageName.setText(PackageNameList.get(position));
            holder.packageAmount.setText(mContext.getResources().getString(R.string.currency) + BaseApp.getInstance().commonUtils().decimalFormat(PackageAmountList.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return PackageNameList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView packageName, packageAmount;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            packageName = itemView.findViewById(R.id.tv_packageName);
            packageAmount = itemView.findViewById(R.id.tv_packageAmount);
            cardView = itemView.findViewById(R.id.packageItem);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try{
                if (selectedPackageView == null) {
                    selectedPackagePosition = getLayoutPosition();
                    selectedPackageView = itemView;
                    itemView.setBackground(mContext.getResources().getDrawable(R.drawable.package_selected));
                } else {
                    selectedPackageView.setBackground(mContext.getResources().getDrawable(R.drawable.package_normal));
                    selectedPackagePosition = getLayoutPosition();
                    selectedPackageView = itemView;
                    itemView.setBackground(mContext.getResources().getDrawable(R.drawable.package_selected));

                }
            }catch (Exception e){
                e.printStackTrace();
            }
            if (callback != null) {

                if (PackageNameList.get(getLayoutPosition()).equalsIgnoreCase("more")){
                    mContext.startActivity(new Intent(mContext, BuyMemberShip.class));
                    ((Activity)mContext).finish();
                }else {
                    callback.onPackageSelectNew(getLayoutPosition(), PackageNameList,PackageIdList, PackageAmountList, TaxPer);
                }

            }
        }
    }
}
