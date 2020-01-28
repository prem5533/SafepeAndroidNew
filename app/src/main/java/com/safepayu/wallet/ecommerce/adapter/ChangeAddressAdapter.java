package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.model.response.AddressUserResponse;

import java.util.List;

public class ChangeAddressAdapter extends RecyclerView.Adapter<ChangeAddressAdapter.ProductViewHolder> {


    private Context context;
    private List<AddressUserResponse.AddressessBean> addressessBeans;
    private onEditAddress editAddressItem;
    private int row_index = -1;
    String AddressPOS ;


    public interface onEditAddress{
        void onEditAddressItem(int position,AddressUserResponse.AddressessBean addressessBean);
        void onRemoveAddressItem(int position,AddressUserResponse.AddressessBean addressessBean);
        void onCheckedAddressItem(int position,AddressUserResponse.AddressessBean addressessBean);
    }

    public ChangeAddressAdapter(Context context, List<AddressUserResponse.AddressessBean> addressessBeans, onEditAddress editAddressItem) {
        this.context = context;
        this.addressessBeans = addressessBeans;
        this.editAddressItem = editAddressItem;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chnge_address_adapter,parent,false);
        AddressPOS =   BaseApp.getInstance().sharedPref().getString(BaseApp.getInstance().sharedPref().ADDRESS_POS);
        return new ChangeAddressAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return addressessBeans.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvAddddress,tvmobile,tvPincode,tvAddress,tvCountry,tvType,tvCity,tvEditBtn,tvRemoveBtn,tvstate;
        private CheckBox cbAddressChange;
        private LinearLayout litab;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddddress = itemView.findViewById(R.id.tv_username_addddress);
            tvmobile = itemView.findViewById(R.id.tvmobile_addaddress);
            tvPincode = itemView.findViewById(R.id.tvpincode_addaddress);
            tvAddress = itemView.findViewById(R.id.tvAddress_addaddress);
            tvCountry = itemView.findViewById(R.id.tvcountry_addaddress);
            tvCity = itemView.findViewById(R.id.tvcity_addaddress);
            tvEditBtn = itemView.findViewById(R.id.tvedit_change);
            tvRemoveBtn = itemView.findViewById(R.id.tvchange_remove);
            cbAddressChange = itemView.findViewById(R.id.cb_address_change);
            tvstate= itemView.findViewById(R.id.tvstate_addaddress);

            litab = itemView.findViewById(R.id.litab);

            tvType = itemView.findViewById(R.id.tv_type);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show();
                }
            });
            tvEditBtn.setOnClickListener(this);
            tvRemoveBtn.setOnClickListener(this);


        }

        public void bindData(final int position) {
            tvAddddress.setText(addressessBeans.get(position).getName());
            tvmobile.setText(addressessBeans.get(position).getMobile());
            tvPincode.setText(addressessBeans.get(position).getPincode());
            tvAddress.setText(addressessBeans.get(position).getArea());
            tvCountry.setText(", "+addressessBeans.get(position).getCountry());
            tvType.setText(addressessBeans.get(position).getType());
            tvCity.setText(addressessBeans.get(position).getCity());
            tvstate.setText(addressessBeans.get(position).getState());


            cbAddressChange.setChecked(position==row_index);

            if (position==row_index){
                litab.setVisibility(View.VISIBLE);

            }
            else {
                litab.setVisibility(View.GONE);
            }
            cbAddressChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == row_index) {

                        litab.setVisibility(View.GONE);
                        cbAddressChange.setChecked(false);


                        //XU   row_index = -1;
                    } else {
                        if (editAddressItem != null) {
                            editAddressItem.onCheckedAddressItem(getLayoutPosition(),addressessBeans.get(getLayoutPosition()));
                        }
                        row_index = position;
                        litab.setVisibility(View.VISIBLE);
                       notifyDataSetChanged();
                    }
                }
            });

            if (String.valueOf(position).equals(AddressPOS)){
                cbAddressChange.setChecked(true);
                litab.setVisibility(View.VISIBLE);
            }
            AddressPOS =" ";

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tvedit_change:
                    if (editAddressItem != null) {
                        editAddressItem.onEditAddressItem(getLayoutPosition(),addressessBeans.get(getLayoutPosition()));
                    }
                    break;
                case R.id.tvchange_remove:
                    if (editAddressItem != null) {
                        editAddressItem.onRemoveAddressItem(getLayoutPosition(),addressessBeans.get(getLayoutPosition()));
                    }
                    break;



            }
        }
    }
}
