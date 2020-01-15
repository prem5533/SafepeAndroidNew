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

public class ProductColorAdapter extends RecyclerView.Adapter<ProductColorAdapter.ProductViewHolder> {


     private Context context;
     private List<String> coloritem;

    public ProductColorAdapter(Context context, List<String> coloritem) {
        this.context = context;
        this.coloritem = coloritem;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.productsize_adapter,parent,false);
        return new ProductColorAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return coloritem.size();
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

        public void bindData(int position) {
            tvSize.setText(coloritem.get(position));
        }
    }
}
