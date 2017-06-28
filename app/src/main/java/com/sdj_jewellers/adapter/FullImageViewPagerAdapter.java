package com.sdj_jewellers.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.sdj_jewellers.FullImageActivity;
import com.sdj_jewellers.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 6/26/2017.
 */

public class FullImageViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;

    ArrayList<String> urlList;
    LayoutInflater inflater;

    public FullImageViewPagerAdapter(Context context,  ArrayList<String> urlList) {
        this.context = context;

        this.urlList = urlList;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        // Declare Variables

        ImageView productImage;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.full_image_pager_item, container,
                false);

        productImage = (ImageView) itemView.findViewById(R.id.product_image);
        productImage.setOnTouchListener((FullImageActivity)context);
        // Capture position and set to the ImageView

        Glide.with(context)
                .load(urlList.get(position))
                .into(productImage);


        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((FrameLayout) object);

    }

}


