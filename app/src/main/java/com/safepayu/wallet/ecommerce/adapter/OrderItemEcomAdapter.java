package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.model.response.OrderDetailResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderItemEcomAdapter extends RecyclerView.Adapter<OrderItemEcomAdapter.ProductViewHolder> {


    private Context context;
    private List<OrderDetailResponse.OrderListBean.ProductsBean> getProducts;
    private CancelOrderAgainListener cancelOrderAgainListener;

    public interface CancelOrderAgainListener{
        void cancelOrderAgainItem(int position, String Module,OrderDetailResponse.OrderListBean.ProductsBean productsBean );
    }

    public OrderItemEcomAdapter(Context context,List<OrderDetailResponse.OrderListBean.ProductsBean> getProducts,
                                CancelOrderAgainListener cancelOrderAgainListener) {
        this.context = context;
        this.getProducts=getProducts;
        this.cancelOrderAgainListener=cancelOrderAgainListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item_ecom_adapter,parent,false);
        return new OrderItemEcomAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        /*status=>Status of order
            0	Initial
            1	Reject
            2	Order Pack
            3	Dispatch
            4	Delivered
            5	Refund
            6	Exchange
            7	Refund_Exchange
            8	Pre Rejected
            9	Pre Refund*/

        if (getProducts.get(position).getStatus()==0){
            holder.tvProductStatus.setText("Initial");
        }else if (getProducts.get(position).getStatus()==1){
            holder.tvProductStatus.setText("Reject");
        }else if (getProducts.get(position).getStatus()==2){
            holder.tvProductStatus.setText("Order Pack");
        }else if (getProducts.get(position).getStatus()==3){
            holder.tvProductStatus.setText("Dispatch");
        }else if (getProducts.get(position).getStatus()==4){
            holder.tvProductStatus.setText("Delivered");
            holder.cancelBtn.setText("Return");
        }else if (getProducts.get(position).getStatus()==5){
            holder.tvProductStatus.setText("Refund");
        }else if (getProducts.get(position).getStatus()==6){
            holder.tvProductStatus.setText("Exchange");
        }else if (getProducts.get(position).getStatus()==7){
            holder.tvProductStatus.setText("Refund_Exchange");
        }else if (getProducts.get(position).getStatus()==8){
            holder.tvProductStatus.setText("Pre Rejected");
        }else if (getProducts.get(position).getStatus()==9){
            holder.tvProductStatus.setText("Pre Refund");
        }

        holder.tvProductName.setText(getProducts.get(position).getProduct_name());
        holder.tvProductQqty.setText(""+getProducts.get(position).getProduct_qty());
        holder.tvProductPrice.setText(context.getResources().getString(R.string.rupees)+" "+getProducts.get(position).getNet_amount());

        try {
            if (TextUtils.isEmpty(getProducts.get(position).getProduct_image())) {

            } else {
                Picasso.get()
                        .load(ApiClientEcom.ImagePath + getProducts.get(position).getProduct_image())
                        .error(context.getResources().getDrawable(R.drawable.image_not_available))
                        .into(holder.imageViewProduct);
            }
        } catch (Exception er) {
            er.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return getProducts.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView tvProductPrice,tvProductName,tvProductQqty,tvProductStatus;
        private ImageView imageViewProduct;
        private Button cancelBtn,orderAgainBtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewProduct=itemView.findViewById(R.id.productImage_orderDetailAdapter);
            tvProductPrice=itemView.findViewById(R.id.productPrice_orderDetailAdapter);
            tvProductName=itemView.findViewById(R.id.productName_orderDetailAdapter);
            tvProductQqty=itemView.findViewById(R.id.productQuantity_orderDetailAdapter);
            tvProductStatus=itemView.findViewById(R.id.productStatus_orderDetailAdapter);
            cancelBtn=itemView.findViewById(R.id.cancelReturn_orderDetailAdapter);
            orderAgainBtn=itemView.findViewById(R.id.orderAgain_orderDetailAdapter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  /*  if (orderEcomListener != null) {
                        orderEcomListener.orderItem(getLayoutPosition()); }*/
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cancelOrderAgainListener!=null){
                        if (cancelBtn.getText().toString().equalsIgnoreCase("Cancel")){
                            cancelOrderAgainListener.cancelOrderAgainItem(getLayoutPosition(),"Cancel",getProducts.get(getLayoutPosition()));
                        }else {
                            cancelOrderAgainListener.cancelOrderAgainItem(getLayoutPosition(),"Return",getProducts.get(getLayoutPosition()));
                        }
                    }
                }
            });

            orderAgainBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cancelOrderAgainListener!=null){
                        cancelOrderAgainListener.cancelOrderAgainItem(getLayoutPosition(),"Order Again",getProducts.get(getLayoutPosition()));
                    }
                }
            });
        }
    }
}
