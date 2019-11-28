package com.safepayu.wallet.adapter;

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
import com.safepayu.wallet.models.response.PromotionResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OfferPagerAdapter  extends PagerAdapter {
    // Declare Variables
    private Context context;
    private List<PromotionResponse.DataBean> simage;
    private LayoutInflater inflater;

    public OfferPagerAdapter(Context context, List<PromotionResponse.DataBean> simage) {
        this.context = context;
        this.simage = simage;
    }


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
        Picasso.get().load("http://india.safepayu.com/safepe-new/public/"+ simage.get(position).getImage()).into(image_pager);


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
