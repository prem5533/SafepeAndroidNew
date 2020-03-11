package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

import java.util.List;

public class ProductValueAdapter extends RecyclerView.Adapter<ProductValueAdapter.ProductViewHolder> {


     private Context context;
     private List<String> sizeitem;
    private int row_index = -1;


    public ProductValueAdapter(Context context, List<String> sizeitem) {
        this.context = context;
        this.sizeitem = sizeitem; }



    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.value_adapter,parent,false);
        return new ProductValueAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return sizeitem.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView tvSize;
        private CheckBox cbSize;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSize = itemView.findViewById( R.id.tv_size);
            cbSize = itemView.findViewById(R.id.cb_prductsize);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bindData(final int position) {
            tvSize.setText(sizeitem.get(position));

            cbSize.setChecked(position==row_index);
            cbSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == row_index) {
                        cbSize.setChecked(false);

                        //XU   row_index = -1;
                    } else {
                        /*if (onBankItemListener != null) {
                            onBankItemListener.onBankItemListerner(getLayoutPosition(),mItem.get(getLayoutPosition()) );

                        }*/
                        row_index = position;
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
