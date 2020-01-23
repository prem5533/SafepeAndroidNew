package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;
import com.safepayu.wallet.dialogs.LoadingDialog;
import com.safepayu.wallet.ecommerce.activity.ReviewProduct;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.model.request.ReviewRequest;
import com.safepayu.wallet.ecommerce.model.response.MyOrderListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyOrderEcomAdapter extends RecyclerView.Adapter<MyOrderEcomAdapter.ProductViewHolder> {


     private Context context;
     private OrderEcomListener orderEcomListener;
     private List<MyOrderListResponse.OrderListBean.ProductsBean> productList;
     private LoadingDialog loadingDialog;
     private RatingListener ratingListener;

    public interface OrderEcomListener{
        void orderItem(int position, String Order_id );
    }

    public interface RatingListener{
        void ratingItem( int position,MyOrderListResponse.OrderListBean.ProductsBean productsBean,ReviewRequest reviewRequest );
    }

    public MyOrderEcomAdapter(Context context,List<MyOrderListResponse.OrderListBean.ProductsBean> productList,
                              OrderEcomListener orderEcomListener,RatingListener ratingListener) {
        this.context = context;
        this.orderEcomListener = orderEcomListener;
        this.productList=productList;
        this.ratingListener=ratingListener;

        loadingDialog=new LoadingDialog(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_ecom_adapter,parent,false);
        return new MyOrderEcomAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.tvOrderNo.setText(productList.get(position).getUnique_code());
        holder.tvProductName.setText(productList.get(position).getProduct_name());
        holder.tvProductQuantity.setText(productList.get(position).getProduct_qty()+"");
        holder.tvProductOfferPrice.setText(context.getResources().getString(R.string.rupees)+" "+productList.get(position).getNet_amount());
        holder.tvProductStatus.setText(productList.get(position).getOrder_status());

        try {
            if (TextUtils.isEmpty(productList.get(position).getProduct_image())) {

            } else {
                Picasso.get()
                        .load(ApiClientEcom.ImagePath + productList.get(position).getProduct_image())
                        .error(context.getResources().getDrawable(R.drawable.image_not_available))
                        .into(holder.productImage);
            }
        } catch (Exception er) {
            er.printStackTrace();
        }

        try {
            holder.ratingBarReview.setRating(Float.parseFloat(productList.get(position).getRattings()));
            holder.ratingBar.setRating(Float.parseFloat(productList.get(position).getRattings()));
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            if (Float.parseFloat(productList.get(position).getRattings())==0){
                holder.linearLayoutReview.setVisibility(View.GONE);
                holder.ratingBar.setVisibility(View.VISIBLE);
            }else {
                if (TextUtils.isEmpty(productList.get(position).getReview())){
                    holder.ratingBar.setVisibility(View.GONE);
                    holder.linearLayoutReview.setVisibility(View.VISIBLE);
                }else {
                    holder.linearLayoutReview.setVisibility(View.GONE);
                    holder.ratingBar.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView tvOrderDetailBtn,tvProductName,tvProductQuantity,tvProductOfferPrice,tvProductStatus;
        private TextView tvOrderNo,ReviewBtn;
        private ImageView productImage;
        private LinearLayout linearLayoutReview;
        private RatingBar ratingBar,ratingBarReview;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderDetailBtn = itemView.findViewById(R.id.orderDetail_orderListAdapter);
            tvProductName= itemView.findViewById(R.id.productName_orderListAdapter);
            tvProductQuantity= itemView.findViewById(R.id.productquantity_orderListAdapter);
            tvProductOfferPrice= itemView.findViewById(R.id.productOfferPrice_orderListAdapter);
            tvProductStatus= itemView.findViewById(R.id.orderStatus_orderListAdapter);
            tvOrderNo = itemView.findViewById(R.id.orderNo_orderListAdapter);
            productImage= itemView.findViewById(R.id.image_orderListAdapter);
            ratingBar= itemView.findViewById(R.id.productRating_orderListAdapter);
            ratingBarReview= itemView.findViewById(R.id.productRatingReview_orderListAdapter);
            ReviewBtn= itemView.findViewById(R.id.productReviewBtn_orderListAdapter);
            linearLayoutReview= itemView.findViewById(R.id.RatingReviewLayout_orderListAdapter);

            tvOrderDetailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (orderEcomListener != null) {
                        orderEcomListener.orderItem(getLayoutPosition(),String.valueOf(productList.get(getLayoutPosition()).getOrder_id()));
                    }
                }
            });

            ReviewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,ReviewProduct.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Order_details_id",String.valueOf(productList.get(getLayoutPosition()).getOrder_details_id()));
                    intent.putExtra("Order_id",String.valueOf(productList.get(getLayoutPosition()).getOrder_id()));
                    intent.putExtra("Rating",String.valueOf(productList.get(getLayoutPosition()).getRattings()));
                    intent.putExtra("Product_name",String.valueOf(productList.get(getLayoutPosition()).getProduct_name()));
                    intent.putExtra("Product_image",String.valueOf(productList.get(getLayoutPosition()).getProduct_image()));
                    context.startActivity(intent);
                }
            });

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating,
                                            boolean fromUser) {

                    if (Float.parseFloat(productList.get(getLayoutPosition()).getRattings())==0){
                        if (rating!=0){
                            ReviewRequest reviewRequest=new ReviewRequest();

                            reviewRequest.setOrder_detail_id(String.valueOf(productList.get(getLayoutPosition()).getOrder_details_id()));
                            reviewRequest.setOrder_id(String.valueOf(productList.get(getLayoutPosition()).getOrder_id()));
                            reviewRequest.setRating(String.valueOf(rating));
                            reviewRequest.setReview("");

                            if (ratingListener!=null){
                                ratingListener.ratingItem(getLayoutPosition(),productList.get(getLayoutPosition()),reviewRequest);
                            }
                        }
                    }
                }
            });

        }
    }
}
