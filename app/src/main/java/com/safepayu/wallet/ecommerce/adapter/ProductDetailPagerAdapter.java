package com.safepayu.wallet.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.safepayu.wallet.R;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.ecommerce.api.ApiClientEcom;
import com.safepayu.wallet.ecommerce.model.response.ProductsDetailsResponse;
import com.safepayu.wallet.models.response.PromotionResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDetailPagerAdapter extends PagerAdapter {
    // Declare Variables
    private Context context;
    private List<String> simage;
  //  private List<ProductsDetailsResponse.ProductsBean>simage;
    private LayoutInflater inflater;

    public ProductDetailPagerAdapter(Context context, List<String> simage) {
        this.context = context;
        this.simage = simage;
    }

   /* public ProductDetailPagerAdapter(Context context, List<ProductsDetailsResponse.ProductsBean> simage) {
        this.context = context;
        this.simage = simage;
    }
*/


    @Override
    public int getCount() {
        return simage.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        // Declare Variables
        ImageView image_pager;
        View itemView = LayoutInflater.from(context).inflate(R.layout.offer_pager_adapter,container,false);

        image_pager= (ImageView) itemView.findViewById(R.id.im);
        Picasso.get()
                .load(ApiClientEcom.ImagePath+ simage.get(position))
                .error(context.getResources().getDrawable(R.drawable.image_not_available))
                .into(image_pager);


        // add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);
        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
