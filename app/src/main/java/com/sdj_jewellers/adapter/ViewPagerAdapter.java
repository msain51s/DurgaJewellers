package com.sdj_jewellers.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.sdj_jewellers.FullImageActivity;
import com.sdj_jewellers.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 6/26/2017.
 */

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;

    ArrayList<String> urlList;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context,  ArrayList<String> urlList) {
        this.context = context;

        this.urlList = urlList;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        // Declare Variables

        ImageView productImage;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, FullImageActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("list",urlList);
                context.startActivity(intent);
            }
        });

        productImage = (ImageView) itemView.findViewById(R.id.product_image);
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

